/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.legendshop.core.UserManager;
import com.legendshop.core.constant.ConfigPropertiesEnum;
import com.legendshop.core.constant.FunctionEnum;
import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.constant.PagePathCalculator;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.exception.AuthorizationException;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.model.UserMessages;
import com.legendshop.util.AppUtils;
import com.legendshop.util.BeanUtils;
import com.legendshop.util.FileConfig;

/**
 * Spring MVC controller 基类.
 */
public class BaseController extends MultiActionController {

	/** The DEFAUL t_ them e_ path. */
	private final String DEFAULT_THEME_PATH = "/theme";

	/**
	 * Copy properties.
	 * 
	 * @param target
	 *            the target
	 * @param source
	 *            the source
	 */
	public void copyProperties(Object target, Object source) {
		BeanUtils.copyProperties(target, source);
	}

	/**
	 * Copy properties.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param destClass
	 *            the dest class
	 * @param orig
	 *            the orig
	 * @return the t
	 */
	public <T> T copyProperties(Class<T> destClass, Object orig) {
		return BeanUtils.copyProperties(destClass, orig);
	}

	/**
	 * Save message.
	 * 
	 * @param request
	 *            the request
	 * @param message
	 *            the message
	 */
	public void saveMessage(HttpServletRequest request, String message) {
		if (StringUtils.isNotBlank(message)) {
			List list = getOrCreateRequestAttribute(request, "springMessages", ArrayList.class);
			list.add(message);
		}
	}

	/**
	 * Save error.
	 * 
	 * @param request
	 *            the request
	 * @param errorMsg
	 *            the error msg
	 */
	protected static void saveError(HttpServletRequest request, String errorMsg) {
		if (StringUtils.isNotBlank(errorMsg)) {
			List list = getOrCreateRequestAttribute(request, "springErrors", ArrayList.class);
			list.add(errorMsg);
		}
	}

	/**
	 * Handle exception.
	 * 
	 * @param request
	 *            the request
	 * @param userMessages
	 *            the user messages
	 * @return the string
	 */
	protected String handleException(HttpServletRequest request, UserMessages userMessages) {
		request.setAttribute(UserMessages.MESSAGE_KEY, userMessages);
		// return PathResolver.getPath(PageDefinition.ERROR_PAGE_PATH,
		// PageDefinition.FRONT_PAGE);
		// return PagePathCalculator.calculateFronendPath(request,
		// PageDefinition.ERROR_PAGE_PATH);
		return PageDefinition.ERROR_PAGE_PATH;
	}

	/**
	 * Gets the or create request attribute.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param request
	 *            the request
	 * @param key
	 *            the key
	 * @param clazz
	 *            the clazz
	 * @return the or create request attribute
	 */
	public static <T> T getOrCreateRequestAttribute(HttpServletRequest request, String key, Class<T> clazz) {
		Object value = request.getAttribute(key);
		if (value == null) {
			try {
				value = clazz.newInstance();
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
			request.setAttribute(key, value);
		}
		return (T) value;
	}

	/**
	 * Append param to uri.
	 * 
	 * @param URL
	 *            the uRL
	 * @param paramName
	 *            the param name
	 * @param paramValue
	 *            the param value
	 * @return the string
	 */
	protected static String appendParamToURI(String URL, String paramName, String paramValue) {
		String appendedURI = URL;
		if (null != appendedURI) {
			if (appendedURI.indexOf("?") < 0) {
				appendedURI += "?";
			} else {
				appendedURI += "&";
			}
			appendedURI += (paramName + "=" + paramValue);
		}

		return appendedURI;
	}

	/**
	 * Random numeric.
	 * 
	 * @param random
	 *            the random
	 * @param count
	 *            the count
	 * @return the string
	 */
	private static String randomNumeric(Random random, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			int val = random.nextInt(10);
			sb.append(String.valueOf(val));
		}
		return sb.toString();
	}

