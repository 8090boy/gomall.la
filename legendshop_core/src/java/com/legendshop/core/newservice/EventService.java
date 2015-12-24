/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.newservice;

import java.util.List;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.UserEvent;

/**
 * The Class EventService.
 */
public interface EventService {

	public List<UserEvent> getEvent(String userName);

	public UserEvent getEvent(Long id);

	public void deleteEvent(UserEvent userEvent);

	public Long saveEvent(UserEvent userEvent);

	public void updateEvent(UserEvent userEvent);

	public PageSupport getEvent(CriteriaQuery cq);
}
