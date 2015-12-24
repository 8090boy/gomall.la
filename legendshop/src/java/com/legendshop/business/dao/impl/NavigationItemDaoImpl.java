/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.business.dao.NavigationItemDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.NavigationItem;
/**
 * The Class NavigationItemDaoImpl.
 */

public class NavigationItemDaoImpl extends BaseDaoImpl implements NavigationItemDao {
    private static Logger log = LoggerFactory.getLogger(NavigationItemDaoImpl.class);
     
    public List<NavigationItem> getNavigationItem(){
   		return findByHQL("from NavigationItem where status = 1");
    }
    
    public List<NavigationItem> getAllNavigationItem(){
   		return findByHQL("from NavigationItem");
    }

	public NavigationItem getNavigationItem(Long id){
		return get(NavigationItem.class, id);
	}
	
    public void deleteNavigationItem(NavigationItem navigationItem){
    	delete(navigationItem);
    }
	
	public Long saveNavigationItem(NavigationItem navigationItem){
		return (Long)save(navigationItem);
	}
	
	public void updateNavigationItem(NavigationItem navigationItem){
		 update(navigationItem);
	}
	
	public PageSupport getNavigationItem(CriteriaQuery cq){
		return find(cq);
	}

	@Override
	public List<NavigationItem> getNavigationItemByNaviId(Long Navi_id) {
		return findByHQL("from NavigationItem where naviId=?",Navi_id);
	}

	@Override
	public void deleteNavigationItems(Long naviId) {
		exeByHQL("delete from NavigationItem where naviId = ?", naviId);
	}

	
	
 }
