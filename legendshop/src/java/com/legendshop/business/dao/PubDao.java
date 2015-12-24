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
import com.legendshop.model.entity.Pub;

/**
 * 公告Dao.
 */
public interface PubDao extends BaseDao {

	/**
	 * Gets the pub.
	 * 
	 * @param shopName
	 *            the shop name
	 * @return the pub
	 */
	public abstract List<Pub> getPub(final String shopName);


	/**
	 * Delete pub.
	 *
	 * @param pub the pub
	 */
	public abstract void deletePub(Pub pub);

	/**
	 * Update pub.
	 * 
	 * @param pub
	 *            the pub
	 */
	public abstract void updatePub(Pub pub);


	/**
	 * Save pub.
	 *
	 * @param pub the pub
	 * @return the long
	 */
	public abstract Long savePub(Pub pub);

}