package com.legendshop.business.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.legendshop.spi.constants.Constants;
import com.legendshop.util.CookieUtil;

public class SecurityContextLogoutHandlerImpl extends SecurityContextLogoutHandler {

	/** The support sso. */
	private boolean supportSSO = false;

	/**
	 * Sets the support sso.
	 * 
	 * @param supportSSO
	 *            the new support sso
	 */
	public void setSupportSSO(boolean supportSSO) {
		this.supportSSO = supportSSO;
	}

	/**
	 * 系统退出是删除session和spring security的内容
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (supportSSO) {
			// 删除Cookies
			CookieUtil.deleteCookie(request, response, Constants.CLUB_COOIKES_NAME);
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(Constants.USER_NAME);
			session.removeAttribute(Constants.SPRING_SECURITY_CONTEXT);
		}
		super.logout(request, response, authentication);
	}

}
