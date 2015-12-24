package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.spi.service.NavigationItemService;
import com.legendshop.util.AppUtils;
import com.legendshop.business.dao.NavigationItemDao;
import com.legendshop.model.entity.NavigationItem;


public class NavigationItemServiceImpl  implements NavigationItemService{
    private NavigationItemDao navigationItemDao;

    public void setNavigationItemDao(NavigationItemDao navigationItemDao) {
        this.navigationItemDao = navigationItemDao;
    }

    public List<NavigationItem> getNavigationItem() {
        return navigationItemDao.getNavigationItem();
    }

    public NavigationItem getNavigationItem(Long id) {
        return navigationItemDao.getNavigationItem(id);
    }

    public void deleteNavigationItem(NavigationItem navigationItem) {
        navigationItemDao.deleteNavigationItem(navigationItem);
    }

    public Long saveNavigationItem(NavigationItem navigationItem) {
        if (!AppUtils.isBlank(navigationItem.getItemId())) {
            updateNavigationItem(navigationItem);
            return navigationItem.getItemId();
        }
        return (Long) navigationItemDao.save(navigationItem);
    }

    public void updateNavigationItem(NavigationItem navigationItem) {
        navigationItemDao.updateNavigationItem(navigationItem);
    }

    public PageSupport getNavigationItem(CriteriaQuery cq) {
        return navigationItemDao.find(cq);
    }

	@Override
	public List<NavigationItem> getNavigationItemByNaviId(Long Navi_id) {
		return navigationItemDao.getNavigationItemByNaviId(Navi_id);
	}

	@Override
	public void deleteNavigationItems(Long naviId) {
		navigationItemDao.deleteNavigationItems(naviId);		
	}

	@Override
	public List<NavigationItem> getAllNavigationItem() {
		return navigationItemDao.getAllNavigationItem();
	}
}
