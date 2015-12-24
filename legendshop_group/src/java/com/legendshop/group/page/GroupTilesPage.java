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
 * The Enum TilesPage.
 */
public enum GroupTilesPage implements PageDefinition {
	/** The VARIABLE. 可变路径 ,如果不设置Templates默认支持所有的template */
	VARIABLE(""),

	/** The Index. */
	GINDEX("gindex."),

	GVIEW("gview.");

	/** The value. */
	private String value;

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
		return PagePathCalculator.calculateTilesPath(request, response, path, pageDefinition);
	}

	/**
	 * Instantiates a new tiles page.
	 * 
	 * @param value
	 *            the value
	 */
	private GroupTilesPage(String value, String... template) {
		this.value = value;
	}

	public String getNativeValue() {
		return value;
	}

}
