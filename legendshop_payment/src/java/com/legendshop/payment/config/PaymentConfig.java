/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.config;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.PayType;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.util.ContextServiceLocator;

/**
 * 支付配置基类
 * LegendShop 版权所有, 并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public abstract class PaymentConfig {

	/**
	 *  网站商品的展示地址，不允许加?id=123这类自定义参数
	 */
	public static String show_url = PropertiesUtil.getDomainName() + "/p/order";

	/** The input_charset. */
	public static String input_charset = "UTF-8";

	/** The sign_type. 
	 * 签名方式 不需修改
	 */
	public static String sign_type = "MD5";

	/** The transport. 
	 * 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
	 * */
	public static String transport = "http";

	/**
	 * 获取付款服务
	 * 
	 * @param userName
	 *            the user name
	 * @param payTypeId
	 *            the pay type id
	 * @return the pay type
	 */
	public static PayType getPayType(String userName, String payTypeId) {
		PayTypeService payTypeService = (PayTypeService) ContextServiceLocator.getInstance().getBean("payTypeService");
		return payTypeService.getPayTypeByIdAndName(userName, payTypeId);
	}
}
