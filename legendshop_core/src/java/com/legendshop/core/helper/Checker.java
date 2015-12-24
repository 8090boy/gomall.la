/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统检查器
 * 
 * @param <T>
 *            the generic type
 */
public interface Checker<T> {

	/**
	 * Check.
	 * 
	 * @param t
	 *            the t
	 * @param request
	 *            the request
	 * @return true, if successful, if false will throw exception or means fail
	 */
	public boolean check(T t, HttpServletRequest request);

}
