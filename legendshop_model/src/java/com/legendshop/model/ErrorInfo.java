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
 * The Class ErrorInfo.
 */
public class ErrorInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8291549830638525008L;
	// 错误的字段
	/** The filed. */
	private String filed;
	// 错误类型
	/** The type. */
	private ErrorType type;
	// 错误描述
	/** The desc. */
	private String desc;

	public ErrorInfo(String filed, ErrorType type, String desc) {
		super();
		this.filed = filed;
		this.type = type;
		this.desc = desc;
	}

	/**
	 * Gets the filed.
	 * 
	 * @return the filed
	 */
	public String getFiled() {
		return filed;
	}

	/**
	 * Sets the filed.
	 * 
	 * @param filed
	 *            the filed to set
	 */
	public void setFiled(String filed) {
		this.filed = filed;
	}

	/**
	 * Gets the desc.
	 * 
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the desc.
	 * 
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public ErrorType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(ErrorType type) {
		this.type = type;
	}
}
