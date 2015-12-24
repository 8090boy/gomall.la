/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.legendshop.core.dao.BaseDao;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class CodeTablesCache implements TableCache {

	/** The log. */
	private static Log log = LogFactory.getLog(CodeTablesCache.class);

	/** The code tables. */
	private final Map<String, Map<String, String>> codeTables;

	/** The base dao. */
	private BaseDao baseDaoImpl;

	/**
	 * Instantiates a new code tables cache.
	 */
	public CodeTablesCache() {
		codeTables = new HashMap<String, Map<String, String>>();
	}

	/**
	 * Sets the base dao.
	 * 
	 * @param baseDaoImpl
	 *            the new base dao
	 */
	public void setBaseDao(BaseDao baseDaoImpl) {
		this.baseDaoImpl = baseDaoImpl;
	}

	/**
	 * Clear.
	 * 
	 * @param tableClass
	 *            the table class
	 */
	public void clear(Class tableClass) {
		codeTables.remove(tableClass);
		System.gc();
	}

	/**
	 * Clear all.
	 */
	public void clearAll() {
		codeTables.clear();
		System.gc();
	}

	/**
	 * Adds the cache.
	 * 
	 * @param cacheName
	 *            the cache name
	 * @param cacheData
	 *            the cache data
	 */
	public void addCache(String cacheName, Map<String, String> cacheData) {
		codeTables.put(cacheName, cacheData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.tag.TableCache#getCodeTable(java.lang.String)
	 */
	public Map<String, String> getCodeTable(String beanName) {
		if (beanName == null || beanName.trim().length() == 0) {
			return null;
		}
		Map<String, String> table = codeTables.get(beanName);
		if (table == null) {
			Map<String, String> cacheData = null;
			List list = baseDaoImpl.findByHQL("from " + beanName + " order by name");
			if (list != null && list.size() > 0) {
				cacheData = new LinkedHashMap<String, String>();
				for (Object record : list) {
					CodeTable codeTable = (CodeTable) record;
					cacheData.put(codeTable.getId(), codeTable.getName());
				}
			}

			if (cacheData != null) {
				table = cacheData;
				this.addCache(beanName, cacheData);
			}

		}
		return table;
	}

	/**
	 * Gets the value.
	 * 
	 * @param beanName
	 *            the bean name
	 * @param id
	 *            the id
	 * @return the value
	 */
	public String getValue(String beanName, String id) {
		Map<String, String> table = getCodeTable(beanName);
		return table == null ? "" : table.get(id);
	}

	public void initCodeTablesCache() {
		// TODO Auto-generated method stub

	}

}
