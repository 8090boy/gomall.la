/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.event;

import com.legendshop.event.SystemEvent;
import com.legendshop.model.entity.Sub;

/**
 * 更新订单
 * 
 */
public class OrderUpdateEvent extends SystemEvent<Sub> {

	/**
	 * 参数是订单对象
	 */
	public OrderUpdateEvent(Sub source) {
		super(source, EventId.ORDER_UPDATE_EVENT);
	}

}
