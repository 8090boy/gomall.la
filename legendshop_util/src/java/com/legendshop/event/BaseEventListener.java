/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

import java.util.EventListener;

/**
 * The listener interface for receiving baseEvent events. The class that is
 * interested in processing a baseEvent event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addBaseEventListener<code> method. When
 * the baseEvent event occurs, that object's appropriate
 * method is invoked.
 * 
 * @param <E>
 *            the element type
 * @see BaseEventEvent
 */
public interface BaseEventListener<T> extends EventListener {

	/**
	 * 事件处理的方法.
	 * 
	 * @param event
	 *            the event
	 */
	public void onEvent(SystemEvent<T> event);

	/**
	 * Gets the event id.
	 * 
	 * @return the event id
	 */
	public String getEventId();

	/**
	 * Gets the order.
	 * 
	 * @return the order
	 */
	public Integer getOrder();

}
