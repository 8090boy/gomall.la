/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import com.legendshop.spi.processor.PaymentProcessor;

/**
 * 支付
 */
public interface PaymentService {

	/**
	 * 支付.
	 * 
	 * @param shopName
	 *            店铺名称
	 * @param userName
	 *            the user name
	 * @param payTypeId
	 *            支付类型
	 * @param out_trade_no
	 *            请与贵网站订单系统中的唯一订单号匹配
	 * @param subject
	 *            订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
	 * @param body
	 *            订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
	 * @param price
	 *            订单总金额，显示在支付宝收银台里的“应付总额”里
	 * @param ip
	 *            the ip
	 * @return the string
	 */
	public String payto(String shopName, String userName, String payTypeId, String out_trade_no, String subject, String body,
			String price, String ip);
	
	/**
	 * 根据id得到支付实现
	 * @param payTypeId
	 * @return
	 */
	public PaymentProcessor getPaymentProcessor(String payTypeId);
}
