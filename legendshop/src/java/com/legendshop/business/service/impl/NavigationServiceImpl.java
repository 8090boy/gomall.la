/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import com.legendshop.business.dao.NavigationDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Navigation;
import com.legendshop.spi.service.NavigationService;
import com.legendshop.util.AppUtils;

/**
 * The Class NavigationServiceImpl.
 */
public class NavigationServiceImpl  implements NavigationService{
    private NavigationDao navigationDao;

    public void setNavigationDao(NavigationDao navigationDao) {
        this.navigationDao = navigationDao;
    }

    public Navigation getNavigation(Long id) {
        return navigationDao.getNavigation(id);
    }

    public void deleteNavigation(Navigation navigation) {
        navigationDao.deleteNavigation(navigation);
    }

    public Long saveNavigation(Navigation navigation) {
        if (!AppUtils.isBlank(navigation.getNaviId())) {
            updateNavigation(navigation);
            return navigation.getNaviId();
        }
        return (Long) navigationDao.save(navigation);
    }

    public void updateNavigation(Navigation navigation) {
        navigationDao.updateNavigation(navigation);
    }

    public PageSupport getNavigation(CriteriaQuery cq) {
        return navigationDao.find(cq);
    }
}
