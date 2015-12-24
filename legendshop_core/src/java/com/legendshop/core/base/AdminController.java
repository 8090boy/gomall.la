/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller接口.
 * 
 * @param <E>
 *            entity
 * @param <K>
 *            primary key
 */
public interface AdminController<E, K> extends Controller {

	/**
	 * 查询接口.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, E entity);

	/**
	 * 保存Entity接口.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	public String save(HttpServletRequest request, HttpServletResponse response, E entity);

	/**
	 * 删除Entity接口.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response, K id);

	/**
	 * 加载新增页面接口.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	public String load(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 加载编辑页面接口.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	public String update(HttpServletRequest request, HttpServletResponse response, K id);
}
