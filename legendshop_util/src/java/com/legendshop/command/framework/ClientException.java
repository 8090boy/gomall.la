/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ClientException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2848225058035251507L;

	/** The error code. */
	private String errorCode;

	/**
	 * Instantiates a new client exception.
	 */
	public ClientException() {
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 */
	public ClientException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param message
	 *            the message
	 */
	public ClientException(String message) {

		super(message);
		this.errorCode = null;
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param cause
	 *            the cause
	 * @param errorCode
	 *            the error code
	 */
	public ClientException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new client exception.
	 * 
	 * @param cause
	 *            the cause
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 */
	public ClientException(Throwable cause, String message, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 * 
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
