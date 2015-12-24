/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public interface Command extends Serializable, BeanNameAware {

	// 初始化接口
	/**
	 * Inits the.
	 * 
	 * @param parameter
	 *            the parameter
	 * @throws Exception
	 *             the exception
	 */
	public abstract void init(String parameter) throws Exception;

	/**
	 * Execute.
	 * 
	 * @param params
	 *            the params
	 * @param response
	 *            the response
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public abstract void execute(Map params, Map response) throws Exception;

	// 析构接口
	/**
	 * Fini.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public abstract void fini() throws Exception;

	/**
	 * Gets the error handler.
	 * 
	 * @return the error handler
	 */
	public ErrorHandler getErrorHandler();

	/**
	 * Gets the bean name.
	 * 
	 * @return the bean name
	 */
	public String getBeanName();

}
