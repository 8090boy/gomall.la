/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.exception;


/**
 * 系统安全验证错误.尚未登录
 */
public class AuthorizationException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3853365722199544447L;


	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param message the message
	 */
	public AuthorizationException(String message) {
		super(message, ErrorCodes.UNAUTHORIZED);
	}

	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param message the message
	 * @param code the code
	 */
	public AuthorizationException(String message, String errorCode) {
		super(message, errorCode);
	}

	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param errorCode the error code
	 */
	public AuthorizationException(Throwable cause, String message,String errorCode) {
		super(cause, message, errorCode);
	}

}
