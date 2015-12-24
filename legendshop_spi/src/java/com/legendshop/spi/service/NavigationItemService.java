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
import com.legendshop.model.entity.NavigationItem;

/**
 * The Class NavigationItemService.
 */
public interface NavigationItemService  {

    public List<NavigationItem> getNavigationItem();
    
    public List<NavigationItem> getAllNavigationItem();

    public NavigationItem getNavigationItem(Long id);
    
    public void deleteNavigationItem(NavigationItem navigationItem);
    
    public Long saveNavigationItem(NavigationItem navigationItem);

    public void updateNavigationItem(NavigationItem navigationItem);

    public PageSupport getNavigationItem(CriteriaQuery cq);
    
    public List<NavigationItem> getNavigationItemByNaviId(Long Navi_id);
    
    public void deleteNavigationItems(Long naviId);
}
