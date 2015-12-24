/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductConsult;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.ProductConsultService;
import com.legendshop.spi.service.ProductService;

/**
 * The Class ProductConsultAdminController.
 */
@Controller
@RequestMapping("/admin/productConsult")
public class ProductConsultAdminController extends BaseController {

	/** The product consult service. */
	@Autowired
	private ProductConsultService productConsultService;

	@Autowired
	private ProductService productService;

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
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, ProductConsult productConsult) {
		PageSupport ps = productConsultService.getProductConsult(curPageNO,productConsult);
		ps.savePage(request);
		request.setAttribute("productConsult", productConsult);
		return PathResolver.getPath(request, response, BackPage.PROD_CONSULT_LIST_PAGE);
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
	public String save(HttpServletRequest request, HttpServletResponse response, ProductConsult productConsult) {

		// only for update
		String result = productConsultService.updateProductConsult(request, productConsult);
		if (result != null) {
			return result;
		}
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.PROD_CONSULT_LIST_QUERY);
	}

	/**
	 * Delete.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/delete/{consId}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long consId) {
		String result = productConsultService.deleteProductConsult(request, consId);
		if (result != null) {
			return result;
		}
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
		return PathResolver.getPath(request, response, FowardPage.PROD_CONSULT_LIST_QUERY);
	}

	/**
	 * Load.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/load/{consId}")
	public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable Long consId) {
		ProductConsult productConsult = productConsultService.getProductConsult(consId);
		if (productConsult == null) {
			throw new NotFoundException("productConsult not found with Id " + consId);
		}
		Product product = productService.getProductById(productConsult.getProdId());
		productConsult.setProdName(product.getName());
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), product.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("productConsult", productConsult);
		return PathResolver.getPath(request, response, BackPage.PROD_CONSULT_EDIT_PAGE);
	}

}
