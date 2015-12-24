/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.legendshop.business.dao.ProductConsultDao;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.QueryMap;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductConsult;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.service.ProductConsultService;
import com.legendshop.util.SafeHtml;
import com.legendshop.util.sql.ConfigCode;

/**
 * The Class ProductConsultServiceImpl.
 */
public class ProductConsultServiceImpl extends BaseServiceImpl implements ProductConsultService {

	/** The product consult dao. */
	private ProductConsultDao productConsultDao;

	/** 产品Dao. */
	private ProductDao productDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductConsultService#getProductConsultList
	 * (java.lang.Long)
	 */
	@Override
	public List<ProductConsult> getProductConsultList(Long prodId) {
		return productConsultDao.getProductConsultList(prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductConsultService#getProductConsult
	 * (java.lang.Long)
	 */
	@Override
	public ProductConsult getProductConsult(Long id) {
		return productConsultDao.getProductConsult(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductConsultService#deleteProductConsult
	 * (java.lang.Long)
	 */
	@Override
	public String deleteProductConsult(HttpServletRequest request, Long consId) {
		String userName = UserManager.getUserName(request.getSession());
		ProductConsult consult = productConsultDao.getProductConsult(consId);
		Product product = productDao.getProduct(consult.getProdId());
		String result = checkPrivilege(request, userName, product.getUserName());
		if (result == null) {// 没有错的前提
			productConsultDao.deleteProductConsult(consult);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductConsultService#saveProductConsult
	 * (com.legendshop.model.entity.ProductConsult)
	 */
	@Override
	public Long saveProductConsult(ProductConsult productConsult) {
		return productConsultDao.saveProductConsult(productConsult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductConsultService#updateProductConsult
	 * (com.legendshop.model.entity.ProductConsult)
	 */
	@Override
	public String updateProductConsult(HttpServletRequest request, ProductConsult productConsult) {
		ProductConsult consult = productConsultDao.getProductConsult(productConsult.getConsId());
		if (consult == null) {
			throw new NotFoundException("ProductConsult Not Found");
		}
		String userName = UserManager.getUserName(request.getSession());
		Product product = productDao.getProduct(consult.getProdId());
		String result = checkPrivilege(request, userName, product.getUserName());
		if (result == null) {
			SafeHtml safe = new SafeHtml();
			consult.setAnswer(safe.makeSafe(productConsult.getAnswer()));
			consult.setAnsUserName(userName);
			consult.setAnswertime(new Date());
			productConsultDao.updateProductConsult(consult);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductConsultService#getProductConsult
	 * (com.legendshop.core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getProductConsult(String curPageNO, ProductConsult productConsult) {
		return productConsultDao.getProductConsult(resolveQuery(curPageNO,productConsult));
	}

	@Override
	public PageSupport getProductConsultForList(String curPageNO, Integer pointType, Long prodId) {
		QueryMap map = new QueryMap();
		if (pointType != null && pointType != 0) {
			map.put("pointType", pointType);
		}
		map.put("prodId", prodId);
		String queryAllSQL = ConfigCode.getInstance().getCode("prod.getProductConsultListCount", map);
		String querySQL = ConfigCode.getInstance().getCode("prod.getProductConsultList", map);
		SimpleSqlQuery query = new SimpleSqlQuery(ProductConsult.class, querySQL, queryAllSQL, map.toArray());
		query.setMyaction("javascript:consultPager");
		query.setCurPage(curPageNO);
		query.setPageProvider(PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		return productConsultDao.getProductConsult(query);
	}
	
	private SimpleSqlQuery resolveQuery(String curPageNO, ProductConsult productConsult) {
		QueryMap map = new QueryMap();
		map.put("pointType", productConsult.getPointType());
		// 用户已经回复
		if (productConsult.getReplyed() != null) {
			if (productConsult.getReplyed() == 0) {
				map.put("replyed", "and pc.answer is null");
			} else {
				map.put("replyed", "and pc.answer is not null");
			}
		} else {// by default
			map.put("replyed", "and pc.answer is null");
		}

		map.hasAllDataFunction("userName", productConsult.getUserName());
		map.like("prodName", productConsult.getProdName());
		map.put("startTime", productConsult.getStartTime());
		map.put("endTime", productConsult.getEndTime());

		String queryAllSQL = ConfigCode.getInstance().getCode("prod.getProductConsultCount", map);
		String querySQL = ConfigCode.getInstance().getCode("prod.getProductConsult", map);
		map.remove("replyed");// 不作为参数
		Object[] params = map.toArray();
		SimpleSqlQuery query = new SimpleSqlQuery(ProductConsult.class, querySQL, queryAllSQL, params);

		query.parseExportPageSize();
		query.setCurPage(curPageNO);
		query.setPageProvider(PageProviderEnum.PAGE_PROVIDER);
		return query;
	}
	/**
	 * Sets the product consult dao.
	 * 
	 * @param productConsultDao
	 *            the productConsultDao to set
	 */
	public void setProductConsultDao(ProductConsultDao productConsultDao) {
		this.productConsultDao = productConsultDao;
	}

	@Override
	public PageSupport getProductConsult(CriteriaQuery query) {
		return productConsultDao.getProductConsult(query);
	}

	/**
	 * @param productDao
	 *            the productDao to set
	 */
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public long checkFrequency(ProductConsult consult) {
		return productConsultDao.checkFrequency(consult);
	}



}
