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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.ConflictException;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.NsortService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.sql.ConfigCode;

/**
 * 产品子分类控制器.
 */
@Controller
@RequestMapping("/admin/nsort")
public class NsortAdminController extends BaseController implements AdminController<Nsort, Long> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(NsortAdminController.class);

	/** The nsort service. */
	@Autowired
	private NsortService nsortService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#query(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Nsort nsort) {
		if (AppUtils.isBlank(nsort.getSortId())) {
			throw new BusinessException("sort id can not be empty");
		}
		Sort sort = nsortService.getSort(nsort.getSortId());
		if (AppUtils.isBlank(sort)) {
			throw new NotFoundException("Sort is empty");
		}
		HqlQuery hql = new HqlQuery();
		hql.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		hql.setCurPage(curPageNO);
		Map<String, Object> map = new HashMap<String, Object>();
		hql.addParams(sort.getSortId());
		String userName = UserManager.getUserName(request.getSession());
		if (nsort.getParentNsortId() == null) {
			map.put("isSecNsort", "and n.parentNsortId is null");
		}
		if (!CommonServiceUtil.haveViewAllDataFunction(request)) {
			map.put("userName", userName);
			hql.addParams(userName);
		}
		if (!AppUtils.isBlank(nsort.getNsortName())) {
			map.put("nsortName", nsort.getNsortName());
			hql.addParams(nsort.getNsortName());
		}
		String QueryNsortCount = ConfigCode.getInstance().getCode("biz.QueryNsortCount", map);
		String QueryNsort = ConfigCode.getInstance().getCode("biz.QueryNsort", map);
		hql.setAllCountString(QueryNsortCount);
		hql.setQueryString(QueryNsort);
		PageSupport ps = nsortService.getNsortList(hql);
		List<Nsort> list = ps.getResultList();// 2级分类
		List<Nsort> subNsort = nsortService.getNSort3BySort(nsort.getSortId());// 3级分类
		if (!AppUtils.isBlank(list)) {
			for (Nsort n : list) {
				for (Nsort nsort2 : subNsort) {
					n.addSubSort(nsort2);
				}
			}
		}
		ps.savePage(request);
		nsort.setSortName(sort.getSortName());
		request.setAttribute("nsort", nsort);
		return PathResolver.getPath(request, response, BackPage.NSORT_LIST_PAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.base.AdminController#save(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Nsort nsort) {
		String name = UserManager.getUserName(request.getSession());
		Sort sort = nsortService.getSort(nsort.getSortId());
		String result = checkPrivilege(request, name, sort.getUserName());
		if (result != null) {
			return result;
		}
		nsortService.save(nsort);
		saveMessage(request, ResourceBundleHelper.getSucessfulString());
		return PathResolver.getPath(request, response, FowardPage.NSORT_LIST_QUERY);
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
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		Nsort nsort = nsortService.getNsort(id);
		Sort sort = nsortService.getSort(nsort.getSortId());
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), sort.getUserName());
		if (result != null) {
			return result;
		}
		log.info("{} delete Pub NsortName {}", sort.getUserName(), nsort.getNsortName());
		if ( nsortService.hasChildNsort(sort.getUserName(), id,nsort.getParentNsortId())) {
			throw new ConflictException("发现子商品分类，不能删除该商品分类！");
		}
		if (nsortService.hasChildNsortBrand(id)) {
			throw new ConflictException("商品分类下有品牌, 该商品分类不能删除！");
		}
		
		if (nsortService.hasChildProduct(sort.getUserName(), id, nsort.getParentNsortId())) {
			throw new ConflictException("商品分类下有产品, 该商品分类不能删除！");
		}
		nsortService.delete(id);
		saveMessage(request, ResourceBundleHelper.getDeleteString());
		return PathResolver.getPath(request, response, FowardPage.NSORT_LIST_QUERY) + "?sortId=" + nsort.getSortId();
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

		Nsort nsort = new Nsort();
		long sortId = ServletRequestUtils.getLongParameter(request, "sortId", -1);
		if (sortId != -1) {
			nsort.setSortId(sortId);
		}
		long parentNsortId = ServletRequestUtils.getLongParameter(request, "parentNsortId", -1);
		if (parentNsortId != -1) {
			nsort.setParentNsortId(parentNsortId);
		}

		request.setAttribute("nsort", nsort);

		return PathResolver.getPath(request, response, BackPage.NSORT_EDIT_PAGE);
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
		checkNullable("nsort Id", id);
		String result = checkLogin(request, response, UserManager.getUserName(request));
		if (result != null) {
			return result;
		}
		Nsort nsort = nsortService.getNsortById(id);
		if (nsort != null) {
			Sort sort = nsortService.getSort(nsort.getSortId());
			request.setAttribute("sort", sort);
			request.setAttribute("nsort", nsort);
		}

		return PathResolver.getPath(request, response, BackPage.NSORT_EDIT_PAGE);
	}

	/**
	 * Append brand.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/appendBrand/{id}")
	public String appendBrand(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		request.setAttribute("nsortId", id);
		return PathResolver.getPath(request, response, BackPage.NSORT_APPENDBRAND_PAGE);
	}

	@RequestMapping(value = "/turnon/{nsortId}")
	public void turnOn(HttpServletRequest request, HttpServletResponse response, @PathVariable Long nsortId) {
		Nsort nsort = nsortService.getNsort(nsortId);
		if (nsort != null) {
			Sort sort = nsortService.getSort(nsort.getSortId());
			String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), sort.getUserName());
			if (result != null) {
				return;
			}
			nsortService.turnOn(nsort);
		}

	}

	@RequestMapping(value = "/turnoff/{nsortId}")
	public void turnOff(HttpServletRequest request, HttpServletResponse response, @PathVariable Long nsortId) {
		Nsort nsort = nsortService.getNsort(nsortId);
		if (nsort != null) {
			Sort sort = nsortService.getSort(nsort.getSortId());
			String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), sort.getUserName());
			if (result != null) {
				return;
			}
			nsortService.turnOff(nsort);
		}

	}

	@RequestMapping(value = "/updatestatus/{nsortId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Integer updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable Long nsortId,
			@PathVariable Integer status) {
		Nsort nsort = nsortService.getNsort(nsortId);
		if (nsort == null) {
			return -1;
		}
		Sort sort = nsortService.getSort(nsort.getSortId());
		if (!status.equals(nsort.getStatus())) {
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				String loginName = UserManager.getUserName(request.getSession());
				// user
				if (!loginName.equals(sort.getUserName())) {
					return -1;
				}
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status)) {
					nsort.setStatus(status);
					nsortService.update(nsort);
				}
			} else {
				// admin
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status) || Constants.STOPUSE.equals(status)) {
					sort.setStatus(status);
					nsortService.update(nsort);
				}
			}
		}
		return nsort.getStatus();
	}

}
