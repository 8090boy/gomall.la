/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.event;

import java.util.Date;

import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.UserManager;
import com.legendshop.core.newservice.EventService;
import com.legendshop.event.processor.ThreadProcessor;
import com.legendshop.model.entity.AbstractEntity;
import com.legendshop.model.entity.UserEvent;

/**
 * The Class ThreadEventProcessor.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class ThreadEventProcessor<T> extends ThreadProcessor<T> {

	private EventService eventService;

	private boolean logEvent;

	/** The operation type. */
	private OperationTypeEnum operationType;

	/**
	 * Generate event.
	 * 
	 * @param entity
	 *            the entity
	 * @param operationType
	 *            the operation type
	 * @return the user event
	 */
	public void logUserEvent(AbstractEntity entity, OperationTypeEnum operationType) {
		if (logEvent) {
			UserEvent userEvent = new UserEvent();
			userEvent.setCreateTime(new Date());
			userEvent.setOperation(operationType.name());
			userEvent.setRelateData(entity.toString());
			userEvent.setType(entity.getClass().getSimpleName());
			userEvent.setUserId(UserManager.getUserId());
			userEvent.setUserName(entity.getUserName());
			userEvent.setModifyUser(UserManager.getUserName());
			userEvent.setRelateId(entity.getId().toString());
			eventService.saveEvent(userEvent);
		}
	}

	/**
	 * Sets the operation type.
	 * 
	 * @param operationType
	 *            the operationType to set
	 */
	public void setOperationType(OperationTypeEnum operationType) {
		this.operationType = operationType;
	}

	/**
	 * @param eventService
	 *            the eventService to set
	 */
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	/**
	 * @param logEvent
	 *            the logEvent to set
	 */
	public void setLogEvent(boolean logEvent) {
		this.logEvent = logEvent;
	}

	/**
	 * @return the logEvent
	 */
	public boolean isLogEvent() {
		return logEvent;
	}

}
