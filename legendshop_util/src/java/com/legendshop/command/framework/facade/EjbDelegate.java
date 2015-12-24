/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

import com.legendshop.command.framework.Request;
import com.legendshop.command.framework.Response;
import com.legendshop.command.framework.State;
import com.legendshop.command.framework.servicerepository.ServiceLocator;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class EjbDelegate implements DelegateType {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3352641969938792969L;

	/** The logger. */
	private static Logger logger = Logger.getLogger(EjbDelegate.class);

	/** The home. */
	private static BizFacadeHome home;

	/** The state. */
	private static boolean state = false;

	/**
	 * Gets the session facade.
	 * 
	 * @return the session facade
	 * @throws RemoteException
	 *             the remote exception
	 * @throws CreateException
	 *             the create exception
	 */
	public BizFacadeRemote getSessionFacade() throws RemoteException, CreateException {
		BizFacadeRemote sf = home.create();

		return sf;
	}

	/**
	 * 初始化SessionFacade,获得Home接口，并调用EJBCreate，然后调用Session Bean的init（）方法。.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @return true, if successful
	 */
	public boolean init(String jndiName) {
		if (state) {
			return state;
		}
		home = (BizFacadeHome) ServiceLocator.getInstance().getOne(jndiName);
		state = true;
		if (home == null) {
			logger.error("Delegate get Home Interface fail,jndi = " + jndiName);
			state = false;
		}
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
	 * 调用服务器端服务。在只有一个请求参数和返回值的情况下使用。.
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

	/**
	 * 调用服务器端服务。在不需要请求参数和只有一个返回参数的情况下使用。.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.facade.DelegateType#execute(com.legendshop
	 * .command.framework.Request)
	 */
	public Response execute(Request request) throws Exception {
		return getSessionFacade().execute(request);
		/*
		 * Response resp=null;
		 * 
		 * try { BizFacadeRemote sf=this.getSessionFacade();
		 * logger.debug("EjbDelegate execute，执行SessionBean的execute方法： "+sf);
		 * resp=sf.execute(request); } catch (GoOnException goon) { throw goon;
		 * } catch (JCFException jcf) { throw jcf; }catch (Exception e) {
		 * e.printStackTrace(); } return resp;
		 */
	}

}
