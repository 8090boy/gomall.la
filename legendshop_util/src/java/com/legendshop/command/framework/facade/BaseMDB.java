/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import com.legendshop.command.framework.BaseProcessor;
import com.legendshop.command.framework.GoOnException;
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
public abstract class BaseMDB extends AbstractMessageDrivenBean implements MessageListener {

	/** The logger. */
	private static Logger logger = Logger.getLogger(BaseMDB.class);

	/** The init lock. */
	private final Object initLock = new Object();

	/** The global init. */
	private boolean globalInit;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.ejb.support.AbstractMessageDrivenBean#
	 * setMessageDrivenContext(javax.ejb.MessageDrivenContext)
	 */
	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) {
		super.setMessageDrivenContext(messageDrivenContext);
		setBeanFactoryLocator(ContextSingletonBeanFactoryLocator.getInstance("classpath*:beanRefContext.xml"));
		setBeanFactoryLocatorKey("applicationContext-main");
	}

	/**
	 * Inits the.
	 */
	public abstract void init();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.ejb.support.AbstractMessageDrivenBean#onEjbCreate()
	 */
	protected void onEjbCreate() {
		synchronized (initLock) {
			if (globalInit)
				return;
			else {
				init();
				globalInit = true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {

	}

	/**
	 * An example business method.
	 * 
	 * @param request
	 *            the request
	 * @return the response
	 * @throws Exception
	 *             the exception
	 * @ejb.interface-method view-type = "remote"
	 */
	public Response execute(Request request) throws Exception {
		logger.debug("MessageDriverBean Facade execute");
		Response resp = new Response();
		if (request == null) {
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode("request is Null");
			return resp;
		}
		String serviceName = request.getServiceName();
		if (serviceName == null) {
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode("serviceName is Null");
			return resp;
		}
		BaseProcessor processor = (BaseProcessor) getBeanFactory().getBean(serviceName);
		try {
			processor.doActivities(request, resp);
		} catch (GoOnException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			// 抛出特定的异常。
			try {
				super.getMessageDrivenContext().setRollbackOnly();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new EJBException("事务回滚错误,抛出EJBException.");
			}
		}
		if (resp.getState().isOK()) {
			resp.setReturnCode(Response.SUCCESS);
		}
		return resp;
	}

}
