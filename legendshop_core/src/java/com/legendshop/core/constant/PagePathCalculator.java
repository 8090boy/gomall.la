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

import com.legendshop.core.helper.ThreadLocalContext;

/**
 * The Class PagePathCalculator.
 */
public class PagePathCalculator {

	/**
	 * Calculate backend path.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param pageDefinition
	 *            the page definition
	 * @return the string
	 */
	public static String calculateBackendPath(HttpServletRequest request, HttpServletResponse response, String pathValue,
			PageDefinition pageDefinition) {
		StringBuilder path = new StringBuilder("/pages/backend/");
		path.append(ThreadLocalContext.getBackEndType(request, response, pathValue, pageDefinition));
		path.append(pathValue);
		return path.toString();
	}

	/**
	 * Calculate plugin backend path.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param pluginName
	 *            the plugin name
	 * @param pageDefinition
	 *            the page definition
	 * @return the string
	 */
	public static String calculatePluginBackendPath(HttpServletRequest request, HttpServletResponse response, String pluginName,
			String pathValue, PageDefinition pageDefinition) {
		StringBuilder path = new StringBuilder("/plugins/");
		path.append(pluginName);
		path.append("/jsp/backend/");
		path.append(ThreadLocalContext.getBackEndType(request, response, pathValue, pageDefinition));
		path.append(pathValue);
		return path.toString();
	}

	/**
	 * Calculate fronend path.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param pluginName
	 *            the plugin name
	 * @param pathValue
	 *            the path value
	 * @param templates
	 *            the templates
	 * @return the string
	 */
	public static String calculatePluginFronendPath(HttpServletRequest request, HttpServletResponse response, String pluginName,
			String pathValue, PageDefinition pageDefinition) {
		StringBuilder path = new StringBuilder("/plugins/");
		path.append(pluginName);
		path.append("/jsp/frontend/");
		path.append(ThreadLocalContext.getFrontType(request, response, pathValue, pageDefinition));
		path.append(pathValue);
		return path.toString();
	}

	/**
	 * Calculate fronend path.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param pathValue
	 *            the path value
	 * @param templates
	 *            the templates
	 * @return the string
	 */
	public static String calculateFronendPath(HttpServletRequest request, HttpServletResponse response, String pathValue,
			PageDefinition pageDefinition) {
		StringBuilder path = new StringBuilder("/pages/frontend/");
		path.append(ThreadLocalContext.getFrontType(request, response, pathValue, pageDefinition));
		path.append(pathValue);
		return path.toString();
	}

	/**
	 * Calculate action path.
	 * 
	 * @param actionType
	 *            the action type
	 * @param pathValue
	 *            the path value
	 * @return the string
	 */
	public static String calculateActionPath(String actionType, String pathValue) {
		return actionType + pathValue;
	}

	/**
	 * Calculate tiles path.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param pageDefinition
	 *            the page definition
	 * @return the string
	 */
	public static String calculateTilesPath(HttpServletRequest request, HttpServletResponse response, String path,
			PageDefinition pageDefinition) {
		return path + ThreadLocalContext.getFrontType(request, response, path, pageDefinition);
	}

}
