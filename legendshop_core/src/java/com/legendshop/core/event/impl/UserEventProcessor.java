/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.event.impl;

import com.legendshop.core.newservice.EventService;
import com.legendshop.event.processor.ThreadProcessor;
import com.legendshop.model.entity.UserEvent;
import com.legendshop.util.AppUtils;

/**
 * 用户的操作历史
 */
public class UserEventProcessor extends ThreadProcessor<UserEvent> {
	private EventService eventService;
	private boolean logEvent;

	/**
	 * 只是支持已经登录用户信息
	 */
	public boolean isSupport(UserEvent event) {
		if (logEvent && AppUtils.isNotBlank(event.getUserId()) && AppUtils.isNotBlank(event.getUserName())) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.event.processor.AbstractProcessor#process(java.lang.Object
	 * )
	 */
	@Override
	public void process(UserEvent event) {
		eventService.saveEvent(event);
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public void setLogEvent(boolean logEvent) {
		this.logEvent = logEvent;
	}
}
