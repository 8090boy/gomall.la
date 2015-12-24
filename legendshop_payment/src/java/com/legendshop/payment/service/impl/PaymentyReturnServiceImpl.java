/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.service.impl;

import java.util.Date;

import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.payment.alipay.util.AlipayNotify;
import com.legendshop.payment.config.PaymentReturnCodeEnum;
import com.legendshop.payment.model.PaymentResponse;
import com.legendshop.payment.service.PaymentyReturnService;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.OrderStatusEnum;
import com.legendshop.spi.constants.SubStatusEnum;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;

/**
 * The Class PaymentyReturnServiceImpl.
 */
public class PaymentyReturnServiceImpl implements PaymentyReturnService {

	/**
	 * 支付类型服务
	 */
	private PayTypeService payTypeService;

	/**
	 * 订单服务
	 */
	private SubService subService;

	/**
	 * 处理支付接口翻回来的消息
	 */
	@Override
	public void parseResponse(PaymentResponse response) {
		String verifyStatus = "";
		Sub sub = subService.getSubBySubNumber(response.getOutOrderNo());
		if(sub == null){
			return;
		}
		if(AppUtils.isBlank(response.getTotalFee())){
			response.setTotalFee(String.valueOf(sub.getTotal())); //"接口没有返回总费用，采用订单设置
		}
		PayType payType = payTypeService.getPayTypeById(sub.getPayId());
		String key = payType.getValidateKey();
		boolean verifyResult = AlipayNotify.verify(sub, payType, response.getParams(), key);
		if (verifyResult) {
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (PaymentReturnCodeEnum.WAIT_SELLER_SEND_GOODS.value().equals(response.getTradeStatus())) { // 待发货
				// 判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 已经付款,但卖家没有发货
				if (!OrderStatusEnum.PADYED.value().equals(sub.getStatus())) {
					sub.setPayDate(new Date());
					sub.setStatus(OrderStatusEnum.PADYED.value());
					sub.setTradeNo(response.getTradeNo());
					subService.updateSub(sub);
					subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());
				}
			} else if (PaymentReturnCodeEnum.TRADE_FINISHED.value().equals(response.getTradeStatus())
					|| PaymentReturnCodeEnum.TRADE_SUCCESS.value().equals(response.getTradeStatus())) {
				// 判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				if (!OrderStatusEnum.SUCCESS.value().equals(sub.getStatus()) && !"Y".equals(sub.getSubCheck())) {

					sub.setPayDate(new Date());
					sub.setStatus(OrderStatusEnum.SUCCESS.value());
					sub.setSubCheck(Constants.TRUE_INDICATOR);// 完成订单的标识
					sub.setTradeNo(response.getTradeNo());
					subService.updateSub(sub);
					subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());
				}
			}else if (PaymentReturnCodeEnum.WAIT_BUYER_PAY.value().equals(response.getTradeStatus())) {
				//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
				//判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				//out.println("success");	//请不要修改或删除
			}else if (PaymentReturnCodeEnum.WAIT_BUYER_CONFIRM_GOODS.value().equals(response.getTradeStatus())) {
				//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
				//判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				//out.println("success");	//请不要修改或删除
			}

			verifyStatus = "验证成功";
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {
			verifyStatus = "验证失败";
		}
		
		response.setVerifyStatus(verifyStatus);
		response.setSub(sub);
	}

	public void setPayTypeService(PayTypeService payTypeService) {
		this.payTypeService = payTypeService;
	}

	public void setSubService(SubService subService) {
		this.subService = subService;
	}

}
