package com.legendshop.util.handler;

import java.util.Map;

public class DefaultMapFactoryBean extends org.springframework.beans.factory.config.MapFactoryBean {

	/** The default key. */
	private String defaultKey;

	/**
	 * Create a RbpDefaultKeyMap object with default key.
	 * 
	 * @return the object
	 */
	@Override
	protected Map createInstance() {
		Map<?, ?> map = (Map<?, ?>) super.createInstance();
		if (map instanceof DefaultKeyMap) {
			((DefaultKeyMap<?, ?>) map).setDefaultKey(this.defaultKey);
		}
		return map;
	}

	/**
	 * Gets the default key.
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
}