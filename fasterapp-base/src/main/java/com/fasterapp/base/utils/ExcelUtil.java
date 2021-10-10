package com.fasterapp.base.utils;

import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Date;

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

		BigDecimal bd;
		try{
			bd = BigDecimal.valueOf(cell.getNumericCellValue());
		}catch(Exception exc){
			bd = new BigDecimal(cell.getStringCellValue().trim());
		}

		return bd.intValue();
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

		BigDecimal bd;
		try{
			bd = BigDecimal.valueOf(cell.getNumericCellValue());
		}catch(Exception exc){
			bd = new BigDecimal(cell.getStringCellValue().trim());
		}

		return bd.doubleValue();
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

		try{
			return cell.getDateCellValue();
		}catch(Exception exc){
			return DateUtil.parse(cell.getStringCellValue().trim());
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
