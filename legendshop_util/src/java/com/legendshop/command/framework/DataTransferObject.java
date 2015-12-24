/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class DataTransferObject implements Serializable {

	/** The values. */
	private Map values;

	/**
	 * 构造函数.
	 * 
	 * @param values
	 *            the values
	 */
	public DataTransferObject(Map values) {
		this.values = values;
	}

	/**
	 * 构造函数.
	 */
	public DataTransferObject() {
		values = new HashMap();
	}

	/**
	 * Gets the values.
	 * 
	 * @return Map
	 */
	public Map getValues() {
		return values;
	}

	/**
	 * Gets the value.
	 * 
	 * @param key
	 *            Object
	 * @return Object
	 */
	public Object getValue(Object key) {
		return values.get(key);
	}

	/**
	 * Sets the values.
	 * 
	 * @param vs
	 *            Map
	 */
	public void setValues(Map vs) {
		values.putAll(vs);
	}

	/**
	 * Sets the value.
	 * 
	 * @param key
	 *            Object
	 * @param value
	 *            Object
	 */
	public void setValue(Object key, Object value) {
		values.put(key, value);
	}
}
