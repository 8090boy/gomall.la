/*
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
package com.legendshop.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.spi.locator.GenericServiceLocator;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.HomeService;

/**
 * To define the C2C index page.
 * 
 * @author George Guo
 * 
 */
@Controller
public class HomePageController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(HomePageController.class);

	@Autowired
	private GenericServiceLocator<HomeService> homeServiceLocator;

	/**
	 * 前台首页.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Start to call home...");
		try {
			String shopName = PropertiesUtil.getDefaultShopName();
			ThreadLocalContext.setCurrentShopName(request, response,shopName);
			ShopDetailView shopDetail = ThreadLocalContext.getShopDetailView(request, response, shopName);
			return homeServiceLocator.getConcreteService(request, response, TilesPage.HOME).getHome(request, response,shopDetail);
		} catch (Exception e) {
			logger.error("invoking index", e);
			if (!PropertiesUtil.isSystemInstalled()) {
				// redirect to the install page
				redirectToInstallPage(request);
			}
			throw new BusinessException(e,"Visit home error",ErrorCodes.BUSINESS_ERROR);
		}
	}

	public void setHomeServiceLocator(GenericServiceLocator<HomeService> homeServiceLocator) {
		this.homeServiceLocator = homeServiceLocator;
	}

}
