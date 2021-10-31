package com.fasterapp.modules.excel.services.impl;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("NullExcelImportProcess")
public class NullExcelImportProcess extends ExcelImportProcess {
    @Override
    protected void processData(Map values) throws Exception {

    }
}
