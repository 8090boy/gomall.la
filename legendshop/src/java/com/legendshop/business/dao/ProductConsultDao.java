/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import java.util.List;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.model.entity.ProductConsult;

/**
 * The Interface ProductConsultDao.
 */
public interface ProductConsultDao extends BaseDao {

	/**
	 * Gets the product consult list.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the product consult list
	 */
	public abstract List<ProductConsult> getProductConsultList(Long prodId);

	/**
	 * Gets the product consult.
	 * 
	 * @param id
	 *            the id
	 * @return the product consult
	 */
	public abstract ProductConsult getProductConsult(Long id);

	/**
	 * Delete product consult.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void deleteProductConsult(Long id);

	/**
	 * Save product consult.
	 * 
	 * @param productConsult
	 *            the product consult
	 * @return the long
	 */
	public abstract Long saveProductConsult(ProductConsult productConsult);

	/**
	 * Update product consult.
	 * 
	 * @param productConsult
	 *            the product consult
	 */
	public abstract void updateProductConsult(ProductConsult productConsult);

	/**
	 * Gets the product consult.
	 * 
	 * @param cq
	 *            the cq
	 * @return the product consult
	 */
	public abstract PageSupport getProductConsult(CriteriaQuery cq);

	/**
	 * Gets the product consult.
	 * 
	 * @param query
	 *            the query
	 * @return the product consult
	 */
	public abstract PageSupport getProductConsult(SimpleSqlQuery query);

	/**
	 * Delete product consult.
	 * 
	 * @param consult
	 *            the consult
	 */
	public abstract void deleteProductConsult(ProductConsult consult);

	/**
	 * Check frequency.
	 * 
	 * @param consult
	 *            the consult
	 * @return the integer
	 */
	public abstract long checkFrequency(ProductConsult consult);

}
