/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.locator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.TemplateEnum;
import com.legendshop.spi.locator.GenericServiceLocator;
import com.legendshop.spi.service.SortService;

/**
 * The Class SortServiceLocator.
 */
public class SortServiceLocator extends GenericServiceLocator<SortService> {

	/** The sort service. */
	private SortService sortService;

	/**
	 * Gets the concrete service.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param page
	 *            the page
	 * @param sortId
	 *            the sort id
	 * @return the concrete service
	 */
	public SortService getConcreteService(HttpServletRequest request, HttpServletResponse response, PageDefinition page, Sort sort) {
		//ThreadLocalContext.setCurrentShopName(request, response, sort.getUserName());
		String template = ThreadLocalContext.getFrontType(request, response, page.getNativeValue(), page);
		SortService service = serviceMap.get(template);
		if (service == null) {
			service = serviceMap.get(TemplateEnum.DEFAULT);
		}
		return service;
	}

	public Sort getSort(Long sortId) {
		Sort sort = sortService.getSortById(sortId);
		if (sort == null) {
			throw new NotFoundException("sort can not be null", ErrorCodes.ENTITY_NO_FOUND);
		}
		return sort;
	}

	/**
	 * Sets the sort service.
	 * 
	 * @param sortService
	 *            the sortService to set
	 */
	public void setSortService(SortService sortService) {
		this.sortService = sortService;
	}

	/**
	 * Gets the sort service.
	 * 
	 * @return the sortService
	 */
	public SortService getSortService() {
		return sortService;
	}
}
