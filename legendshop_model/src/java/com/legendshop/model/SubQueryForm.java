/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

import java.io.Serializable;

/**
 * 查询订单信息对象.
 */
public class SubQueryForm implements Serializable {
	
	private static final long serialVersionUID = 453773941274461497L;

	/** The order type. */
	Integer orderType;
	
	/** The order active status. */
	Integer orderActiveStatus;
	
	/** The kw type. */
	Integer kwType;
	
	/** The kw text. */
	String kwText;
	
	/**
	 * Gets the order type.
	 *
	 * @return the order type
	 */
	public Integer getOrderType() {
		return orderType;
	}
	
	/**
	 * Sets the order type.
	 *
	 * @param orderType the new order type
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
	/**
	 * Gets the order active status.
	 *
	 * @return the order active status
	 */
	public Integer getOrderActiveStatus() {
		return orderActiveStatus;
	}
	
	/**
	 * Sets the order active status.
	 *
	 * @param orderActiveStatus the new order active status
	 */
	public void setOrderActiveStatus(Integer orderActiveStatus) {
		this.orderActiveStatus = orderActiveStatus;
	}
	
	/**
	 * Gets the kw type.
	 *
	 * @return the kw type
	 */
	public Integer getKwType() {
		return kwType;
	}
	
	/**
	 * Sets the kw type.
	 *
	 * @param kwType the new kw type
	 */
	public void setKwType(Integer kwType) {
		this.kwType = kwType;
	}
	
	/**
	 * Gets the kw text.
	 *
	 * @return the kw text
	 */
	public String getKwText() {
		return kwText;
	}
	
	/**
	 * Sets the kw text.
	 *
	 * @param kwText the new kw text
	 */
	public void setKwText(String kwText) {
		this.kwText = kwText;
	}
}