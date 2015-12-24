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
import com.legendshop.model.entity.ProductProperty;

/**
 * The Class ProductPropertyDao.
 */

public interface ProductPropertyDao extends BaseDao {
     
    public abstract List<ProductProperty> getProductProperty(String shopName);

	public abstract ProductProperty getProductProperty(Long id);
	
    public abstract void deleteProductProperty(ProductProperty productProperty);
	
	public abstract Long saveProductProperty(ProductProperty productProperty);
	
	public abstract void updateProductProperty(ProductProperty productProperty);
	
	public abstract PageSupport getProductProperty(CriteriaQuery cq);
	
 }