	/**
	 * Gets the file set.
	 * 
	 * @param request
	 *            the request
	 * @return the file set
	 */
	protected Set<MultipartFile> getFileSet(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Set<MultipartFile> fileset = new LinkedHashSet<MultipartFile>();
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = it.next();
			MultipartFile file = multipartRequest.getFile(key);
			if (file.getOriginalFilename().length() > 0) {
				fileset.add(file);
			}
		}
		return fileset;
	}

	/**
	 * Checks for all data function.
	 * 
	 * @param cq
	 *            the cq
	 * @param request
	 *            the request
	 * @param userName
	 *            the user name
	 * @return the criteria query
	 */
	protected CriteriaQuery hasAllDataFunction(CriteriaQuery cq, HttpServletRequest request, String userName) {
		if (FoundationUtil.haveViewAllDataFunction(request)) {
			if (!AppUtils.isBlank(userName)) {
				cq.like("userName", "%" + StringUtils.trim(userName) + "%");
			}
		} else {
			String name = UserManager.getUserName(request.getSession());
			if (name == null) {
				throw new AuthorizationException(userName + " did not logon yet!");
			}
			cq.eq("userName", name);
		}
		return cq;
	}

	// 高级权限，等于超级管理员
	/**
	 * Checks for all data and operation function.
	 * 
	 * @param cq
	 *            the cq
	 * @param request
	 *            the request
	 * @param userName
	 *            the user name
	 * @return the criteria query
	 */
	@Deprecated
	protected CriteriaQuery hasAllDataAndOperationFunction(CriteriaQuery cq, HttpServletRequest request, String userName) {
		if (UserManager.hasFunction(request.getSession(),
				new String[] { FunctionEnum.FUNCTION_VIEW_ALL_DATA.value(),
						FunctionEnum.FUNCTION_F_ADMIN.value(), FunctionEnum.FUNCTION_F_SYSTEM.value() })) {
			if (!AppUtils.isBlank(userName)) {
				cq.like("userName", "%" + StringUtils.trim(userName) + "%");
			}
		} else {
			String name = UserManager.getUserName(request.getSession());
			if (name == null) {
				throw new AuthorizationException(userName + " did not logon yet!");
			}
			cq.eq("userName", name);
		}
		return cq;
	}

	/**
	 * Checks for all data function.
	 * 
	 * @param cq
	 *            the cq
	 * @param request
	 *            the request
	 * @param key
	 *            the key
	 * @param name
	 *            the name
	 */
	protected void hasAllDataFunction(CriteriaQuery cq, HttpServletRequest request, String key, String name) {
		if (FoundationUtil.haveViewAllDataFunction(request)) {
			if (AppUtils.isNotBlank(name)) {
				cq.like(key, "%" + StringUtils.trim(name) + "%");
			}
		} else {
			String userName = UserManager.getUserName(request.getSession());
			if (userName == null) {
				throw new AuthorizationException(name + " did not logon yet!");
			}
			cq.eq(key, userName);
		}
	}

	/**
	 * Check privilege.
	 * 
	 * @param request
	 *            the request
	 * @param loginName
	 *            the login name
	 * @param userName
	 *            the user name
	 */
	protected String checkPrivilege(HttpServletRequest request, String loginName, String userName) {
		String result = null;
		if (!FoundationUtil.haveViewAllDataFunction(request)) {
			if (!loginName.equals(userName)) {
				UserMessages userMessages = new UserMessages(ErrorCodes.UNAUTHORIZED, "Access Deny",
						" can not edit this object belongs to " + userName);
				result = handleException(request, userMessages);
			}
		}
		return result;
	}

	/**
	 * Check login.
	 * 
	 * @param loginName
	 *            the login name
	 */
	protected String checkLogin(HttpServletRequest request, HttpServletResponse response, String loginName) {
		if (loginName == null) {
			// 没有登录返回登录页面
			// return PathResolver.getPath(PageDefinition.LOGIN_PATH,
			// PageDefinition.TILES);
			return PagePathCalculator.calculateTilesPath(request, response, PageDefinition.LOGIN_PATH, null);
		} else {
			return null;
		}
	}

	/**
	 * Check nullable.
	 * 
	 * @param name
	 *            the name
	 * @param obj
	 *            the obj
	 */
	protected void checkNullable(String name, Object obj) {
		if (obj == null) {
			throw new BusinessException(name + "  is non nullable");
		}
	}

	/**
	 * Gets the session attribute.
	 * 
	 * @param request
	 *            the request
	 * @param name
	 *            the name
	 * @return the session attribute
	 */
	protected Object getSessionAttribute(HttpServletRequest request, String name) {
		Object obj = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			obj = session.getAttribute(name);
		}
		return obj;
	}

	/**
	 * Sets the session attribute.
	 * 
	 * @param request
	 *            the request
	 * @param name
	 *            the name
	 * @param obj
	 *            the obj
	 */
	protected void setSessionAttribute(HttpServletRequest request, String name, Object obj) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(name, obj);
		}
	}

	/**
	 * 重定向到安装页面
	 * 
	 * @param request
	 */
	protected String redirectToInstallPage(HttpServletRequest request) {
		String version = PropertiesUtil.getProperties(FileConfig.GlobalFile, ConfigPropertiesEnum.LEGENDSHOP_VERSION.name());
		UserMessages uem = new UserMessages();
		uem.setTitle("系统还没有安装成功");
		uem.setDesc("System will be available until install operation is finished!");
		uem.setCode(ErrorCodes.SYSTEM_UNINSTALLED);
		uem.addCallBackList("安装系统", "LegendShop " + version, "install");
		request.setAttribute(UserMessages.MESSAGE_KEY, uem);

		return "redirect:plugins/install/index.jsp";
	}

}
