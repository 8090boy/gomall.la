/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.business.dao.SkuDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Sku;
import com.legendshop.spi.service.SkuService;
import com.legendshop.util.AppUtils;

/**
 * The Class SkuServiceImpl.
 */
public class SkuServiceImpl  implements SkuService{
    private SkuDao skuDao;

    public void setSkuDao(SkuDao skuDao) {
        this.skuDao = skuDao;
    }

    public List<Sku> getSku(String userName) {
        return skuDao.getSku(userName);
    }

    public Sku getSku(Long id) {
        return skuDao.getSku(id);
    }

    public void deleteSku(Sku sku) {
        skuDao.deleteSku(sku);
    }

    public Long saveSku(Sku sku) {
        if (!AppUtils.isBlank(sku.getSkuId())) {
            updateSku(sku);
            return sku.getSkuId();
        }
        return (Long) skuDao.save(sku);
    }

    public void updateSku(Sku sku) {
        skuDao.updateSku(sku);
    }

    public PageSupport getSku(CriteriaQuery cq) {
        return skuDao.find(cq);
    }
}
