package com.legendshop.spi.constants;

import com.legendshop.util.constant.StringEnum;

public enum SaveToCartStatusEnum implements StringEnum {

	/**
	 * 下单成功
	 */
	OK("OK"),

	/** 商品不存在，下单失败. */
	ERR("ERR"),

	/** 您是商品主人，不能购买此商品。 */
	OWNER("OWNER"),

	/** Product less 缺货. */
	LESS("LESS"),

	/** 超出购物车大小. */
	MAX("MAX");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new register enum.
	 * 
	 * @param value
	 *            the value
	 */
	private SaveToCartStatusEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
