/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import java.util.List;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.DeliveryType;

/**
 * 物流配送方式Dao
 */

public interface DeliveryTypeDao extends BaseDao {

	/**
	 * Gets the delivery type.
	 *
	 * @param shopName the shop name
	 * @return the delivery type
	 */
	public abstract List<DeliveryType> getDeliveryType(String shopName);

	/**
	 * Gets the delivery type.
	 *
	 * @param id the id
	 * @return the delivery type
	 */
	public abstract DeliveryType getDeliveryType(Long id);

	/**
	 * Delete delivery type.
	 *
	 * @param deliveryType the delivery type
	 */
	public abstract void deleteDeliveryType(DeliveryType deliveryType);
	
	/**
	 * Delete delivery type.
	 *
	 * @param userName the user name
	 */
	public abstract void deleteDeliveryType(String userName);

	/**
	 * Save delivery type.
	 *
	 * @param deliveryType the delivery type
	 * @return the long
	 */
	public abstract Long saveDeliveryType(DeliveryType deliveryType);

	/**
	 * Update delivery type.
	 *
	 * @param deliveryType the delivery type
	 */
	public abstract void updateDeliveryType(DeliveryType deliveryType);

	/**
	 * Gets the delivery type.
	 *
	 * @param cq the cq
	 * @return the delivery type
	 */
	public abstract PageSupport getDeliveryType(CriteriaQuery cq);

}
