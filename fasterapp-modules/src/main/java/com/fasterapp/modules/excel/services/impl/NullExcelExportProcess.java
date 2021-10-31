package com.fasterapp.modules.excel.services.impl;

import com.fasterapp.modules.excel.services.IExcelExportProcess;
import com.fasterapp.modules.excel.voes.ExportVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component("NullExcelExportProcess")
public class NullExcelExportProcess implements IExcelExportProcess {
    @Override
    public Workbook process(ExportVo exportVo) throws Exception {
        return null;
    }
}
