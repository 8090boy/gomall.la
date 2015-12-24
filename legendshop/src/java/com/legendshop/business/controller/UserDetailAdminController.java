/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SqlQuery;
import com.legendshop.event.EventContext;
import com.legendshop.event.EventHome;
import com.legendshop.event.GenericEvent;
import com.legendshop.model.entity.User;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.event.EventId;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.service.UserDetailService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.sql.ConfigCode;

/**
 * 用户产品评论.
 */
@Controller
@RequestMapping("/admin/system/userDetail")
public class UserDetailAdminController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(UserDetailAdminController.class);

	/** The user detail service. */
	@Autowired
	private UserDetailService userDetailService;

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param userDetail
	 *            the user detail
	 * @return the string
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, UserDetail userDetail) {
		String userName = request.getParameter("userName") == null ? "" : request.getParameter("userName").trim();
		String enabled = request.getParameter("enabled") == null ? "" : request.getParameter("enabled");
		String haveShop = request.getParameter("haveShop") == null ? "" : request.getParameter("haveShop");
		String userMail = request.getParameter("userMail") == null ? "" : request.getParameter("userMail").trim();

		log.debug("search = {},enabled = {}, haveShop = {}, userMail ={} ", new Object[] { userName, enabled, haveShop, userMail });

		Map<String, Object> map = new HashMap<String, Object>();
		SqlQuery hqlQuery = new SqlQuery(60, curPageNO, PageProviderEnum.PAGE_PROVIDER);

		if ("1".equals(haveShop)) {
			map.put("haveShop", "and u.shop_id is not null");
		} else if ("0".equals(haveShop)) {
			map.put("haveShop", "and u.shop_id is null");
		}

		if (!AppUtils.isBlank(userName)) {
			map.put("userName", userName);
			hqlQuery.addParams("%" + userName + "%");
		}

		if (!AppUtils.isBlank(enabled)) {
			map.put("enabled", enabled);
			hqlQuery.addParams(enabled);
		}

		if (!AppUtils.isBlank(userMail)) {
			map.put("userMail", userMail);
			hqlQuery.addParams("%" + userMail + "%");
		}
		if (!CommonServiceUtil.isDataForExport(hqlQuery, request)) {// 非导出情况
		}
		if (!CommonServiceUtil.isDataSortByExternal(hqlQuery, request, map)) {
			map.put(Constants.ORDER_INDICATOR, "order by u.user_regtime desc");
		}
		String totalUserDetail = ConfigCode.getInstance().getCode("biz.QueryUserDetailCount", map);
		String userDetailSQL = ConfigCode.getInstance().getCode("biz.QueryUserDetail", map);
		hqlQuery.setAllCountString(totalUserDetail);
		hqlQuery.setQueryString(userDetailSQL);

		EventContext eventContext = new EventContext(request);
		EventHome.publishEvent(new GenericEvent(eventContext, EventId.CAN_ADD_SHOPDETAIL_EVENT));
		Boolean isSupportOpenShop = eventContext.getBooleanResponse();

		request.setAttribute("supportOpenShop", isSupportOpenShop);

		PageSupport ps = userDetailService.getUserDetailList(hqlQuery);
		ps.setResultList(convert(ps.getResultList()));
		ps.savePage(request);
		request.setAttribute("userName", userName);
		request.setAttribute("userMail", userMail);
		request.setAttribute("enabled", enabled);
		request.setAttribute("haveShop", haveShop);

		return PathResolver.getPath(request, response, BackPage.USER_DETAIL_LIST_PAGE);
	}

	/**
	 * Convert.
	 * 
	 * @param objs
	 *            the objs
	 * @return the list
	 */
	private List<UserDetail> convert(List<Object> objs) {
		if (AppUtils.isBlank(objs)) {
			return null;
		}
		List<UserDetail> list = new ArrayList<UserDetail>();
		for (Object obj : objs) {
			UserDetail userDetail = new UserDetail();
			Object[] arrayObj = (Object[]) obj;
			if (arrayObj[0] != null) {
				userDetail.setUserId((String) (arrayObj[0]));
			}
			if (arrayObj[1] != null) {
				userDetail.setUserName((String) (arrayObj[1]));
			}

			if (arrayObj[2] != null) {
				userDetail.setNickName((String) (arrayObj[2]));
			}

			if (arrayObj[3] != null) {
				userDetail.setUserMail((String) (arrayObj[3]));
			}

			if (arrayObj[4] != null) {
				userDetail.setUserRegip((String) (arrayObj[4]));
			}

			if (arrayObj[5] != null) {
				userDetail.setModifyTime((Date) (arrayObj[5]));
			}

			if (arrayObj[6] != null) {
				userDetail.setUserRegtime((Date) (arrayObj[6]));
			}

			if (arrayObj[7] != null) {
				userDetail.setEnabled((String) (arrayObj[7]));
			}

			if (arrayObj[8] != null) {
				userDetail.setShopId(((Integer) (arrayObj[8])).longValue());
			}
			list.add(userDetail);
		}
		return list;
	}

	/**
	 * 删除用户所有信息
	 * @param userId
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteUserDetail(String userId, String userName) {
		if (userId == null) {
			return Constants.FAIL;
		}
		try {
			return userDetailService.deleteUserDetail(userId, userName);
		} catch (Exception e) {
			log.error("deleteUserDetail", e);
			return  Constants.FAIL;
		}

	}

	/**
	 * Update status.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @param status
	 *            the status
	 * @return the integer
	 */
	@RequestMapping(value = "/updatestatus/{userId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	String updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId,
			@PathVariable String status) {
		User user = userDetailService.getUser(userId);
		if (user == null) {
			return "-1";
		}
		if (!status.equals(user.getEnabled())) {
			user.setEnabled(String.valueOf(status));
			userDetailService.uppdateUser(user);
		}
		return status;
	}

}
