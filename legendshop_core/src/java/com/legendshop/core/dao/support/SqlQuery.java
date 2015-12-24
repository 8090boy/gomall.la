/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legendshop.core.constant.PageProviderEnum;

/**
 * Hibernate SQL.
 */
public class SqlQuery extends AbstractSQLQuery implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8185266081416916752L;

	/** The entity class. */
	private Map<String, Class> entityClass;

	/**
	 * Instantiates a new sql query.
	 * 
	 * @param queryString
	 *            the query string
	 * @param allCountString
	 *            the all count string
	 * @param params
	 *            the params
	 */
	public SqlQuery(String queryString, String allCountString, List params) {
		this.queryString = queryString;
		this.allCountString = allCountString;
		this.params = params;
	}

	/**
	 * Instantiates a new sql query.
	 * 
	 * @param pageSize
	 *            the page size
	 * @param curPage
	 *            the cur page
	 */
	public SqlQuery(int pageSize, String curPage, PageProviderEnum pageProvider) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.pageProvider = pageProvider;
	}

	public SqlQuery(int pageSize, String curPage) {
		this(pageSize, curPage, PageProviderEnum.PAGE_PROVIDER);
	}

	/**
	 * Instantiates a new sql query.
	 * 
	 * @param myaction
	 *            the myaction
	 */
	public SqlQuery(String myaction) {
		this.myaction = myaction;
	}

	/**
	 * Gets the entity class.
	 * 
	 * @return the entity class
	 */
	public Map<String, Class> getEntityClass() {
		return entityClass;
	}

	/**
	 * Sets the entity class.
	 * 
	 * @param entityClass
	 *            the entity class
	 */
	public void setEntityClass(Map<String, Class> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Adds the entity class.
	 * 
	 * @param name
	 *            the name
	 * @param entity
	 *            the entity
	 */
	public void addEntityClass(String name, Class entity) {
		if (entityClass == null) {
			entityClass = new HashMap<String, Class>();
		}
		entityClass.put(name, entity);
	}

}