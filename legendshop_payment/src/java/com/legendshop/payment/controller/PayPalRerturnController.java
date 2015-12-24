package com.legendshop.payment.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.constant.PathResolver;
import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.payment.paypal.PayPalService;
import com.legendshop.payment.paypal.util.PayPalConfig;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.timer.SubService;

/**
 * 支付宝支付返回接口
 *
 */
@Controller
@RequestMapping("/pay/paypal")
public class PayPalRerturnController extends PaymentReturnBaseController {
	@Autowired
	private PayTypeService  payTypeService;
	
	/** The order service. */
	@Autowired
	private SubService subService;
	
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
	@SuppressWarnings({ "deprecation", "rawtypes" })
	@RequestMapping("/notify")
	public String notify(HttpServletRequest request, HttpServletResponse response) {
		
	    Enumeration en = request.getParameterNames();
		String str = "cmd=_notify-validate";
		while (en.hasMoreElements()) {
			String paramName = (String) en.nextElement();
			String paramValue = request.getParameter(paramName);
			str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
		}
		String res = "";
		try {
			URL u = new URL(PayPalService.PAYPAL_GATEWAY);
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(str);
			pw.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			res = in.readLine();
			in.close();
		}
		catch (IOException ex){
			//log.error(ex);
		}
		// assign posted variables to local variables
		//String itemName      = request.getParameter("item_name"); // 订单名称
		//String payerEmail    = request.getParameter("payer_email");// 付款邮件
		String itemNumber      = request.getParameter("item_number"); // 订单Id
		String paymentStatus   = request.getParameter("payment_status"); // 支付状态
		String paymentAmount   = request.getParameter("mc_gross"); // 支付金额
		String paymentCurrency = request.getParameter("mc_currency");// 货币方式
		String txnId 		   = request.getParameter("txn_id");// 流水号
		String receiverEmail   = request.getParameter("receiver_email");// 支付帐号
		
		Sub     sub			   = subService.getSubBySubNumber(itemNumber);
		PayType payType        = payTypeService.getPayTypeById(sub.getPayId());
		String seller_email    = payType.getSellerEmail();
		
		if (res.equals("VERIFIED")) { //支付成功
			boolean isPaymentStatus   = "Completed".equals(paymentStatus); //检测付款状态
			boolean isPaymentAmount   = sub.getTotal().equals(paymentAmount); //检测付款金额
			boolean isReceiverEmail   = seller_email.equals(receiverEmail); //检测付款邮箱
			boolean isPaymentCurrency = PayPalConfig.currency_code.equals(paymentCurrency) ;//检测币种
			if (isPaymentCurrency&& isPaymentStatus && isPaymentAmount
					&& isReceiverEmail) {
				 //更新订单状态
			}else{
				//错误
			}
		}else if (res.equals("INVALID")) {
			//错误
		} else {
			//错误
		}
		return PathResolver.getPath(request, response, TilesPage.LEAVEWORD);
	}
}
