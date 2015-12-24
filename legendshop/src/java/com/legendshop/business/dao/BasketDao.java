/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import java.util.List;
import java.util.Map;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.model.entity.Basket;
import com.legendshop.spi.constants.SaveToCartStatusEnum;

/**
 * The Interface BasketDao.
 */
public interface BasketDao extends BaseDao {

	/**
	 * Delete basket by id.
	 * 
	 * @param basketId
	 *            the basket id
	 */
	public abstract void deleteBasketById(Long basketId);

	/**
	 * Gets the basket byuser name.
	 * 
	 * @param userName
	 *            the user name
	 * @return the basket byuser name
	 */
	public abstract List<Basket> getBasketByUserName(String userName);

	// 得到有效订单总数
	/**
	 * Gets the total basket byuser name.
	 * 
	 * @param userName
	 *            the user name
	 * @return the total basket byuser name
	 */
	public abstract Long getTotalBasketByUserName(String userName);

	/**
	 * group by shopName
	 * Gets the basket byuser name group by shop name.
	 * 
	 * @param userName
	 *            the user name
	 * @return the basket byuser name group by shop name
	 */
	public abstract Map<String, List<Basket>> getBasketGroupByName(String userName);
	
	
	/**
	 * Gets the basket group by id.
	 *
	 * @param basketIdList the basket id list
	 * @return the basket group by id
	 */
	public abstract Map<String, List<Basket>> getBasketGroupById(Long[] basketIdList);

	/**
	 * Gets the basket by id.
	 * 
	 * @param id
	 *            the id
	 * @return the basket by id
	 */
	public abstract Basket getBasketById(Long id);

	/**
	 * Gets the basket by id name.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @param shopName
	 *            the shop name
	 * @param attribute
	 *            the attribute
	 * @return the basket by id name
	 */
	public abstract Basket getBasketByIdName(Long prodId, String userName, String shopName, String attribute);

	// 用户在shopName的订购数
	/**
	 * Gets the basket by user name.
	 * 
	 * @param userName
	 *            the user name
	 * @param shopName
	 *            the shop name
	 * @return the basket by user name
	 */
	public abstract Long getBasketByUserName(String userName, String shopName);

	/**
	 * Save basket.
	 * 
	 * @param basket
	 *            the basket
	 * @return the long
	 */
	public abstract Long saveBasket(Basket basket);

	/**
	 * Update basket.
	 * 
	 * @param basket
	 *            the basket
	 */
	public abstract void updateBasket(Basket basket);

	/**
	 * Gets the basket.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the basket
	 */
	public abstract List<Basket> getBasket(String prodId, String userName);

	/**
	 * Delete basket by user name.
	 * 
	 * @param userName
	 *            the user name
	 */
	public abstract void deleteBasketByUserName(String userName);

	/**
	 * Delete basket by sub number.
	 * 
	 * @param subNumber
	 *            the sub number
	 */
	public abstract void deleteBasketBySubNumber(String subNumber);

	/**
	 * Save to cart.
	 * 
	 * @param userName
	 *            the user name
	 * @param prodId
	 *            the prod id
	 * @param count
	 *            the count
	 * @param attribute
	 *            the attribute
	 * @return true, if successful
	 */
	public SaveToCartStatusEnum saveToCart(String userName, Long prodId, Integer count, String attribute, int points);

	/**
	 * 删除商城的所有购物车
	 *
	 * @param userName the user name
	 */
	public abstract void deleteBasket(String userName);
	
	
	/**
	 * 删除用户的购物车
	 *
	 * @param userName the user name
	 */
	public abstract void deleteUserBasket(String userName);

	/**
	 * Gets the basket list by id.
	 *
	 * @param basketIdList the basket id list
	 * @return the basket list by id
	 */
	public abstract List<Basket> getBasketListById(Long[] basketIdList);


}