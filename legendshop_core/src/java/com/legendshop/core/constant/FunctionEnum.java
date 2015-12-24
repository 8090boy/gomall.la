/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.constant;

import com.legendshop.util.constant.StringEnum;

/**
 * 系统关键角色
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * 
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * 
 * ----------------------------------------------------------------------------.
 */
public enum FunctionEnum implements StringEnum {

	/** The FUNCTIO n_ vie w_ al l_ data. */
	FUNCTION_VIEW_ALL_DATA("F_VIEW_ALL_DATA"),

	/** The FUNCTIO n_ f_ admin. */
	FUNCTION_F_ADMIN("F_ADMIN"),

	/** The FUNCTIO n_ f_ system. */
	FUNCTION_F_SYSTEM("F_SYSTEM"),

	/** The FUNCTIO n_ secured. */
	@Deprecated
	FUNCTION_SECURED("F_SECURED"),

	/** The FUNCTIO n_ securest. */
	@Deprecated
	FUNCTION_SECUREST("F_SECUREST");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new function enum.
	 * 
	 * @param value
	 *            the value
	 */
	private FunctionEnum(String value) {
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
		FunctionEnum[] licenseEnums = values();
		for (FunctionEnum licenseEnum : licenseEnums) {
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
		FunctionEnum[] licenseEnums = values();
		for (FunctionEnum licenseEnum : licenseEnums) {
			if (licenseEnum.name().equals(name)) {
				return licenseEnum.value();
			}
		}
		return null;
	}
}
