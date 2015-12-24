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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.plugins.Plugin;
import com.legendshop.spi.page.BackPage;
import com.legendshop.util.handler.PluginRepository;

/**
 * 系统缓存控制器.
 */
@Controller
@RequestMapping("/admin/system/plugin")
public class PluginController extends BaseController {

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param systemParameter
	 *            the system parameter
	 * @return the string
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response) {
		List<Plugin> plugins = PluginRepository.getInstance().getPlugins();
		request.setAttribute("pluginList",plugins);
		return PathResolver.getPath(request, response, BackPage.PLUGIN_LIST_PAGE);
	}

	@RequestMapping("/turnon/{pluginId}")
	public @ResponseBody
	String turnOn(HttpServletRequest request, HttpServletResponse response, @PathVariable String pluginId) {
		return PluginRepository.getInstance().turnOn(pluginId);
	}

	@RequestMapping("/turnoff/{pluginId}")
	public @ResponseBody
	String turnOff(HttpServletRequest request, HttpServletResponse response, @PathVariable String pluginId) {
		return PluginRepository.getInstance().turnOff(pluginId);
	}
}
