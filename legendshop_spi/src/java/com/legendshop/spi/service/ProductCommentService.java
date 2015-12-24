/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductComment;
import com.legendshop.model.entity.ProductCommentCategory;

/**
 * The Interface ProductCommentService.
 */
public interface ProductCommentService {

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @return the product comment
	 */
	public abstract ProductComment getProductCommentById(Long id);

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void delete(Long id);

	/**
	 * Save.
	 * 
	 * @param productComment
	 *            the product comment
	 * @return the long
	 */
	public abstract Long save(ProductComment productComment);

	/**
	 * Update.
	 * 
	 * @param productComment
	 *            the product comment
	 */
	public abstract void update(ProductComment productComment);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getProductCommentList(CriteriaQuery cq);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param hql
	 *            the hql
	 * @return the data by criteria query
	 */
	public abstract PageSupport getProductCommentList(HqlQuery hql);

	/**
	 * Inits the product comment category.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the product comment category
	 */
	public abstract ProductCommentCategory initProductCommentCategory(Long prodId);
	
	/**
	 * Validate comment.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the string
	 */
	public String validateComment(Long prodId, String userName);

}