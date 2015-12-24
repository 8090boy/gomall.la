package com.legendshop.payment.paypal.util;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.page.PagerUtil;
import com.legendshop.model.entity.PayType;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.util.ContextServiceLocator;

public class PayPalConfig {

	/** The notify_url. */
	public static String notify_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/paypal/notify";

	// 付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	/** The return_url. */
	public static String return_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/paypal/response";

	// 网站商品的展示地址，不允许加?id=123这类自定义参数
	/** The show_url. */
	public static String show_url = PropertiesUtil.getDomainName() + "/p/order";

	//币种
	/** 网站币种，默认美元 */
	public static String currency_code = "USD"; 
	
	/**
	 * Gets the pay type.
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
