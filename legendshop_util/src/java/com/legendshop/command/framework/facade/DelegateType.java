/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import java.io.Serializable;

import com.legendshop.command.framework.Request;
import com.legendshop.command.framework.Response;
import com.legendshop.command.framework.State;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public interface DelegateType extends Serializable {

	/**
	 * Inits the.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @return true, if successful
	 */
	public boolean init(String jndiName);

	/**
	 * Execute.
	 * 
	 * @param request
	 *            the request
	 * @return the response
	 * @throws Exception
	 *             the exception
	 */
	public Response execute(Request request) throws Exception;

	/**
	 * 调用服务器端服务。在只有一个请求参数和返回值的情况下使用。 处理调用后端过程中，参数封装，错误代码设置，异常处理等公共部分。.
	 * 
	 * @param paramName
	 *            参数名称
	 * @param paramValue
	 *            参数值
	 * @param serviceName
	 *            服务名
	 * @param retParamName
	 *            服务端返回值
	 * @param state
	 *            调用结果信息
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public Object execute(String paramName, Object paramValue, String serviceName, String retParamName, State state)
			throws Exception;

	/**
	 * 调用服务器端服务。在不需要请求参数和只有一个返回参数的情况下使用。 处理调用后端过程中，参数封装，错误代码设置，异常处理等公共部分。.
	 * 
	 * @param serviceName
	 *            服务名
	 * @param retParamName
	 *            服务端返回值
	 * @param state
	 *            调用结果信息
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public Object execute(String serviceName, String retParamName, State state) throws Exception;

	/**
	 * 调用服务器端服务。在只有一个请求参数和没有返回值的情况下使用。.
	 * 
	 * @param paramName
	 *            the param name
	 * @param paramValue
	 *            the param value
	 * @param serviceName
	 *            the service name
	 * @param state
	 *            the state
	 * @throws Exception
	 *             the exception
	 */
	public void execute(String paramName, Object paramValue, String serviceName, State state) throws Exception;

}