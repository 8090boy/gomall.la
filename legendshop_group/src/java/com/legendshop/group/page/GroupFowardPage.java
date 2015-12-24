/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.group.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.constant.PagePathCalculator;

/**
 * The Enum BackPage.
 */
public enum GroupFowardPage implements PageDefinition {

	/** The VARIABLE. 可变路径 */
	VARIABLE(""),

	/** The prod list query. */
	PROD_LIST_QUERY("/admin/group/product/query"),

	/** The gsort list query. */
	GSORT_LIST_QUERY("/admin/gsort/query"), ;

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new group foward page.
	 * 
	 * @param value
	 *            the value
	 * @param template
	 *            the template
	 */
	private GroupFowardPage(String value, String... template) {
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
	 * com.legendshop.core.constant.PageDefinition#getValue(javax.servlet.http
	 * .HttpServletRequest, java.lang.String)
	 */

	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateActionPath("forward:", path);
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
