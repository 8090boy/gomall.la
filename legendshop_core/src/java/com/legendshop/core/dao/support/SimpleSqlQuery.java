/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.io.Serializable;

import com.legendshop.core.constant.PageProviderEnum;

/**
 * The Class SimpleSqlQuery.
 */
public class SimpleSqlQuery extends AbstractQuery implements Serializable {

	private static final long serialVersionUID = 8858066913104616330L;

	/** The query string. */
	protected String queryString;

	/** The all count string. */
	protected String allCountString;

	/** The param. */
	protected Object[] param;

	/** The entity class. */
	private Class<?> entityClass;

	/**
	 * Instantiates a new simple sql query.
	 * 
	 * @param queryString
	 *            the query string
	 * @param allCountString
	 *            the all count string
	 * @param param
	 *            the param
	 */
	public SimpleSqlQuery(Class<?> entityClass, String queryString, String allCountString, Object[] param) {
		this.entityClass = entityClass;
		this.queryString = queryString;
		this.allCountString = allCountString;
		this.param = param;
	}

	/**
	 * Instantiates a new sql query.
	 * 
	 * @param pageSize
	 *            the page size
	 * @param curPage
	 *            the cur page
	 * @param pageProvider
	 *            the page provider
	 */
	public SimpleSqlQuery(int pageSize, String curPage, PageProviderEnum pageProvider) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.pageProvider = pageProvider;
	}

	/**
	 * Instantiates a new simple sql query.
	 * 
	 * @param pageSize
	 *            the page size
	 * @param curPage
	 *            the cur page
	 */
	public SimpleSqlQuery(int pageSize, String curPage) {
		this(pageSize, curPage, PageProviderEnum.PAGE_PROVIDER);
	}

	/**
	 * Instantiates a new sql query.
	 * 
	 * @param myaction
	 *            the myaction
	 */
	public SimpleSqlQuery(String myaction) {
		this.myaction = myaction;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * @param queryString
	 *            the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * @return the allCountString
	 */
	public String getAllCountString() {
		return allCountString;
	}

	/**
	 * @param allCountString
	 *            the allCountString to set
	 */
	public void setAllCountString(String allCountString) {
		this.allCountString = allCountString;
	}

	/**
	 * @return the param
	 */
	public Object[] getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(Object[] param) {
		this.param = param;
	}

	/**
	 * @return the entityClass
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass
	 *            the entityClass to set
	 */
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

}