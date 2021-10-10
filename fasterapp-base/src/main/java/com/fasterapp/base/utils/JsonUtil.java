package com.fasterapp.base.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Tony on 2021/9/2.
 */
public class JsonUtil {
	/**
	 *
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toString(Object object) throws Exception{
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	/**
	 *
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T toObject(String json, Class clazz) throws Exception{
		Gson gson = new Gson();
		return (T) gson.fromJson(json, clazz);
	}

	/**
	 *
	 * @param json
	 * @param type
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T toObject(String json, Type type) throws Exception{
		Gson gson = new Gson();
		return (T) gson.fromJson(json, type);
	}
}
