package com.fasterapp.modules.excel.services;

import com.fasterapp.modules.excel.voes.ExportVo;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by Tony on 2021/9/14.
 */
public interface IExcelExportProcess {
	/**
	 *
	 * @param exportVo
	 * @return
	 * @throws Exception
	 */
	Workbook process(ExportVo exportVo) throws Exception;
}
