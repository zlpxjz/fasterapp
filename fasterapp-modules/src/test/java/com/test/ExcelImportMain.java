package com.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

/**
 * Created by Tony on 2021/9/16.
 */
public class ExcelImportMain {
	public static void main(String[] args) throws Exception{
		Workbook workbook = new XSSFWorkbook(new FileInputStream("d:/tmp/1.xlsx"));
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(1);

		String value;
		for(int index = 0; index < 5; index++){
			Cell cell = row.getCell(index);
			if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
				value = cell.getCellFormula();
			}else{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				value = cell.getStringCellValue();
			}

			System.out.println(value);
		}
	}
}
