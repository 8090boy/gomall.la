/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;


/**
 * The Class AbstractPluginMatcher.
 */
public abstract class AbstractPluginMatcher {

	/** The resource. */
	protected String resource;

	/** The type. */
	protected String type;

	/** The value. */
	protected String value;

	/**
	 * Gets the resource.
	 * 
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Sets the resource.
	 * 
	 * @param resource
	 *            the new resource
	 */
	public void setResource(final String resource) {
		this.resource = resource;
	}

	/**
	 * Gets the parsed resource.
	 * 
	 * @return the parsed resource
	 */
	public String getParsedResource() {
		return null;
	}

	/**
	 * Checks if is match.
	 * 
	 * @return true, if is match
	 */
	public abstract boolean isMatch();

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
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

}