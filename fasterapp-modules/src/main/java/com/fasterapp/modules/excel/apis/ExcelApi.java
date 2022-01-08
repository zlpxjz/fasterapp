package com.fasterapp.modules.excel.apis;

import com.fasterapp.base.annotations.ApiNoLog;
import com.fasterapp.base.core.api.ApiResponse;
import com.fasterapp.base.core.api.BaseApi;
import com.fasterapp.base.utils.ApiUtil;
import com.fasterapp.base.utils.StringUtil;
import com.fasterapp.modules.excel.services.IExcelService;
import com.fasterapp.modules.excel.voes.ExportVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony on 2021/9/13.
 */
@RestController
@RequestMapping(path="/v1/excel")
@Slf4j
public class ExcelApi extends BaseApi {
	@Autowired
	private IExcelService excelService;

	/**
	 * Excel导入
	 * @param code
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@PostMapping(path="/import/{code}")
	public void importExcel(@PathVariable("code") String code, @ApiNoLog MultipartHttpServletRequest request, @ApiNoLog HttpServletResponse response){
		MultipartFile file = request.getFile("file");
		if(file == null){
			ApiUtil.response(response, 500, ApiResponse.error("附件不存在。"));
			return;
		}

		Map<String, Object> variables = new HashMap<>();
		String byteType = request.getParameter("byte");
		String name;
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			name = names.nextElement();
			variables.put(name, StringUtil.isNotNullAndBlank(byteType)? StringUtil.encode(request.getParameter(name)) : request.getParameter(name));
		}

		try {
			excelService.importExcel(code, file, response, variables);
		}catch(Exception exc){}
	}

	/**
	 * Excel导出
	 * @param code
	 * @param exportVo
	 * @param response
	 * @throws Exception
	 */
	@PostMapping(path="/export/{code}")
	public void exportExcel(@PathVariable("code") String code, @RequestBody ExportVo exportVo, @ApiNoLog HttpServletResponse response){
		try {
			excelService.exportExcel(code, exportVo, response);
		}catch(Exception exc){}
	}
}
