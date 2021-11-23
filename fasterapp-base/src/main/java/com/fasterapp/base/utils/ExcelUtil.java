package com.fasterapp.base.utils;

import com.fasterapp.base.AppException;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Date;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

/**
 * Created by Tony on 2021/9/2.
 */
public class ExcelUtil {
	/**
	 * 获取单元格的整数值
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public static Integer getAsInteger(Cell cell) throws Exception{
		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return null;
		}

		return getAsDecimal(cell).intValue();
	}

	/**
	 * 获取单元格的浮点型
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public static Double getAsDouble(Cell cell) throws Exception{
		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return null;
		}

		return getAsDecimal(cell).doubleValue();
	}

	/**
	 *
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal getAsDecimal(Cell cell) throws Exception{
		int cellType = cell.getCellType();
		switch(cellType){
			case CELL_TYPE_NUMERIC:
				return BigDecimal.valueOf(cell.getNumericCellValue());
			case CELL_TYPE_STRING:
				return new BigDecimal(cell.getStringCellValue().trim());
			default:
				throw new AppException("无效数据格式。");
		}
	}

	/**
	 * 获取单元格的浮点型
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public static Date getAsDate(Cell cell) throws Exception{
		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return null;
		}

		int cellType = cell.getCellType();
		switch(cellType){
			case CELL_TYPE_NUMERIC:
				return cell.getDateCellValue();
			case CELL_TYPE_STRING:
				return DateUtil.parse(cell.getStringCellValue().trim());
			default:
				throw new AppException("无效数据格式。");
		}
	}

	/**
	 * 获取单元格的浮点型
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public static String getAsString(Cell cell) throws Exception{
		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return null;
		}

		return String.valueOf(cell.getRichStringCellValue().getString().trim());
	}
}
