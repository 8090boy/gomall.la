/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Enum FrontPage.
 */
public enum CommonPage implements PageDefinition {
	/** The FAIL. */
	FAIL("/fail"),

	/** The ERRO r_ page. */
	ERROR_PAGE(ERROR_PAGE_PATH), ;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new common page.
	 * 
	 * @param value
	 *            the value
	 * @param template
	 *            the template
	 */
	private CommonPage(String value) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.constant.PageDefinition#getValue(java.lang.String,
	 * java.util.List)
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateFronendPath(request, response, path, pageDefinition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.constant.PageDefinition#getNativeValue()
	 */
	public String getNativeValue() {
		return value;
	}

}
