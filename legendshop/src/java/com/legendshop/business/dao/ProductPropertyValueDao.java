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
import com.legendshop.model.entity.ProductPropertyValue;

/**
 * The Class ProductPropertyValueDao.
 */

public interface ProductPropertyValueDao extends BaseDao {
     
    public abstract List<ProductPropertyValue> getProductPropertyValue(String shopName);

	public abstract ProductPropertyValue getProductPropertyValue(Long id);
	
    public abstract void deleteProductPropertyValue(ProductPropertyValue productPropertyValue);
	
	public abstract void saveProductPropertyValue(ProductPropertyValue productPropertyValue);
	
	public abstract void updateProductPropertyValue(ProductPropertyValue productPropertyValue);
	
	public abstract PageSupport getProductPropertyValue(CriteriaQuery cq);
	
 }
