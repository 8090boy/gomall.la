/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.locator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;

/**
 * The Interface ServiceLocator. 服务定位器
 * 
 * @param <T>
 *            the generic type
 */
public interface ServiceLocator<T> {

	/**
	 * Gets the concrete service.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param page
	 *            the page
	 * @return the concrete service
	 */
	public T getConcreteService(HttpServletRequest request, HttpServletResponse response, PageDefinition page);
}
