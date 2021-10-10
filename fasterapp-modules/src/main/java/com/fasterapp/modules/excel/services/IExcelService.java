package com.fasterapp.modules.excel.services;

import com.fasterapp.modules.excel.voes.ExportVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Tony on 2021/9/13.
 */
public interface IExcelService {
	/**
	 * 导入Excel
	 * @param code
	 * @param file
	 * @param response
	 * @param params
	 * @throws Exception
	 */
	void importExcel(String code, MultipartFile file, HttpServletResponse response, Map<String, Object> params) throws Exception;

	/**
	 * 导出Excel
	 * @param code
	 * @param exportVo
	 * @param response
	 * @throws Exception
	 */
	void exportExcel(String code, ExportVo exportVo, HttpServletResponse response) throws Exception;
}
