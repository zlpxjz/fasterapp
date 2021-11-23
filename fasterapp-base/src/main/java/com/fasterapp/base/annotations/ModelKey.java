package com.fasterapp.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Tony on 2021/11/1.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ModelKey {
	/**
	 * 字段名
	 * @return
	 */
	String name();

	/**
	 * 类型
	 * @return
	 */
	String type();
	/**
	 * 当Type为Varchar或Char时表示长度
	 * @return
	 */
	int length() default 255;

	/**
	 * 数字类型定义，表示长度
	 * @return
	 */
	int precision() default 0;
}
