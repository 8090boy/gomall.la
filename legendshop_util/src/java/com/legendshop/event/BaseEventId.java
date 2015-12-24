/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

/**
 * The Interface BaseEventId.
 */
public interface BaseEventId {

	/**
	 * Gets the event id.
	 * 
	 * @return the event id
	 */
	public String getEventId();

	/**
	 * Instance.
	 * 
	 * @return true, if successful
	 */
	public boolean instance(String name);
}
