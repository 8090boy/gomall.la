/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class JCFErrorHandler implements ErrorHandler {

	/** The bean name. */
	private String beanName;

	/** The logger. */
	private static Logger logger = Logger.getLogger(JCFErrorHandler.class);

	/**
	 * 默认的错误处理器.
	 * 
	 * @param resp
	 *            the resp
	 * @param th
	 *            the th
	 * @throws Exception
	 *             the exception
	 */
	public void handleError(Response resp, Throwable th) throws Exception {
		if (th instanceof ClientException) {
			logger.error("JCFErrorHandler is dealing with ClientException errorCode is " + ((ClientException) th).getErrorCode(),
					th);
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode(
					((ClientException) th).getErrorCode() == null ? ErrorCode.BUSINESS_ERROR : ((ClientException) th)
							.getErrorCode());
			throw (ClientException) th;
		} else if (th instanceof GoOnException) {
			logger.error("JCFErrorHandler is dealing with GoOnException", th);
			resp.setReturnCode(Response.PARTIAL_SUCCESS);
			resp.getState().setErrCode(
					((GoOnException) th).getErrorCode() == null ? ErrorCode.BUSINESS_ERROR : ((GoOnException) th).getErrorCode());
		} else if (th instanceof JCFException) {
			logger.error("JCFErrorHandler is dealing with JCFException errorCode is " + ((JCFException) th).getErrorCode(), th);
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode(
					((JCFException) th).getErrorCode() == null ? ErrorCode.JCF_ERROR : ((JCFException) th).getErrorCode());
			throw (JCFException) th;
		} else if (th instanceof RemoteException) {
			logger.error("JCFErrorHandler is dealing with RemoteException", th);
			resp.setReturnCode(Response.SYSTEM_LEVEL_ERROR);
			resp.getState().setErrCode(ErrorCode.REMOTE_ERROR);
			throw (RemoteException) th;
		} else if (th instanceof NullPointerException) {
			logger.error("JCFErrorHandler is dealing with NullPointerException", th);
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode(ErrorCode.NULLPOINT_ERROR);
			throw (NullPointerException) th;
		} else {
			logger.error("JCFErrorHandler is dealing with Exception", th);
			resp.setReturnCode(Response.APPLICATION_LEVEL_ERROR);
			resp.getState().setErrCode(ErrorCode.SYSTEM_ERROR);
			if (th instanceof Exception)
				throw (Exception) th;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang
	 * .String)
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;

	}

}