package com.legendshop.payment.service;

import com.legendshop.payment.model.PaymentResponse;

/**
 * 支付接口返回调用的服务类.
 */
public interface PaymentyReturnService {

	/**
	 * 处理支付接口翻回来的消息
	 *
	 * @param paymentResponse the payment response
	 */
	public void parseResponse(PaymentResponse paymentResponse);
	
}
