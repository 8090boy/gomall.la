/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.constants;

import com.legendshop.util.constant.IntegerEnum;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * -------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * --------
 * ----------------------------------------------------------------------------.
 */
public enum OrderStatusEnum implements IntegerEnum {
	/*
	 * WAIT_BUYER_PAY(表示买家已在支付宝交易管理中产生了交易记录，但没有付款);
	 * 
	 * WAIT_SELLER_SEND_GOODS(表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货);
	 * 
	 * WAIT_BUYER_CONFIRM_GOODS(表示卖家已经发了货，但买家还没有做确认收货的操作);
	 * 
	 * TRADE_FINISHED(表示买家已经确认收货，这笔交易完成);
	 */
	// 1: 等待买家付款,2:买家已经付款,3:卖家已经发货,4:交易成功,5:交易关闭(已取消订单),6:退款中的订单
	// 7.货到付款(PayTypeEnum中的一种)
	/** 没有付款. */
	UNPAY(1),

	/** 已经付款,但卖家没有发货. */
	PADYED(2),

	/** 发货，导致实际库存减少. */
	CONSIGNMENT(3),

	/** 交易成功，购买数增加1. */
	SUCCESS(4),

	/** 交易失败.,还原库存 */
	CLOSE(5),

	/**退款中的订单. */
	REFUNDMENT(6),

	/**  货到付款  */
	PAY_AT_GOODS_ARRIVED(7);

	/** The num. */
	private Integer num;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.constant.IntegerEnum#value()
	 */
	public Integer value() {
		return num;
	}

	/**
	 * Instantiates a new order status enum.
	 * 
	 * @param num
	 *            the num
	 */
	OrderStatusEnum(Integer num) {
		this.num = num;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(OrderStatusEnum.SUCCESS.value());
		System.out.println(OrderStatusEnum.SUCCESS);
	}
}
