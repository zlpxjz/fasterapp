package com.fasterapp.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tony on 2021/9/2.
 */
public class DateUtil {
	private final static Logger logger= LoggerFactory.getLogger(DateUtil.class);

	//时间格式常量
	public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FULL_DAY = "yyyy-MM-dd";
	public static final String FULL_TIME = "HH:mm:ss";

	//日期类型枚举
	public enum TYPE {Year, Month, Day}

	/**
	 * 对应JAVA日期常量数字
	 */
	public enum FIELD{
		YEAR(1), MONTH(2), WEEK_OF_YEAR(3), WEEK_OF_MONTH(4), DAY_OF_MONTH(5), DAY_OF_YEAR(6),DAY_OF_WEEK(7),DAY_OF_WEEK_IN_MONTH(8);

		private int value;
		FIELD(int value){
			this.value = value;
		}
	}

	/**
	 *
	 * @return
	 */
	public static Timestamp getTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取SQL格式系统日期
	 * @return
	 */
	public static java.sql.Date getSqlDate(){
		return getSqlDate(getSysDate());
	}

	/**
	 * 获取SQL格式日期
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date){
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 获取系统日期
	 * @return
	 */
	public static Date getSysDate(){
		return new Date();
	}

	/**
	 * 日期格式判断
	 * @param value
	 * @return
	 */
	public static  boolean isDate(Object value) {
		return isDate(value, FULL_FORMAT);
	}

	/**
	 * 日期格式判断
	 * @param value
	 * @param format
	 * @return
	 */
	public static  boolean isDate(Object value, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			sdf.setLenient(false);
			sdf.parse(value.toString());
			return true;
		} catch (Exception e) {
			logger.error("日期格式转换异常。", e);
		}

		return false;
	}

	/**
	 * 使用缺省格式格式化日期
	 * @param date
	 * @return
	 */
	public static String format(Date date) throws Exception{
		return format(date, FULL_FORMAT);
	}

	/**
	 * 日期格式化
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String format(Date date, String format) throws Exception {
		if(date == null) {
			return null;
		}

		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return dtf.format(localDateTime);
	}

	/**
	 * 使用缺省格式转换日期
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String date) throws Exception {
		return parse(date, FULL_FORMAT);
	}

	/**
	 * 日期转换
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String date, String format) throws  Exception{
		if(date == null || date.trim().trim().equals("")) return null;

		if(date.split(" ").length == 1){
			date = date + " 01:01:01";
		}

		DateTimeFormatter dtf=DateTimeFormatter.ofPattern(format);
		LocalDateTime localDateTime=LocalDateTime.parse(date,dtf);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Date add(FIELD field, int value) throws Exception{
		return add(getSysDate(), field, value);
	}

	/**
	 * 日期加
	 * @param date
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Date add(Date date, FIELD field, int value) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field.value, value);
		return calendar.getTime();
	}

	/**
	 * 获取当前月份的首日日期
	 * @return
	 */
	public static Date  getFirstDayOfMonth(){
		return getFirstDayOfMonth(LocalDate.now());
	}

	/**
	 * 获取指定月份的首日日期
	 * @param date
	 * @return
	 */
	public static Date  getFirstDayOfMonth(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = instant.atZone(zoneId).toLocalDate();
		return getFirstDayOfMonth(localDate);
	}

	/**
	 * 获取指定月份的首日日期
	 * @param localDate
	 * @return
	 */
	public static Date  getFirstDayOfMonth(LocalDate localDate){
		LocalDate firstLocalDate = localDate.withDayOfMonth(1);
		ZonedDateTime zonedDateTime = firstLocalDate.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 获取当前月份的最后一天日期
	 * @return
	 */
	public static Date  getEndDayOfMonth() {
		return getEndDayOfMonth(LocalDate.now());
	}

	/**
	 * 获取指定日期的月份最后一天日期
	 * @param date
	 * @return
	 */
	public static Date  getEndDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		LocalDate localDate = LocalDate.of(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DATE));
		return getEndDayOfMonth(localDate);
	}

	/**
	 * 获取指定日期的月份最后一天日期
	 * @param localDate
	 * @return
	 */
	public static Date  getEndDayOfMonth(LocalDate localDate) {
		LocalDate firstLocalDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
		ZonedDateTime zonedDateTime = firstLocalDate.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 获取两个日期之间的天数间隔
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int interval(Date beginDate,Date endDate){
		return interval(beginDate, endDate, TYPE.Day);
	}

	/**
	 * 获取两个日期之间的间隔
	 * @param beginDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	public static int interval(Date beginDate,Date endDate, TYPE type){
		if(beginDate == null || endDate == null) return 0;

		ZoneId zoneId = ZoneId.systemDefault();
		Instant beginInstant = beginDate.toInstant();
		LocalDate fromLocalDate = beginInstant.atZone(zoneId).toLocalDate();

		Instant endInstant = endDate.toInstant();
		LocalDate endLocalDate = endInstant.atZone(zoneId).toLocalDate();

		int days = (int)(endLocalDate.toEpochDay() - fromLocalDate.toEpochDay());
		switch(type){
			case Year:
				return (int) Math.ceil(days/365);
			case Month:
				return (int) Math.ceil(days/30);
			case Day:
				return days;
		}

		return 0;
	}
}
