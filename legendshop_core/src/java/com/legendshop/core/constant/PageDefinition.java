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
 * The Interface PageDefinition.
 * 
 * page type
 */
public interface PageDefinition {

	/** The PAGE. */
	public final int FRONT_PAGE = 1;

	/** The BAC k_ page. */
	public final int BACK_PAGE = 2;

	/** The TILES. */
	public final int TILES = 3;

	/** The FOWARD. */
	public final int FOWARD = 4;

	/** The REDIRECT. */
	public final int REDIRECT = 5;

	/** common path. */
	public final String LOGIN_PATH = "login.";

	/** The ERRO r_ pag e_ path. */
	public final String ERROR_PAGE_PATH = "/common/error";

	/** The DEFAUL t_ them e_ path. */
	public String DEFAULT_THEME_PATH = "default";

	/**
	 * Gets the value.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the value
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Gets the native value.
	 * 
	 * @return the native value
	 */
	public String getNativeValue();

	/**
	 * Gets the value.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param path
	 *            the path
	 * @param pageDefinition
	 *            the page definition
	 * @return the value
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition);

}
