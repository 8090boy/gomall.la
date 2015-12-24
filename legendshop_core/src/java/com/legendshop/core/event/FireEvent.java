/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.event;

import java.util.Date;

import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.UserManager;
import com.legendshop.event.SystemEvent;
import com.legendshop.model.entity.AbstractEntity;
import com.legendshop.model.entity.UserEvent;

/**
 * 记录操作历史 The Class FireEvent.
 */
public class FireEvent extends SystemEvent<UserEvent> {

	public FireEvent(AbstractEntity entity, OperationTypeEnum operationType) {
		super(CoreEventId.FIRE_EVENT);
		this.setSource(generateEvent(entity, operationType));
	}

	private UserEvent generateEvent(AbstractEntity entity, OperationTypeEnum operationType) {
		UserEvent userEvent = new UserEvent();
		userEvent.setCreateTime(new Date());
		userEvent.setOperation(operationType.name());
		userEvent.setRelateData(entity.toString());
		userEvent.setType(entity.getClass().getSimpleName());
		userEvent.setUserId(UserManager.getUserId());
		userEvent.setUserName(entity.getUserName());
		userEvent.setModifyUser(UserManager.getUserName());
		userEvent.setRelateId(entity.getId().toString());
		return userEvent;
	}
}
