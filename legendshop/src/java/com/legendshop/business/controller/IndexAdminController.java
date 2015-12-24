/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.business.helper.MenuManager;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.event.CoreEventId;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.event.EventContext;
import com.legendshop.event.EventHome;
import com.legendshop.event.GenericEvent;
import com.legendshop.model.UserInfo;
import com.legendshop.model.entity.Menu;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.service.IndexService;

/**
 * 后台首页功能.
 */
@Controller
public class IndexAdminController extends BaseController {
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(IndexAdminController.class);

	/** The index service. */
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private MenuManager menuManager;

	/**
	 * 后台首页，加载用户相关资料并显示.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/admin/dashboard")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		log.debug("adminIndex starting");
		String userName = UserManager.getUserName(request.getSession());
		ShopDetailView shopDetail = ThreadLocalContext.getShopDetailView(request, response, userName);
		UserInfo userInfo = indexService.getAdminIndex(userName, shopDetail);
		request.setAttribute("userInfo", userInfo);

		EventContext eventContext = new EventContext(request);
		EventHome.publishEvent(new GenericEvent(eventContext, CoreEventId.LICENSE_UPGRADE_CHECK_EVENT));

		if (eventContext.getBooleanResponse() && CommonServiceUtil.haveViewAllDataFunction(request)) {
			request.setAttribute("needUpgrade", true);
		}

		return PathResolver.getPath(request, response, BackPage.DASH_BOARD);
	}

	@RequestMapping("/admin/index")
	public String home(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.ADMIN_HOME);
	}

	@RequestMapping("/admin/menu/{order}")
	public String menu(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer order) {
		HttpSession session = request.getSession();
		List<Menu> menuList = menuManager.getMenus(session);
		Menu currentMenu = null;
		for (Menu menu : menuList) {
			if(menu.getMenuId().intValue() ==order){
				currentMenu = menu;
				break;
			}
		}
		 request.setAttribute("currentMenu", currentMenu);
		 return PathResolver.getPath(request, response, BackPage.MENU);
	}

	@RequestMapping("/admin/top")
	public String top(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		 menuManager.getMenus(session);
		return PathResolver.getPath(request, response, BackPage.ADMIN_TOP);
	}

	/**
	 * @param menuManager the menuManager to set
	 */
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

}
