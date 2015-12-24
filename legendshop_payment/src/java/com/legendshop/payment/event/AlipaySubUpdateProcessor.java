/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.event;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.OperationTypeEnum;
import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.payment.alipay.AlipayService;
import com.legendshop.spi.constants.OrderStatusEnum;
import com.legendshop.spi.constants.PayTypeEnum;
import com.legendshop.spi.event.ThreadEventProcessor;
import com.legendshop.spi.service.PayTypeService;

/**
 * 支付宝交货接口通知
 */
public class AlipaySubUpdateProcessor extends ThreadEventProcessor<Sub> {
	private final Logger log = LoggerFactory.getLogger(AlipaySubUpdateProcessor.class);

	private PayTypeService payTypeService;
	@Override
	public void process(Sub sub) {
		log.debug("AlipaySubUpdateProcessor calling");
		super.logUserEvent(sub, OperationTypeEnum.UPDATE);
		// 交货接口, 需要增加判断是支付宝才调用 TODO
		if (sub.getStatus().equals(OrderStatusEnum.CONSIGNMENT.value())) {
			try {
				String result = alipaySendGoods(sub);
				log.debug("alipaySendGoods result = {}", result);
			} catch (Exception e) {
				log.warn("alipaySendGoods fail", e);
			}

		}
	}
	
	/**
	 * 支付宝发货接口.
	 * 
	 * @param sub
	 *            the sub
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private String alipaySendGoods(Sub sub) throws Exception {
		// 构造函数，生成请求URL
		if (sub != null) {
			PayType payType = payTypeService.getPayTypeById(sub.getPayId());
			if (PayTypeEnum.isAlipay(payType)) {
				Map<String, String> sParaTemp = new HashMap<String, String>();
				// 发货时的运输类型
				String transport_type = "EXPRESS";
				sParaTemp.put("trade_no", sub.getTradeNo());
				sParaTemp.put("logistics_name", "Legendshop");
				sParaTemp.put("invoice_no", sub.getSubNumber());
				sParaTemp.put("transport_type", transport_type);
				sParaTemp.put("partner", payType.getPartner());
				String sHtmlText = AlipayService.send_goods_confirm_by_platform(sParaTemp, payType.getValidateKey());
				log.debug("alipaySendGoods sHtmlText = {}", sHtmlText);
				return sHtmlText;
			}

		}

		return "";
	}

	/**
	 * @param payTypeService the payTypeService to set
	 */
	public void setPayTypeService(PayTypeService payTypeService) {
		this.payTypeService = payTypeService;
	}


}
