package com.fasterapp.base.utils;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tony on 2021/9/3.
 */
public class CollectionUtil {
	/**
	 * 判断集合是否为空
	 * @param values
	 * @return
	 */
	public static boolean isEmpty(Collection values){
		return values == null || values.isEmpty();
	}

	/**
	 * 判断集合是否为空
	 * @param values
	 * @return
	 */
	public static boolean isNotEmpty(Collection values){
		return ! isEmpty(values);
	}

	/**
	 *
	 * @param values
	 * @param <T>
	 * @return
	 */
	public static <T> T[] toArray(List<T> values){
		if(isEmpty(values)) return null;

		Object[] objects = values.toArray();

		return (T[]) objects;
	}
}
