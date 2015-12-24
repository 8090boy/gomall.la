/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.util.List;

import com.legendshop.core.dao.impl.BaseDaoImpl;

/**
 * The Class TagLibDaoImpl.
 */
public class TagLibDaoImpl extends BaseDaoImpl implements TagLibDao {

	/**
	 * Find by hql.
	 * 
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	public List findDataByHQL(String hql) {
		return super.findByHQL(hql);
	}

	/**
	 * Find by sql.
	 * 
	 * @param sql
	 *            the sql
	 * @return the list
	 */
	public List findDataBySQL(String sql) {
		return super.findBySQL(sql);
	}

}
