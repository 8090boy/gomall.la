/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.AdminController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.controller.AbstractSortController;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.NsortService;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品分类控制器。.
 */
@Controller
@RequestMapping("/admin/sort")
public class SortAdminController extends AbstractSortController implements AdminController<Sort, Long> {

	@Autowired
	private NsortService nsortService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#query(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String, java.lang.Object)
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sort sort) {
		// 产品类型: 普通产品
		sort.setSortType(ProductTypeEnum.PRODUCT.value());
		PageSupport ps = querySort(request, response, curPageNO, sort);
		ps.savePage(request);
		request.setAttribute("sort", sort);
		return PathResolver.getPath(request, response, BackPage.SORT_LIST_PAGE);
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
	public String save(HttpServletRequest request, HttpServletResponse response, Sort entity) {
		// 产品类型: 普通产品
		entity.setSortType(ProductTypeEnum.PRODUCT.value());
		parseSort(request, response, entity);
		saveMessage(request, ResourceBundleHelper.getSucessfulString());
		return PathResolver.getPath(request, response, FowardPage.SORT_LIST_QUERY);
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
	public  @ResponseBody  String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		deleteSort(request, response, id);
		saveMessage(request, ResourceBundleHelper.getDeleteString());
		return PathResolver.getPath(request, response, FowardPage.SORT_LIST_QUERY);
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
		return PathResolver.getPath(request, response, BackPage.SORT_EDIT_PAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#update(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/update/{id}")
	public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		checkNullable("sortId", id);
		Sort sort = sortService.getSortAndBrand(id);
		String result = checkPrivilege(request, UserManager.getUserName(request), sort.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("sort", sort);
		return PathResolver.getPath(request, response, BackPage.SORT_EDIT_PAGE);
	}

	@RequestMapping(value = "/updatestatus/{sortId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Integer updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId,
			@PathVariable Integer status) {
		Sort sort = sortService.getSortById(sortId);
		if (sort == null) {
			return -1;
		}
		if (!status.equals(sort.getStatus())) {
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				String loginName = UserManager.getUserName(request.getSession());
				// user
				if (!loginName.equals(sort.getUserName())) {
					return -1;
				}
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status)) {
					sort.setStatus(status);
					sortService.updateSort(sort);
				}
			} else {
				// admin
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status) || Constants.STOPUSE.equals(status)) {
					sort.setStatus(status);
					sortService.updateSort(sort);
				}
			}
		}
		return sort.getStatus();
	}

	
	/**
	 * 发布产品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/publish")
	public String publish(HttpServletRequest request, HttpServletResponse response) {
		 List<Sort> sortList = sortService.getSort(PropertiesUtil.getDefaultShopName(),true);
		request.setAttribute("sortList", sortList);
		return PathResolver.getPath(request, response, BackPage.PROD_PUBLISH_PAGE);
	}
	
	/**
	 * 发布产品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/publish/{prodId}")
	public String publishProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		 List<Sort> sortList = sortService.getSort(PropertiesUtil.getDefaultShopName(),true);
		request.setAttribute("sortList", sortList);
		request.setAttribute("prodId", prodId);
		return PathResolver.getPath(request, response, BackPage.PROD_PUBLISH_PAGE);
	}
	
	/**
	 * 根据名称和类型找到对应的商品分类
	 * 发布产品是选择分类
	 * @param request
	 * @param response
	 * @param sortName
	 * @return
	 */
	@RequestMapping(value = "/loadsort")
	public String  loadsort(HttpServletRequest request, HttpServletResponse response, String sortName) {
			List<Sort> sortList = sortService.getSort(PropertiesUtil.getDefaultShopName(), ProductTypeEnum.PRODUCT.value(), sortName);
			request.setAttribute("sortList", sortList);
			return PathResolver.getPath(request, response, BackPage.PUBLISH_SORT_QUERY_PAGE);
	}
	
	/**
	 * 根据名称和类型找到对应的商品二级分类
	 * 发布产品是选择二级分类
	 * @param request
	 * @param response
	 * @param sortName
	 * @return
	 */
	@RequestMapping(value = "/loadnsort")
	public String  loadnsort(HttpServletRequest request, HttpServletResponse response, Long sortId, String nsortName) {
			List<Nsort> nsortList = sortService.getNsortBySortId(sortId, nsortName);
			request.setAttribute("nSort2List", nsortList);
			return PathResolver.getPath(request, response, BackPage.PUBLISH_NSORT_QUERY_PAGE);
	}
	
	/**
	 * 根据名称和类型找到对应的商品三级分类
	 * @param request
	 * @param response
	 * @param sortId
	 * @param nsortName
	 * @return
	 */
	@RequestMapping(value = "/loadsubsort")
	public String  loadsubsort(HttpServletRequest request, HttpServletResponse response, Long nsortId, String nsortName) {
			List<Nsort> nsortList = sortService.getSubNsortBySortId(nsortId, nsortName);
			request.setAttribute("nSort3List", nsortList);
			return PathResolver.getPath(request, response, BackPage.PUBLISH_SUBSORT_QUERY_PAGE);
	}
	
	/**
	 * 二级商品分类
	 * @param request
	 * @param response
	 * @param sortId
	 * @return
	 */
	@RequestMapping(value = "/publish2/{sortId}")
	public String publish2(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId) {
		List<Sort> sortList = sortService.getSort(PropertiesUtil.getDefaultShopName(),true);
		Set<Nsort> nSort2List = null;
		if(sortList != null){
			for (Sort sort : sortList) {
				if(sort.getSortId().equals(sortId)){
					nSort2List = sort.getNsort();
				}
			}
		}
		request.setAttribute("nSort2List", nSort2List);
		return PathResolver.getPath(request, response, BackPage.PROD_PUBLISH2_PAGE);
	}
	
	/**
	 * 三级商品分类
	 * @param request
	 * @param response
	 * @param nsortId
	 * @return
	 */
	@RequestMapping(value = "/publish3/{nsortId}")
	public String publish3(HttpServletRequest request, HttpServletResponse response, @PathVariable Long nsortId) {
		request.setAttribute("nSort3List",nsortService.getNSort3ByNSort2(nsortId));
		return PathResolver.getPath(request, response, BackPage.PROD_PUBLISH3_PAGE);
	}
	
}
