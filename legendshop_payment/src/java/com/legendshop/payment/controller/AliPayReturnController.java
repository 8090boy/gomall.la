package com.legendshop.payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.service.locator.SubServiceLocator;
import com.legendshop.core.UserManager;
import com.legendshop.model.entity.Sub;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.payment.config.PaymentReturnCodeEnum;
import com.legendshop.payment.model.PaymentResponse;
import com.legendshop.payment.service.PaymentyReturnService;
import com.legendshop.spi.service.UserDetailService;

/**
 * 支付宝支付返回接口
 * 
 */
@Controller
@RequestMapping("/pay/alipay")
public class AliPayReturnController extends PaymentReturnBaseController {

	@Autowired
	private PaymentyReturnService paymentyReturnService;

	@Autowired
	private SubServiceLocator subServiceLocator;

	@Autowired
	private UserDetailService userDetailService;

	/**
	 * return, 支付平台返回的消息
	 * 
	 * @param request
	 * @param response
	 * @param ipAddress
	 * @return
	 */
	@RequestMapping("/response")
	public String response(HttpServletRequest request, HttpServletResponse response) {
		// 构造Dto
		PaymentResponse paymentResponse = constructPaymentResponse(request);	
		paymentyReturnService.parseResponse(paymentResponse);
		// 获取订单
		Sub sub = subServiceLocator.getSub(paymentResponse.getSub().getSubId());
		if (sub != null) {
			UserDetail userDetail = null;
			// 获取卖家并更新积分
			try {
				userDetail = userDetailService.getUserDetail(UserManager.getUserName(request));
			} catch (Exception e) {
				System.out.println("Get user is error!");
			}
			try {
				if( userDetail.getScore() == null ){
					userDetail.setScore(sub.getScore());
				}else{
					userDetail.setScore(userDetail.getScore() + sub.getScore());
				}
				userDetailService.updateUser(userDetail);
			} catch (Exception e) {
				System.out.println("User is error  as Setting and sevaed !");
			}
		}
		request.setAttribute("payResult", paymentResponse);
		return RETURN_URL;
	}

	/**
	 * 支付通知接口
	 * 
	 * @param request
	 * @param response
	 * @param ipAddress
	 * @return
	 */
	@RequestMapping("/notify")
	public @ResponseBody
	String notify(HttpServletRequest request, HttpServletResponse response) {
		// 构造Dto
		PaymentResponse paymentResponse = constructPaymentResponse(request);
		log.debug("notify return order_no = {} ", paymentResponse.getOutOrderNo());
		paymentyReturnService.parseResponse(paymentResponse);
		if (PaymentReturnCodeEnum.instance(paymentResponse.getTradeStatus())) {
			return "success"; // 正常交易返回码
		} else {
			return "fail"; // 异常
		}
	}
}
