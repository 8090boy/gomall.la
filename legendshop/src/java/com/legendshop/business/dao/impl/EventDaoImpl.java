/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.dao.EventDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.UserEvent;

/**
 * The Class EventDaoImpl.
 */

public class EventDaoImpl extends BaseDaoImpl implements EventDao {
	private static Logger log = LoggerFactory.getLogger(EventDaoImpl.class);

	public List<UserEvent> getEvent(String userName) {
		return findByHQL("from UserEvent where userName = ?", userName);
	}

	public UserEvent getEvent(Long id) {
		return get(UserEvent.class, id);
	}

	public void deleteEvent(UserEvent userEvent) {
		delete(userEvent);
	}

	public Long saveEvent(UserEvent userEvent) {
		return (Long) save(userEvent);
	}

	public void updateEvent(UserEvent userEvent) {
		update(userEvent);
	}

	public PageSupport getEvent(CriteriaQuery cq) {
		return find(cq);
	}

}
