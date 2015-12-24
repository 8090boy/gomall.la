/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import com.legendshop.core.dao.BaseDao;
import java.util.List;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Navigation;

/**
 * The Class NavigationDao.
 */

public interface NavigationDao extends BaseDao {

	/**
	 * Gets the navigation.
	 *
	 * @param id the id
	 * @return the navigation
	 */
	public abstract Navigation getNavigation(Long id);

	/**
	 * Delete navigation.
	 *
	 * @param navigation the navigation
	 */
	public abstract void deleteNavigation(Navigation navigation);

	/**
	 * Save navigation.
	 *
	 * @param navigation the navigation
	 * @return the long
	 */
	public abstract Long saveNavigation(Navigation navigation);

	/**
	 * Update navigation.
	 *
	 * @param navigation the navigation
	 */
	public abstract void updateNavigation(Navigation navigation);

	/**
	 * Gets the navigation.
	 *
	 * @param cq the cq
	 * @return the navigation
	 */
	public abstract PageSupport getNavigation(CriteriaQuery cq);

	/**
	 * Gets the navigation list.
	 *
	 * @return the navigation list
	 */
	public abstract List<Navigation> getNavigationList();
}
