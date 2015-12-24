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
public enum GroupBackPage implements PageDefinition {

	/** The VARIABLE. 可变路径 */
	VARIABLE(""),
	
	/** The prod list page. */
	PROD_LIST_PAGE("/group/gprodList"),
	
	/** The prod list content page. */
	PROD_LIST_CONTENT_PAGE("/group/gprodContentList"),

	/** The PRO d_ edi t_ page. */
	PROD_EDIT_PAGE("/group/gprod"),

	/** The gsort list page. */
	GSORT_LIST_PAGE("/gsort/gsortList"),

	/** The gsort edit page. */
	GSORT_EDIT_PAGE("/gsort/gsort"), ;

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new group back page.
	 *
	 * @param value the value
	 * @param template the template
	 */
	private GroupBackPage(String value, String... template) {
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
		return PagePathCalculator.calculatePluginBackendPath(request, response, "group", path, pageDefinition);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.constant.PageDefinition#getNativeValue()
	 */
	public String getNativeValue() {
		return value;
	}

}
