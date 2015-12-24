/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.constants;

import com.legendshop.model.entity.PayType;
import com.legendshop.util.constant.StringEnum;

/**
 * LegendShop 版权所有,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * -------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * --------
 * ----------------------------------------------------------------------------.
 */
public enum PayTypeEnum implements StringEnum {
	/** 支付宝直接支付. */
	ALI_DIRECT_PAY("ADP"),

	/** 支付宝担保支付. */
	ALI_PAY("ALP"),

	/** 货到付款 */
	PAY_AT_GOODS_ARRIVED("PGA"),
	
	/**财付通担保支付**/
	TENPAY("TNP"),
	
	/**财付通即时支付**/
	TEN_DIRECT_PAY("TDP"),
	
	/**PAYPAL支付**/
	PAYPAL("PAP"),
	
	/** 网银在线支付**/
	CHINABANK("CBP"),
	
	/** 快钱支付 **/
	KQ_PAY("KQP")
	;
	/** The value. */
	private final String value;

	/**
	 * Instantiates a new visit type enum.
	 * 
	 * @param value
	 *            the value
	 */
	private PayTypeEnum(String value) {
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
	 * Checks if is alipay.
	 * 
	 * @param payType
	 *            the pay type
	 * @return true, if is alipay
	 */
	public static boolean isAlipay(PayType payType) {
		if (payType == null) {
			return false;
		}
		if (ALI_DIRECT_PAY.value().equals(payType.getPayTypeId()) || ALI_PAY.value().equals(payType.getPayTypeId())) {
			return true;
		} else {
			return false;
		}

	}
}
