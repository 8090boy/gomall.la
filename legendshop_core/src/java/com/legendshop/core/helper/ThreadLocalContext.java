/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.constant.LanguageEnum;
import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.service.ShopService;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.model.visit.VisitHistory;
import com.legendshop.page.TemplatePageManager;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;
import com.legendshop.util.CookieUtil;

/**
 * The Class ThreadLocalContext.
 */
public class ThreadLocalContext {

	/** The log. */
	private final static Logger log = LoggerFactory.getLogger(ThreadLocalContext.class);

	/** The shop service. */
	private static ShopService shopService;

	/** The template manager. */
	private static TemplatePageManager templatePageManager;

	// 如果是则用session， 否则用cookies
	/** The use sessoin. */
	private static boolean useSessoin = false;

	/** The locale resolver. */
	private static LocaleResolver localeResolver;

	/** The shopdetail view. */
	private static String SHOPDETAIL_VIEW = "REQ_SHOPDETAIL_VIEW";

	/** The front type. */
	private static String FRONT_TYPE = "REQ_FRONTTYPE";

	/** The end type. */
	private static String END_TYPE = "REQ_ENDTYPE";

	/** The request holder. */
	private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	static {
		init();
	}

	/**
	 * Gets the shop detail view. 取得当前商城系统，如果找不到就用默认商城
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param newShopName
	 *            the new shop name
	 * @return the shop detail view
	 */
	public static ShopDetailView getShopDetailView(HttpServletRequest request, HttpServletResponse response,
			String newShopName) {
		if (AppUtils.isBlank(newShopName)) {
			return null;
		}
		ShopDetailView shopDetail = (ShopDetailView) request.getAttribute(SHOPDETAIL_VIEW);
		if (shopDetail == null) {
			shopDetail = shopService.getShopDetailView(newShopName);
			// 如果有默认店，则先到默认店去，默认店要先配置好
			if (shopDetail == null) {
				String defaultShop = PropertiesUtil.getObject(SysParameterEnum.DEFAULT_SHOP, String.class);

				if (AppUtils.isBlank(defaultShop)) {
					throw new RuntimeException("Can't find default shop name");
				}
				newShopName = defaultShop;
				// load default shopDetail from cache
				shopDetail = shopService.getShopDetailView(newShopName); // 得到当前商城

			}

			if (shopDetail != null) {
				request.setAttribute(SHOPDETAIL_VIEW, shopDetail);
				// updateShopDetailStatus(request, response, shopDetail);
			}
		}
		return shopDetail;
	}
	
	/**
	 * 返回指定用户的商城信息
	 * @param newShopName
	 * @return
	 */
	public static ShopDetailView getShopDetailView(String newShopName) {
		ShopDetailView shopDetail =  shopService.getShopDetailView(newShopName);
		return shopDetail;
	}

	/**
	 * 更新商城Session状态 shopDetail不能为空.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopDetail
	 *            the shop detail
	 */
	private static void updateShopDetailStatus(HttpServletRequest request, HttpServletResponse response,
			ShopDetailView shopDetail) {
		String newShopName = shopDetail.getUserName();
		// String currentShopName = getCurrentShopName(request,response);
		String currentShopName = (String) request.getSession().getAttribute(AttributeKeys.SHOP_NAME);

		// newShopName will no be null, it at least to be default shop
		if (newShopName == null) {
			log.debug("shopName can not both NULL");
			return;
		}

		if (!newShopName.equals(currentShopName) || AppUtils.isBlank(currentShopName)) {
			// 换商城,第一次进入其他商城
			log.debug("从商城  {} 进入另外一个商城  {}", currentShopName, newShopName);
			currentShopName = newShopName;
			setShopNameToCookie(request, response, currentShopName);

			// 更新用户访问历史
			// updateVisitHistory(shopDetail, request);
			VisitHistoryHelper.visit(shopDetail, request, response);
		}

		// set locale
		setLocalByShopDetail(request, response, shopDetail);
	}

