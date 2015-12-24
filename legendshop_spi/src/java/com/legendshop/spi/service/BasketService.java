/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import com.legendshop.model.entity.Basket;
import com.legendshop.spi.constants.SaveToCartStatusEnum;

/**
 * The Interface BasketService.
 */
public interface BasketService {

	/**
	 * Save to cart.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param shopName
	 *            the shop name
	 * @param prodattr
	 *            the prodattr
	 * @param userName
	 *            the user name
	 * @param count
	 *            the count
	 */
	public abstract void saveToCart(Long prodId, String shopName, String prodattr, String userName, Integer count,int points);

	/**
	 * Delete basket by user name.
	 * 
	 * @param userName
	 *            the user name
	 */
	public abstract void deleteBasketByUserName(String userName);

	/**
	 * Delete basket by id.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void deleteBasketById(Long id);

	/**
	 * Gets the basket byuser name.
	 * 
	 * @param userName
	 *            the user name
	 * @return the basket byuser name
	 */
	public abstract List<Basket> getBasketByUserName(String userName);

	/**
	 * 得到有效订单总数.
	 *
	 * @param userName the user name
	 * @return the total basket byuser name
	 */
	public abstract Long getTotalBasketByUserName(String userName);

	/**
	 * 保存到购物车.
	 *
	 * @param userName the user name
	 * @param prodId the prod id
	 * @param count the count
	 * @param attribute the attribute
	 * @return true, if successful
	 */
	public abstract SaveToCartStatusEnum saveToCart(String userName, Long prodId, Integer count, String attribute, int points);

	/**
	 * Gets the basket list by id.
	 *
	 * @param basketIdList the basket id list
	 * @return the basket list by id
	 */
	public abstract List<Basket> getBasketListById(Long[] basketIdList);

	/**
	 * Update basket.
	 *
	 * @param basket the basket
	 */
	public abstract void updateBasket(Basket basket);



}