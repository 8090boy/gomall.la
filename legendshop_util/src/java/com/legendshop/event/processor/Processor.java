/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event.processor;

/**
 * The Interface Processor.
 * 
 * @param <T>
 *            the generic type
 */
public interface Processor<T> {

	/**
	 * Checks if is support.
	 * 
	 * @param task
	 *            the task
	 * @return true, if is support
	 */
	public boolean isSupport(T task);

	/**
	 * On event.
	 * 
	 * @param task
	 *            the task
	 */
	public void onEvent(T task);
}
