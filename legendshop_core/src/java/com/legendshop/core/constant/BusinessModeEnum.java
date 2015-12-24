package com.legendshop.core.constant;

import com.legendshop.util.constant.StringEnum;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * -------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * --------
 * ----------------------------------------------------------------------------
 * 
 */
public enum BusinessModeEnum implements StringEnum {

	B2C("单用户"),

	C2C("多用户"),

	B2B2C("多商城");

	private final String value;

	private BusinessModeEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public static boolean instance(String name) {
		BusinessModeEnum[] businessModeEnums = values();
		for (BusinessModeEnum businessModeEnum : businessModeEnums) {
			if (businessModeEnum.name().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static String getValue(String name) {
		BusinessModeEnum[] businessModeEnums = values();
		for (BusinessModeEnum businessModeEnum : businessModeEnums) {
			if (businessModeEnum.name().equals(name)) {
				return businessModeEnum.value();
			}
		}
		return null;
	}
}
