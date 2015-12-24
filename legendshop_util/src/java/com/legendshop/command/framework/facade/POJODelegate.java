/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import org.apache.log4j.Logger;

import com.legendshop.command.framework.ErrorCode;
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
public class POJODelegate implements DelegateType {

	/** The logger. */
	private static Logger logger = Logger.getLogger(POJODelegate.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3352641969938792968L;

	/** The facade. */
	private PojoSessionFacade facade;

	/** The state. */
	private static boolean state = false;

	/**
	 * 初始化Spring配置。.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @return true, if successful
	 */
	public boolean init(String jndiName) {
		// 放在InitSystem中初始化
		if (state) {
			return state;
		}
		state = true;
		return state;
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * 调用服务器端服务。在只有一个请求参数和返回值的情况下使用.
	 * 
	 * @param paramName
	 *            the param name
	 * @param paramValue
	 *            the param value
	 * @param serviceName
	 *            the service name
	 * @param retParamName
	 *            the ret param name
	 * @param state
	 *            the state
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public Object execute(String paramName, Object paramValue, String serviceName, String retParamName, State state)
			throws Exception {
		Object retObj = null;
		if (!DelegateUtil.isNullParam(paramValue, paramName, state)) {
			Request req = new Request();
			req.setValue(paramName, paramValue);
			req.setServiceName(serviceName);
			Response resp = this.execute(req);
			DelegateUtil.setState(state, resp);
			retObj = resp.getValue(retParamName);
		}
		return retObj;
	}

	/**
	 * 调用服务器端服务。在不需要请求参数和只有一个返回参数的情况下使用.
	 * 
	 * @param serviceName
	 *            the service name
	 * @param retParamName
	 *            the ret param name
	 * @param state
	 *            the state
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public Object execute(String serviceName, String retParamName, State state) throws Exception {
		Object retObj = null;
		Request req = new Request();
		req.setServiceName(serviceName);
		Response resp = this.execute(req);
		DelegateUtil.setState(state, resp);
		retObj = resp.getValue(retParamName);
		return retObj;
	}

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
	public void execute(String paramName, Object paramValue, String serviceName, State state) throws Exception {
		if (!DelegateUtil.isNullParam(paramValue, paramName, state)) {
			Request req = new Request();
			req.setValue(paramName, paramValue);
			req.setServiceName(serviceName);
			Response resp = this.execute(req);
			DelegateUtil.setState(state, resp);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.facade.DelegateType#execute(com.legendshop
	 * .command.framework.Request)
	 */
	public Response execute(Request request) {
		logger.debug("POJODelegate executing");
		Response resp = new Response();
		if (request == null) {
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode(ErrorCode.REQUEST_IS_NULL);
			return resp;
		}
		String serviceName = request.getServiceName();
		if (serviceName == null) {
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode(ErrorCode.SERVICENAME_IS_NULL);
			return resp;
		}
		try {
			getFacade().doActivities(request, resp, serviceName);
		} catch (Exception e) {
			logger.error("POJODelegate 异常 " + e.getMessage());
			// 数据库异常要到POJODelegate的时候才处理
			if (resp.getState().isOK()) {
				resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
				resp.getState().setErrCode(ErrorCode.DB_TRANSACTION_ERROR);
			}
		}

		// if (resp.getState().isOK()) {
		// resp.setReturnCode(Response.SUCCESS);
		// }
		return resp;

	}

	/**
	 * Sets the facade.
	 * 
	 * @param facade
	 *            the new facade
	 */
	public void setFacade(PojoSessionFacade facade) {
		this.facade = facade;
	}

	/**
	 * Gets the facade.
	 * 
	 * @return the facade
	 */
	public PojoSessionFacade getFacade() {
		return facade;
	}

}
