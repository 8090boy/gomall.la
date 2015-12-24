/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

/**
 * The Class GenericEvent.
 */
public class GenericEvent extends SystemEvent<EventContext> {

	/**
	 * Instantiates a new generic event.
	 * 
	 * @param source
	 *            the source
	 * @param eventId
	 *            the event id
	 */
	public GenericEvent(EventContext source, BaseEventId eventId) {
		super(source, eventId);
	}

}
