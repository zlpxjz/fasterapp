package com.fasterapp.base.utils;

import com.fasterapp.base.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tony on 2021/9/2.
 */
public class StringUtil {
	private final static Logger logger= LoggerFactory.getLogger(StringUtil.class);

	private static AtomicInteger SEQ = new AtomicInteger(0);

	/**
	 * 取值
	 * @param value
	 * @return
	 */
	public static String valueOf(Object value){
		return valueOf(value, "");
	}

	/**
	 *
	 * @param value
	 * @param ifNull
	 * @return
	 */
	public static String valueOf(Object value, String ifNull){
		if(value == null) return ifNull;

		return value.toString().trim();
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String uuid = getSeperatedUUID();

		return uuid.replace("-", "").toLowerCase();
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getSeperatedUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.toLowerCase();
	}


	/**
	 * 返回有完整时间格式+jvm序号+序列数字形成的编码
	 * @return
	 */
	public static String getCode() throws Exception{
		String jvmIndex = System.getenv("jvm_index");
		if(StringUtil.isNullOrBlank(jvmIndex)){
			throw new AppException("为避免产生的编号重复，请设置系统启动参数:jvm_index。");
		}

		SEQ.compareAndSet(9999, 1);
		return new StringBuilder(DateUtil.format(DateUtil.getSysDate(), "yyMMddHHmmss")).append(lpad(jvmIndex, "0",2))
				.append(lpad(String.valueOf(SEQ.incrementAndGet()), "0", 4)).toString();
	}

	/**
	 * 判断字符串是否相等（区分大小写）
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean equals(String value1, String value2){
		if(value1 == null || value2== null){
			return false;
		}

		return value1.trim().equals(value2.trim());
	}

	/**
	 * 判断字符串是否相等，不区分大小写。
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String value1, String value2){
		if(value1 == null || value2 == null){
			return false;
		}
		return value1.trim().equalsIgnoreCase(value2.trim());
	}

	/**
	 * 判断字符串是否为Null或者空串
	 * @param value
	 * @return
	 */
	public static boolean isNotNullAndBlank(String value){
		return ! isNullOrBlank(value);
	}

	/**
	 * 判断字符串是否为Null或者空串
	 * @param value
	 * @return
	 */
	public static boolean isNullOrBlank(String value){
		return value == null || value.trim().length() == 0;
	}

	/**
	 * 首字母大写
	 * @param value
	 * @return
	 */
	public static String upperFirst(String value) {
		if(isNullOrBlank(value)){
			return value;
		}

		if (Character.isUpperCase(value.charAt(0))) {
			return value;
		}else {
			return (new StringBuilder()).append(Character.toUpperCase(value.charAt(0))).append(value.substring(1)).toString();
		}
	}

	/**
	 * 首字母小写
	 * @param value
	 * @return
	 */
	public static String lowerFirst(String value) {
		if(isNullOrBlank(value)){
			return value;
		}

		if (Character.isLowerCase(value.charAt(0))) {
			return value;
		}else {
			return (new StringBuilder()).append(Character.toLowerCase(value.charAt(0))).append(value.substring(1)).toString();
		}
	}

	/**
	 * 前后去空格
	 * @param value
	 * @return
	 */
	public static String trim(String value) {
		if(isNullOrBlank(value)){
			return value;
		}

		return value.trim();
	}

	/**
	 *
	 * @title: camelToUnderline
	 * @description: 驼峰转下划线
	 * @param param
	 * @return
	 * @return: String
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 *
	 * @title: underlineToCamel
	 * @description:下划线转驼峰
	 * @param param
	 * @return
	 * @return: String
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == '_') {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 左填充
	 * @param value
	 * @param c
	 * @param length
	 * @return
	 */
	public static String lpad(String value, String c, int length){
		StringBuffer sb = new StringBuffer(value);

		while(sb.length() < length){
			sb.insert(0, c);
		}

		return sb.toString();
	}

	/**
	 * 右填充
	 * @param value
	 * @param c
	 * @param length
	 * @return
	 */
	public static String rpad(String value, String c, int length){
		StringBuffer sb = new StringBuffer(value);

		while(sb.length() < length){
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 *
	 * @param value
	 * @param defValue
	 * @return
	 */
	public String ifnull(String value, String defValue){
		if(value == null){
			return defValue;
		}

		if(value.toString().trim().length() == 0){
			return defValue;
		}

		return value;
	}

	/**
	 * 字符串字符转换
	 * @param value

	 * @return
	 */
	public static String encode(String value){
		return encode(value, "utf-8");
	}

	/**
	 * 字符串字符转换
	 * @param value
	 * @param encoding
	 * @return
	 */
	public static String encode(String value, String encoding){
		try {
			return   URLDecoder.decode(value,encoding);
		}catch(Exception exc){
			logger.error("编码转换异常.", exc);
			return value;
		}
	}

	public static String format(String message, Object... parameters){
		if(message == null || message.trim().equalsIgnoreCase("")){
			return null;
		}

		return MessageFormat.format(message, parameters);
	}

	/**
	 * 判断scopes中是否包含
	 * @param value
	 * @param scopes
	 * @return
	 */
	public static boolean in (String value, String... scopes){
		for(String v : scopes){
			if(isNullOrBlank(v) && isNullOrBlank(value)){
				return true;
			}

			return v.equalsIgnoreCase(value);
		}

		return false;
	}

	/**
	 *
	 * @param value
	 * @param scopes
	 * @return
	 */
	public static boolean  notin(String value, String... scopes){
		return ! in(value, scopes);
	}
}
