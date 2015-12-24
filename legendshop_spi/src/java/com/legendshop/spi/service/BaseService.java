/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Interface BaseService.
 */
public interface BaseService {

	/**
	 * Gets the session attribute.
	 * 
	 * @param request
	 *            the request
	 * @param name
	 *            the name
	 * @return the session attribute
	 */
	public abstract Object getSessionAttribute(HttpServletRequest request, String name);

	/**
	 * Sets the session attribute.
	 * 
	 * @param request
	 *            the request
	 * @param name
	 *            the name
	 * @param obj
	 *            the obj
	 */
	public abstract void setSessionAttribute(HttpServletRequest request, String name, Object obj);

	/**
	 * 设置广告.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopName
	 *            the shop name
	 * @param page
	 *            the page
	 * @return the and set advertisement
	 */
	public abstract void getAndSetAdvertisement(HttpServletRequest request, HttpServletResponse response, String shopName,
			String page);

	/**
	 * get one Adv.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopName
	 *            the shop name
	 * @param key
	 *            the key
	 * @return the and set one advertisement
	 */
	public void getAndSetOneAdvertisement(HttpServletRequest request, HttpServletResponse response, String shopName, String key);

	/**
	 * Check privilege. 检查权限
	 * 
	 * @param request
	 *            the request
	 * @param loginName
	 *            the login name
	 * @param userName
	 *            the user name
	 * @return the string
	 */
	public String checkPrivilege(HttpServletRequest request, String loginName, String userName);

}