/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.model.entity.Navigation;
import com.legendshop.model.entity.NavigationItem;
import com.legendshop.business.dao.NavigationDao;
import com.legendshop.business.dao.NavigationItemDao;
/**
 * The Class NavigationDaoImpl.
 */

public class NavigationDaoImpl extends BaseDaoImpl implements NavigationDao {
    private static Logger log = LoggerFactory.getLogger(NavigationDaoImpl.class);
     
    private NavigationItemDao navigationItemDao;
    
    public void setNavigationItemDao(NavigationItemDao navigationItemDao) {
		this.navigationItemDao = navigationItemDao;
	}

	public Navigation getNavigation(Long id){
		return get(Navigation.class, id);
	}
	
	@CacheEvict(value = "NavigationList", allEntries = true)
    public void deleteNavigation(Navigation navigation){
		navigationItemDao.deleteNavigationItems(navigation.getNaviId());
    	delete(navigation);
    }
	
	@CacheEvict(value = "NavigationList", allEntries = true)
	public Long saveNavigation(Navigation navigation){
		return (Long)save(navigation);
	}
	
	@CacheEvict(value = "NavigationList", allEntries = true)
	public void updateNavigation(Navigation navigation){
		 update(navigation);
	}
	
	public PageSupport getNavigation(CriteriaQuery cq){
		return find(cq);
	}

	@Override
	@Cacheable(value = "NavigationList")
	public List<Navigation> getNavigationList() {
		List<Navigation> list=findByHQL("from Navigation where status = 1");
		//load 第二级导航
		List<NavigationItem> navigationItemList = navigationItemDao.getNavigationItem(); // 2级导航
		//将2级导航加入1级导航中
		for (Navigation navi : list) {
			for (NavigationItem navigationItem : navigationItemList) {
				navi.addSubItems(navigationItem);
			}
		}
		return list;
	}

	
 }
