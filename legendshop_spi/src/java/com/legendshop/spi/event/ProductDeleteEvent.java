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
 * The Class ProductDeleteEvent.
 */
public class ProductDeleteEvent extends SystemEvent<Product> {

	/**
	 * Instantiates a new login event.
	 *
	 * @param shopDetail the shop detail
	 */
	public ProductDeleteEvent(Product prod) {
		super(EventId.PRODUCT_DELETE);
		this.setSource(prod);
	}

}
