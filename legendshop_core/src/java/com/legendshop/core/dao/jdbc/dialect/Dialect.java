/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.jdbc.dialect;

/**
 * Dialect.
 */
public abstract interface Dialect {

	/**
	 * Gets the limit string.
	 * 
	 * @param querySelect
	 *            the query select
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @return the limit string
	 */
	public String getLimitString(String querySelect, int offset, int limit);

	/**
	 * Gets the limit string.
	 * 
	 * @param sql
	 *            the sql
	 * @param hasOffset
	 *            the has offset
	 * @return the limit string
	 */
	public String getLimitString(String sql, boolean hasOffset);
}
