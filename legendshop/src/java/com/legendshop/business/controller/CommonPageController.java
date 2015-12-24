package com.legendshop.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.spi.locator.GenericServiceLocator;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.service.CommonPageService;

@Controller
public class CommonPageController extends BaseController {

	@Autowired
	private GenericServiceLocator<CommonPageService> commonPageServiceLocator;

	/**
	 * Top.页面顶部
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/top")
	public String top(HttpServletRequest request, HttpServletResponse response) {
		return commonPageServiceLocator.getConcreteService(request, response, FrontPage.TOP).getTop(request, response);
	}

	/**
	 * Top user info. 获取动态用户数据
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/topuserinfo")
	public String topUserInfo(HttpServletRequest request, HttpServletResponse response) {

		return commonPageServiceLocator.getConcreteService(request, response, FrontPage.TOP).getTopUserInfo(request, response);
	}

	/**
	 * Home top.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/home/top")
	public String homeTop(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, FrontPage.HOME_TOP);
	}

	/**
	 * Bottom.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/bottom")
	public String bottom(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, FrontPage.BOTTOM);
	}

	/**
	 * All.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/all")
	public String all(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, FrontPage.ALL);
	}

	/**
	 * Topall. 页面顶部
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/topall")
	public String topall(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, FrontPage.TOPALL);
	}

	/**
	 * 页面底部.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param newsCategory
	 *            the news category
	 * @return the string
	 */
	@RequestMapping("/copy")
	public String copyAll(HttpServletRequest request, HttpServletResponse response, String curPageNO, String newsCategory) {
		return commonPageServiceLocator.getConcreteService(request, response, FrontPage.COPY).getCopy(request, response);
	}

	/**
	 * Sets the common page service locator.
	 * 
	 * @param commonPageServiceServiceLocator
	 *            the new common page service locator
	 */
	public void setCommonPageServiceLocator(GenericServiceLocator<CommonPageService> commonPageServiceServiceLocator) {
		this.commonPageServiceLocator = commonPageServiceServiceLocator;
	}

}
