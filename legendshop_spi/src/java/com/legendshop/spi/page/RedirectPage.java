/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.constant.PagePathCalculator;

/**
 * The Enum RedirectPage.
 */
public enum RedirectPage implements PageDefinition {
	/** The VARIABLE. 可变路径 */
	VARIABLE(""),

	VIEWS("/views");

	/** The value. */
	private final String value;

	private RedirectPage(String value, String... template) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.constant.PageDefinition#getValue(javax.servlet.http
	 * .HttpServletRequest)
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response) {
		return getValue(request, response, value, this);
	}

	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateActionPath("redirect:", path);
	}

	/**
	 * Instantiates a new tiles page.
	 * 
	 * @param value
	 *            the value
	 */
	private RedirectPage(String value) {
		this.value = value;
	}

	public String getNativeValue() {
		return value;
	}

}
