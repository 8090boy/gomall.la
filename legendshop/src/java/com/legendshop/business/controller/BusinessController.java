/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.spi.form.SearchForm;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.BusinessService;

/**
 * 前台主要功能.
 */
@Controller
public class BusinessController extends BaseController {

	/** The business service. */
	@Autowired
	private BusinessService businessService;

	/**
	 * 客户留言.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param ipAddress
	 *            the ip address
	 * @return the string
	 */
	@RequestMapping("/leaveword")
	public String leaveword(HttpServletRequest request, HttpServletResponse response, String ipAddress) {
		return PathResolver.getPath(request, response, TilesPage.LEAVEWORD);
	}

	/**
	 * League.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/league")
	public String league(HttpServletRequest request, HttpServletResponse response) {
		return businessService.getLeague(request, response);
	}

	/**
	 * Friendlink.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/friendlink")
	public String friendlink(HttpServletRequest request, HttpServletResponse response) {
		return businessService.getFriendlink(request, response);
	}

	@RequestMapping("/visitedshop")
	public String visitedShop(HttpServletRequest request, HttpServletResponse response) {
		businessService.getVisitedShop(request);
		return PathResolver.getPath(request, response, FrontPage.VISITED_SHOP);
	}

	/**
	 * After operation.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/afteroperation")
	public String afterOperation(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, TilesPage.AFTER_OPERATION);
	}

	/**
	 * Search.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param searchForm
	 *            the search form
	 * @return the string
	 */
	@RequestMapping("/search")
	public String search(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm) {
		return businessService.search(request, response, searchForm);
	}

}
