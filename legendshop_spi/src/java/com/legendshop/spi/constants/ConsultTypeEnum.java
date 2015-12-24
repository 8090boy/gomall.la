/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.constants;

import com.legendshop.util.constant.IntegerEnum;

/**
 * 商品咨询类型 The Enum ConsultTypeEnum.
 */
public enum ConsultTypeEnum implements IntegerEnum {

	/** The prod. */
	PROD(1), // 商品咨询

	/** The stock. */
	STOCK(2), // 库存配送

	/** The warrant. */
	WARRANT(3);// 售后咨询

	/** The num. */
	private Integer type;

	/**
	 * Instantiates a new consult type enum.
	 * 
	 * @param num
	 *            the num
	 */
	ConsultTypeEnum(Integer num) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.constant.IntegerEnum#value()
	 */
	public Integer value() {
		return type;
	}

}
