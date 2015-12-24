/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.legendshop.command.framework.BaseProcessor;
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
public class PojoSessionFacade implements BeanFactoryAware {
	private BeanFactory beanFactory;

	/**
	 * Do activities.
	 * 
	 * @param request
	 *            the request
	 * @param resp
	 *            the resp
	 * @param serviceName
	 *            the service name
	 * @throws Exception
	 *             the exception
	 */
	public void doActivities(Request request, Response resp, String serviceName) throws Exception {
		BaseProcessor processor = (BaseProcessor) beanFactory.getBean(serviceName);
		processor.doActivities(request, resp);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}
}
