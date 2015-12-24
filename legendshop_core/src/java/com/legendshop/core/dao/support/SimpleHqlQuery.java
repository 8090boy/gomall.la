/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.util.AppUtils;
import com.legendshop.util.sql.ConfigCode;

/**
 * 简单的HQL构造器 封装了HQL的参数 整合了DisplayTag的标签
 * 
 */
public class SimpleHqlQuery extends HqlQuery {

	private static final long serialVersionUID = -4278947596603935645L;

	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public SimpleHqlQuery() {
	}

	public SimpleHqlQuery(String curPage) {
		this.curPage = curPage;
	}

	public void fillParameter(String key, Object value) {
		if (AppUtils.isNotBlank(value)) {
			paramMap.put(key, value);
			params.add(value);
		}
	}

	public void fillOrder(HttpServletRequest request, String orderIndicator, String orderStr) {
		if (!FoundationUtil.isDataSortByExternal(this, request, paramMap)) {
			paramMap.put(orderIndicator, orderStr);
		}
	}

	public void fillOrder(HttpServletRequest request, String orderStr) {
		if (!FoundationUtil.isDataSortByExternal(this, request, paramMap)) {
			paramMap.put(AttributeKeys.ORDER_INDICATOR, orderStr);
		}
	}

	public void fillPageSize(HttpServletRequest request) {
		if (!FoundationUtil.isDataForExport(this, request)) {// 非导出情况
			setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		}
	}

	public void fillLikeParameter(String key, Object value) {
		if (AppUtils.isNotBlank(value)) {
			String newValue =  value.toString().trim() + "%";
			paramMap.put(key, newValue);
			params.add(newValue);
		}
	}

	/**
	 * 要在paramMap初始化完后调用
	 * 
	 * @param queryKey
	 * @param allCountKey
	 */
	public void initSQL(String queryKey, String allCountKey) {
		setQueryString(ConfigCode.getInstance().getCode(queryKey, paramMap));
		setAllCountString(ConfigCode.getInstance().getCode(allCountKey, paramMap));
	}

	/**
	 * 
	 * @param request
	 * @param userName
	 *            当前Entity所属的用户
	 * @return 当前登录用户名称
	 */
	public String hasAllDataFunction(HttpServletRequest request, String userName) {
		String loginedUser = UserManager.getUserName(request);
		// 条件,注意条件顺序跟SQL对应
		if (!FoundationUtil.haveViewAllDataFunction(request)) {
			paramMap.put("userName", loginedUser);
			this.addParams(loginedUser);
		} else {
			// 管理员
			if (AppUtils.isNotBlank(userName)) {
				paramMap.put("userName", userName);
				this.addParams(userName);
			}
		}
		return loginedUser;
	}
	
	public String getUserName(){
		return (String)paramMap.get("userName");
	}

}