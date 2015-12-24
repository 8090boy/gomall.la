/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.business.dao.ProductPropertyDao;
import com.legendshop.business.dao.ProductPropertyValueDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductProperty;
import com.legendshop.model.entity.ProductPropertyValue;
/**
 * The Class ProductPropertyDaoImpl.
 */

public class ProductPropertyDaoImpl extends BaseDaoImpl implements ProductPropertyDao {
    private static Logger log = LoggerFactory.getLogger(ProductPropertyDaoImpl.class);
    
    private ProductPropertyValueDao productPropertyValueDao;
     
    public List<ProductProperty> getProductProperty(String userName){
   		return findByHQL("from ProductProperty where userName = ?", userName);
    }

	public ProductProperty getProductProperty(Long id){
		return get(ProductProperty.class, id);
	}
	
    public void deleteProductProperty(ProductProperty productProperty){
    	delete(productProperty);
    }
	
	public Long saveProductProperty(ProductProperty productProperty){
		Long propId =  (Long)save(productProperty);
		List<ProductPropertyValue> propValueList = productProperty.getProductPropertyValueList();
		if(propValueList != null){
			for (ProductPropertyValue productPropertyValue : propValueList) {
				productPropertyValue.setPropId(propId);
				productPropertyValueDao.saveProductPropertyValue(productPropertyValue);
			}
		}
		return propId;
	}
	
	public void updateProductProperty(ProductProperty productProperty){
		 update(productProperty);
	}
	
	public PageSupport getProductProperty(CriteriaQuery cq){
		return find(cq);
	}

	/**
	 * @param productPropertyValueDao the productPropertyValueDao to set
	 */
	public void setProductPropertyValueDao(ProductPropertyValueDao productPropertyValueDao) {
		this.productPropertyValueDao = productPropertyValueDao;
	}
	
 }
