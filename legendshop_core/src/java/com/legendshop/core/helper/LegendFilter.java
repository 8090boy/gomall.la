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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.exception.ApplicationException;
import com.legendshop.core.exception.BaseException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.handler.HandlerManager;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;

/**
 * The Class LegendFilter.
 */
public class LegendFilter extends OncePerRequestFilter {

	private HandlerManager handlerManager;

	public static String HTML_PATH = PropertiesUtil.getHtmlPath();

	private List<String> supportPageList;

	private String contextPath = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		boolean isSupportUrl = supportURL(uri);
		Boolean supportStaticPage = PropertiesUtil.getObject(SysParameterEnum.STATIC_PAGE_SUPPORT, Boolean.class);
		if (supportStaticPage == null) { //system does not installed
			isSupportUrl = false;
		}
			if (supportStaticPage != null && supportStaticPage) {
				processStaticPage(uri, isSupportUrl, request, response, chain);
			} else {
				doHandle(isSupportUrl, request, response, chain);
			}
		
	}

	protected void doHandle(boolean isSupportUrl, HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		try {
			if (isSupportUrl) {
				ThreadLocalContext.setRequest(request);
				handlerManager.handle(request, response);
			}
			chain.doFilter(request, response);
		} catch (BaseException be) {
			throw be;
		} catch (Exception e) {
			throw new ApplicationException(e, "",ErrorCodes.SYSTEM_ERROR);
		} finally {
			if(isSupportUrl){
				ThreadLocalContext.clean();
			}
		}
	}

	public void processStaticPage(String str_uri, boolean isSupportUrl, HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String prefixUriPath = supportURL(str_uri, contextPath);
		if (prefixUriPath != null) {
			String name = str_uri.substring(str_uri.lastIndexOf("/") + 1);
			if (AppUtils.isBlank(name)) {
				name = "index";
			}

			String fileName = name + ".html";
			String chrrentShopName = ThreadLocalContext.getCurrentShopName(request, response);
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
				ServletResponse newResponse = new CharResponseWrapper(response);
				chain.doFilter(request, newResponse);
				String text = newResponse.toString();
				if (text != null) {
					FileProcessor.writeFile(text, htmPath + prefixUriPath, fileName);
					response.sendRedirect(toHtmlPage);
					// request.getRequestDispatcher(toHtmlPage).forward(request,response);
				}
			} else {
				response.sendRedirect(toHtmlPage);
				// request.getRequestDispatcher(toHtmlPage).forward(request,response);
			}

		} else {
			doHandle(isSupportUrl, request, response, chain);
		}

	}

	/**
	 * 不包括有后缀的URL,只是支持Controller的动作（不带后缀）
	 * 
	 * @param url
	 * @return
	 */
	private boolean supportURL(String url) {
		if (url == null) {
			return false;
		}
		return url.indexOf(".") < 0;
	}

	/**
	 * 不包括有后缀的URL,只是支持Controller的动作（不带后缀）
	 * 
	 * @param url
	 * @return
	 */
	private String supportURL(String url, String contextpath) {
		String uriPath = url.substring(contextpath.length()); // 去掉前缀
		String prefixUriPath = uriPath.substring(0, uriPath.lastIndexOf("/")); // 去掉后缀
		if ((supportPageList.contains(prefixUriPath) || uriPath.equals("/index"))) { // 首页除外
			return prefixUriPath;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.filter.GenericFilterBean#destroy()
	 */
	@Override
	public void destroy() {

	}

	@Override
	protected void initFilterBean() throws ServletException {
		handlerManager = (HandlerManager) ContextServiceLocator.getInstance().getBean("handlerManager");
		contextPath = this.getFilterConfig().getServletContext().getContextPath();
		supportPageList = new ArrayList<String>();
		supportPageList.add("/sort");
		supportPageList.add("/views");
		supportPageList.add("/views");
		supportPageList.add("/group");
		supportPageList.add("/group/view");
	}

	public void setHandlerManager(HandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}

}
