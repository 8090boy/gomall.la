/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.RedirectStrategy;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.FunctionEnum;
import com.legendshop.util.AppUtils;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class OriginUrlRedirectStrategy implements RedirectStrategy {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/** The context relative. */
	private boolean contextRelative;

	// 如果用户是一个商家，则跳转到后台
	private boolean forwardToBackEndForBusiness;

	/**
	 * Redirects the response to the supplied URL.
	 * <p>
	 * If <tt>contextRelative</tt> is set, the redirect value will be the value
	 * after the request context path. Note that this will result in the loss of
	 * protocol information (HTTP or HTTPS), so will cause problems if a
	 * redirect is being performed to change to HTTPS, for example.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param url
	 *            the url
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String service = request.getParameter(AttributeKeys.RETURN_URL);
		if (AppUtils.isNotBlank(service)) {
			response.sendRedirect(service);
			return;
		}
		// 如果用户是一个商家，则跳转到后台
		if (forwardToBackEndForBusiness) {
			if (UserManager.hasFunction(request, FunctionEnum.FUNCTION_F_ADMIN.value())) {
				response.sendRedirect(request.getContextPath() + "/admin/index");
				return;
			}
		}

		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
		redirectUrl = response.encodeRedirectURL(redirectUrl);

		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to '" + redirectUrl + "'");
		}

		response.sendRedirect(redirectUrl);
	}

	/**
	 * Calculate redirect url.
	 * 
	 * @param contextPath
	 *            the context path
	 * @param url
	 *            the url
	 * @return the string
	 */
	private String calculateRedirectUrl(String contextPath, String url) {
		if (url != null && !url.startsWith("http://")) {
			if (contextRelative) {
				return url;
			} else {
				return contextPath + url;
			}
		}

		// Full URL, including http(s)://

		if (!contextRelative) {
			return url;
		}

		// Calculate the relative URL from the fully qualified URL, minus the
		// scheme and base context.
		url = url.substring(url.indexOf("://") + 3); // strip off scheme
		url = url.substring(url.indexOf(contextPath) + contextPath.length());

		if (url.length() > 1 && url.charAt(0) == '/') {
			url = url.substring(1);
		}

		return url;
	}

	/**
	 * If <tt>true</tt>, causes any redirection URLs to be calculated minus the
	 * protocol and context path (defaults to <tt>false</tt>).
	 * 
	 * @param useRelativeContext
	 *            the new context relative
	 */
	public void setContextRelative(boolean useRelativeContext) {
		this.contextRelative = useRelativeContext;
	}

	/**
	 * @param forwardToBackEndForBusiness
	 *            the forwardToBackEndForBusiness to set
	 */
	public void setForwardToBackEndForBusiness(boolean forwardToBackEndForBusiness) {
		this.forwardToBackEndForBusiness = forwardToBackEndForBusiness;
	}

}