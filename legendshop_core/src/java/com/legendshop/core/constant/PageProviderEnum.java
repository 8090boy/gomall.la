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
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * --------------------------------------------
 * ---------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ------------------
 * -----------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public enum PageProviderEnum implements StringEnum {

	SIMPLE_PAGE_PROVIDER("SimplePageProvider"),

	PAGE_PROVIDER("PageProvider");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new register enum.
	 * 
	 * @param value
	 *            the value
	 */
	private PageProviderEnum(String value) {
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

}
