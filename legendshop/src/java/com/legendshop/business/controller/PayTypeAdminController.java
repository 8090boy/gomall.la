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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.AuthorizationException;
import com.legendshop.core.exception.ConflictException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.PayType;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.processor.PaymentProcessor;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.PaymentService;
import com.legendshop.util.AppUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
@Controller
@RequestMapping("/admin/paytype")
public class PayTypeAdminController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(PayTypeAdminController.class);

	/** The pay type service. */
	@Autowired
	private PayTypeService payTypeService;

	@Autowired
	private PaymentService paymentService;

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param payType
	 *            the pay type
	 * @return the string
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, PayType payType) {
		CriteriaQuery cq = new CriteriaQuery(PayType.class, curPageNO, "javascript:pager");
		cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		//cq = hasAllDataAndOperationFunction(cq, request, StringUtils.trim(payType.getUserName()));
		String name = UserManager.getUserName(request.getSession());
		if (name == null) {
			throw new AuthorizationException(name + " did not logon yet!");
		}
		cq.eq("userName", name);

		PageSupport ps = payTypeService.getPayTypeList(cq);
		ps.savePage(request);
		request.setAttribute("bean", payType);
		return PathResolver.getPath(request, response, BackPage.PAY_TYPE_LIST_PAGE);
	}

	/**
	 * Save.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param payType
	 *            the pay type
	 * @return the string
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, PayType payType) {
		String name = UserManager.getUserName(request.getSession());
		if (AppUtils.isBlank(name)) {
			throw new AuthorizationException("you are not logon yet!");
		}
		payType.setUserName(name);
		PaymentProcessor paymentProcessor = paymentService.getPaymentProcessor(payType.getPayTypeId());
		if (AppUtils.isNotBlank(paymentProcessor)) {
			payType.setPayTypeName(paymentProcessor.getName());
		}
		if (AppUtils.isNotBlank(payType.getPayId())) {
			return update(request, response, payType);
		}
		PayType origin = payTypeService.getPayTypeByIdAndName(payType.getUserName(), payType.getPayTypeId());
		if (AppUtils.isNotBlank(origin)) {
			throw new ConflictException("你已经创建一个叫 “" + payType.getPayTypeName() + "” 的支付方式");
		}
		payTypeService.save(payType);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.PAY_TYPE_LIST_QUERY);
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
	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		payTypeService.delete(id);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
		return PathResolver.getPath(request, response, FowardPage.PAY_TYPE_LIST_QUERY);
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
	@RequestMapping(value = "/load/{id}")
	public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		PayType payType = payTypeService.getPayTypeById(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), payType.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("bean", payType);
		return PathResolver.getPath(request, response, BackPage.PAY_TYPE_EDIT_PAGE);
	}

	/**
	 * Load.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PAY_TYPE_EDIT_PAGE);
	}

	/**
	 * Update.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param payType
	 *            the pay type
	 * @return the string
	 */
	private String update(HttpServletRequest request, HttpServletResponse response, PayType payType) {
		PayType origin = payTypeService.getPayTypeById(payType.getPayId());
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), origin.getUserName());
		if (result != null) {
			return result;
		}
		log.info("{} update paytype, Partner: {}, ValidateKey: {}, SellerEmail: {} ",
				new Object[] { origin.getUserName(), origin.getPartner(), origin.getValidateKey(), origin.getSellerEmail() });
		origin.setMemo(payType.getMemo());
		origin.setPartner(payType.getPartner());
		origin.setPayTypeId(payType.getPayTypeId());
		origin.setPayTypeName(payType.getPayTypeName());
		origin.setSellerEmail(payType.getSellerEmail());
		origin.setValidateKey(payType.getValidateKey());
		try {
			payTypeService.update(origin);
		} catch (DataIntegrityViolationException e) {
			throw new ConflictException(e, "你已经创建一个叫 “" + payType.getPayTypeName() + "” 的支付方式",ErrorCodes.RESOURCE_CONFLICT);
		}
		return PathResolver.getPath(request, response, FowardPage.PAY_TYPE_LIST_QUERY);
	}

}
