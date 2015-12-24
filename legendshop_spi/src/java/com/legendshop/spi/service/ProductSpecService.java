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
import com.legendshop.model.entity.ProductSpec;

/**
 * The Class ProductSpecService.
 */
public interface ProductSpecService  {

    public List<ProductSpec> getProductSpec(String userName);

    public ProductSpec getProductSpec(Long id);
    
    public void deleteProductSpec(ProductSpec productSpec);
    
    public Long saveProductSpec(ProductSpec productSpec);

    public void updateProductSpec(ProductSpec productSpec);

    public PageSupport getProductSpec(CriteriaQuery cq);
}
