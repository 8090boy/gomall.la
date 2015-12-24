/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.DynamicPropertiesHelper;
import com.legendshop.business.helper.PageGengrator;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.dynamic.Model;
import com.legendshop.model.entity.ProductDetail;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.ProductService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;

/**
 * 商品规格 商品动态属性，动态参数控制器.
 */
@Controller
@RequestMapping(value = "/dynamic")
public class ProductDynParamController extends BaseController {

	@Autowired
	private ProductService productService;

	/**
	 * 查询商品动态属性
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	@RequestMapping("/attribute/{prodId}")
	public String queryAttribute(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		ProductDetail prod = (ProductDetail) request.getAttribute("prod");
		String attribute = null;
		if (prod != null) {
			attribute = prod.getAttribute();
		} else {
			attribute = productService.getProdDetail(prodId).getAttribute();
		}

		if (AppUtils.isNotBlank(attribute)) {
			List<Model> modelList =JSONUtil.getArray(attribute, Model.class);
			//System.out.println("attribute = " + attribute);
			request.setAttribute("list", modelList);
			request.setAttribute("attribute", attribute);
		}
		return PathResolver.getPath(request, response, BackPage.SHOW_DYNAMIC_ATTRIBUTE);
	}

	/**
	 * 查询商品动态参数
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	@RequestMapping("/parameter/{prodId}")
	public String queryParameter(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		ProductDetail prod = (ProductDetail) request.getAttribute("prod");
		String parameter = null;
		if (prod != null) {
			parameter = prod.getParameter();
		} else {
			parameter = productService.getProdDetail(prodId).getParameter();
		}

		if (AppUtils.isNotBlank(parameter)) {
			List<Model> modelList =JSONUtil.getArray(parameter, Model.class);
			DynamicPropertiesHelper helper = new DynamicPropertiesHelper();
			request.setAttribute("dynamicProperties", "<table class='goodsAttributeTable'>" + helper.gerenateHTML(modelList)
					+ "</table>");
		}
		return PathResolver.getPath(request, response, BackPage.SHOW_DYNAMIC);
	}

	/**
	 * 保存商品动态参数
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, FowardPage.DYNAMIC_QUERY);

	}
	
	/**
	 * 查询商品动态参数
	 * @param prodId
	 * @return
	 */
	@RequestMapping(value = "/queryDynamicParameter")
	public @ResponseBody String queryDynamicParameter(HttpServletRequest request, HttpServletResponse response, Long prodId) {
		if (AppUtils.isBlank(prodId)) {
			return "";
		}
		ProductDetail prod = (ProductDetail) request.getAttribute("prod");
		String parameter = null;
		if (prod != null) {
			parameter = prod.getParameter();
		} else {
			parameter = productService.getProdParameter(prodId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (AppUtils.isNotBlank(parameter)) {
			List<Model> modelList = JSONUtil.getArray(parameter, Model.class);
			DynamicPropertiesHelper helper = new DynamicPropertiesHelper();
			map.put("dynamicProperties", "<table class='goodsAttributeTable'>" + helper.gerenateHTML(modelList) + "</table>");
			String result = PageGengrator.getInstance().crateHTML(request.getSession().getServletContext(), "showdynamic.ftl", map, ThreadLocalContext.getLocale());
			return result;
		}
		return "";
	}

}
