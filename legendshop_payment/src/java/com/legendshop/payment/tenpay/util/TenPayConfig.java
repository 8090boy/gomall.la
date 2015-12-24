package com.legendshop.payment.tenpay.util;

import java.util.ArrayList;
import java.util.List;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.page.PagerUtil;
import com.legendshop.payment.config.PaymentConfig;

public class TenPayConfig extends PaymentConfig{

	/** The notify_url. */
	public static String notify_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/tenpay/notify";

	// 付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	/** The return_url. */
	public static String return_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/tenpay/response";


	// 字符编码格式 目前支持 gbk 或 utf-8
	/** The input_charset. */
	public static String input_charset = "utf-8";
	
	/**
	 * cmdno=1&date=20051219&bargainor_id=1000000301&transaction_id=1000000301200512190000012138&sp_billno=1111&total_fee=1300
	 *&fee_type=1&return_url=http://www.xxx.com/tenpay1.aspx&attach=1&spbill_create_ip=219.133.62.73&key=1000000301
	 */
	public static List<String> payKeys = new ArrayList<String>();
	
	static {
		payKeys.add("agent_type");
		payKeys.add("agentid");
		payKeys.add("attach");
		payKeys.add("bank_type");
		payKeys.add("body");
		payKeys.add("buyer_id");
		payKeys.add("fee_type");
		payKeys.add("goods_tag");
		payKeys.add("input_charset");
		payKeys.add("notify_url");
		payKeys.add("out_trade_no");
		payKeys.add("partner");
		payKeys.add("product_fee");
		payKeys.add("return_url");
		payKeys.add("seller_id");
		payKeys.add("service_version");
		payKeys.add("sign");
		payKeys.add("sign_key_index");
		payKeys.add("sign_type");
		payKeys.add("spbill_create_ip");
		payKeys.add("subject");
		payKeys.add("time_expire");
		payKeys.add("time_start");
		payKeys.add("total_fee");
		payKeys.add("trade_mode");
		payKeys.add("trans_type");
		payKeys.add("transport_desc");
		payKeys.add("transport_fee");
	}
	

}
