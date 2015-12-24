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
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.model.entity.Hotsearch;

/**
 * The Interface HotsearchService.
 */
public interface HotsearchService {

	/**
	 * List.
	 * 
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<Hotsearch> getHotsearch(String userName);

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @return the hotsearch
	 */
	public abstract Hotsearch getHotsearchById(Long id);

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @param userName
	 *            the user name
	 * @return the hotsearch
	 */
	public abstract Hotsearch getHotsearchByIdAndName(Integer id, String userName);

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void delete(Long id);


	/**
	 * Save hotsearch.
	 *
	 * @param hotsearch the hotsearch
	 * @return the long
	 */
	public abstract Long saveHotsearch(Hotsearch hotsearch);

	/**
	 * Update.
	 * 
	 * @param hotsearch
	 *            the hotsearch
	 */
	public abstract void updateHotsearch(Hotsearch hotsearch);

	/**
	 * Gets the search.
	 *
	 * @param sortId the sort id
	 * @return the search
	 */
	public abstract List<Hotsearch> getHotsearch(Long sortId);

	/**
	 * Query.
	 *
	 * @param query the query
	 * @return the page support
	 */
	public abstract PageSupport query(SimpleSqlQuery query);

}