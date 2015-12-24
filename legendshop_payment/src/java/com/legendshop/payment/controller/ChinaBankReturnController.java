package com.legendshop.payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.constant.PathResolver;
import com.legendshop.spi.page.TilesPage;

/**
 * 银联返回接口
 *
 */
@Controller
@RequestMapping("/pay/cbpay")
public class ChinaBankReturnController extends PaymentReturnBaseController {
	/**
	 * return, 支付平台返回的消息
	 * @param request
	 * @param response
	 * @param ipAddress
	 * @return
	 */
	@RequestMapping("/response")
	public String response(HttpServletRequest request, HttpServletResponse response) {
		String order_no = request.getParameter("out_trade_no");	//获取订单号,根据订单号找出对应的订单和用户
		System.out.println("return order_no ==== " + order_no);
	
		return PathResolver.getPath(request, response, TilesPage.LEAVEWORD);
	}
	/**
	 * 支付通知接口
	 * @param request
	 * @param response
	 * @param ipAddress
	 * @return
	 */
	@RequestMapping("/notify")
	public String notify(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, TilesPage.LEAVEWORD);
	}
}
