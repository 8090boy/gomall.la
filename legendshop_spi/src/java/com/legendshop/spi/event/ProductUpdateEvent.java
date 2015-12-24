/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.event;

import com.legendshop.event.SystemEvent;
import com.legendshop.model.entity.Product;

/**
 * 商品修改触发的事件.
 */
public class ProductUpdateEvent extends SystemEvent<Product> {

	/**
	 * Instantiates a new login event.
	 * 
	 * @param userDetail
	 *            the user detail
	 * @param ipAddress
	 *            the ip address
	 */
	public ProductUpdateEvent(Product prod) {
		super(EventId.PRODUCT_UPDATE);
		this.setSource(prod);
	}

}
