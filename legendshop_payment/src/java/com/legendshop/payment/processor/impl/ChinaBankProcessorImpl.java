/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.spi.processor.PaymentProcessor;

/**
 * 网银在线支付
 * 
 * 官方网站：http://www.legendesign.net
 */
public class ChinaBankProcessorImpl implements PaymentProcessor {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ChinaBankProcessorImpl.class);

	@Override
	public String getName() {
		return "网银在线支付";
	}

	@Override
	public String payto(String shopName, String userName, String payTypeId,
			String out_trade_no, String subject, String body, String price,
			String ip) {
		// TODO Auto-generated method stub
		return null;
	}


}
