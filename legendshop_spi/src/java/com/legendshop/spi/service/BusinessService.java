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

import com.legendshop.spi.form.SearchForm;

/**
 * The Interface BusinessService.
 */
public interface BusinessService extends BaseService {

	/**
	 * Friendlink.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	public abstract String getFriendlink(HttpServletRequest request, HttpServletResponse response);

	/**
	 * League.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	public abstract String getLeague(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Gets the newsfor common.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	public abstract String getNewsforCommon(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Visited shop.
	 * 
	 * @param request
	 *            the request
	 * @return the string
	 */
	public abstract void getVisitedShop(HttpServletRequest request);

	/**
	 * Search. 站内查询
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param searchForm
	 *            the search form
	 * @return the string
	 */
	public abstract String search(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm);

}