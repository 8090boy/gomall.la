/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.business.dao.ProductSpecDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductSpec;
import com.legendshop.spi.service.ProductSpecService;
import com.legendshop.util.AppUtils;

/**
 * The Class ProductSpecServiceImpl.
 */
public class ProductSpecServiceImpl  implements ProductSpecService{
    private ProductSpecDao productSpecDao;

    public void setProductSpecDao(ProductSpecDao productSpecDao) {
        this.productSpecDao = productSpecDao;
    }

    public List<ProductSpec> getProductSpec(String userName) {
        return productSpecDao.getProductSpec(userName);
    }

    public ProductSpec getProductSpec(Long id) {
        return productSpecDao.getProductSpec(id);
    }

    public void deleteProductSpec(ProductSpec productSpec) {
        productSpecDao.deleteProductSpec(productSpec);
    }

    public Long saveProductSpec(ProductSpec productSpec) {
        if (!AppUtils.isBlank(productSpec.getProdSpecId())) {
            updateProductSpec(productSpec);
            return productSpec.getProdSpecId();
        }
        return (Long) productSpecDao.save(productSpec);
    }

    public void updateProductSpec(ProductSpec productSpec) {
        productSpecDao.updateProductSpec(productSpec);
    }

    public PageSupport getProductSpec(CriteriaQuery cq) {
        return productSpecDao.find(cq);
    }
}
