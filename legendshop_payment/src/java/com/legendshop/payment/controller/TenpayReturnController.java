package com.legendshop.payment.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.exception.BusinessException;
import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.payment.tenpay.RequestHandler;
import com.legendshop.payment.tenpay.ResponseHandler;
import com.legendshop.payment.tenpay.client.ClientResponseHandler;
import com.legendshop.payment.tenpay.client.TenpayHttpClient;
import com.legendshop.payment.tenpay.util.TenpayUtil;
import com.legendshop.spi.constants.OrderStatusEnum;
import com.legendshop.spi.constants.SubStatusEnum;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.Arith;

/**
 * 财付通返回接口
 * 
 */
@Controller
@RequestMapping("/pay/tenpay")
public class TenpayReturnController extends PaymentReturnBaseController {

	@Autowired
	private PayTypeService payTypeService;

	/** The order service. */
	@Autowired
	private SubService subService;

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
		String order_no = request.getParameter("out_trade_no"); // 获取订单号,根据订单号找出对应的订单和用户
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		// 商户订单号
		String out_trade_no = resHandler.getParameter("out_trade_no");
		Sub sub = subService.getSubBySubNumber(out_trade_no);
		if (sub == null) {
			throw new BusinessException("Can not find order");
		}
		PayType payType = payTypeService.getPayTypeById(sub.getPayId());
		String key = payType.getValidateKey();

		resHandler.setKey(key);

		System.out.println("前台回调返回参数:" + resHandler.getAllParameters());

