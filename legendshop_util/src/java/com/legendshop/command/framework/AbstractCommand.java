/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */

package com.legendshop.command.framework;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public abstract class AbstractCommand implements Command {

	/** The error handler. */
	private ErrorHandler errorHandler;

	/** The bean name. */
	private String beanName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.command.framework.Command#getErrorHandler()
	 */
	public ErrorHandler getErrorHandler() {

		return errorHandler;
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

	/**
	 * Set the fine grained error handler.
	 * 
	 * @param errorHandler
	 *            the new error handler
	 */
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.command.framework.Command#getBeanName()
	 */
	public String getBeanName() {
		return beanName;
	}
}
