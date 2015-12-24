/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.exception;


/**
 * 系统错误.
 */
public class ApplicationException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8958497176157909350L;

	/**
	 * Instantiates a new application exception.
	 *
	 * @param message the message
	 */
	public ApplicationException(String message) {
		super(message,ErrorCodes.SYSTEM_ERROR);
	}


	/**
	 * Instantiates a new application exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public ApplicationException(String message,String errorCode) {
		super(message, errorCode);
	}



	/**
	 * Instantiates a new application exception.
	 *
	 * @param throwable the throwable
	 * @param message the message
	 * @param errorCode the error code
	 */
	public ApplicationException(Throwable throwable, String message, String errorCode) {
		super(throwable, message, errorCode);
	}
	
}
