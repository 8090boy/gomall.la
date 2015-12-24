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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.model.dynamic.DynamicModel;
import com.legendshop.model.dynamic.Model;
import com.legendshop.model.entity.DynamicTemp;
import com.legendshop.model.entity.Product;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.ProductService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;

/**
 * The Class AskController.
 */
@Controller
@RequestMapping("/admin")
public class ProductDynParamAdminController extends BaseController {

	/** The product service. */
	@Autowired
	private ProductService productService;

	/**
	 * Query. 动态参数查询列表页面
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param dynamicTemp
	 *            the dynamic temp
	 * @return the string
	 */
	@RequestMapping("/prodspec/query/{type}")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, DynamicTemp dynamicTemp,
			@PathVariable Integer type) {
		SimpleHqlQuery hql = new SimpleHqlQuery(curPageNO);
		// 配置参数,注意顺序跟SQL一致
		hql.fillLikeParameter("name", dynamicTemp.getName());
		hql.fillParameter("status", dynamicTemp.getStatus());
		hql.fillParameter("sortId", dynamicTemp.getSortId());
		hql.fillParameter("type", type);
		hql.hasAllDataFunction(request, dynamicTemp.getUserName());
		// 每页大小
		hql.fillPageSize(request);
		PageSupport ps = productService.getDynamicTemp(hql);
		ps.savePage(request);
		dynamicTemp.setUserName(hql.getUserName());
		return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_LIST_PAGE);
	}

	/**
	 * Delete.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param tempId
	 *            the temp id
	 * @return the string
	 */
	@RequestMapping(value = "/prodspec/delete/{tempId}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long tempId) {
		String userName = UserManager.getUserName(request.getSession());
		int specType = productService.deleteDynamicTemp(tempId, userName);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
		String path = PathResolver.getPath(request, response, FowardPage.PROD_SPEC_LIST_QUERY) + "/" + specType;
		return path;
	}

	/**
	 * Load.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param tempId
	 *            the temp id
	 * @return the string
	 */
	@RequestMapping(value = "/prodspec/load/{tempId}")
	public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable Long tempId) {
		DynamicTemp dynamicTemp = productService.getDynamicTemp(tempId);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), dynamicTemp.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("dynamicTemp", dynamicTemp);
		return "/ask/ask";
	}

	/**
	 * Load attributeprod attribute.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	@RequestMapping(value = "/dynamic/loadAttribute/{prodId}")
	public String loadAttribute(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		Product product = productService.getProductById(prodId);
		if (AppUtils.isNotBlank(product)) {
			String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), product.getUserName());
			if (result != null) {
				return result;
			}
			request.setAttribute("prod", product);
			if (AppUtils.isNotBlank(product.getAttribute())) {
				request.setAttribute(Constants.DYN_TEMP_JSON, product.getAttribute());
			}
		}
		// prod
		request.setAttribute(Constants.DYNAMIC_TYPE, 1);

		return PathResolver.getPath(request, response, BackPage.DYNAMIC_ATTRIBUTE);
	}

	/**
	 * Load dynamic attribute. 编辑动态属性
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param tempId
	 *            the temp id
	 * @return the string
	 */
	@RequestMapping(value = "/prodspec/attribute/{tempId}")
	public String attribute(HttpServletRequest request, HttpServletResponse response, @PathVariable Long tempId) {
		String userName = UserManager.getUserName(request.getSession());
		DynamicTemp dynamicTemp = productService.getDynamicTemp(tempId);
		if (AppUtils.isNotBlank(dynamicTemp)) {
			String result = checkPrivilege(request, userName, dynamicTemp.getUserName());
			if (result != null) {
				return result;
			}
			request.setAttribute("dynamicTemp", dynamicTemp);
			//List<Model> modelList = JSONUtil.getArray(dynamicTemp.getContent(), Model.class);
			request.setAttribute(Constants.DYN_TEMP_JSON, dynamicTemp.getContent());
			request.setAttribute(Constants.DYNAMIC_TYPE, dynamicTemp.getType());
		}

		return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_EDIT_PAGE);
	}

	@RequestMapping(value = "/prodspec/createattribute/{type}")
	public String createattribute(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer type) {
		DynamicTemp dynamicTemp = new DynamicTemp();
		dynamicTemp.setType(type);
		request.setAttribute("dynamicTemp", dynamicTemp);
		request.setAttribute(Constants.DYNAMIC_TYPE, type);
		return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_EDIT_PAGE);
	}

	/**
	 * Load parameter.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	@RequestMapping(value = "/dynamic/loadParameter/{prodId}")
	public String loadParameter(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		Product product = productService.getProductById(prodId);
		if (AppUtils.isNotBlank(product)) {
			String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), product.getUserName());
			if (result != null) {
				return result;
			}
			request.setAttribute("prod", product);
			if (AppUtils.isNotBlank(product.getParameter())) {
				//List<Model> modelList = JSONUtil.getArray(product.getParameter(), Model.class);
				request.setAttribute(Constants.DYN_TEMP_JSON, product.getParameter());
			}
		}
		request.setAttribute(Constants.DYNAMIC_TYPE, 2);
		return PathResolver.getPath(request, response, BackPage.DYNAMIC_ATTRIBUTE);
	}

	/**
	 * Update status.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param tempId
	 *            the temp id
	 * @param status
	 *            the status
	 * @return the integer
	 */
	@RequestMapping(value = "/prodspec/updatestatus/{tempId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Integer updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable Long tempId,
			@PathVariable Integer status) {
		String loginName = UserManager.getUserName(request.getSession());
		DynamicTemp dynamicTemp = productService.getDynamicTemp(tempId);
		if (dynamicTemp == null) {
			return -1;
		}
		String result = checkPrivilege(request, loginName, dynamicTemp.getUserName());
		if (result != null) {
			return -1;
		}
		if (!status.equals(dynamicTemp.getStatus())) {
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				// user
				if (!loginName.equals(dynamicTemp.getUserName())) {
					return -1;
				}
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status)) {
					dynamicTemp.setStatus(status);
					productService.updateDynamicTemp(dynamicTemp);
				}
			} else {
				// admin
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status) || Constants.STOPUSE.equals(status)) {
					dynamicTemp.setStatus(status);
					productService.updateDynamicTemp(dynamicTemp);
				}
			}
		}
		return dynamicTemp.getStatus();
	}

	@RequestMapping(value = "/dynamic/update", method = RequestMethod.POST)
	public @ResponseBody
	boolean updateDynamicTemp(@RequestBody DynamicModel dmodel) {
		Long tempId = dmodel.getTempId();
		Integer type = dmodel.getType();
		Long sortId = dmodel.getSortId();
		Model[] model = dmodel.getModel();

		boolean result = true;
		if (model != null) {
			DynamicTemp dynamicTemp = productService.getDynamicTemp(tempId);
			if (dynamicTemp != null) {
				String userName = UserManager.getUserName();
				if (dynamicTemp.getUserName().equals(userName)) {
					dynamicTemp.setType(type);
					dynamicTemp.setContent(JSONUtil.getJson(model));
					dynamicTemp.setSortId(sortId);
					result = productService.updateDynamicTemp(dynamicTemp);
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/dynamic/save", method = RequestMethod.POST)
	public @ResponseBody
	long saveDynamicTemp(@RequestBody DynamicModel dmodel) {
		Integer type = dmodel.getType();
		Long sortId = dmodel.getSortId();
		String tempName = dmodel.getTempName();
		Model[] model = dmodel.getModel();
		if (model != null) {
			String userName = UserManager.getUserName();
			DynamicTemp dynamicTemp = new DynamicTemp();
			dynamicTemp.setName(tempName);
			dynamicTemp.setUserName(userName);
			dynamicTemp.setType(type);
			dynamicTemp.setContent(JSONUtil.getJson(model));
			dynamicTemp.setSortId(sortId);
			dynamicTemp.setStatus(Constants.ONLINE);
			return productService.saveDynamicTemp(dynamicTemp);
		}

		return -1l;
	}

	@RequestMapping(value = "/dynamic/delete", method = RequestMethod.POST)
	public @ResponseBody
	boolean deleteDynamicTemp(HttpServletRequest request, HttpServletResponse response, Long tempId) {
		// 检查权限
		String userName = UserManager.getUserName(request);
		if (AppUtils.isNotBlank(userName)) {
			DynamicTemp temp = productService.getDynamicTemp(tempId);
			if (temp != null) {
				if (userName.equals(temp.getSortName())) {
					return productService.deleteDynamicTemp(temp);
				}
			}
		}
		return false;
	}

	@RequestMapping(value = "/dynamic/loadspec", method = RequestMethod.POST)
	public @ResponseBody
	DynamicTemp loadDynamicAttributeFromTemp(Long tempId) {
		if (AppUtils.isBlank(tempId)) {
			return null;
		}
		DynamicTemp temp = productService.getDynamicTemp(tempId);
		if (temp != null) {
			if (AppUtils.isNotBlank(temp.getContent())) {
				temp.setModelList(JSONUtil.getArray(temp.getContent(), Model.class));
			}
		}
		return temp;
	}

	@RequestMapping(value = "/dynamic/savetoprod", method = RequestMethod.POST)
	public @ResponseBody
	boolean saveDynamic(@RequestBody DynamicModel dmodel) {
		Model[] model = dmodel.getModel();
		Long prodId = dmodel.getProdId();
		Integer type = dmodel.getType();

		if (model != null) {
			String userName = UserManager.getUserName();
			String result = JSONUtil.getJson(model);
			if (AppUtils.isNotBlank(result)) {
				Product product = productService.getProd(prodId, userName);
				if (product != null) {
					if (Constants.TEMPLATE_ATTRIBUTE.equals(type)) {
						product.setAttribute(result);
					} else {
						product.setParameter(result);
					}
					productService.updateProd(product);
				}
			}
		}

		return true;
	}

}
