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

import com.legendshop.business.dao.SkuDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Sku;
/**
 * The Class SkuDaoImpl.
 */

public class SkuDaoImpl extends BaseDaoImpl implements SkuDao {
    private static Logger log = LoggerFactory.getLogger(SkuDaoImpl.class);
     
    public List<Sku> getSku(String userName){
   		return findByHQL("from Sku where userName = ?", userName);
    }

	public Sku getSku(Long id){
		return get(Sku.class, id);
	}
	
    public void deleteSku(Sku sku){
    	delete(sku);
    }
	
	public Long saveSku(Sku sku){
		return (Long)save(sku);
	}
	
	public void updateSku(Sku sku){
		 update(sku);
	}
	
	public PageSupport getSku(CriteriaQuery cq){
		return find(cq);
	}
	
 }
