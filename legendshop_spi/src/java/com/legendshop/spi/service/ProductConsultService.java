/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductConsult;

/**
 * The Interface ProductConsultService.
 */
public interface ProductConsultService extends BaseService {

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
	 * @param request
	 *            the request
	 * @param id
	 *            the id
	 * @return the string
	 */
	public abstract String deleteProductConsult(HttpServletRequest request, Long id);

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
	 * @param request
	 *            the request
	 * @param productConsult
	 *            the product consult
	 * @return the string
	 */
	public String updateProductConsult(HttpServletRequest request, ProductConsult productConsult);

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
	 * @param curPageNO the cur page no
	 * @param productConsult the product consult
	 * @return the product consult
	 */
	public abstract PageSupport getProductConsult(String curPageNO, ProductConsult productConsult);

	/**
	 * Check frequency. 检查用户发布频率
	 * 
	 * @param consult
	 *            the consult
	 * @return the integer
	 */
	public abstract long checkFrequency(ProductConsult consult);

	/**
	 * Gets the product consult for list.
	 *
	 * @param curPageNO the cur page no
	 * @param pointType the point type
	 * @param prodId the prod id
	 * @return the product consult for list
	 */
	public abstract PageSupport getProductConsultForList(String curPageNO, Integer pointType, Long prodId);

}
