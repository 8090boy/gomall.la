/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.spi.constants.OrderStatusEnum;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.PaymentService;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;

/**
 * 支付控制器.
 */
@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(PaymentController.class);

	/** The payment service. */
	@Autowired
	private PaymentService paymentService;
	
	/** The order service. */
	@Autowired
	private SubService subService;
	
	@Autowired
	private PayTypeService payTypeService;

	/**
	 * 支付.
	 * TODO, for 货到付款应该到不同的地方去，写到service去
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/payto")
	public String payment(HttpServletRequest request, HttpServletResponse response) {
		String userName = UserManager.getUserName(request.getSession());
		if (AppUtils.isBlank(userName)) {
			throw new RuntimeException("not logined yet!");
		}		
		
		// 必填参数
		// UtilDate date = new UtilDate();//调取支付宝工具类生成订单号
		// String out_trade_no = date.getOrderNum();//请与贵网站订单系统中的唯一订单号匹配
		String out_trade_no = request.getParameter("subNumber");
		checkNull("out_trade_no", out_trade_no);
		String payTypeId = request.getParameter("payTypeId");
		checkNull("payTypeId", payTypeId);
		Sub sub = subService.getSubBySubNumber(out_trade_no);
		//update sub 更新订单
		if(sub == null){
			throw new NotFoundException("Can not find order");
		}
		//更新订单支付方式
		updateSub(sub,payTypeId);
		
		// 订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
		String subject = sub.getProdName(); // prodName, 作为标题
		// 订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
		String body = request.getParameter("others");
		// 订单总金额，显示在支付宝收银台里的“应付总额”里
		String price = String.valueOf(sub.getActualTotal());
	
		//向外部系统发送支付请求
		String payment_result = paymentService.payto(sub.getShopName(), userName,payTypeId, out_trade_no, subject, body,
				price, request.getRemoteAddr());
		log.debug("payment result = {}", payment_result);
		// 支付结果
		request.setAttribute("payment_result", payment_result);
		return PathResolver.getPath(request, response, BackPage.PAY_PAGE);
	}
	
	private void updateSub(Sub sub,String payTypeId){
			PayType payType = payTypeService.getPayTypeByIdAndName(sub.getShopName(), payTypeId);
			// 更新订单的状态和付款方式
			if (payType != null) {
				sub.setPayTypeName(payType.getPayTypeName());
				sub.setPayTypeId(payType.getPayTypeId());
				sub.setPayId(payType.getPayId());
				sub.setPayDate(new Date());
				sub.setStatus(OrderStatusEnum.UNPAY.value());//等待买家付款 
				subService.updateSub(sub);
			}
	}

	/**
	 * Check null.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	private void checkNull(String name, String value) {
		if (AppUtils.isBlank(value)) {
			throw new BusinessException(name + " can no be null", ErrorCodes.NON_NULLABLE);
		}
	}

}
