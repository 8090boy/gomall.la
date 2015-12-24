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

import com.legendshop.model.UserInfo;
import com.legendshop.model.entity.ShopDetailView;

/**
 * The Interface IndexService.
 */
public interface IndexService extends BaseService {

	/**
	 * Gets the index.
	 * 
	 * @param request
	 *            the request
	 * @param shopDetail
	 *            the shop detail
	 * @return the index
	 */
	public void getIndex(HttpServletRequest request, HttpServletResponse response, ShopDetailView shopDetail);

	/**
	 * Index admin.
	 * 
	 * @param userName
	 *            the user name
	 * @param shopDetail
	 *            the shop detail
	 * @return the user info
	 */
	public abstract UserInfo getAdminIndex(String userName, ShopDetailView shopDetail);

}