/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.alipay.config;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.page.PagerUtil;
import com.legendshop.payment.config.PaymentConfig;

/**
 * 支付宝配置
 * LegendShop 版权所有,并保留所有权利。
 * 
 * 
 * 官方网站：http://www.legendesign.net
 */
public class AlipayConfig extends PaymentConfig{
	// 如何获取安全校验码和合作身份者ID
	// 1.访问支付宝商户服务中心(b.alipay.com)，然后用您的签约支付宝账号登陆.
	// 2.访问“技术服务”→“下载技术集成文档”（https://b.alipay.com/support/helperApply.htm?action=selfIntegration）
	// 3.在“自助集成帮助”中，点击“合作者身份(Partner ID)查询”、“安全校验码(Key)查询”
	
	/** The notify_url 交易过程中服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数. */
	public static String notify_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/alipay/notify";


	/** The return_url.  付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数*/
	public static String return_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/alipay/response";


}
