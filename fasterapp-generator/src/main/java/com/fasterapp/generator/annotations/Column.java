package com.fasterapp.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Tony on 2021/11/1.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 是否关键字
	 * @return
	 */
	boolean key() default false;

	/**
	 * 是否外键
 	 * @return
	 */
	boolean foreign() default false;

	/**
	 * 字段名
	 * @return
	 */
	String name() default "";

	/**
	 * 是否唯一
	 * @return
	 */
	boolean unique() default false;

	/**
	 * 是否可谓空
	 * @return
	 */
	boolean nullable() default true;

	/**
	 * 是否出现在Insert语句中
	 * @return
	 */
	boolean insertable() default true;

	/**
	 * 是否出现在Update语句中
	 * @return
	 */
	boolean updatable() default true;

	/**
	 * 备注
	 * @return
	 */
	String comment();

	/**
	 *
	 * @return
	 */
	String type();
}
