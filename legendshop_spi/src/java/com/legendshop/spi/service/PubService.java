/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Pub;

/**
 * The Interface PubService.
 */
public interface PubService extends BaseService{

	/**
	 * List.
	 * 
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<Pub> getPubList(String userName);

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @return the pub
	 */
	public abstract Pub getPubById(Long id);


	/**
	 * Delete pub.
	 *
	 * @param pub the pub
	 */
	public abstract void deletePub(Pub pub);

	/**
	 * Save.
	 * 
	 * @param pub
	 *            the pub
	 * @param userName
	 *            the user name
	 * @param viewAllDataFunction
	 *            the view all data function
	 * @return the long
	 */
	public abstract Long save(Pub pub, String userName, boolean viewAllDataFunction);

	/**
	 * Update.
	 * 
	 * @param pub
	 *            the pub
	 */
	public abstract void update(Pub pub);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getPubList(CriteriaQuery cq);

}