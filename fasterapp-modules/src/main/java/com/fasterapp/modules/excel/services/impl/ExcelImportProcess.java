package com.fasterapp.modules.excel.services.impl;

import com.fasterapp.base.AppException;
import com.fasterapp.base.utils.ExcelUtil;
import com.fasterapp.base.utils.FileUtil;
import com.fasterapp.base.utils.JsonUtil;
import com.fasterapp.base.utils.StringUtil;
import com.fasterapp.modules.excel.ImportConfig;
import com.fasterapp.modules.excel.ImportField;
import com.fasterapp.modules.excel.services.IExcelImportProcess;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2021/9/14.
 */
@Slf4j
public abstract class ExcelImportProcess implements IExcelImportProcess {
	@Value("${fasterapp.excel.import.save:false}")
	private String isSave;

	@Value("${fasterapp.excel.import.save.dir:./}")
	private String saveDir;

	@Override
	public final Workbook process(MultipartFile file, Map<String, Object> params) throws Exception {
		InputStream is = this.getInputStream(file);
		Workbook workbook;

		String suffix = FileUtil.getSuffix(file.getOriginalFilename());
		if ("xlsx".equalsIgnoreCase(suffix)) {
			workbook = new XSSFWorkbook(is);
		} else {
			workbook = new HSSFWorkbook(is);
		}
		boolean isSuccess = processWorkbook(workbook, params);
		if(! isSuccess) {
			return workbook;
		}else {
			return null;
		}
	}

	/**
	 *
	 * @param workbook
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected boolean processWorkbook(Workbook workbook, Map<String, Object> params) throws Exception{
		ImportConfig config = this.getImportConfig();
		Sheet sheet = workbook.getSheetAt(config.getSheetIndex());
		return readSheet(sheet, params, config);
	}

	/**
	 *
	 * @param sheet
	 * @param params
	 * @throws Exception
	 */
	protected  boolean readSheet(Sheet sheet, Map<String, Object> params, ImportConfig config) throws Exception{
		boolean isSuccess = true;

		validateSheet(sheet, config);

		int startRowIndex = config.getStartRowIndex(), lastRowIndex = sheet.getLastRowNum();
		int errorIndex = config.getErrorIndex();
		for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			String message = readRow(row, params, config);
			if(StringUtil.isNotNullAndBlank(message)){
				isSuccess = false;
				Cell errorCell = row.createCell(errorIndex);
				errorCell.setCellValue(message);
			}
		}

		return isSuccess;
	}

	/**
	 * 读取Excel行数据
	 * @param row
	 * @return
	 * @throws Exception
	 */
	protected String readRow(Row row, Map<String, Object> params, ImportConfig config) throws Exception{
		Map<String, Object> datas = new HashMap<>();

		StringBuffer messages = new StringBuffer();
		int colIndex;
		List<ImportField> columns = config.getColumns();
		for (ImportField column : columns) {
			colIndex = column.getColIndex();
			Cell cell = row.getCell(colIndex);

			try {
				datas.put(column.getDataKey(), readCell(column, cell));
			}catch(AppException exc){
				messages.append(column.getTitle()).append(exc.getMessage()).append(";");
			}catch(Exception exc){
				log.error("行读取数据异常。Column={}", JsonUtil.toString(column), exc);
				messages.append(column.getTitle()).append("未知异常;");
			}
		}

		try {
			this.processData(datas);
		}catch(Exception exc){
			log.error("数据保存异常。", exc);
			messages.append("未知异常;");
		}

		return messages.toString();
	}

	/**
	 * 读取Excel列数据
	 * @param field
	 * @param cell
	 * @throws Exception
	 */
	protected Object readCell(ImportField field, Cell cell) throws Exception{
		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return null;
		}

		switch (field.getDataType()) {
			case Integer:
				return ExcelUtil.getAsInteger(cell);

			case Double:
				return ExcelUtil.getAsDouble(cell);

			case Date:
				return ExcelUtil.getAsDate(cell);

			default:
				return ExcelUtil.getAsString(cell);
		}
	}

	/**
	 * 获取导入配置信息
	 * @return
	 * @throws Exception
	 */
	protected ImportConfig getImportConfig() throws Exception{
		return null;
	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private InputStream getInputStream(MultipartFile file) throws Exception{
		if("true".equals(isSave)){
			String fileName = FileUtil.save(saveDir, file);
			return new FileInputStream(fileName);
		}else{
			return file.getInputStream();
		}
	}

	/**
	 * 根据配置验证Excel
	 * @param sheet
	 * @param config
	 * @throws Exception
	 */
	private void validateSheet(Sheet sheet, ImportConfig config) throws Exception{
		int titleLine = config.getTitleLine();
		Row titleRow = sheet.getRow(titleLine);
		if(titleRow == null){
			throw new AppException("缺少标题行。");
		}

		if("index".equalsIgnoreCase(config.getReadModel())){
			int maxIndex = 0;
			List<ImportField> columns = config.getColumns();
			for(ImportField column : columns){
				if(column.getIndex() > maxIndex){
					maxIndex = column.getIndex();
				}
			}

			if(maxIndex > titleRow.getLastCellNum()) {
				throw new AppException("配置的列数超出实际导入文件的列数，请检查。");
			}
		} else {
			Map<String, Integer> titles = this.getTitles(sheet, config);
			List<ImportField> columns = config.getColumns();
			for(ImportField column : columns){
				if(! titles.containsKey(column.getTitle())){
					throw new AppException("列【" + column.getTitle() + "】在导入文件中不存在，请检查。");
				}
			}
		}
	}

	/**
	 * 获取Excel的导入标题
	 * @param sheet
	 * @param config
	 * @return
	 */
	private Map<String, Integer> getTitles(Sheet sheet, ImportConfig config){
		Map<String,  Integer> titles = new HashMap<>();

		Row row = sheet.getRow(config.getTitleLine());
		int startIndex = config.getStartColIndex(), cellCount = row.getLastCellNum();
		for(int index = startIndex; index < cellCount; index++){
			titles.put(row.getCell(index).getStringCellValue(), index);
		}

		return titles;
	}

	/**
	 * 处理读取的行数据
	 * @param values
	 * @throws Exception
	 */
	protected abstract void processData(Map values) throws Exception;
}
