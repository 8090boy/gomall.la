/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;



/**
 * The Class StatusKeyValueEntity.
 */
public class StatusKeyValueEntity extends  KeyValueEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6535409283073212173L;
	/** status = selected?. for select  option */
	private String status;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
