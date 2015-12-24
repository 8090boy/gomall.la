package com.legendshop.business.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.spi.processor.PaymentProcessor;

/**
 * 货到付款
 */
public class PayAtGoodsArrivedProcessorImpl implements PaymentProcessor {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(PayAtGoodsArrivedProcessorImpl.class);

	@Override
	public String getName() {
		return "货到付款";
	}

	@Override
	public String payto(String shopName, String userName, String payTypeId,
			String out_trade_no, String subject, String body, String price,
			String ip) {
		// TODO Auto-generated method stub
		return null;
	}


}
