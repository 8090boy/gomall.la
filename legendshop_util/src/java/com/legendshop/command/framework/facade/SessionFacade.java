/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractStatelessSessionBean;

import com.legendshop.command.framework.BaseProcessor;
import com.legendshop.command.framework.ErrorCode;
import com.legendshop.command.framework.Request;
import com.legendshop.command.framework.Response;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public abstract class SessionFacade extends AbstractStatelessSessionBean {

	/** The global init. */
	private boolean globalInit;

	/** The init lock. */
	private final Object initLock = new Object();

	/** The jndi ctx. */
	private Context jndiCtx;

	/**
	 * 获取EJB环境变量的值。EJB环境变量在<env-entry></env-entry>元素中定义。.
	 * 
	 * @param name
	 *            环境变量的名称
	 * @return 环境变量值
	 */
	protected Object getEnvEntry(String name) {
		Context jndiCtx = getJNDIContext();
		Object obj = null;
		try {
			obj = jndiCtx.lookup("java:comp/env/" + name);
		} catch (Exception error) {
			error.printStackTrace();
		}

		return obj;
	}

	/**
	 * 获取EJB所在的JNDI Context对象。.
	 * 
	 * @return the jNDI context
	 */
	protected synchronized Context getJNDIContext() {
		if (jndiCtx == null) {
			try {
				jndiCtx = new InitialContext();
			} catch (Exception ignored) {
			}
		}
		return jndiCtx;
	}

	/**
	 * 如果子类需要进行全局的初始化工作，那么应该Override这个方法。.
	 */
	public abstract void init();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.ejb.support.AbstractSessionBean#setSessionContext
	 * (javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) {
		super.setSessionContext(ctx);
		setBeanFactoryLocator(ContextSingletonBeanFactoryLocator.getInstance("classpath*:beanRefContext.xml"));
		setBeanFactoryLocatorKey("applicationContext-main");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.ejb.support.AbstractStatelessSessionBean#onEjbCreate
	 * ()
	 */
	protected void onEjbCreate() throws CreateException {
		synchronized (initLock) {
			if (globalInit)
				return;
			else {
				init();
				globalInit = true;
			}
		}

	}

	/**
	 * An example business method.
	 * 
	 * @param request
	 *            the request
	 * @return the response
	 * @throws EJBException
	 *             the eJB exception
	 * @ejb.interface-method view-type = "remote"
	 */
	public Response execute(Request request) throws EJBException {
		logger.debug("SessionFacade executing");
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
		BaseProcessor processor = (BaseProcessor) getBeanFactory().getBean(serviceName);
		try {
			processor.doActivities(request, resp);
		} catch (Exception e) {
			logger.error("SessionFacade 异常 " + e.getMessage());
			// 数据库异常要到SessionFacade的时候才处理
			if (resp.getState().isOK()) {
				resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
				resp.getState().setErrCode(ErrorCode.DB_TRANSACTION_ERROR);
			}
			try {
				super.getSessionContext().setRollbackOnly();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new EJBException("事务回滚错误,抛出EJBException.");
			}
			logger.error("Transation is Rollbacked.");
		}
		// if (resp.getState().isOK()) {
		// resp.setReturnCode(Response.SUCCESS);
		// }
		return resp;
	}

}
