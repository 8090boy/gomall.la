/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.processor.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 财付通即时支付
 * 
 * 官方网站：http://www.legendesign.net
 */
public class TenDirectPayProcessorImpl extends AbstractTenPaymentProcessorImpl {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(TenDirectPayProcessorImpl.class);

	@Override
	public String getName() {
		return "财付通即时支付";
	}

	/**
	 *  交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
	 */
	@Override
	public String parsrTradeMode(Map<String, String> sParaTemp) {
		return "1";
	}


}
