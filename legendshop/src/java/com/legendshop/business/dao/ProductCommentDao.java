/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.model.entity.ProductComment;
import com.legendshop.model.entity.ProductCommentCategory;

/**
 * The Interface ProductCommentDao.
 */
public interface ProductCommentDao extends BaseDao {

	/**
	 * Delete product comment.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 */
	public void deleteProductComment(Long prodId, String userName);

	/**
	 * Save product comment.
	 * 
	 * @param productComment
	 *            the product comment
	 */
	public void saveProductComment(ProductComment productComment);

	/**
	 * Update product comment.
	 * 
	 * @param productComment
	 *            the product comment
	 */
	public void updateProductComment(ProductComment productComment);

	/**
	 * Delete product comment by id.
	 * 
	 * @param id
	 *            the id
	 */
	public void deleteProductCommentById(Long id);

	/**
	 * Inits the product comment category.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the product comment category
	 */
	public ProductCommentCategory initProductCommentCategory(Long prodId);
	

	/**
	 * Validate comment.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the string
	 */
	public abstract String validateComment(Long prodId, String userName);
}