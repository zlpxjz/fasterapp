package com.fasterapp.modules.excel.services.impl;

import com.fasterapp.base.core.api.ApiResponse;
import com.fasterapp.base.utils.ApiUtil;
import com.fasterapp.base.utils.StringUtil;
import com.fasterapp.modules.excel.services.IExcelExportProcess;
import com.fasterapp.modules.excel.services.IExcelImportProcess;
import com.fasterapp.modules.excel.services.IExcelService;
import com.fasterapp.modules.excel.voes.ExportVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by Tony on 2021/9/14.
 */
@Service("ExcelService")
@Slf4j
public class ExcelServiceImpl implements IExcelService {
	@Autowired
	private Map<String, IExcelImportProcess> importProcessMap;

	@Autowired
	private Map<String, IExcelExportProcess> exportProcessMap;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importExcel(String code, MultipartFile file, HttpServletResponse response, Map<String, Object> params) throws Exception {
		try {
			IExcelImportProcess importProcess = importProcessMap.get(code);
			if(importProcess == null){
				throw new Exception(StringUtil.format("导入处理器code={0}不存在", code));
			}

			Workbook workbook = importProcess.process(file, params);
			if (workbook != null) {
				response.setStatus(500);
				response.setContentType("application/vnd.ms-excel");
				String inlineType = "attachment";
				response.setHeader("Content-Disposition", inlineType + ";filename=\"" + file.getOriginalFilename() + "\"");
				OutputStream os = response.getOutputStream();
				workbook.write(os);
				os.close();

				throw new Exception();
			} else {
				ApiUtil.response(response, ApiResponse.success("导入成功。"));
			}
		}catch(Exception exc){
			log.error("Excel导入异常。", exc);
			ApiUtil.response(response, ApiResponse.error("导入异常，请检查后再重新导入。"));
			throw exc;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void exportExcel(String code, ExportVo exportVo, HttpServletResponse response) throws Exception {
		try {
			IExcelExportProcess exportProcess = exportProcessMap.get(code);
			if(exportProcess == null){
				throw new Exception(StringUtil.format("导出处理器code={0}不存在", code));
			}

			Workbook workbook = exportProcess.process(exportVo);
			response.setContentType("application/vnd.ms-excel");
			String inlineType = "attachment";
			response.setHeader("Content-Disposition", inlineType + ";filename=\"" + exportVo.getFileName() + "\"");
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.close();
		}catch(Exception exc){
			log.error("Excel导出异常。", exc);
			ApiUtil.response(response, ApiResponse.error("500", "导出异常。"));
			throw exc;
		}
	}
}
