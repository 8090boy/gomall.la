/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.test;

import com.legendshop.core.dao.impl.BaseDaoImpl;

/**
 * The Class BaseJdbcDaoImpl.
 */
public class HibernateDaoImpl extends BaseDaoImpl{



	/* (non-Javadoc)
	 * @see com.legendshop.core.dao.jdbc.BaseJdbcDao#stat(java.lang.String, java.lang.Object[])
	 */
	public long stat(String sql, Object... args) {
		//return jdbcTemplate.queryForLong(sql, args);
		return findUniqueBy("select count (*) from User",Long.class, args);
	}

	public void saveUser(User user){
		save(user);
	}

	public User findUser(Long id){
		return get(User.class, id);
	}
}
