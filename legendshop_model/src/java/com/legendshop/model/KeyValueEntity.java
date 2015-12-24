/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

import java.io.Serializable;

/**
 * Key Value Mapping.
 */
public class KeyValueEntity implements Serializable, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5568358970483740841L;

	/** The key. */
	private String key;

	/** The value. */
	private String value = "";

	/**
	 * Instantiates a new key value entity.
	 */
	public KeyValueEntity() {

	}

	/**
	 * Instantiates a new key value entity.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public KeyValueEntity(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 * 
	 * @param key
	 *            the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;
		result = 31 * result + key.hashCode();
		result = 31 * result + value.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof KeyValueEntity) {
			KeyValueEntity entity = (KeyValueEntity) obj;
			if (entity.getKey() != null && entity.getValue() != null && entity.getKey().equals(this.key)
					&& entity.getValue().equals(this.value)) {
				return true;
			}
		}
		return false;
	}

	public KeyValueEntity clone(){
		KeyValueEntity entity = new KeyValueEntity();
		entity.setKey(this.getKey());
		entity.setValue(this.getValue());
		
		return entity;
	}
}
