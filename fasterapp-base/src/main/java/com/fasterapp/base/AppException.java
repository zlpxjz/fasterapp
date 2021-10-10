package com.fasterapp.base;

/**
 * Created by Tony on 2021/9/2.
 */
public class AppException extends Exception {
	private String code = "9999";

	public AppException(String code, String message) {
		super(message);
		this.code = code;
	}

	public AppException(String message) {
		super(message);
	}

	public String getCode() {
		return code;
	}
}