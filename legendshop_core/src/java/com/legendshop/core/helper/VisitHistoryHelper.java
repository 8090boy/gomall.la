/**
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.model.entity.ProductDetail;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.util.AppUtils;
import com.legendshop.util.CookieUtil;

/**
 * 访问历史，Id保存在Cookies.
 */
public class VisitHistoryHelper {

	/** The max length. */
	private static Integer maxLength = 6;

	private static Integer MAX_AGE = 3 * 24 * 60 * 60; // 3天

	private static String VISITED_PROD = "Visited_Prod";

	private static String VISITED_SHOP_DETAIL = "Visited_ShopDetail";

	private static LinkedList<Object> getStringCollection(String str) {
		LinkedList<Object> values = new LinkedList<Object>();
		if (str == null) {
			return values;
		}
		StringTokenizer tokenizer = new StringTokenizer(str, ",");

		values = new LinkedList<Object>();
		while (tokenizer.hasMoreTokens()) {
			try {
				values.add(tokenizer.nextToken());
			} catch (Exception e) {
				return new LinkedList<Object>();
			}
		}
		return values;
	}

	public static void visit(ProductDetail prod, HttpServletRequest request, HttpServletResponse response) {
		String id = String.valueOf(prod.getId());
		boolean find = false;
		LinkedList<Object> products = getStringCollection(CookieUtil.getCookieValue(request, VISITED_PROD));
		for (Object prodId : products) {
			if (id.equals(prodId.toString())) {
				find = true;
			}
		}

		if (!find) {
			if (products.size() >= maxLength) {
				products.removeFirst();
			}
			products.addLast(id);
			// cookies changed
			CookieUtil.addCookie(response, MAX_AGE, VISITED_PROD, AppUtils.list2String(products));
		}
	}

	/**
	 * Visit.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 */
	public static void visit(ShopDetailView shopDetail, HttpServletRequest request, HttpServletResponse response) {
		String userName = shopDetail.getUserName();
		boolean find = false;
		LinkedList<Object> shopDetails = getStringCollection(CookieUtil.getCookieValue(request, VISITED_SHOP_DETAIL));

		for (Object shopName : shopDetails) {
			if (userName.equals(shopName.toString())) {
				find = true;
			}
		}

		if (!find) {
			if (shopDetails.size() >= maxLength) {
				shopDetails.removeFirst();
			}
			shopDetails.addLast(userName);
			// cookies changed
			CookieUtil.addCookie(response, MAX_AGE, VISITED_SHOP_DETAIL, AppUtils.list2String(shopDetails));
		}
	}

	public static List<Object> getVisitedShopDetail(HttpServletRequest request) {
		return getStringCollection(CookieUtil.getCookieValue(request, VISITED_SHOP_DETAIL));
	}

	public static List<Object> getVisitedProd(HttpServletRequest request) {
		return getStringCollection(CookieUtil.getCookieValue(request, VISITED_PROD));
	}

}
