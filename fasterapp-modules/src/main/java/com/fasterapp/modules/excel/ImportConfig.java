package com.fasterapp.modules.excel;

import lombok.Data;

import java.util.List;

/**
 * Created by Tony on 2021/9/16.
 */
@Data
public class ImportConfig {
	private int sheetIndex = 0;
	private int titleLine;
	private int  startRowIndex = 0;
	private int  startColIndex = 0;
	private int  errorIndex = 0;
	private String readModel = "index";
	private List<ImportField> columns;
}
