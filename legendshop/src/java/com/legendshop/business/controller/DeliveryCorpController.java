/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.DeliveryCorp;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.DeliveryCorpService;
import com.legendshop.util.AppUtils;

/**
 * The Class DeliveryCorpController
 * 
 */
@Controller
@RequestMapping("/admin/deliveryCorp")
public class DeliveryCorpController extends BaseController implements AdminController<DeliveryCorp, Long> {
	@Autowired
	private DeliveryCorpService deliveryCorpService;

	@Override
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, DeliveryCorp deliveryCorp) {
		CriteriaQuery cq = new CriteriaQuery(DeliveryCorp.class, curPageNO);
		cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		cq = hasAllDataFunction(cq, request, StringUtils.trim(deliveryCorp.getUserName()));

		if (!AppUtils.isBlank(deliveryCorp.getName())) {
			cq.like("name", "%" + deliveryCorp.getName() + "%");
		}
		/*
		 * //TODO add your condition
		 */

		PageSupport ps = deliveryCorpService.getDeliveryCorp(cq);
		ps.savePage(request);
		request.setAttribute("deliveryCorp", deliveryCorp);
		return PathResolver.getPath(request, response, BackPage.DELIVERYCORP_LIST_PAGE);
	}

	@Override
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, DeliveryCorp deliveryCorp) {
		DeliveryCorp dc = null;
		if (AppUtils.isNotBlank(deliveryCorp.getDvyId())) {
			dc = deliveryCorpService.getDeliveryCorp(deliveryCorp.getDvyId());
			dc.setName(deliveryCorp.getName());
			dc.setUrl(deliveryCorp.getUrl());
		} else {
			dc = deliveryCorp;
			deliveryCorp.setCreateTime(new Date());
		}
		dc.setUserId(UserManager.getUserId(request));
		dc.setUserName(UserManager.getUserName(request));
		dc.setModifyTime(new Date());

		deliveryCorpService.saveDeliveryCorp(dc);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.DELIVERYCORP_LIST_QUERY);
	}

	@Override
	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		DeliveryCorp deliveryCorp = deliveryCorpService.getDeliveryCorp(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), deliveryCorp.getUserName());
		if (result != null) {
			return result;
		}
		deliveryCorpService.deleteDeliveryCorp(deliveryCorp);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
		return PathResolver.getPath(request, response, FowardPage.DELIVERYCORP_LIST_QUERY);
	}

	@RequestMapping(value = "/load/{id}")
	public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		DeliveryCorp deliveryCorp = deliveryCorpService.getDeliveryCorp(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), deliveryCorp.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("deliveryCorp", deliveryCorp);
		return PathResolver.getPath(request, response, BackPage.DELIVERYCORP_EDIT_PAGE);
	}

	@Override
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.DELIVERYCORP_EDIT_PAGE);
	}

	@Override
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		DeliveryCorp deliveryCorp = deliveryCorpService.getDeliveryCorp(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), deliveryCorp.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("deliveryCorp", deliveryCorp);
		return PathResolver.getPath(request, response, FowardPage.DELIVERYCORP_LIST_QUERY);
	}

}
