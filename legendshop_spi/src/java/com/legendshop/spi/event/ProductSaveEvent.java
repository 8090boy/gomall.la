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
 * The Class ProductSaveEvent.
 */
public class ProductSaveEvent extends SystemEvent<Product> {

	/**
	 * Instantiates a new login event.
	 *
	 * @param prod the prod
	 */
	public ProductSaveEvent(Product prod) {
		super(EventId.PRODUCT_SAVE);
		this.setSource(prod);
	}

}
