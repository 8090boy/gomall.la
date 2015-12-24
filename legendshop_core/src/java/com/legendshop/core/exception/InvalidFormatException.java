/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.exception;


/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class InvalidFormatException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1474030746589727246L;


	/**
	 * Instantiates a new invalid format exception.
	 *
	 * @param message the message
	 */
	public InvalidFormatException(String message) {
		super(message, ErrorCodes.INVALID_FORMAT);
	}

	/**
	 * Instantiates a new invalid format exception.
	 *
	 * @param message the message
	 * @param code the code
	 */
	public InvalidFormatException(String message, String errorCode) {
		super(message,errorCode);
	}

	/**
	 * Instantiates a new invalid format exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param errorCode the error code
	 */
	public InvalidFormatException(Throwable cause, String message,String errorCode) {
		super(cause, message, errorCode);
	}

}
