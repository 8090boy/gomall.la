/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.constant;

/**
 * 标签类型
 * 
 * ----------------------------------------------------------------------------.
 */
public enum TagTypeEnum implements StringEnum {

	/** 用于首页的标签 */
	INDEX("I");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new function enum.
	 * 
	 * @param value
	 *            the value
	 */
	private TagTypeEnum(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.constant.StringEnum#value()
	 */
	public String value() {
		return this.value;
	}

	/**
	 * Instance.
	 * 
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public static boolean instance(String name) {
		TagTypeEnum[] licenseEnums = values();
		for (TagTypeEnum licenseEnum : licenseEnums) {
			if (licenseEnum.name().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the value.
	 * 
	 * @param name
	 *            the name
	 * @return the value
	 */
	public static String getValue(String name) {
		TagTypeEnum[] licenseEnums = values();
		for (TagTypeEnum licenseEnum : licenseEnums) {
			if (licenseEnum.name().equals(name)) {
				return licenseEnum.value();
			}
		}
		return null;
	}
}