	/**
	 * Sets the local by shop detail.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopDetail
	 *            the shop detail
	 */
	private static void setLocalByShopDetail(HttpServletRequest request, HttpServletResponse response,
			ShopDetailView shopDetail) {
		String langStyle = shopDetail.getFrontEndLanguage();

		// 总配置，如果不是USERCHOICE则无需设置,直接覆盖配置文件即可
		if (AppUtils.isBlank(langStyle)
				|| LanguageEnum.USERCHOICE.equals(langStyle) // shop level
				|| !LanguageEnum.USERCHOICE.equals(request.getSession().getServletContext()
						.getAttribute(AttributeKeys.LANGUAGE_MODE))) { // system
																		// level
			return;
		}

		Locale locale = null;
		if (useSessoin) {
			String sessionLangStyle = (String) request.getSession().getAttribute(AttributeKeys.SESSION_LANGSTYLE);
			if (langStyle != null && langStyle.equals(sessionLangStyle)) {
				return;
			}
			request.getSession().setAttribute(AttributeKeys.SESSION_LANGSTYLE, langStyle);
			String[] language = shopDetail.getFrontEndLanguage().split("_");
			locale = new Locale(language[0], language[1]);
		} else {
			String sessionLangStyle = (String) CookieUtil.getCookieValue(request, AttributeKeys.SESSION_LANGSTYLE);
			if (langStyle != null && langStyle.equals(sessionLangStyle)) {
				return;
			}
			// CookieUtil.addCookie(response,AttributeKeys.SESSION_LANGSTYLE,
			// langStyle);
			String[] language = shopDetail.getFrontEndLanguage().split("_");
			locale = new Locale(language[0], language[1]);
		}
		log.debug("{} setLocalByShopDetail with langStyle {}", shopDetail.getSiteName(), langStyle);

		localeResolver.setLocale(request, response, locale);
	}

	/**
	 * Visit.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 * @param request
	 *            the request
	 */
	@Deprecated
	private static void updateVisitHistory(ShopDetailView shopDetail, HttpServletRequest request) {
		VisitHistory visitHistory = (VisitHistory) request.getSession().getAttribute(AttributeKeys.VISIT_HISTORY);
		if (visitHistory == null) {
			visitHistory = new VisitHistory();
		}
		visitHistory.visit(shopDetail);
		request.getSession().setAttribute(AttributeKeys.VISIT_HISTORY, visitHistory);
	}

	/**
	 * Gets the current shop name.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the current shop name
	 */
	public static String getCurrentShopName(HttpServletRequest request, HttpServletResponse response) {
		// 1. load from request
		String currentShopName = (String) request.getAttribute(AttributeKeys.SHOP_NAME);
		if (AppUtils.isBlank(currentShopName)) {
			// 1. load from session
			currentShopName = (String) request.getSession().getAttribute(AttributeKeys.SHOP_NAME);

			// 2. load from db
			if (AppUtils.isBlank(currentShopName)) {
				ShopDetailView shopDetail = getShopDetailView(request, response, currentShopName);
				if (shopDetail == null) {
					// 3. load default shop
					currentShopName = PropertiesUtil.getObject(SysParameterEnum.DEFAULT_SHOP, String.class);
				} else {
					currentShopName = shopDetail.getUserName();
				}
				request.getSession().setAttribute(AttributeKeys.SHOP_NAME, currentShopName);
			}
			// set return if in session
			request.setAttribute(AttributeKeys.SHOP_NAME, currentShopName);
		}
		// return if in session
		return currentShopName;

		// 从session和cookie中取商城名称
		// if (AppUtils.isNotBlank(shopName)) {
		// return shopName;
		// }
		// Cookie cookies[] = request.getCookies();
		// if (cookies != null) {
		// for (Cookie cookie : cookies) {
		// if (AttributeKeys.SHOP_NAME.equalsIgnoreCase(cookie.getName())) {
		// shopName = cookie.getValue();
		// }
		// }
		// }
		//
		// if(AppUtils.isNotBlank(shopName)){
		// request.getSession().setAttribute(AttributeKeys.SHOP_NAME, shopName);
		// }

	}

	/**
	 * Sets the current shop name.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopName
	 *            the shop name
	 */
	public static void setCurrentShopName(HttpServletRequest request, HttpServletResponse response, String shopName) {
		String currentShopName = (String) request.getAttribute(AttributeKeys.SHOP_NAME);
		if (AppUtils.isNotBlank(currentShopName)) {
			log.debug("currentShopName  {}  have been set in this Thread", currentShopName);
			return;
		}
		ShopDetailView shopDetailView = getShopDetailView(request, response, shopName);
		if (!shopDetailView.getStatus().equals(AttributeKeys.ONLINE)) {
			shopName = PropertiesUtil.getDefaultShopName();
			shopDetailView = getShopDetailView(request, response, shopName);
		} else {
			shopName = shopDetailView.getUserName();
		}
		updateShopDetailStatus(request, response, shopDetailView);
	}

