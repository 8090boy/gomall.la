/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.xml;

/**
 * 配置文件异常
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = 2878865248118400325L;

	/**
	 * Instantiates a new config exception.
	 */
	public ConfigException() {
	}

	/**
	 * Instantiates a new config exception.
	 * 
	 * @param msg
	 *            the msg
	 */
	public ConfigException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new config exception.
	 * 
	 * @param msg
	 *            the msg
	 * @param throwable
	 *            the throwable
	 */
	public ConfigException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
