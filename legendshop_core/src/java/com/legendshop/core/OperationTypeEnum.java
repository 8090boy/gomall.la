/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core;

import com.legendshop.util.constant.StringEnum;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * 
 * 操作类型.
 */
public enum OperationTypeEnum implements StringEnum {

	/** The save. */
	SAVE("保存"),

	/** The update. */
	UPDATE("更新"),

	/** The deltet. */
	DELETE("删除"),

	/** The turn on. */
	TURN_ON("上线"),

	/** The turn off. */
	TURN_OFF("下线"),

	/** The password. */
	UPDATE_PASSWORD("更改密码"),

	UPDATE_STATUS("更改状态"),

	UPDATE_PRICE("更改价格");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new role enum.
	 * 
	 * @param value
	 *            the value
	 */
	private OperationTypeEnum(String value) {
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
		OperationTypeEnum[] licenseEnums = values();
		for (OperationTypeEnum licenseEnum : licenseEnums) {
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
		OperationTypeEnum[] licenseEnums = values();
		for (OperationTypeEnum licenseEnum : licenseEnums) {
			if (licenseEnum.name().equals(name)) {
				return licenseEnum.value();
			}
		}
		return null;
	}
}
