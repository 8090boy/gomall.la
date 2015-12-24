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
import com.legendshop.model.entity.ProductPropertyValue;

/**
 * The Class ProductPropertyValueService.
 */
public interface ProductPropertyValueService  {

    public List<ProductPropertyValue> getProductPropertyValue(String userName);

    public ProductPropertyValue getProductPropertyValue(Long id);
    
    public void deleteProductPropertyValue(ProductPropertyValue productPropertyValue);
    
    public Long saveProductPropertyValue(ProductPropertyValue productPropertyValue);

    public void updateProductPropertyValue(ProductPropertyValue productPropertyValue);

    public PageSupport getProductPropertyValue(CriteriaQuery cq);
}
