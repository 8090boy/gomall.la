package com.legendshop.core.tag;

import java.util.Map;

public interface TableCache {

	/**
	 * Gets the code table.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the code table
	 */
	public abstract Map<String, String> getCodeTable(String beanName);

	/**
	 * Inits the code tables cache.
	 */
	public abstract void initCodeTablesCache();

}