package com.fasterapp.base.utils;

import com.fasterapp.base.AppException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by Tony on 2021/10/26.
 */
public class SecurityUtil {
	private static final String KEY = "Password@Key@123";
	private static final String IV = "Password@IV@1234";

	private static final String ALGO = "RSA";
	private static final String CHARSET = "UTF-8";
	private static Base64 base64 = null;

	static {
		base64 = new Base64();
	}

	/**
	 * 对称加密
	 * @param text 加密串
	 * @return
	 * @throws Exception 抛出异常
	 */
	public static String encrypt(String text) throws Exception{
		if (text == null || text.equals("")) {
			throw new AppException("加密串不能为空。");
		}

		Key keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		byte[] bytes = cipher.doFinal(text.getBytes());
		return base64.encodeToString(bytes);
	}

	/**
	 * 检查密码强度
	 * 密码必须包括1)0~9. 2)a~z 3)A~Z 4)~!@#$%^&*
	 * @param password
	 * @return
	 */
	public static boolean checkStrength(String password) {
		if(password.matches(".*[a-z]{1,}.*") || password.matches(".*[A-Z]{1,}.*") && password.matches(".*\\d{1,}.*")){
		//if (password.matches(".*[a-z]{1,}.*") && password.matches(".*[A-Z]{1,}.*") && password.matches(".*\\d{1,}.*") && password.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception{
		System.out.println(encrypt("Ftp123456"));
	}
}
