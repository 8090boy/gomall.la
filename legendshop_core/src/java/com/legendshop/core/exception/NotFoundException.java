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
public class NotFoundException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1107139638880131340L;


	/**
	 * Instantiates a new not found exception.
	 *
	 * @param message the message
	 */
	public NotFoundException(String message) {
		super(message, ErrorCodes.ENTITY_NO_FOUND);
	}


	/**
	 * Instantiates a new not found exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public NotFoundException(String message,String errorCode) {
		super(message, errorCode);
	}



	/**
	 * Instantiates a new not found exception.
	 *
	 * @param throwable the throwable
	 * @param message the message
	 * @param errorCode the error code
	 */
	public NotFoundException(Throwable throwable, String message,String errorCode) {
		super(throwable, message,errorCode);
	}

}
