/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.kq;

/**
 *类名： QK_service
 *功能： QK外部服务接口控制
 *详细：该页面是请求参数核心处理文件，不需要修改
 *版本：3.1
 *修改日期：2010-11-24
 *说明：
 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 该代码仅供学习和研究PayPal接口使用，只是提供一个参考。
 */
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.payment.kq.util.KQSubmit;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * ------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------
 * -------------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public class KQService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(KQService.class);

	/**
	 *  QK提供给商户的服务接入网关URL.
	 * */
	public static final String PAYPAL_GATEWAY = "https://www.99bill.com/gateway/recvMerchantInfoAction.htm";
 

	 
	/**
	 * 构造即时到帐接口.
	 * 
	 * @param sParaTemp
	 *            请求参数集合
	 * @param validateKey
	 *            the validate key
	 * @return 表单提交HTML信息
	 */
	public static String create_direct_pay_by_user(Map<String, String> sParaTemp, String validateKey) {

		// 增加基本配置
		String strButtonName = "确认";

		return KQSubmit.buildForm(sParaTemp, PAYPAL_GATEWAY, "post", strButtonName, validateKey);
	}
	 
}
