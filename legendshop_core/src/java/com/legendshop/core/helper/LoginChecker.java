/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import javax.servlet.http.HttpServletRequest;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.UserManager;

/**
 * 是否登录Checker
 */
public class LoginChecker implements Checker<UserManager> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.helper.Checker#check(java.lang.Object,
	 * javax.servlet.http.HttpServletRequest)
	 */
	public boolean check(UserManager userManager, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute(AttributeKeys.USER_NAME);
		if (userName == null) {
			userName = UserManager.getUserName(request);
		}

		return userName != null;
	}

}
