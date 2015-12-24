/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao;

import java.util.List;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.UserEvent;

/**
 * 事件Dao.
 */

public interface EventDao extends BaseDao {

	/**
	 * 根据获取事件
	 *
	 * @param shopName the shop name
	 * @return the event
	 */
	public abstract List<UserEvent> getEvent(String shopName);

	/**
	 * 获取Id事件
	 *
	 * @param id the id
	 * @return the event
	 */
	public abstract UserEvent getEvent(Long id);

	/**
	 * 删除事件
	 *
	 * @param userEvent the user event
	 */
	public abstract void deleteEvent(UserEvent userEvent);

	/**
	 * 保存事件
	 *
	 * @param userEvent the user event
	 * @return the long
	 */
	public abstract Long saveEvent(UserEvent userEvent);

	/**
	 * 更新事件
	 *
	 * @param userEvent the user event
	 */
	public abstract void updateEvent(UserEvent userEvent);

	/**
	 * 得到事件列表
	 *
	 * @param cq the cq
	 * @return the event
	 */
	public abstract PageSupport getEvent(CriteriaQuery cq);

}
