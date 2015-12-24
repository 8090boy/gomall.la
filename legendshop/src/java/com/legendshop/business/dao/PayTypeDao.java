/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.model.entity.PayType;

/**
 * The Interface PayTypeDao.
 */
public interface PayTypeDao extends BaseDao {

	/**
	 * Update pay type.
	 * 
	 * @param payType
	 *            the pay type
	 */
	void updatePayType(PayType payType);

	/**
	 * Delete pay type by id.
	 * 
	 * @param id
	 *            the id
	 */
	void deletePayTypeById(Long id);

	/**
	 * 查找支付方式
	 *
	 * @param userName the user name
	 * @param payTypeId the pay type id
	 * @return the pay type by id and name
	 */
	PayType getPayTypeByIdAndName(String userName, String payTypeId);

}