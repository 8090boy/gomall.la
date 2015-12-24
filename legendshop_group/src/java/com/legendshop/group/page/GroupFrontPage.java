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
 * The Enum FrontPage.
 */
public enum GroupFrontPage implements PageDefinition {
	/** The VARIABLE. 可变路径 */
	VARIABLE(""),

	/** The CLIEN t_ servic e_ panel. */
	CLIENT_SERVICE_PANEL("/group/clientServicePanel"),

	/** The QUESTION. */
	QUESTION("/group/question"),

	/** The QUESTIO n_ panel. */
	QUESTION_PANEL("/group/questionPanel");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new group front page.
	 * 
	 * @param value
	 *            the value
	 * @param template
	 *            the template
	 */
	private GroupFrontPage(String value) {
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
	 * .HttpServletRequest, java.lang.String, java.util.List)
	 */

	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculatePluginFronendPath(request, response, "group", path, pageDefinition);
	}

	public String getNativeValue() {
		return value;
	}

}
