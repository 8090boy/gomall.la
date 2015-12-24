/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductConsult;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.service.ProductConsultService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.SafeHtml;

/**
 * The Class ProductConsultAdminController.
 */
@Controller
@RequestMapping("/productConsult")
public class ProductConsultController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ProductConsultController.class);

	/** The product consult service. */
	@Autowired
	private ProductConsultService productConsultService;

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param productConsult
	 *            the product consult
	 * @return the string
	 */
	@RequestMapping("/list/{prodId}")
	public String list(HttpServletRequest request, HttpServletResponse response, String curPageNO, Integer pointType,
			@PathVariable Long prodId) {
		parseConsult(request, response, curPageNO, pointType, prodId);
		return PathResolver.getPath(request, response, FrontPage.PRODUCT_CONSULTS);
	}

	@RequestMapping("/listcontent/{prodId}")
	public String listcontent(HttpServletRequest request, HttpServletResponse response, String curPageNO, Integer pointType,
			@PathVariable Long prodId) {
		parseConsult(request, response, curPageNO, pointType, prodId);
		return PathResolver.getPath(request, response, FrontPage.PRODUCT_CONSULTS_LIST);
	}

	private void parseConsult(HttpServletRequest request, HttpServletResponse response, String curPageNO, Integer pointType,
			Long prodId) {
		PageSupport ps = productConsultService.getProductConsultForList(curPageNO, pointType, prodId);

		// 重新命名，防止跟同一个页面的其他列表冲突
		request.setAttribute("prodCousultList", ps.getResultList());
		request.setAttribute("prodCousultCurPageNO", curPageNO);
		request.setAttribute("prodCousultToolBar", ps.getToolBar());
		request.setAttribute("prodId", prodId);
		request.setAttribute("prodCousultTotal", ps.getTotal());
	}


	/**
	 * Save.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param productConsult
	 *            the product consult
	 * @return the string
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody
	Integer save(HttpServletRequest request, HttpServletResponse response, ProductConsult consult) {
		// only for update
		String userName = UserManager.getUserName(request.getSession());
		if (userName == null) {
			log.debug("save product consult required user login before");
			return -1;
		}
		String content = consult.getContent();
		if (AppUtils.isBlank(content) || content.length() < 5 || content.length() > 200) {
			// 检查长度
			return -2;
		}

		SafeHtml safe = new SafeHtml();
		consult.setContent(safe.makeSafe(content));
		consult.setRecDate(new Date());
		consult.setPostip(request.getRemoteAddr());
		consult.setUserId(UserManager.getUserId());
		consult.setAskUserName(userName);

		// 频率检查
		long frequency = productConsultService.checkFrequency(consult);
		if (frequency > 0) {
			return -3;
		}

		productConsultService.saveProductConsult(consult);
		return 0; // OK
	}

}
