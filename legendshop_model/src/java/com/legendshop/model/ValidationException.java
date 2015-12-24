/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

/**
 * The Class ValidationException.
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 5850511968628572771L;
	/** The code. */
	private int code;

	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

}
