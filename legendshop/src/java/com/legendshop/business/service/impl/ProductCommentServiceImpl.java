/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.ProductCommentDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductComment;
import com.legendshop.model.entity.ProductCommentCategory;
import com.legendshop.spi.service.ProductCommentService;
import com.legendshop.util.AppUtils;

/**
 * 产品评论服务.
 */
public class ProductCommentServiceImpl implements ProductCommentService {

	/** 产品评论Dao. */
	private ProductCommentDao productCommentDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductCommentService#load(java.lang.
	 * Long)
	 */
	@Override
	public ProductComment getProductCommentById(Long id) {
		return productCommentDao.get(ProductComment.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductCommentService#delete(java.lang
	 * .Long)
	 */
	@Override
	public void delete(Long id) {
		productCommentDao.deleteProductCommentById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductCommentService#save(com.legendshop
	 * .model.entity.ProductComment)
	 */
	@Override
	public Long save(ProductComment productComment) {
		if (!AppUtils.isBlank(productComment.getId())) {
			update(productComment);
			return productComment.getId();
		}
		return (Long) productCommentDao.save(productComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductCommentService#update(com.legendshop
	 * .model.entity.ProductComment)
	 */
	@Override
	public void update(ProductComment productComment) {
		productCommentDao.updateProductComment(productComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.timer.SubService#validateComment(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	public String validateComment(Long prodId, String userName) {
		return productCommentDao.validateComment(prodId, userName);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductCommentService#getDataByCriteriaQuery
	 * (com.legendshop.core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getProductCommentList(CriteriaQuery cq) {
		return productCommentDao.find(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductCommentService#getDataByCriteriaQuery
	 * (com.legendshop.core.dao.support.HqlQuery)
	 */
	@Override
	public PageSupport getProductCommentList(HqlQuery hql) {
		return productCommentDao.find(hql);
	}

	/**
	 * Sets the product comment dao.
	 * 
	 * @param productCommentDao
	 *            the new product comment dao
	 */
	@Required
	public void setProductCommentDao(ProductCommentDao productCommentDao) {
		this.productCommentDao = productCommentDao;
	}

	@Override
	public ProductCommentCategory initProductCommentCategory(Long prodId) {
		return productCommentDao.initProductCommentCategory(prodId);
	}
	

}
