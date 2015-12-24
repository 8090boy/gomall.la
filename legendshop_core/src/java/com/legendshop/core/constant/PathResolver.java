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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class PathResolver.
 */
public class PathResolver {

	private final static Logger log = LoggerFactory.getLogger(PathResolver.class);

	/**
	 * Gets the path.
	 * 
	 * @param request
	 *            the request
	 * @param pageLetEnum
	 *            the page let enum
	 * @return the path
	 */
	public static String getPath(HttpServletRequest request, HttpServletResponse response, PageDefinition page) {
		String path = page.getValue(request, response);
		if (log.isDebugEnabled()) {
			log.debug("enter page {},  path = {}", page, path);
		}
		return path;
	}

	/**
	 * Gets the path.
	 * 
	 * @param request
	 *            the request
	 * @param pathValue
	 *            the path value
	 * @param page
	 *            the page
	 * @return the path
	 */
	public static String getPath(HttpServletRequest request, HttpServletResponse response, String pathValue, PageDefinition page) {
		String path = page.getValue(request, response, pathValue, null);
		if (log.isDebugEnabled()) {
			log.debug("enter page {},  path = {}", page, path);
		}
		return path;
	}

}
