/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;

import java.util.HashMap;

/**
 * The Class DefaultKeyMap.
 * 
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class DefaultKeyMap<K, V> extends HashMap<K, V> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1767115340400652866L;
	/** The default key. */
	private String defaultKey;

	/**
	 * Default constructor.
	 */
	public DefaultKeyMap() {

	}

	/**
	 * Constructor with default key provided.
	 * 
	 * @param defaultKey
	 *            the default key
	 */
	public DefaultKeyMap(String defaultKey) {
		this.defaultKey = defaultKey;
	}

	/**
	 * Gets default key.
	 * 
	 * @return the defaultKey
	 */
	public String getDefaultKey() {
		return this.defaultKey;
	}

	/**
	 * Sets the default key.
	 * 
	 * @param defaultKey
	 *            the defaultKey to set
	 */
	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

	/**
	 * Enhanced retrieve method, it will return a default value when returning
	 * null with the provided key.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the V
	 */
	@Override
	public V get(Object key) {
		V value = super.get(key);
		if (value == null) {
			value = super.get(this.defaultKey);
		}
		return value;
	}
}