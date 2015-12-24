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
public class ConflictException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1134208068875011459L;


	/**
	 * Instantiates a new conflict exception.
	 *
	 * @param message the message
	 */
	public ConflictException(String message) {
		super(message,  ErrorCodes.RESOURCE_CONFLICT);
	}


	/**
	 * Instantiates a new conflict exception.
	 *
	 * @param message the message
	 * @param code the code
	 */
	public ConflictException(String message, String errorCode) {
		super(message, errorCode);
	}


	/**
	 * Instantiates a new conflict exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param errorCode the error code
	 */
	public ConflictException(Throwable cause, String message,  String errorCode) {
		super(cause, message,  errorCode);
	}


}
