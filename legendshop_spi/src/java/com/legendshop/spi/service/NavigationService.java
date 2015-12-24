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
import com.legendshop.model.entity.Navigation;

/**
 * The Class NavigationService.
 */
public interface NavigationService  {

    public Navigation getNavigation(Long id);
    
    public void deleteNavigation(Navigation navigation);
    
    public Long saveNavigation(Navigation navigation);

    public void updateNavigation(Navigation navigation);

    public PageSupport getNavigation(CriteriaQuery cq);
}
