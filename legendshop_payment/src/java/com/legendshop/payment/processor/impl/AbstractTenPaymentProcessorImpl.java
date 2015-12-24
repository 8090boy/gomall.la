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
import com.legendshop.payment.paypal.util.PayPalConfig;
import com.legendshop.payment.tenpay.TenPayService;
import com.legendshop.payment.tenpay.util.TenPayConfig;
import com.legendshop.payment.tenpay.util.TenpayUtil;
import com.legendshop.spi.processor.PaymentProcessor;
import com.legendshop.util.AppUtils;

/**
 * 财付通即时支付
 * 
 * 官方网站：http://www.legendesign.net
 */
public abstract class AbstractTenPaymentProcessorImpl implements PaymentProcessor {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(AbstractTenPaymentProcessorImpl.class);

	@Override
	public String payto(String shopName, String userName, String payTypeId,
			String out_trade_no, String subject, String body, String total_fee,
			String ip) {
		
		PayType payType = PayPalConfig.getPayType(shopName, payTypeId);
		String seller_email = payType.getSellerEmail();
		String partner 		= payType.getPartner();
		String key 			= payType.getValidateKey();
		//String show_url 	= TenPayConfig.show_url;
		String notify_url 	= TenPayConfig.notify_url;
		String return_url = TenPayConfig.return_url;
		
		Map<String, String> sParaTemp = new HashMap<String, String>();

		//当前时间 yyyyMMddHHmmss
		String currTime = TenpayUtil.getCurrTime();

//		//8位日期
//		String strDate = currTime.substring(0, 8);

//		//6位时间
//		String strTime = currTime.substring(8, currTime.length());

//		//四位随机数
//		String strRandom = TenpayUtil.buildRandom(4) + "";
		
//		//10位序列号,可以自行调整。
//		String strReq = strTime + strRandom;
		

		//财付通交易单号，规则为：10位商户号+8位时间（YYYYmmdd)+10位流水号
//		String transaction_id = seller_email + strDate + strReq;
		
		//商家订单号,长度若超过32位，取前32位。财付通只记录商家订单号，不保证唯一。
//		String sp_billno = strReq;
		
	
		//-----------------------------
		//设置支付参数
		//-----------------------------
		//sParaTemp.put("key", key);
		sParaTemp.put("partner", partner);		        //商户号
		sParaTemp.put("out_trade_no", out_trade_no);		//商家订单号 //strReq?
		Double totalFee = Double.valueOf(total_fee) * 100;
		sParaTemp.put("total_fee", (totalFee.intValue())+"");			        //商品金额,以分为单位
		sParaTemp.put("return_url", return_url);		    //交易完成后跳转的URL
		sParaTemp.put("notify_url", notify_url);		    //接收财付通通知的URL
		if(subject.length()> 32){
			subject = subject.substring(0, 32);
		}
		sParaTemp.put("subject", subject);              //商品名称(中介交易时必填)
		if(AppUtils.isBlank(body)){
			body = subject;
			if(body == null){
				body = "商品";
			}
		}
		sParaTemp.put("body", body);	                    //商品描述
		sParaTemp.put("bank_type", "DEFAULT");		    //银行类型(中介担保时此参数无效)
		sParaTemp.put("spbill_create_ip",ip);   //用户的公网ip，不是商户服务器IP
		sParaTemp.put("fee_type", "1");                    //币种，1人民币

		//系统可选参数
		sParaTemp.put("sign_type", "MD5");                //签名类型,默认：MD5
		sParaTemp.put("service_version", "1.0");			//版本号，默认为1.0
		sParaTemp.put("input_charset", "utf-8");            //字符编码
		sParaTemp.put("sign_key_index", "1");             //密钥序号


		//业务可选参数
		sParaTemp.put("attach", "");                      //附加数据，原样返回
		sParaTemp.put("product_fee", "");                 //商品费用，必须保证transport_fee + product_fee=total_fee
		sParaTemp.put("transport_fee", "0");               //物流费用，必须保证transport_fee + product_fee=total_fee
		sParaTemp.put("time_start", currTime);            //订单生成时间，格式为yyyymmddhhmmss
		sParaTemp.put("time_expire", "");                 //订单失效时间，格式为yyyymmddhhmmss
		sParaTemp.put("buyer_id", "");                    //买方财付通账号
		sParaTemp.put("goods_tag", "");                   //商品标记
		sParaTemp.put("trade_mode", parsrTradeMode(sParaTemp));  //交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
		sParaTemp.put("transport_desc", "");              //物流说明
		sParaTemp.put("trans_type", "1");                  //交易类型，1实物交易，2虚拟交易
		sParaTemp.put("agentid", "");                     //平台ID
		sParaTemp.put("agent_type", "");                  //代理模式，0无代理(默认)，1表示卡易售模式，2表示网店模式
		sParaTemp.put("seller_id", "");                   //卖家商户号，为空则等同于partner
		
		String result = TenPayService.create_direct_pay_by_user(sParaTemp, key);
		System.out.println("send to tencent gateway " + result);
		return result;
	}

	public abstract String parsrTradeMode(Map<String, String> sParaTemp);

}
