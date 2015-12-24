/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event.processor;

/**
 * The Class BaseProcessor.
 */
public abstract class BaseProcessor<T> extends AbstractProcessor<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.event.processor.Processor#onEvent(java.lang.Object)
	 */
	public void onEvent(T eventContext) {
		process(eventContext);
	}

}
