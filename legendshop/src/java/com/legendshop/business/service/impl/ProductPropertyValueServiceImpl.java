/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.business.dao.ProductPropertyValueDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductPropertyValue;
import com.legendshop.spi.service.ProductPropertyValueService;
import com.legendshop.util.AppUtils;

/**
 * The Class ProductPropertyValueServiceImpl.
 */
public class ProductPropertyValueServiceImpl  implements ProductPropertyValueService{
    private ProductPropertyValueDao productPropertyValueDao;

    public void setProductPropertyValueDao(ProductPropertyValueDao productPropertyValueDao) {
        this.productPropertyValueDao = productPropertyValueDao;
    }

    public List<ProductPropertyValue> getProductPropertyValue(String userName) {
        return productPropertyValueDao.getProductPropertyValue(userName);
    }

    public ProductPropertyValue getProductPropertyValue(Long id) {
        return productPropertyValueDao.getProductPropertyValue(id);
    }

    public void deleteProductPropertyValue(ProductPropertyValue productPropertyValue) {
        productPropertyValueDao.deleteProductPropertyValue(productPropertyValue);
    }

    public Long saveProductPropertyValue(ProductPropertyValue productPropertyValue) {
        if (!AppUtils.isBlank(productPropertyValue.getValueId())) {
            updateProductPropertyValue(productPropertyValue);
            return productPropertyValue.getValueId();
        }
        return (Long) productPropertyValueDao.save(productPropertyValue);
    }

    public void updateProductPropertyValue(ProductPropertyValue productPropertyValue) {
        productPropertyValueDao.updateProductPropertyValue(productPropertyValue);
    }

    public PageSupport getProductPropertyValue(CriteriaQuery cq) {
        return productPropertyValueDao.find(cq);
    }
}
