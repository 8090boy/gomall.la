/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public interface ServiceLocatorIF {

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public ApplicationContext getContext();

	/**
	 * Gets the bean.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the bean
	 */
	public Object getBean(String beanName);

	/**
	 * Sets the context.
	 * 
	 * @param ctx
	 *            the new context
	 */
	public void setContext(ApplicationContext ctx);

	/**
	 * Gets the bean.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @param bean
	 *            the bean
	 * @return the bean
	 */
	public <T> T getBean(Class<T> type, String bean);

	/**
	 * Gets the bean.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param type
	 *            the type
	 * @return the bean
	 */
	public <T> T getBean(Class<T> type);

	/**
	 * Contains bean.
	 * 
	 * @param bean
	 *            the bean
	 * @return true, if successful
	 */
	public boolean containsBean(String bean);

	/**
	 * Refresh.
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	public void refresh(ServletContext servletContext);

	/**
	 * Checks if is context refreshed.
	 * 
	 * @return true, if is context refreshed
	 */
	public boolean isContextRefreshed();

	/**
	 * Sets the context refreshed.
	 * 
	 * @param isContextRefreshed
	 *            the new context refreshed
	 */
	public void setContextRefreshed(boolean isContextRefreshed);
}
