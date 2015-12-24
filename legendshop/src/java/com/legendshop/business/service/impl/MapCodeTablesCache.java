package com.legendshop.business.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.business.dao.ConstTableDao;
import com.legendshop.core.tag.TableCache;
import com.legendshop.model.entity.ConstTable;

public class MapCodeTablesCache implements TableCache {

	private static Logger log = LoggerFactory.getLogger(MapCodeTablesCache.class);

	/** The code tables. */
	private Map<String, Map<String, String>> codeTables = new HashMap<String, Map<String, String>>();

	private ConstTableDao constTableDao;

	/**
	 * Gets the code tables.
	 * 
	 * @return the code tables
	 */
	public Map<String, Map<String, String>> getCodeTables() {
		return codeTables;
	}

	/**
	 * Sets the code tables.
	 * 
	 * @param codeTables
	 *            the code tables
	 */
	public void setCodeTables(Map<String, Map<String, String>> codeTables) {
		this.codeTables = codeTables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.tag.TableCache#getCodeTable(java.lang.String)
	 */
	@Override
	public Map<String, String> getCodeTable(String beanName) {
		if (beanName == null || beanName.trim().length() == 0) {
			return null;
		}
		Map<String, String> table = codeTables.get(beanName);
		return table;
	}

	/**
	 * Inits the code tables cache.
	 */
	@Override
	public void initCodeTablesCache() {
		List<ConstTable> list = constTableDao.loadAllConstTable();
		for (ConstTable constTable : list) {
			String type = constTable.getId().getType();
			Map<String, String> items = codeTables.get(type);
			if (items == null) {
				items = new LinkedHashMap<String, String>();
			}
			items.put(constTable.getId().getKey(), constTable.getValue());
			codeTables.put(type, items);
		}

		log.info("codeTables size = {}", codeTables.size());
	}

	public void setConstTableDao(ConstTableDao constTableDao) {
		this.constTableDao = constTableDao;
	}

}
