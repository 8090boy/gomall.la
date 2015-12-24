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
import com.legendshop.model.entity.NavigationItem;

/**
 * 导航管理Dao.
 */

public interface NavigationItemDao extends BaseDao {
     
    /**
     * Gets the navigation item.
     *
     * @return the navigation item
     */
    public abstract List<NavigationItem> getNavigationItem();
    
    /**
     * Gets the all navigation item.
     *
     * @return the all navigation item
     */
    public abstract List<NavigationItem> getAllNavigationItem();

	/**
	 * Gets the navigation item.
	 *
	 * @param id the id
	 * @return the navigation item
	 */
	public abstract NavigationItem getNavigationItem(Long id);
	
    /**
     * Delete navigation item.
     *
     * @param navigationItem the navigation item
     */
    public abstract void deleteNavigationItem(NavigationItem navigationItem);
	
	/**
	 * Save navigation item.
	 *
	 * @param navigationItem the navigation item
	 * @return the long
	 */
	public abstract Long saveNavigationItem(NavigationItem navigationItem);
	
	/**
	 * Update navigation item.
	 *
	 * @param navigationItem the navigation item
	 */
	public abstract void updateNavigationItem(NavigationItem navigationItem);
	
	/**
	 * Gets the navigation item.
	 *
	 * @param cq the cq
	 * @return the navigation item
	 */
	public abstract PageSupport getNavigationItem(CriteriaQuery cq);
	
	/**
	 * Gets the navigation item by navi id.
	 *
	 * @param Navi_id the navi_id
	 * @return the navigation item by navi id
	 */
	public abstract List<NavigationItem> getNavigationItemByNaviId(Long Navi_id);
	
	/**
	 * Delete navigation items.
	 *
	 * @param naviId the navi id
	 */
	public abstract void deleteNavigationItems(Long naviId);
 }
