package com.legendshop.payment.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.legendshop.core.base.BaseController;
import com.legendshop.payment.model.PaymentResponse;
import com.legendshop.spi.service.timer.SubService;

/**
 * 支付返回接口
 * 
 */
public abstract class PaymentReturnBaseController extends BaseController {

	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected static String RETURN_URL = "/plugins/payment/jsp/return_url";

	@Autowired
	protected SubService subService;

	/**
	 * 支付平台响应消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract String response(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 支付平台通知消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract String notify(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 从返回来的httprequest中取值组装DTO，暂时for 淘宝用，如果其他支付方式参数不一样将放在子类中实现
	 * 
	 * @return
	 */
	protected PaymentResponse constructPaymentResponse(
			HttpServletRequest request) {

		PaymentResponse response = new PaymentResponse();
		response.setOutOrderNo(request.getParameter("out_trade_no"));
		log.debug("OutOrderNo = {} ", response.getOutOrderNo());
		Map params = new HashMap();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			System.out.println("---------------------"+ name +"========"+ valueStr +"------------------");
			params.put(name, valueStr);
		}
		response.setParams(params);
		response.setTradeNo(request.getParameter("trade_no")); // 支付宝交易号
		response.setTotalFee(request.getParameter("price")); // 获取总金额
		response.setSubject(request.getParameter("subject"));// 商品、订单名称
		response.setBody(getNonNullParam(request, "body"));// 商品描述、订单备注、描述
		response.setBuyerEmail(request.getParameter("buyer_email")); // 买家支付宝账号
		response.setReceiveName(getNonNullParam(request, "receive_name"));// 收货人姓名
		response.setReceiveAddress(getNonNullParam(request, "receive_address")); // 收货人地址
		response.setReceiveZip(getNonNullParam(request, "receive_zip")); // 收货人邮编
		response.setReceivePhone(getNonNullParam(request, "receive_phone")); // 收货人电话
		response.setReceiveMobile(getNonNullParam(request, "receive_mobile")); // 收货人手机
		response.setTradeStatus(request.getParameter("trade_status")); // 交易状态
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		return response;
	}

	/**
	 * 转变为非空字符串
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	protected String getNonNullParam(HttpServletRequest request, String key) {
		String result = "";
		if (request.getParameter(key) != null) {
			result = request.getParameter(key);
		}
		return result;
	}
}
