/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

/**
 * The Class PluginRuntimeException.
 */
public class PluginRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7923496076274766942L;

	/**
	 * Instantiates a new plugin runtime exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public PluginRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new plugin runtime exception.
	 * 
	 * @param message
	 *            the message
	 */
	public PluginRuntimeException(String message) {
		super(message);
	}
}
