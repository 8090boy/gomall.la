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
import com.legendshop.model.entity.Sku;

/**
 * The Class SkuService.
 */
public interface SkuService  {

    public List<Sku> getSku(String userName);

    public Sku getSku(Long id);
    
    public void deleteSku(Sku sku);
    
    public Long saveSku(Sku sku);

    public void updateSku(Sku sku);

    public PageSupport getSku(CriteriaQuery cq);
}
