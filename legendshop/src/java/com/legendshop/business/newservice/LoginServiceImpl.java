package com.legendshop.business.newservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.legendshop.business.handler.ValidateCodeUsernamePasswordAuthenticationFilter;
import com.legendshop.spi.constants.Constants;

/**
 * 用户登录服务 The Class LoginServiceImpl.
 */
public class LoginServiceImpl extends ValidateCodeUsernamePasswordAuthenticationFilter {
	Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	/**
	 * 
	 * 用户一注册立即登录
	 */
	@Override
	public Authentication onAuthentication(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		log.debug("userName {} register and login", username);
		Authentication authentication = super.onAuthentication(request, response, username, password);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(Constants.USER_NAME, username);
			session.setAttribute(Constants.SPRING_SECURITY_CONTEXT, securityContext);
		}
		return authentication;
	}

}
