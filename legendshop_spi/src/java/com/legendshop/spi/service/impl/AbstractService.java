/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.model.UserMessages;
import com.legendshop.model.entity.Advertisement;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.dao.AdvertisementDao;
import com.legendshop.spi.dao.ShopDetailDao;
import com.legendshop.spi.service.BaseService;
import com.legendshop.util.AppUtils;

/**
 * The Class AbstractService.
 */
public abstract class AbstractService implements BaseService {
	/** The shop detail dao. */
	protected ShopDetailDao shopDetailDao;

	/** The advertisement dao. */
	protected AdvertisementDao advertisementDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BaseService#getSessionAttribute(
	 * javax.servlet.http.HttpServletRequest, java.lang.String)
	 */

	public Object getSessionAttribute(HttpServletRequest request, String name) {
		Object obj = null;
		HttpSession session = request.getSession();
		if (session != null) {
			obj = session.getAttribute(name);
		}
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BaseService#setSessionAttribute(
	 * javax.servlet.http.HttpServletRequest, java.lang.String,
	 * java.lang.Object)
	 */

	public void setSessionAttribute(HttpServletRequest request, String name, Object obj) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.setAttribute(name, obj);
		}
	}

	/**
	 * 设置广告.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopName
	 *            the shop name
	 * @param page
	 *            the page
	 * @return the and set advertisement
	 */
	public void getAndSetAdvertisement(HttpServletRequest request, HttpServletResponse response, String shopName, String page) {
		Map<String, List<Advertisement>> advertisement = advertisementDao.getAdvertisement(shopName, page);
		if (AppUtils.isNotBlank(advertisement)) {
			for (String type : advertisement.keySet()) {
				request.setAttribute(type, advertisement.get(type));
			}
		}
	}

	// 只是得到一个广告
	/**
	 * Sets the one advertisement.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopName
	 *            the shop name
	 * @param key
	 *            the key
	 * @return the and set one advertisement
	 */
	public void getAndSetOneAdvertisement(HttpServletRequest request, HttpServletResponse response, String shopName, String key) {
		List<Advertisement> advertisement = advertisementDao.getOneAdvertisement(shopName, key);
		if (!AppUtils.isBlank(advertisement)) {
			request.setAttribute(key, advertisement);
		}
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @return the int
	 */
	protected int random(int count) {
		Random random = new Random();
		return random.nextInt(count);
	}

	/**
	 * Sets the shop detail dao.
	 * 
	 * @param shopDetailDao
	 *            the new shop detail dao
	 */
	public void setShopDetailDao(ShopDetailDao shopDetailDao) {
		this.shopDetailDao = shopDetailDao;
	}

	/**
	 * Sets the advertisement dao.
	 * 
	 * @param advertisementDao
	 *            the new advertisement dao
	 */
	public void setAdvertisementDao(AdvertisementDao advertisementDao) {
		this.advertisementDao = advertisementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.BaseService#checkPrivilege(javax.servlet.http
	 * .HttpServletRequest, java.lang.String, java.lang.String)
	 */
	public String checkPrivilege(HttpServletRequest request, String loginName, String userName) {
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

	protected String handleException(HttpServletRequest request, UserMessages userMessages) {
		request.setAttribute(UserMessages.MESSAGE_KEY, userMessages);
		return PageDefinition.ERROR_PAGE_PATH;
	}

}