		// 判断签名
		if (resHandler.isTenpaySign()) {
			// 通知id
			String notify_id = resHandler.getParameter("notify_id");
			// 财付通订单号
			String transaction_id = resHandler.getParameter("transaction_id");
			// 金额,以分为单位
			String total_fee = resHandler.getParameter("total_fee");
			// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
			String discount = resHandler.getParameter("discount");
			// 支付结果
			String trade_state = resHandler.getParameter("trade_state");
			// 交易模式，1即时到账，2中介担保
			String trade_mode = resHandler.getParameter("trade_mode");

			if ("1".equals(trade_mode)) { // 即时到账
				if ("0".equals(trade_state)) {
					// ------------------------------
					// 即时到账处理业务开始, 钱已经到账，更新订单
					// ------------------------------

					// 注意交易单不要重复处理
					// 注意判断返回金额
					sub.setPayDate(new Date());
					sub.setStatus(OrderStatusEnum.PADYED.value());
					sub.setTradeNo(transaction_id);
					subService.updateSub(sub);
					subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());
					// ------------------------------
					// 即时到账处理业务完毕
					// ------------------------------

					System.out.println("即时到帐付款成功");
				} else {
					System.out.println("即时到帐付款失败");
				}
			} else if ("2".equals(trade_mode)) { // 中介担保
				if ("0".equals(trade_state)) {
					// ------------------------------
					// 中介担保处理业务开始，更新订单表示商家已经收钱
					// ------------------------------
					sub.setPayDate(new Date());
					sub.setStatus(OrderStatusEnum.PADYED.value());
					sub.setTradeNo(transaction_id);
					subService.updateSub(sub);
					subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());

					// 注意交易单不要重复处理
					// 注意判断返回金额

					// ------------------------------
					// 中介担保处理业务完毕
					// ------------------------------

					System.out.println("中介担保付款成功");
				} else {
					System.out.println("trade_state=" + trade_state);
				}
			}
		} else {
			System.out.println("认证签名失败");
		}

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

		// ---------------------------------------------------------
		// 财付通支付通知（后台通知）示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------
		try {
			// 创建支付应答对象
			ResponseHandler resHandler = new ResponseHandler(request, response);
			// 商户订单号
			String out_trade_no = resHandler.getParameter("out_trade_no");
			Sub sub = subService.getSubBySubNumber(out_trade_no);
			if (sub == null) {
				throw new BusinessException("Can not find order");
			}
			PayType payType = payTypeService.getPayTypeById(sub.getPayId());
			String key = payType.getValidateKey();
			resHandler.setKey(key);

			System.out.println("后台回调返回参数:" + resHandler.getAllParameters());

			// 判断签名
			if (resHandler.isTenpaySign()) {

				// 通知id
				String notify_id = resHandler.getParameter("notify_id");

				// 创建请求对象
				RequestHandler queryReq = new RequestHandler(null, null);
				// 通信对象
				TenpayHttpClient httpClient = new TenpayHttpClient();
				// 应答对象
				ClientResponseHandler queryRes = new ClientResponseHandler();

				// 通过通知ID查询，确保通知来至财付通
				queryReq.init();
				queryReq.setKey(key);
				queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
				queryReq.setParameter("partner", payType.getPartner());
				queryReq.setParameter("notify_id", notify_id);

				// 通信对象
				httpClient.setTimeOut(5);
				// 设置请求内容
				httpClient.setReqContent(queryReq.getRequestURL());
				System.out.println("验证ID请求字符串:" + queryReq.getRequestURL());

				// 后台调用
				if (httpClient.call()) {
					// 设置结果参数
					queryRes.setContent(httpClient.getResContent());
					System.out.println("验证ID返回字符串:" + httpClient.getResContent());
					queryRes.setKey(key);

					// 获取id验证返回状态码，0表示此通知id是财付通发起
					String retcode = queryRes.getParameter("retcode");

					// 财付通订单号
					String transaction_id = resHandler.getParameter("transaction_id");
					// 金额,以分为单位
					String total_fee = resHandler.getParameter("total_fee");
					// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
					String discount = resHandler.getParameter("discount");
					// 支付结果
					String trade_state = resHandler.getParameter("trade_state");
					// 交易模式，1即时到账，2中介担保
					String trade_mode = resHandler.getParameter("trade_mode");

					// 判断签名及结果
					if (queryRes.isTenpaySign() && "0".equals(retcode)) {
						System.out.println("id验证成功");

						if ("1".equals(trade_mode)) { // 即时到账
							if ("0".equals(trade_state)) {
								// ------------------------------
								// 即时到账处理业务开始
								// ------------------------------
								// 已经付款,但卖家没有发货
								if (!OrderStatusEnum.PADYED.value().equals(sub.getStatus())) {
									sub.setPayDate(new Date());
									sub.setStatus(OrderStatusEnum.PADYED.value());
									sub.setTradeNo(out_trade_no);
									subService.updateSub(sub);
									subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());
								}
								// 处理数据库逻辑
								// 注意交易单不要重复处理
								// 注意判断返回金额

								// ------------------------------
								// 即时到账处理业务完毕
								// ------------------------------

								System.out.println("即时到账支付成功");
								// 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
								resHandler.sendToCFT("success");

							} else {
								System.out.println("即时到账支付失败");
								resHandler.sendToCFT("fail");
							}
						} else if ("2".equals(trade_mode)) { // 中介担保
							// ------------------------------
							// 中介担保处理业务开始
							// ------------------------------
							// 已经付款,但卖家没有发货
							if (!OrderStatusEnum.PADYED.value().equals(sub.getStatus())) {
								sub.setPayDate(new Date());
								sub.setStatus(OrderStatusEnum.PADYED.value());
								sub.setTradeNo(out_trade_no);
								subService.updateSub(sub);
								subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());
							}
							// 处理数据库逻辑
							// 注意交易单不要重复处理
							// 注意判断返回金额
							if(!sub.getTotal().equals(Arith.div(new Double(total_fee), 100d) )){
								
							}
							OrderStatusEnum orderStatusEnum = OrderStatusEnum.PADYED;

							int iStatus = TenpayUtil.toInt(trade_state);
							switch (iStatus) {
							case 0: // 付款成功
								break;
							case 1: // 交易创建

								break;
							case 2: // 收获地址填写完毕
									
								break;
							case 4: // 卖家发货成功
								orderStatusEnum =OrderStatusEnum.CONSIGNMENT;
								break;
							case 5: // 买家收货确认，交易成功
								orderStatusEnum =OrderStatusEnum.SUCCESS;
								break;
							case 6: // 交易关闭，未完成超时关闭
								orderStatusEnum =OrderStatusEnum.CLOSE;
								break;
							case 7: // 修改交易价格成功

								break;
							case 8: // 买家发起退款
								orderStatusEnum =OrderStatusEnum.REFUNDMENT;
								break;
							case 9: // 退款成功
								orderStatusEnum =OrderStatusEnum.SUCCESS;
								break;
							case 10: // 退款关闭
								orderStatusEnum =OrderStatusEnum.CLOSE;
								break;
							default:
							}
							
							sub.setPayDate(new Date());
							sub.setStatus(orderStatusEnum.value());
							sub.setTradeNo(out_trade_no);
							subService.updateSub(sub);
							subService.saveSubHistory(sub, SubStatusEnum.CHANGE_STATUS.value());

							// ------------------------------
							// 中介担保处理业务完毕
							// ------------------------------

							System.out.println("trade_state = " + trade_state);
							// 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
							resHandler.sendToCFT("success");
						}
					} else {
						// 错误时，返回结果未签名，记录retcode、retmsg看失败详情。
						System.out.println("查询验证签名失败或id验证失败" + ",retcode:" + queryRes.getParameter("retcode"));
					}
				} else {
					System.out.println("后台调用通信失败");
					System.out.println(httpClient.getResponseCode());
					System.out.println(httpClient.getErrInfo());
					// 有可能因为网络原因，请求已经处理，但未收到应答。
				}
			} else {
				System.out.println("通知签名验证失败");
			}
		} catch (Exception e) {
			log.error("error happened", e);
		}
		return "success"; //正常交易返回码
	}
}
