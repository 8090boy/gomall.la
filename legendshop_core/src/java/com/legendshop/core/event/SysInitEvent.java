/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.event;

import javax.servlet.ServletContextEvent;

import com.legendshop.event.SystemEvent;

public class SysInitEvent extends SystemEvent<ServletContextEvent> {

	/**
	 * Instantiates a new sys init event.
	 * 
	 * @param event
	 *            the event
	 */
	public SysInitEvent(ServletContextEvent event) {
		super(event, CoreEventId.SYS_INIT_EVENT);
	}

}