	/**
	 * Sets the shop name.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopName
	 *            the shop name
	 */
	private static void setShopNameToCookie(HttpServletRequest request, HttpServletResponse response, String shopName) {
		request.getSession().setAttribute(AttributeKeys.SHOP_NAME, shopName);
		// Cookie cookie = new Cookie(AttributeKeys.SHOP_NAME, shopName);
		//
		// cookie.setMaxAge(60 * 60 * 24 * 365);
		// cookie.setPath("/");
		// response.addCookie(cookie);
	}

	/**
	 * Gets the front type.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param pageDefinition
	 *            the page definition
	 * @return the front type
	 */
	public static String getFrontType(HttpServletRequest request, HttpServletResponse response, String path,
			PageDefinition pageDefinition) {
		String frontType = (String) request.getAttribute(FRONT_TYPE);
		if (frontType == null) {
			ShopDetailView shopDetail = ThreadLocalContext.getShopDetailView(request, response,
					getCurrentShopName(request, response));

			if (shopDetail != null && shopDetail.getFrontEndTemplet() != null) {
				frontType = shopDetail.getFrontEndTemplet();
			}
			if (pageDefinition != null) {
				List<String> templates = templatePageManager.getTemplateByPage(path);
				if (AppUtils.isNotBlank(templates) && !templates.contains(frontType)) {
					frontType = templates.iterator().next();
				}
			}

			if (frontType == null) {
				frontType = PageDefinition.DEFAULT_THEME_PATH;
			}
			request.setAttribute(FRONT_TYPE, frontType);
		}
		return frontType;
	}

	/**
	 * Gets the back end type.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param template
	 *            the template
	 * @return the back end type
	 */
	public static String getBackEndType(HttpServletRequest request, HttpServletResponse response, String pathValue,
			PageDefinition pageDefinition) {
		String endType = (String) request.getAttribute(END_TYPE);
		if (endType == null) {
			ShopDetailView shopDetail = ThreadLocalContext.getShopDetailView(request, response,
					getCurrentShopName(request, response));
			if (shopDetail != null && shopDetail.getBackEndTemplet() != null) {
				endType = shopDetail.getBackEndTemplet();
			}
			if (pageDefinition != null) {
				List<String> templates = templatePageManager.getTemplateByPage(pathValue);
				if (AppUtils.isNotBlank(templates) && !templates.contains(endType)) {
					endType = templates.iterator().next();
				}
			}
			if (endType == null) {
				endType = PageDefinition.DEFAULT_THEME_PATH;
			}

			request.setAttribute(END_TYPE, endType);
		}
		return endType;
		// 后台只有一套模版，所以只用defalut
		// return PageDefinition.DEFAULT_THEME_PATH;
	}

	/**
	 * 初始化Service.
	 */
	private static final void init() {
		if (PropertiesUtil.isSystemInstalled()) {
			log.info("start to init ThreadLocalContext");
			if (shopService == null && ContextServiceLocator.getInstance().containsBean("shopDetailService")) {
				shopService = (ShopService) ContextServiceLocator.getInstance().getBean("shopDetailService");
			}

			if (localeResolver == null && ContextServiceLocator.getInstance().containsBean("localeResolver")) {
				localeResolver = (LocaleResolver) ContextServiceLocator.getInstance().getBean("localeResolver");
			}

			if (templatePageManager == null && ContextServiceLocator.getInstance().containsBean("templatePageManager")) {
				templatePageManager = (TemplatePageManager) ContextServiceLocator.getInstance().getBean(
						"templatePageManager");
			}
		}

	}

	/**
	 * Clean.
	 */
	public static void clean() {
		if (requestHolder.get() != null) {
			requestHolder.remove();
		}
	}

	/**
	 * 是否开始请求.
	 * 
	 * @return true, if successful
	 */
	public static boolean requestStarted() {
		return requestHolder.get() != null;
	}

	/**
	 * Gets the locale.
	 * 
	 * @return the locale
	 */
	public static Locale getLocale() {
		HttpServletRequest request = requestHolder.get();
		if (request == null) {
			return null;
		}
		return localeResolver.resolveLocale(request);
	}

	/**
	 * Gets the request.
	 * 
	 * @return the request
	 */
	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	/**
	 * Sets the request.
	 * 
	 * @param request
	 *            the new request
	 */
	public static void setRequest(HttpServletRequest request) {
		requestHolder.set(request);
	}

}
