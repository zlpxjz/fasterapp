package com.fasterapp.base.utils;

import com.fasterapp.base.exceptions.AppException;
import com.fasterapp.base.core.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Tony on 2021/9/2.
 */
public class ApiUtil {
	private static Logger log = LoggerFactory.getLogger(ApiUtil.class);

	/**
	 * api获取 HTTP头属性值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getHeader(HttpServletRequest request, String name){
		return request.getHeader(name);
	}

	/**
	 * 获取HTTP请求URI
	 * @param request
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}

	/**
	 * 获取访问客户端真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * HTTP请求响应
	 * @param response
	 * @param apiResponse
	 */
	public static void response(HttpServletResponse response, ApiResponse apiResponse){
		if(ApiResponse.Success.equals(apiResponse.getCode())) {
			response(response, 200, apiResponse);
		}else{
			response(response, Integer.valueOf(apiResponse.getCode()), apiResponse);
		}
	}

	/**
	 * HTTP异常响应
	 * @param exc
	 * @param response
	 * @throws Exception
	 */
	public static void response(HttpServletResponse response, Exception exc){
		ApiResponse apiResponse;
		if (exc instanceof AppException) {
			apiResponse = ApiResponse.error(exc.getMessage());
		} else {
			apiResponse = ApiResponse.error();
		}

		response(response, 500, apiResponse);
	}

	/**
	 * HTTP请求响应
	 * @param response
	 * @param apiResponse
	 */
	public static void response(HttpServletResponse response, int status, ApiResponse apiResponse){
		try {
			response.setCharacterEncoding("UTF-8");
			response.setStatus(status);
			response.setContentType("application/json;charset=UTF8");
			PrintWriter writer = response.getWriter();
			writer.write(JsonUtil.toString(apiResponse));
			writer.close();
		}catch(Exception exc){
			log.error("返回异常。", exc);
		}
	}
}
