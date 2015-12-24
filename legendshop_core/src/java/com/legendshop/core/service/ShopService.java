/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.service;

import com.legendshop.model.entity.ShopDetailView;

/**
 * The Interface ShopService.
 */
public interface ShopService {

	/**
	 * 根据用户名拿到商城实体.
	 * 
	 * @param currentShopName
	 *            the current shop name
	 * @return the shop detail view
	 */
	public ShopDetailView getShopDetailView(String currentShopName);

	/**
	 * Gets the shop name by domain.
	 * 
	 * @param domainName
	 *            the domain name
	 * @return the shop name by domain
	 */
	public String getShopNameByDomain(String domainName);

	/**
	 * Checks if is shop exists.
	 * 
	 * @param userName
	 *            the user name
	 * @return the boolean
	 */
	public abstract Boolean isShopExists(final String userName);
}
