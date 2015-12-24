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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.event.CoreEventId;
import com.legendshop.event.EventContext;
import com.legendshop.event.EventHome;
import com.legendshop.event.GenericEvent;
import com.legendshop.spi.page.BackPage;

/**
 * License控制器.
 */
@Controller
@RequestMapping("/admin/license")
public class LicenseController extends BaseController {

	/**
	 * Upgrade.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/upgrade")
	public String upgrade(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.UPGRADE_PAGE);
	}

	/**
	 * Post upgrade.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/postUpgrade")
	public
	@ResponseBody
	Integer postUpgrade(HttpServletRequest request, HttpServletResponse response) {
		EventContext eventContext = new EventContext(request);
		EventHome.publishEvent(new GenericEvent(eventContext, CoreEventId.LICENSE_STATUS_CHECK_EVENT));
		return (Integer)eventContext.getResponse();
	}

}
