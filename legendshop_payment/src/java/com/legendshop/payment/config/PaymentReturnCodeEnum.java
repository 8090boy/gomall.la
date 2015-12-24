package com.legendshop.payment.config;

import com.legendshop.util.constant.StringEnum;

/**
 * 支付接口反馈的状态参数
 * 
 */
public enum PaymentReturnCodeEnum implements StringEnum {

	/**
	 * 等待买家付款
	 */
	WAIT_BUYER_PAY("WAIT_BUYER_PAY"),

	/**
	 * 待发货
	 */
	WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS"),

	/**
	 * 该判断表示卖家已经发了货，但买家还没有做确认收货的操作
	 */
	WAIT_BUYER_CONFIRM_GOODS("WAIT_BUYER_CONFIRM_GOODS"),

	/**
	 * 交易完成
	 */
	TRADE_FINISHED("TRADE_FINISHED"),

	/**
	 * 交易成功
	 */
	TRADE_SUCCESS("TRADE_SUCCESS"),

	;

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new function enum.
	 * 
	 * @param value
	 *            the value
	 */
	private PaymentReturnCodeEnum(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.constant.StringEnum#value()
	 */
	public String value() {
		return this.value;
	}

	/**
	 * Instance.
	 * 
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public static boolean instance(String name) {
		PaymentReturnCodeEnum[] licenseEnums = values();
		for (PaymentReturnCodeEnum licenseEnum : licenseEnums) {
			if (licenseEnum.name().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the value.
	 * 
	 * @param name
	 *            the name
	 * @return the value
	 */
	public static String getValue(String name) {
		PaymentReturnCodeEnum[] licenseEnums = values();
		for (PaymentReturnCodeEnum licenseEnum : licenseEnums) {
			if (licenseEnum.name().equals(name)) {
				return licenseEnum.value();
			}
		}
		return null;
	}
}
