/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.Type;

import com.legendshop.util.AppUtils;

/**
 * The Class AbstractSQLQuery.
 */
public abstract class AbstractSQLQuery extends AbstractQuery {
	/** The query string. */
	protected String queryString;

	/** The all count string. */
	protected String allCountString;

	/** The param. */
	protected Object[] param;

	/** The types. */
	protected Type[] types;

	/** The params. */
	protected List<Object> params = new ArrayList<Object>();

	/**
	 * Gets the query string.
	 * 
	 * @return the query string
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Sets the query string.
	 * 
	 * @param queryString
	 *            the new query string
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * Gets the all count string.
	 * 
	 * @return the all count string
	 */
	public String getAllCountString() {
		return allCountString;
	}

	/**
	 * Sets the all count string.
	 * 
	 * @param allCountString
	 *            the new all count string
	 */
	public void setAllCountString(String allCountString) {
		this.allCountString = allCountString;
	}

	/**
	 * Gets the param.
	 * 
	 * @return the param
	 */
	public Object[] getParam() {
		return param;
	}

	/**
	 * Sets the param.
	 * 
	 * @param param
	 *            the new param
	 */
	public void setParam(Object[] param) {
		this.param = param;
	}

	/**
	 * Gets the types.
	 * 
	 * @return the types
	 */
	public Type[] getTypes() {
		return types;
	}

	/**
	 * Sets the types.
	 * 
	 * @param types
	 *            the new types
	 */
	public void setTypes(Type[] types) {
		this.types = types;
	}

	/**
	 * Gets the params.
	 * 
	 * @return the params
	 */
	public List getParams() {
		return params;
	}

	/**
	 * Sets the params.
	 * 
	 * @param params
	 *            the new params
	 */
	public void setParams(List params) {
		this.params = params;
	}

	/**
	 * Adds the params.
	 * 
	 * @param param
	 *            the param
	 */
	public void addParams(Object param) {
		if (!AppUtils.isBlank(param)) {
			params.add(param);
		}
	}
}
