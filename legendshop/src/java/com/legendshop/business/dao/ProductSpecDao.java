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
import com.legendshop.model.entity.ProductSpec;

/**
 * The Class ProductSpecDao.
 */

public interface ProductSpecDao extends BaseDao {
     
    public abstract List<ProductSpec> getProductSpec(String shopName);

	public abstract ProductSpec getProductSpec(Long id);
	
    public abstract void deleteProductSpec(ProductSpec productSpec);
	
	public abstract Long saveProductSpec(ProductSpec productSpec);
	
	public abstract void updateProductSpec(ProductSpec productSpec);
	
	public abstract PageSupport getProductSpec(CriteriaQuery cq);
	
 }
