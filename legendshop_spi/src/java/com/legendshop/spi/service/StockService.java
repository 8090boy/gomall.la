/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import com.legendshop.model.entity.Product;

/**
 * 库存服务.
 */
public interface StockService {
	
	/**
	 * hold住库存，客户下单时调用，使得后面的人不能继续下单.
	 *
	 * @param prodId the prod id
	 * @param count the count
	 * @return true, if successful
	 */
	public boolean addHold(Long prodId,  Integer count);
	
	/**
	 * 释放holding，客户退货或者订单取消，还原订单数量.
	 *
	 * @param prodId the prod id
	 * @param count the count
	 */
	public void releaseHold(Long prodId,Integer count);
	
	/**
	 * 入库, 增加库存.
	 *
	 * @param prodId the prod id
	 * @param count the count
	 */
	public void addStock(Long prodId, Integer count);

	/**
	 * 出库，减少库存.
	 *
	 * @param prodId the prod id
	 * @param count the count
	 */
	public void reduceStock(Long prodId,Integer count);
	
	/**
	 * 检查库存，是否可以订购.
	 *
	 * @param product the product
	 * @param count the count
	 * @return true, if successful
	 */
	public boolean canOrder(Product product, Integer count);

	/**
	 * 增加购买数
	 *
	 * @param prodId the prod id
	 * @param basketCount the basket count
	 */
	public void addBuys(Long prodId, Integer basketCount);
}
