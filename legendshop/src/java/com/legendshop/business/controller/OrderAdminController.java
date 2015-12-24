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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.PermissionException;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.Sub;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;

/**
 * 订单控制器。 用于显示用户订单信息,管理员操作.
 */
@Controller
@RequestMapping("/admin/order")
public class OrderAdminController extends BaseController implements AdminController<Sub, Long> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(OrderAdminController.class);

	/** The sub service. */
	@Autowired
	private SubService subService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#query(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sub entity) {
		log.debug("query sub by SubNumber {}, ShopName {}" + entity.getSubNumber(), entity.getShopName());
		String loginName = UserManager.getUserName(request);
		String subNumber = entity.getSubNumber();
		if (!AppUtils.isBlank(subNumber)) {
			subNumber = subNumber.trim();
		}
		// Qbc查找方式
		CriteriaQuery cq = new CriteriaQuery(Sub.class, curPageNO);
		if (CommonServiceUtil.haveViewAllDataFunction(request)) {
			if (!AppUtils.isBlank(entity.getShopName())) {
				cq.eq("shopName", entity.getShopName());
			}
		} else {
			cq.eq("shopName", loginName);
		}

		if (AppUtils.isNotBlank(subNumber)) {
			cq.like("subNumber", subNumber + "%");
		}

		if (AppUtils.isNotBlank(entity.getUserName())) {
			cq.like("userName", entity.getUserName() + "%");
		}
		cq.eq("status", entity.getStatus());
		cq.eq("subCheck", entity.getSubCheck());
		if (!CommonServiceUtil.isDataForExport(cq, request)) {// 非导出情况
			cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class));
		}
		if (!CommonServiceUtil.isDataSortByExternal(cq, request)) {
			cq.addOrder("desc", "subDate");
		}

		PageSupport ps = subService.getOrderList(cq);
		ps.savePage(request);
		request.setAttribute("subForm", entity);
		return null; // no used
	}

	/**
	 * Query processing order.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	@RequestMapping("/processing")
	public String queryProcessingOrder(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sub entity) {
		entity.setSubCheck(Constants.FALSE_INDICATOR);
		query(request, response, curPageNO, entity);
		return PathResolver.getPath(request, response, BackPage.PROCESSING_ORDER_LIST_PAGE);
	}

	/**
	 * Query processed order.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	@RequestMapping("/processed")
	public String queryProcessedOrder(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sub entity) {
		entity.setSubCheck(Constants.TRUE_INDICATOR);
		query(request, response, curPageNO, entity);
		return PathResolver.getPath(request, response, BackPage.PROCESSED_ORDER_LIST_PAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#save(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Sub entity) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#delete(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, HttpServletResponse response, Long id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#load(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	/**
	 * Load by sub nember.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param subNumber
	 *            the sub number
	 * @return the string
	 */
	@RequestMapping(value = "/loadBySubnumber/{subNumber}")
	public String loadBySubNember(HttpServletRequest request, HttpServletResponse response, @PathVariable String subNumber) {
		// String userName = request.getParameter("userName");
		// String subNumber = request.getParameter("subNumber");
		List<Basket> baskets = subService.getBasketBySubNumber(subNumber);
		if (!AppUtils.isBlank(baskets)) {// 每一个订单最少应该有一个商品
			Double totalcash = CommonServiceUtil.calculateTotalCash(baskets);
			Sub sub = subService.getSubBySubNumber(subNumber);
			String loginName = UserManager.getUserName(request);
			if (!CommonServiceUtil.haveViewAllDataFunction(request) && !sub.getShopName().equals(loginName)) {
				throw new PermissionException(loginName + " cann't view Sub id is " + sub.getSubId());
			}
			request.setAttribute("sub", sub);
			request.setAttribute("baskets", baskets);
			request.setAttribute("totalcash", totalcash);
		}
		return PathResolver.getPath(request, response, BackPage.ORDERDETAIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#update(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, Long id) {
		return null;
	}

	/**
	 * 修改价格
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/modifyPrice")
	public String modifyPrice(HttpServletRequest request, HttpServletResponse response, Long id) {
		return PathResolver.getPath(request, response, BackPage.MODIFYPRICE);
	}
	
	/**
	 * 管理员后台修改价格
	 * @param subId
	 * @param totalPrice
	 * @return
	 */
	@RequestMapping(value = "/adminChangeSubPrice")
	public @ResponseBody  String adminChangeSubPrice(HttpServletRequest request, HttpServletResponse response,Long subId, String totalPrice) {
		if (subId == null || totalPrice == null) {
			return Constants.FAIL;
		}
		Double price = null;
		try {
			price = Double.valueOf(totalPrice.trim());
		} catch (Exception e) {
			return  Constants.FAIL;
		}
		if (price == null || price < 0 || price > 9999999999999D) {
			return Constants.FAIL;
		}
		Sub sub = subService.getSubById(subId);
		// 检查权限
		String userName = UserManager.getUserName(request);
			if (!sub.getUserName().equals(userName) && !sub.getShopName().equals(userName)) {
				log.warn("can not change sub {} does not belongs to you", subId);
				return Constants.FAIL;
		}
		boolean result = false;
		try {
			result = subService.updateSubPrice(sub, userName, price);
		} catch (Exception e) {
			throw new BusinessException("update price failed");
		}

		if (result) {
			return Constants.SUCCESS;
		} else {
			return Constants.FAIL;
		}

	}

}
