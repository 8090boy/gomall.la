/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.service.ShopService;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.UserDetail;

/**
 * The Interface ShopDetailService.
 */
public interface ShopDetailService extends ShopService {

	/**
	 * Find shop detail.
	 * 
	 * @param id
	 *            the id
	 * @return the shop detail
	 */
	public abstract ShopDetail getShopDetailById(Long id);
	
	/**
	 * Gets the shop detail by shop id.
	 *
	 * @param shopId the shop id
	 * @return the shop detail by shop id
	 */
	public abstract ShopDetail getShopDetailByShopId(final Long shopId);

	/**
	 * Find user detail by name.
	 * 
	 * @param userName
	 *            the user name
	 * @return the user detail
	 */
	public abstract UserDetail getShopDetailByName(String userName);

	/**
	 * Delete.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 */
	public abstract void delete(ShopDetail shopDetail);

	// UserId 一定不能为空
	/**
	 * Save.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 */
	public abstract void save(ShopDetail shopDetail);

	/**
	 * Update.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 */
	public abstract void update(ShopDetail shopDetail);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getShopDetail(CriteriaQuery cq);

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
	 * @param product
	 *            the product
	 */
	public abstract void updateShopDetail(Product product);

	/**
	 * 审核商城.
	 *
	 * @param userId the user id
	 * @param shopDetail the shop detail
	 * @param status 状态,是否上线1：在线，0下线，-1审核中,-2拒绝,-3关闭（管理员操作）
	 * @return true, if successful
	 */
	public abstract boolean updateShop(String userId, ShopDetail shopDetail, Integer status);

	/**
	 * 根据用户名称加载ShopId
	 *
	 * @param userName the user name
	 * @return the shop id by name
	 */
	public abstract Long getShopIdByName(String userName);

}