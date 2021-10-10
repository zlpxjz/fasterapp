package com.fasterapp.modules.excel.services;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Tony on 2021/9/14.
 */
public interface IExcelImportProcess {
	/**
	 *
	 * @param file
	 * @param params
	 * @throws Exception
	 */
	Workbook process(MultipartFile file, Map<String, Object> params) throws Exception;
}
