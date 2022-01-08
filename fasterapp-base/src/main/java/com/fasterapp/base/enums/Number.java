package com.fasterapp.base.enums;

/**
 * Created by Tony on 2022/1/6.
 */
public enum Number implements NumberEnum {
	One(1),Two(2), Three(3);

	private int value;

	private Number(int value){
		this.value = value;
	}

	@Override
	public Integer value() {
		return value;
	}
}
