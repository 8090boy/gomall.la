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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.QueryMap;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.core.exception.PermissionException;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.model.entity.Hotsearch;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.HotsearchService;
import com.legendshop.util.sql.ConfigCode;

/**
 * 热门搜索控制器.
 */
@Controller
@RequestMapping("/admin/hotsearch")
public class HotsearchAdminController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(HotsearchAdminController.class);

	/** The hotsearch service. */
	@Autowired
	private HotsearchService hotsearchService;

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param hotsearch
	 *            the hotsearch
	 * @return the string
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Hotsearch hotsearch) {
		QueryMap map = new QueryMap();
		map.put("sort", hotsearch.getSort());
		map.put("title", hotsearch.getTitle());
		map.hasAllDataFunction("userName", hotsearch.getUserName());
		 String queryAllSQL = ConfigCode.getInstance().getCode("prod.queryHotSearchCount", map);
		 String querySQL = ConfigCode.getInstance().getCode("prod.queryHotSearch", map);
		SimpleSqlQuery query = new SimpleSqlQuery(Hotsearch.class, querySQL, queryAllSQL,  map.toArray());
		PageSupport ps = hotsearchService.query(query);
		ps.savePage(request);
		request.setAttribute("bean", hotsearch);
		return PathResolver.getPath(request, response, BackPage.HOT_LIST_PAGE);
	}

	/**
	 * Save.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param hotsearch
	 *            the hotsearch
	 * @return the string
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Hotsearch hotsearch) {
		String userName = UserManager.getUserName(request);
		if(hotsearch.getId() != null){//update
				Hotsearch entity = hotsearchService.getHotsearchById(hotsearch.getId());
				if (entity != null) {
					//check authority
					if (!CommonServiceUtil.haveViewAllDataFunction(request) && !userName.equals(entity.getUserName())) {
						throw new PermissionException("Can't edit Hotsearch does not onw to you!");
					}
					entity.setDate(new Date());
					entity.setMsg(hotsearch.getMsg());
					entity.setTitle(hotsearch.getTitle());
					entity.setSort(hotsearch.getSort());
					entity.setSeq(hotsearch.getSeq());
					entity.setStatus(hotsearch.getStatus());
					hotsearchService.updateHotsearch(entity);
				}
		}else{//save
			hotsearch.setUserId(UserManager.getUserId(request.getSession()));
			hotsearch.setUserName(userName);
			hotsearch.setDate(new Date());
			hotsearchService.saveHotsearch(hotsearch);
		}
		saveMessage(request, ResourceBundleHelper.getSucessfulString());
		return PathResolver.getPath(request, response, FowardPage.HOT_LIST_QUERY);
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
		Hotsearch hotsearch = hotsearchService.getHotsearchById(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), hotsearch.getUserName());
		if (result != null) {
			return result;
		}
		log.info("{} delete Hotsearch Title {}, Msg {}",
				new Object[] { hotsearch.getUserName(), hotsearch.getTitle(), hotsearch.getMsg() });
		hotsearchService.delete(id);
		saveMessage(request, ResourceBundleHelper.getDeleteString());
		return PathResolver.getPath(request, response, FowardPage.HOT_LIST_QUERY);
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
		Hotsearch hotsearch = hotsearchService.getHotsearchById(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), hotsearch.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("bean", hotsearch);
		return PathResolver.getPath(request, response, BackPage.HOT_EDIT_PAGE);
	}

	/**
	 * Update.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param hotsearch
	 *            the hotsearch
	 * @return the string
	 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Hotsearch hotsearch) {
		Hotsearch origin = hotsearchService.getHotsearchById(hotsearch.getId());
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), origin.getUserName());
		if (result != null) {
			return result;
		}
		log.info("{} update Hotsearch Title{}", origin.getUserName(), origin.getTitle());
		hotsearch.setUserId(origin.getUserId());
		hotsearch.setUserName(origin.getUserName());
		hotsearchService.updateHotsearch(hotsearch);
		return PathResolver.getPath(request, response, FowardPage.HOT_LIST_QUERY);
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
		return PathResolver.getPath(request, response, BackPage.HOT_EDIT_PAGE);
	}
	
	/**
	 * 更新状态
	 * @param request
	 * @param response
	 * @param brandId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updatestatus/{hotId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Integer updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable Long hotId,
			@PathVariable Integer status) {
		Hotsearch hotsearch = hotsearchService.getHotsearchById(hotId);
		if (hotsearch == null) {
			return -1;
		}
		if (!status.equals(hotsearch.getStatus())) {
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				String loginName = UserManager.getUserName(request.getSession());
				// user
				if (!loginName.equals(hotsearch.getUserName())) {
					return -1;
				}
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status)) {
					hotsearch.setStatus(status);
					hotsearchService.updateHotsearch(hotsearch);
				}
			} else {
				// admin
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status) || Constants.STOPUSE.equals(status)) {
					hotsearch.setStatus(status);
					hotsearchService.updateHotsearch(hotsearch);
				}
			}
		}
		return hotsearch.getStatus();
	}

}
