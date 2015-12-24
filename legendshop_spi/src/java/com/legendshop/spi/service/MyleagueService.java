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
import com.legendshop.model.entity.Myleague;

/**
 * The Interface MyleagueService.
 */
public interface MyleagueService {

	/**
	 * List.
	 * 
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<Myleague> getMyleagueList(String userName);

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @return the myleague
	 */
	public abstract Myleague getMyleagueById(Long id);

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void delete(Long id);

	/**
	 * Save.
	 * 
	 * @param myleague
	 *            the myleague
	 * @return the long
	 */
	public abstract Long save(Myleague myleague);

	/**
	 * Update.
	 * 
	 * @param myleague
	 *            the myleague
	 */
	public abstract void update(Myleague myleague);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getMyleagueList(CriteriaQuery cq);
	
	/**
	 * Gets the myleague.
	 *
	 * @param userName the user name
	 * @param hopName the hop name
	 * @return the myleague
	 */
	public abstract  Myleague getMyleague(String userName, String shopName);

}