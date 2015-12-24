/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.exception;


/**
 * 系统自定义业务异常.
 */
public class BusinessException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1072822638462729389L;

	/**
	 * Instantiates a new business exception.
	 *
	 * @param message the message
	 */
	public BusinessException(String message) {
		super(message, ErrorCodes.BUSINESS_ERROR);
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param message the message
	 * @param code the code
	 */
	public BusinessException(String message,  String errorCode) {
		super(message, errorCode);
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param errorCode the error code
	 */
	public BusinessException(Throwable cause, String message, String errorCode) {
		super(cause, message, errorCode);
	}


}
