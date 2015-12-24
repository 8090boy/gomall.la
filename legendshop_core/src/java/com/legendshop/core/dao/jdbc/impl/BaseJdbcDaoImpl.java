/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;

import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.dao.jdbc.dialect.Dialect;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.core.page.PagerUtil;

/**
 * The Class BaseJdbcDaoImpl.
 */
public class BaseJdbcDaoImpl implements BaseJdbcDao {

	private final Logger log = LoggerFactory.getLogger(BaseJdbcDaoImpl.class);

	/** The jdbc template. */
	protected JdbcTemplate jdbcTemplate;

	protected Dialect dialect;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.jdbc.BaseJdbcDao#update(java.lang.String,
	 * java.lang.Object[])
	 */
	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.jdbc.BaseJdbcDao#stat(java.lang.String,
	 * java.lang.Object[])
	 */
	public long stat(String sql, Object... args) {
		return jdbcTemplate.queryForLong(sql, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.jdbc.BaseJdbcDao#find(java.lang.String,
	 * java.lang.Class, java.lang.Object[])
	 */
	public <T> T get(String sql, Class<T> clazz, Object... args) {
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz), args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.jdbc.BaseJdbcDao#query(java.lang.String,
	 * java.lang.Class, java.lang.Object[])
	 */
	public <T> List<T> query(String sql, Class<T> clazz, Object... args) {
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz), args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.BaseJdbcDao#updateNamed(java.lang.String,
	 * java.lang.Object)
	 */
	public <T> int updateNamed(String namedSql, T bean) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(namedSql);
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		List<SqlParameter> params = NamedParameterUtils.buildSqlParameterList(parsedSql, source);
		Object[] args = NamedParameterUtils.buildValueArray(parsedSql, source, params);
		return jdbcTemplate.update(sql, args);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.BaseJdbcDao#updateNamed(java.lang.String,
	 * java.util.List)
	 */
	public <T> int[] updateNamed(String namedSql, List<T> beans) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(namedSql);
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (T bean : beans) {
			BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(bean);
			List<SqlParameter> params = NamedParameterUtils.buildSqlParameterList(parsedSql, source);
			Object[] args = NamedParameterUtils.buildValueArray(parsedSql, source, params);
			batchArgs.add(args);
		}
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.BaseJdbcDao#updateNamedMap(java.lang.String,
	 * java.util.Map)
	 */
	public int updateNamedMap(String namedSql, Map<String, Object> paramMap) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		Object[] args = NamedParameterUtils.buildValueArray(namedSql, paramMap);
		return jdbcTemplate.update(sql, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.BaseJdbcDao#updateNamedMap(java.lang.String,
	 * java.util.List)
	 */
	public int[] updateNamedMap(String namedSql, List<Map<String, Object>> paramMaps) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (Map<String, Object> paramMap : paramMaps) {
			Object[] args = NamedParameterUtils.buildValueArray(namedSql, paramMap);
			batchArgs.add(args);
		}
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**
	 * Sets the jdbc template.
	 * 
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public PageSupport find(final SimpleSqlQuery query) {
		// 当前页
		int curPageNO = PagerUtil.getCurPageNO(query.getCurPage());

		// 得到总行数

		if (log.isInfoEnabled()) {
			if (query.getParam() != null) {
				log.info("Running SQL:\n" + query.getAllCountString().replace("?", "{}"), query.getParam());
			} else {
				log.info("Running SQL:\n" + query.getAllCountString());
			}
		}
		long allCounts = this.stat(query.getAllCountString(), query.getParam());

		int offset = PagerUtil.getOffset(allCounts, curPageNO, query.getPageSize());

		Object[] params = null;
		if (query.getParam() != null) {
			int paramLen = query.getParam().length;
			if (offset > 0) {
				params = Arrays.copyOf(query.getParam(), paramLen + 2);
				params[paramLen] = offset;
				params[paramLen + 1] = query.getPageSize();
			} else {
				params = Arrays.copyOf(query.getParam(), paramLen + 1);
				params[paramLen] = query.getPageSize();
			}
		} else {
			if (offset > 0) {
				params = new Object[2];
				params[0] = offset;
				params[1] = query.getPageSize();
			} else {
				params = new Object[1];
				params[0] = query.getPageSize();
			}

		}

		String querySQL = dialect.getLimitString(query.getQueryString(), offset > 0);
		if (log.isInfoEnabled()) {
			if (params != null && params.length > 0) {
				log.info("Running SQL:\n" + querySQL.replace("?", "{}"), params);
			} else {
				log.info("Running SQL:\n" + querySQL);
			}
		}
		// 得到记录
		List<?> list = jdbcTemplate.query(querySQL, BeanPropertyRowMapper.newInstance(query.getEntityClass()), params);

		return new PageSupport(list, query.getMyaction(), offset, curPageNO, allCounts, query.getPageSize(),
				query.getPageProvider());
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param dialect
	 *            the dialect to set
	 */
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	//删除用户信息， for LegendShop
	public void deleteUserItem(String tableName, String userName) {
		update("delete from " + tableName + " where user_name = ?", userName);
	}

}
