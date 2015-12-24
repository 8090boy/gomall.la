/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.io.Serializable;
import java.util.List;

import org.hibernate.type.Type;

import com.legendshop.core.constant.PageProviderEnum;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class HqlQuery extends AbstractSQLQuery implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6728373478527870071L;

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param queryString
	 *            the query string
	 * @param allCountString
	 *            the all count string
	 * @param param
	 *            the param
	 */
	public HqlQuery(String queryString, String allCountString, Object[] param) {
		this.queryString = queryString;
		this.allCountString = allCountString;
		this.param = param;
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param myaction
	 *            the myaction
	 */
	public HqlQuery(String myaction, PageProviderEnum pageProvider) {
		this.myaction = myaction;
		this.pageProvider = pageProvider;
	}

	public HqlQuery(String myaction) {
		this.myaction = myaction;
	}

	/**
	 * Instantiates a new hql query.
	 */
	public HqlQuery() {
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param pageSize
	 *            the page size
	 * @param curPage
	 *            the cur page
	 * @param action
	 *            the action
	 */
	public HqlQuery(int pageSize, String curPage, String action) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.myaction = action;
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param pageSize
	 *            the page size
	 * @param curPage
	 *            the cur page
	 */
	public HqlQuery(int pageSize, String curPage) {
		this(pageSize,curPage, PageProviderEnum.PAGE_PROVIDER);
	}

	public HqlQuery(int pageSize, String curPage, PageProviderEnum pageProvider) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.pageProvider = pageProvider;
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param myaction
	 *            the myaction
	 * @param queryString
	 *            the query string
	 * @param allCountString
	 *            the all count string
	 * @param params
	 *            the params
	 */
	public HqlQuery(String myaction, String queryString, String allCountString, List<Object> params) {
		this.myaction = myaction;
		this.queryString = queryString;
		this.allCountString = allCountString;
		this.params = params;
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param queryString
	 *            the query string
	 * @param allCountString
	 *            the all count string
	 * @param params
	 *            the params
	 */
	public HqlQuery(String queryString, String allCountString, List<Object> params) {
		this.queryString = queryString;
		this.allCountString = allCountString;
		this.params = params;
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param myaction
	 *            the myaction
	 * @param queryString
	 *            the query string
	 * @param allCountString
	 *            the all count string
	 * @param param
	 *            the param
	 * @param types
	 *            the types
	 */
	public HqlQuery(String myaction, String queryString, String allCountString, Object[] param, Type[] types) {
		this.myaction = myaction;
		this.queryString = queryString;
		this.allCountString = allCountString;
		this.param = param;
		this.types = types;
	}

	/**
	 * Instantiates a new hql query.
	 * 
	 * @param queryString
	 *            the query string
	 * @param param
	 *            the param
	 * @param types
	 *            the types
	 */
	public HqlQuery(String queryString, Object[] param, Type[] types) {
		this.queryString = queryString;
		this.param = param;
		this.types = types;
	}

}