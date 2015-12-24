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
import com.legendshop.model.entity.Sku;

/**
 * The Class SkuDao.
 */

public interface SkuDao extends BaseDao {
     
    public abstract List<Sku> getSku(String shopName);

	public abstract Sku getSku(Long id);
	
    public abstract void deleteSku(Sku sku);
	
	public abstract Long saveSku(Sku sku);
	
	public abstract void updateSku(Sku sku);
	
	public abstract PageSupport getSku(CriteriaQuery cq);
	
 }
