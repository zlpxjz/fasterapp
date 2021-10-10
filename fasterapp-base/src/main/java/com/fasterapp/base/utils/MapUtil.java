package com.fasterapp.base.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2021/9/2.
 */
public class MapUtil {
	/**
	 *
	 * @param values
	 * @param key
	 * @param defs
	 * @return
	 * @throws Exception
	 */
	public static String getAsString(Map values, String key, String... defs) throws Exception{
		Object value = values.get(key);
		if(value == null){
			if(defs.length == 1) {
				return defs[0];
			}else {
				return null;
			}
		}

		return value.toString().trim();
	}

	/**
	 *
	 * @param values
	 * @param key
	 * @return
	 */
	public static Integer getAsInteger(Map values, String key, Integer... defs) throws Exception{
		Object value = values.get(key);
		if(value == null){
			if(defs.length == 1){
				return defs[0];
			}else {
				return null;
			}
		}

		return Integer.valueOf(value.toString().trim());
	}

	/**
	 *
	 * @param values
	 * @param key
	 * @return
	 */
	public static Double getAsDouble(Map values, String key, Double... defs)  throws Exception{
		Object value = values.get(key);
		if(value == null){
			if(defs.length == 1){
				return defs[0];
			}else {
				return null;
			}
		}

		return Double.valueOf(value.toString().trim());
	}

	/**
	 *
	 * @param values
	 * @param key
	 * @return
	 */
	public static BigDecimal getAsDecimal(Map values, String key, BigDecimal... defs)  throws Exception{
		Object value = values.get(key);
		if(value == null){
			if(defs.length == 1){
				return defs[0];
			}else {
				return null;
			}
		}

		return new BigDecimal(value.toString().trim());
	}

	/**
	 *
	 * @param values
	 * @param key
	 * @return
	 */
	public static Date getAsDate(Map values, String key, Date... defs)  throws Exception{
		Object value = values.get(key);
		if(value == null){
			if(defs.length == 1){
				return defs[0];
			}else {
				return null;
			}
		}

		return DateUtil.parse(value.toString().trim());
	}

	/**
	 *
	 * @param values
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Map getAsMap(Map values, String key) throws Exception{
		Object value = values.get(key);
		if(value == null){
			return null;
		}else{
			return (Map) value;
		}
	}

	/**
	 *
	 * @param values
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static List<Map> getAsList(Map values, String key) throws Exception{
		Object value = values.get(key);
		if(value == null){
			return null;
		}else{
			return (List<Map>) value;
		}
	}

	public static void main(String[] args) throws Exception{
		Map value = new HashMap();
		value.put("num", null);

		System.out.println(getAsInteger(value, "num"));
	}
}
