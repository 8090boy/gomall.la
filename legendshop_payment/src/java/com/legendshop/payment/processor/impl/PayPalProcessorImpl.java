/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.processor.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.model.entity.PayType;
import com.legendshop.payment.paypal.PayPalService;
import com.legendshop.payment.paypal.util.PayPalConfig;
import com.legendshop.spi.processor.PaymentProcessor;

/**
 * PAYPAL支付
 * 
 * 官方网站：http://www.legendesign.net
 */
public class PayPalProcessorImpl implements PaymentProcessor {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(PayPalProcessorImpl.class);

	@Override
	public String getName() {
		return "PAYPAL支付";
	}

	@Override
	public String payto(String shopName, String userName, String payTypeId,
			String out_trade_no, String subject, String body, String total_fee,
			String ip) {
		
		PayType payType = PayPalConfig.getPayType(shopName, payTypeId);
		String seller_email = payType.getSellerEmail();
		//String partner = payType.getPartner();
		String show_url = PayPalConfig.show_url;
		String currency_code = PayPalConfig.currency_code;
		
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		
		sParaTemp.put("cmd", "_xclick");
		sParaTemp.put("business", seller_email);
		sParaTemp.put("item_name", subject);
		sParaTemp.put("item_number", out_trade_no);//订单号
		sParaTemp.put("no_shipping", "0");
		sParaTemp.put("currency_code", currency_code);
		sParaTemp.put("amount", total_fee);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("return_url", PayPalConfig.return_url);
		sParaTemp.put("notify_url", PayPalConfig.notify_url);
		sParaTemp.put("charset", "utf8");
		return PayPalService.create_direct_pay_by_user(sParaTemp, payType.getValidateKey());
	}


}
