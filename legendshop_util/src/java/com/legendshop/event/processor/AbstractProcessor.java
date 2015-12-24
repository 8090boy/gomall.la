/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event.processor;

/**
 * The Class AbstractProcessor.
 */
public abstract class AbstractProcessor<T> implements Processor<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.event.processor.Processor#isSupport(java.lang.Object)
	 */
	public boolean isSupport(T task) {
		return true;
	}

	/**
	 * On action.
	 * 
	 * @param task
	 *            the task
	 */
	abstract public void process(T task);
}
