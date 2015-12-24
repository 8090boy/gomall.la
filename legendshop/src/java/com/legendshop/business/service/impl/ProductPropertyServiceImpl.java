/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.business.dao.ProductPropertyDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductProperty;
import com.legendshop.spi.service.ProductPropertyService;
import com.legendshop.util.AppUtils;

/**
 * The Class ProductPropertyServiceImpl.
 */
public class ProductPropertyServiceImpl  implements ProductPropertyService{
    private ProductPropertyDao productPropertyDao;

    public void setProductPropertyDao(ProductPropertyDao productPropertyDao) {
        this.productPropertyDao = productPropertyDao;
    }

    public List<ProductProperty> getProductProperty(String userName) {
        return productPropertyDao.getProductProperty(userName);
    }

    public ProductProperty getProductProperty(Long id) {
        return productPropertyDao.getProductProperty(id);
    }

    public void deleteProductProperty(ProductProperty productProperty) {
        productPropertyDao.deleteProductProperty(productProperty);
    }

    public Long saveProductProperty(ProductProperty productProperty) {
        if (!AppUtils.isBlank(productProperty.getPropId())) {
            updateProductProperty(productProperty);
            return productProperty.getPropId();
        }
        return (Long) productPropertyDao.saveProductProperty(productProperty);
    }

    public void updateProductProperty(ProductProperty productProperty) {
        productPropertyDao.updateProductProperty(productProperty);
    }

    public PageSupport getProductProperty(CriteriaQuery cq) {
        return productPropertyDao.find(cq);
    }
}
