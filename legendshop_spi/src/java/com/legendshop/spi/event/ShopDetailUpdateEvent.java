/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.event;

import com.legendshop.event.SystemEvent;
import com.legendshop.model.entity.ShopDetail;

/**
 * 登录时触发的事件.
 */
public class ShopDetailUpdateEvent extends SystemEvent<ShopDetail> {

	/**
	 * Instantiates a new login event.
	 * 
	 * @param userDetail
	 *            the user detail
	 * @param ipAddress
	 *            the ip address
	 */
	public ShopDetailUpdateEvent(ShopDetail shopDetail) {
		super(EventId.SHOPDETAIL_UPDATE);
		this.setSource(shopDetail);
	}

}
