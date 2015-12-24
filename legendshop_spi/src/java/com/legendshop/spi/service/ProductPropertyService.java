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
import com.legendshop.model.entity.ProductProperty;

/**
 * The Class ProductPropertyService.
 */
public interface ProductPropertyService  {

    public List<ProductProperty> getProductProperty(String userName);

    public ProductProperty getProductProperty(Long id);
    
    public void deleteProductProperty(ProductProperty productProperty);
    
    public Long saveProductProperty(ProductProperty productProperty);

    public void updateProductProperty(ProductProperty productProperty);

    public PageSupport getProductProperty(CriteriaQuery cq);
}
