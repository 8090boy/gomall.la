/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.security.GrantedFunction;
import com.legendshop.core.security.model.UserDetail;
import com.legendshop.util.AppUtils;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class UserManager {

	/**
	 * 获得用户名 在SecurityContext中取值
	 */
	public static String getUserName(HttpSession session) {
		UserDetail user = getUser(session);
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	// 在保存用户操作历史时调用，其他调用 String getUsername(HttpSession session)
	public static String getUserName() {
		UserDetail user = getUser(ThreadLocalContext.getRequest().getSession());
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	/**
	 * 获得用户名 在SecurityContext中取值
	 */
	public static String getUserName(HttpServletRequest request) {
		return getUserName(request.getSession());
	}

	/**
	 * 得到用户ID
	 */
	public static String getUserId(HttpSession session) {
		UserDetail user = getUser(session);
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}

	// 在保存用户操作历史时调用，其他调用 String getUserId(HttpSession session)
	public static String getUserId() {
		UserDetail user = getUser(ThreadLocalContext.getRequest().getSession());
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}

	/**
	 * 得到用户ID
	 */
	public static String getUserId(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		return getUserId(request.getSession());
	}

	/**
	 * 得到用户详细信息
	 */
	public static UserDetail getUser(HttpSession session) {
		Authentication auth = getAuthentication(session);
		if (auth != null) {
			if (auth.getPrincipal() instanceof UserDetail) {
				return (UserDetail) auth.getPrincipal();
			}
		}
		return null;
	}

	/**
	 *  1. 先从SecurityContextHolder取值
	 *	2. 对应不经过Spring Security的情况从session中取值
	 *
	 * @param session
	 * @return
	 */
	public static  Authentication getAuthentication(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null && session != null) {
			SecurityContext context = (SecurityContext) session.getAttribute(AttributeKeys.SPRING_SECURITY_CONTEXT);
			if (context != null) {
				auth = context.getAuthentication();
			}
		}
		return auth;
	}

	/**
	 * Gets the password.
	 * 
	 * @param session
	 *            the session
	 * @return the password
	 */
	public static String getPassword(HttpSession session) {
		UserDetail user = getUser(session);
		if (user != null) {
			return user.getPassword();
		}
		return null;
	}

	/**
	 * Gets the session id.
	 * 
	 * @return the session id
	 */
	public static String getSessionID() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx != null) {
			if (ctx instanceof SecurityContext) {
				SecurityContext sc = ctx;
				Authentication auth = sc.getAuthentication();
				if (auth != null) {
					Object details = auth.getDetails();
					if (details instanceof WebAuthenticationDetails) {
						return ((WebAuthenticationDetails) details).getSessionId();
					} else {
						return null;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 得到用的的Authentication，并且从Authentication中获得 Authorities，进而得到 授予用户的 Function.
	 * 
	 * @param session
	 *            the session
	 * @return Collection
	 */
	public static Collection<GrantedFunction> getPrincipalFunctionByAuthorities(HttpSession session) {
		UserDetail user = getUser(session);
		if (user != null) {
			return user.getFunctions();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * Checks for function.
	 * 
	 * @param session
	 *            the session
	 * @param function
	 *            the function
	 * @return true, if successful
	 */
	public static boolean hasFunction(HttpSession session, String function) {
		Collection<GrantedFunction> functions = getPrincipalFunctionByAuthorities(session);
		return hasFunction(functions, function);
	}

	/**
	 * Checks for function.
	 * 
	 * @param request
	 *            the request
	 * @param function
	 *            the function
	 * @return true, if successful
	 */
	public static boolean hasFunction(HttpServletRequest request, String function) {
		return hasFunction(request.getSession(), function);
	}

	// 所有的都满足才返回true
	/**
	 * Checks for function.
	 * 
	 * @param session
	 *            the session
	 * @param functions
	 *            the functions
	 * @return true, if successful
	 */
	public static boolean hasFunction(HttpSession session, String[] functions) {
		if (AppUtils.isBlank(functions)) {
			return false;
		}
		// 当前用户的权限
		Collection<GrantedFunction> grantedFunctions = getPrincipalFunctionByAuthorities(session);
		for (String function : functions) {
			if (!hasFunction(grantedFunctions, function)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks for function.
	 * 
	 * @param request
	 *            the request
	 * @param functions
	 *            the functions
	 * @return true, if successful
	 */
	public static boolean hasFunction(HttpServletRequest request, String[] functions) {
		return hasFunction(request.getSession(), functions);
	}

	/**
	 * Checks for function.
	 * 
	 * @param functions
	 *            the functions
	 * @param function
	 *            the function
	 * @return true, if successful
	 */
	private static boolean hasFunction(Collection<GrantedFunction> functions, String function) {
		boolean result = false;
		for (GrantedFunction gf : functions) {
			if (gf.equals(function)) {
				return true;
			}
		}
		return result;
	}

}
