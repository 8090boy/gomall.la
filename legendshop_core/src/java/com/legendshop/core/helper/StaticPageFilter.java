/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.util.AppUtils;

/**
 * The Class StaticPageFilter.
 */
public class StaticPageFilter implements Filter {

	protected FilterConfig config;

	private String HTML_PATH = null;

	private List<String> supportPageList;

	private String contextPath = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		HTML_PATH = PropertiesUtil.getHtmlPath();
		contextPath = config.getServletContext().getContextPath();
		supportPageList = new ArrayList<String>();
		supportPageList.add("/sort");
		supportPageList.add("/views");
		supportPageList.add("/views");
		supportPageList.add("/group");
		supportPageList.add("/group/view");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (PropertiesUtil.getObject(SysParameterEnum.STATIC_PAGE_SUPPORT, Boolean.class)) {
			HttpServletRequest hreq = (HttpServletRequest) request;

			String str_uri = hreq.getRequestURI();
			// System.out.println("hreq.getRequestURL() = " +
			// hreq.getRequestURL());
			// System.out.println(" hreq.getRealPath = " +
			// hreq.getRealPath("/"));
			// System.out.println(" hreq.getRemoteAddr= " +
			// hreq.getRemoteAddr());
			// System.out.println("str_uri =================== " + str_uri);
			// System.out.println("context path = " +
			// config.getServletContext().getContextPath());

			String prefixUriPath = supportURL(str_uri, contextPath);
			if (prefixUriPath != null) {
				HttpServletResponse hresp = (HttpServletResponse) response;
				String name = str_uri.substring(str_uri.lastIndexOf("/") + 1);

				if (AppUtils.isBlank(name)) {
					name = "index";
				}

				String fileName = name + ".html";
				String chrrentShopName = ThreadLocalContext.getCurrentShopName(hreq, hresp);
				String htmPath = HTML_PATH + chrrentShopName;

				// full name
				StringBuilder sb = new StringBuilder(htmPath).append(prefixUriPath).append("/").append(fileName);
				String fullName = sb.toString();

				// to which html
				sb.setLength(0);
				String toHtmlPage = sb.append(contextPath).append("/").append(AttributeKeys.HTML_COMMON).append(chrrentShopName)
						.append(prefixUriPath).append("/").append(fileName).toString();
				File file = new File(fullName);
				if (!file.exists()) {
					ServletResponse newResponse = new CharResponseWrapper(hresp);
					chain.doFilter(request, newResponse);
					String text = newResponse.toString();
					if (text != null) {
						FileProcessor.writeFile(text, htmPath + prefixUriPath, fileName);
						hresp.sendRedirect(toHtmlPage);
						// request.getRequestDispatcher(toHtmlPage).forward(hreq,
						// hresp);
					}
				} else {
					hresp.sendRedirect(toHtmlPage);
					// request.getRequestDispatcher(toHtmlPage).forward(hreq,
					// hresp);
				}

			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	/**
	 * 不包括有后缀的URL,只是支持Controller的动作（不带后缀）
	 * 
	 * @param url
	 * @return
	 */
	private String supportURL(String url, String contextpath) {
		if (url == null) {
			return null;
		}
		if (url.indexOf(".") < 0) {
			String uriPath = url.substring(contextpath.length()); // 去掉前缀
			String prefixUriPath = uriPath.substring(0, uriPath.lastIndexOf("/")); // 去掉后缀
			if ((supportPageList.contains(prefixUriPath) || uriPath.equals("/index"))) { // 首页除外
				// System.out.println("support url = " + url);
				return prefixUriPath;
			}
		}
		return null;
	}

}
