package com.legendshop.business.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.spi.processor.PaymentProcessor;
import com.legendshop.spi.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	/**支付实现类 */
	private Map<String, PaymentProcessor> paymentProcessors;

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
			String price, String ip) {
		log.debug("payto shopName = {},userName = {},payTypeId = {}", new Object[] { shopName, userName, payTypeId });
		return getPaymentProcessor(payTypeId).payto(shopName, userName, payTypeId, out_trade_no, subject, body, price, ip);

	}

	/**
	 * Gets the payment processor.
	 * 
	 * @param payTypeId
	 *            the pay type id
	 * @return the payment processor
	 */
	public PaymentProcessor getPaymentProcessor(String payTypeId) {
		PaymentProcessor processor = paymentProcessors.get(payTypeId);
		if (processor == null) {
			processor = paymentProcessors.get(1);
		}
		return processor;
	}

	/**
	 * @param paymentProcessors the paymentProcessors to set
	 */
	public void setPaymentProcessors(Map<String, PaymentProcessor> paymentProcessors) {
		this.paymentProcessors = paymentProcessors;
	}
}
