/*
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
package com.legendshop.spi.locator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.spi.constants.TemplateEnum;

/**
 * A common service locator
 * 
 */
public class GenericServiceLocator<T> implements ServiceLocator<T> {

	/** The service register map. */
	protected Map<String, T> serviceMap;

	/**
	 * Gets the concrete service implementor.
	 * 
	 * @param template
	 *            the template
	 * @return the common page service
	 */
	public T getConcreteService(HttpServletRequest request, HttpServletResponse response, PageDefinition page) {
		String template = ThreadLocalContext.getFrontType(request, response, page.getNativeValue(), page);
		T service = serviceMap.get(template);
		if (service == null) {
			service = serviceMap.get(TemplateEnum.DEFAULT);
		}
		return service;
	}

	/**
	 * Sets the service map.
	 * 
	 * @param serviceMap
	 *            the service map
	 */
	public void setServiceMap(Map<String, T> serviceMap) {
		this.serviceMap = serviceMap;
	}
}
