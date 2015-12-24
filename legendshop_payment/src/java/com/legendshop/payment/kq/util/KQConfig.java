package com.legendshop.payment.kq.util;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.page.PagerUtil;
import com.legendshop.model.entity.PayType;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.util.ContextServiceLocator;

public class KQConfig {

	/** The notify_url. */
	public static String notify_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/qk/notify";

	// 付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	/** The return_url. */
	public static String return_url = PropertiesUtil.getDomainName() + PagerUtil.getPath() + "/pay/qk/response";

	// 网站商品的展示地址，不允许加?id=123这类自定义参数
	/** The show_url. */
	public static String show_url = PropertiesUtil.getDomainName() + "/p/order";

	/**字符集.固定选择值。可为空。
		只能选择1、2、3.
		1代表UTF-8; 2代表GBK; 3代表gb2312
		默认值为1 
	*/
	public static String inputCharset = "1"; 
	
	public static String version = "v2.0";
	
	//支付方式.固定选择值
	///只能选择00、10、11、12、13、14
	///00：组合支付（网关支付页面显示快钱支持的各种支付方式，推荐使用）10：银行卡支付（网关支付页面只显示银行卡支付）.11：电话银行支付（网关支付页面只显示电话支付）.12：快钱账户支付（网关支付页面只显示快钱账户支付）.13：线下支付（网关支付页面只显示线下支付方式）.14：B2B支付（网关支付页面只显示B2B支付，但需要向快钱申请开通才能使用）
	public static String payType = "00";

	//语言种类.固定选择值。
	///只能选择1、2、3
	///1代表中文；2代表英文
	///默认值为1
	public static String language = "1";
	

	//支付人联系方式类型.固定选择值
	///只能选择1
	///1代表Email
	public static String	payerContactType = "1";
	
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
