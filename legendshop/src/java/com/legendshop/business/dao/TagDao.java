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
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.model.entity.Tag;

/**
 * The Class TagDao.
 */

public interface TagDao extends BaseDao {

	/**
	 * Gets the page tag.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param page
	 *            the page
	 * @return the page tag
	 */
	public abstract List<Tag> getPageTag(String shopName, String page);

	/**
	 * Gets the tag.
	 * 
	 * @param hql
	 *            the hql
	 * @return the tag
	 */
	public abstract PageSupport getTag(SimpleHqlQuery hql);

	/**
	 * Gets the tag.
	 * 
	 * @param id
	 *            the id
	 * @return the tag
	 */
	public abstract Tag getTag(Long id);

	/**
	 * Delete tag.
	 * 
	 * @param tag
	 *            the tag
	 */
	public abstract void deleteTag(Tag tag);

	/**
	 * Save tag.
	 * 
	 * @param tag
	 *            the tag
	 * @return the long
	 */
	public abstract Long saveTag(Tag tag);

	/**
	 * Update tag.
	 * 
	 * @param tag
	 *            the tag
	 */
	public abstract void updateTag(Tag tag);

	/**
	 * Gets the tag.
	 * 
	 * @param cq
	 *            the cq
	 * @return the tag
	 */
	public abstract PageSupport getTag(CriteriaQuery cq);

	/**
	 * Gets the tag.
	 * 
	 * @param name
	 *            the name
	 * @param userName
	 *            the user name
	 * @return the tag
	 */
	public abstract Tag getTag(String name, String userName);

}
