/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.legendshop.core.UserManager;
import com.legendshop.core.exception.AuthorizationException;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.util.AppUtils;

/**
 * The Class QueryMap. SQL查询封装MAP
 */
public class QueryMap extends LinkedHashMap<String, Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5165708075543969338L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(String key, Object value) {
		if (value != null && !"".equals(value)) {
			super.put(key, value);
		}
		return value;
	}

	/**
	 * Like.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the v
	 */
	public Object like(String key, Object value) {
		if (value != null && !"".equals(value)) {
			super.put(key, value + "%");
		}
		return value;
	}

	/**
	 * To array.
	 * 
	 * @return the object[]
	 */
	public Object[] toArray() {
		return this.values().toArray();
	}

	// 权限判断
	public void hasAllDataFunction(String key, String name) {
		HttpServletRequest request = ThreadLocalContext.getRequest();
		if (FoundationUtil.haveViewAllDataFunction(request)) {
			if (AppUtils.isNotBlank(name)) {
				this.like(key, StringUtils.trim(name) + "%");
			}
		} else {
			String userName = UserManager.getUserName(request.getSession());
			if (userName == null) {
				throw new AuthorizationException(name + " did not logon yet!");
			}
			this.put(key, userName);
		}
	}
}
