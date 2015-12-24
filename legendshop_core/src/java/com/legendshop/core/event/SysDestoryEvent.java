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

/**
 * The Class SysDestoryEvent.
 */
public class SysDestoryEvent extends SystemEvent<ServletContextEvent> {

	/**
	 * Instantiates a new sys destory event.
	 * 
	 * @param event
	 *            the event
	 */
	public SysDestoryEvent(ServletContextEvent event) {
		super(event, CoreEventId.SYS_DESTORY_EVENT);
	}

}
