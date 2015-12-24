/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

/**
 * The Class AbstractEventListenerImpl.
 */
public abstract class AbstractEventListenerImpl<T> implements BaseEventListener<T> {

	/** The event id. */
	private String eventId;

	/** The order. */
	private Integer order = 100;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.event.processor.Processor#getOrder()
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 * 
	 * @param order
	 *            the new order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.event.BaseEventListener#getEventId()
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * Sets the event id.
	 * 
	 * @param eventId
	 *            the new event id
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}
