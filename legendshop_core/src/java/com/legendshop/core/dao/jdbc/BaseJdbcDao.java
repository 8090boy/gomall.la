package com.legendshop.core.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;

public interface BaseJdbcDao {

	public JdbcTemplate getJdbcTemplate();

	// 更新
	public int update(String sql, Object... args);

	// 统计
	public long stat(String sql, Object... args);

	// 查找
	public <T> T get(String sql, Class<T> clazz, Object... args);

	// 查询
	public <T> List<T> query(String sql, Class<T> clazz, Object... args);

	// 命名更新
	public <T> int updateNamed(String namedSql, T bean);

	// 批量命名更新
	public <T> int[] updateNamed(String namedSql, List<T> beans);

	// 命名更新
	public int updateNamedMap(String namedSql, Map<String, Object> paramMap);

	// 批量命名更新
	public int[] updateNamedMap(String namedSql, List<Map<String, Object>> paramMaps);

	// 分页查询
	public PageSupport find(SimpleSqlQuery query);
	
	//删除用户信息
	public void deleteUserItem(String tableName, String userName);

}
