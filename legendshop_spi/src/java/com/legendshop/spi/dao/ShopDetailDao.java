/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.dao;

import java.util.List;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.ShopDetailView;

/**
 * The Interface ShopDetailDao.
 */
public interface ShopDetailDao extends BaseDao {

	/**
	 * Checks if is shop exists.
	 * 
	 * @param userName
	 *            the store name
	 * @return the boolean
	 */
	public abstract Boolean isShopExists(final String userName);

	/**
	 * Gets the shop detail for update.
	 * 
	 * @param userName
	 *            the store name
	 * @return the shop detail for update
	 */
	public abstract ShopDetail getShopDetailForUpdate(final String userName);

	/**
	 * Gets the shop detail view.
	 * 
	 * @param userName
	 *            the store name
	 * @return the shop detail view
	 */
	public abstract ShopDetailView getShopDetailView(final String userName);

	/**
	 * Gets the shop detail.
	 * 
	 * @param userName
	 *            the store name
	 * @return the shop detail
	 */
	public abstract ShopDetail getShopDetail(final String userName);

	/**
	 * Gets the shop detail.
	 * 
	 * @param shopId
	 *            the shop id
	 * @return the shop detail
	 */
	public abstract List<ShopDetailView> getShopDetail(final Long[] shopId);

	/**
	 * Checks if is league shop exists.
	 * 
	 * @param userName
	 *            the store name
	 * @return the boolean
	 */
	public abstract Boolean isLeagueShopExists(final String userName);

	/**
	 * Canbe league shop.
	 * 
	 * @param isShopExists
	 *            the is shop exists
	 * @param userName
	 *            the store name
	 * @param friendName
	 *            the friend name
	 * @return the boolean
	 */
	public abstract Boolean isBeLeagueShop(final boolean isShopExists, final String userName, final String friendName);

	/**
	 * Gets the product num.
	 * 
	 * @param userName
	 *            the user name
	 * @return the product num
	 */
	public abstract Integer getProductNum(String userName);

	/**
	 * Gets the off product num.
	 * 
	 * @param userName
	 *            the user name
	 * @return the off product num
	 */
	public abstract Integer getOffProductNum(String userName);

	/**
	 * 更新产品.
	 * 
	 * @param shopdetail
	 *            the shopdetail
	 */
	public abstract void updateShopDetail(ShopDetail shopdetail);

	/**
	 * 更新产品时更新商城所拥有的产品数.
	 * 
	 * @param product
	 *            the product
	 */
	public abstract void updateShopDetailWhenProductChange(Product product);

	/**
	 * Gets the shop detail by user id.
	 * 
	 * @param userId
	 *            the user id
	 * @return the shop detail by user id
	 */
	public abstract ShopDetail getShopDetailByUserId(String userId);

	/**
	 * Update shop detail.
	 * 
	 * @param userName
	 *            the user name
	 */
	public abstract void updateShopDetail(String userName);


	/**
	 * Update shop.
	 *
	 * @param userId the user id
	 * @param shopDetail the shop detail
	 * @param status the status
	 * @return true, if successful
	 */
	public abstract boolean updateShop(String userId, ShopDetail shopDetail, Integer status);

	/**
	 * Save shop detail.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 */
	public abstract void saveShopDetail(ShopDetail shopDetail);

	/**
	 * Gets the shop detail by shop id.
	 * 
	 * @param shopId
	 *            the shop id
	 * @return the shop detail by shop id
	 */
	public abstract ShopDetail getShopDetailByShopId(final Long shopId);

	/**
	 * Delete shop detail.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 */
	public abstract void deleteShopDetail(ShopDetail shopDetail);

	/**
	 * Gets the all shop count.
	 * 
	 * @return the all shop count
	 */
	public abstract Long getAllShopCount();

	/**
	 * Gets the shop name by domain.
	 *
	 * @param domainName the domain name
	 * @return the shop name by domain
	 */
	public abstract String getShopNameByDomain(String domainName);

	/**
	 * Gets the shop id by name.
	 *
	 * @param userName the user name
	 * @return the shop id by name
	 */
	public abstract Long getShopIdByName(String userName);

}