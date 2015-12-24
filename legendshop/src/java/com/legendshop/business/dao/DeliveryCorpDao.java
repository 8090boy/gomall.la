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
import com.legendshop.model.entity.DeliveryCorp;

/**
 * The Class DeliveryCorpDao.
 */

public interface DeliveryCorpDao extends BaseDao {

	/**
	 * Gets the delivery corp.
	 *
	 * @param shopName the shop name
	 * @return the delivery corp
	 */
	public abstract List<DeliveryCorp> getDeliveryCorp(String shopName);

	/**
	 * Gets the delivery corp.
	 *
	 * @param id the id
	 * @return the delivery corp
	 */
	public abstract DeliveryCorp getDeliveryCorp(Long id);

	/**
	 * Delete delivery corp.
	 *
	 * @param deliveryCorp the delivery corp
	 */
	public abstract void deleteDeliveryCorp(DeliveryCorp deliveryCorp);

	/**
	 * Save delivery corp.
	 *
	 * @param deliveryCorp the delivery corp
	 * @return the long
	 */
	public abstract Long saveDeliveryCorp(DeliveryCorp deliveryCorp);

	/**
	 * Update delivery corp.
	 *
	 * @param deliveryCorp the delivery corp
	 */
	public abstract void updateDeliveryCorp(DeliveryCorp deliveryCorp);

	/**
	 * Gets the delivery corp.
	 *
	 * @param cq the cq
	 * @return the delivery corp
	 */
	public abstract PageSupport getDeliveryCorp(CriteriaQuery cq);

	/**
	 * Delete delivery corp.
	 *
	 * @param userName the user name
	 */
	public abstract void deleteDeliveryCorp(String userName);

}
