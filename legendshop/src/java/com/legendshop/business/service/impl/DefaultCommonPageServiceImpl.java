/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.business.dao.NavigationDao;
import com.legendshop.business.dao.impl.NavigationDaoImpl;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.NewsPositionEnum;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.util.AppUtils;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 默认模板的顶部数据收集器
 */
public class DefaultCommonPageServiceImpl extends AbstractCommonPageService {

	/**
	 * 默认模板的顶部数据
	 */
	@Override
	public String getTop(HttpServletRequest request, HttpServletResponse response) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		ShopDetailView shopDetail = ThreadLocalContext.getShopDetailView(request, response, shopName);
		if (shopDetail == null) {
			return PathResolver.getPath(request, response, FrontPage.TOPALL);
		}
		// 产品分类
		
		request.setAttribute("sortList", sortDao.getSort(shopName, ProductTypeEnum.PRODUCT.value(), null, null, true));

		// 分类新闻
		request.setAttribute("newsSortList", newsDao.getNews(shopName, NewsPositionEnum.NEWS_SORT, 8));
		
		//网站导航
		request.setAttribute("navigationList", navigationDao.getNavigationList());
		// 返回页面
		return PathResolver.getPath(request, response, FrontPage.TOP);
	}

	@Override
	public String getTopUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		String userName = UserManager.getUserName(request.getSession());

		// 顶部新闻
		request.setAttribute("newsTopList", newsDao.getNews(shopName, NewsPositionEnum.NEWS_TOP, 8));

		// 是否是商家
		boolean shopExists = shopDetailDao.isShopExists(userName);
		// 是否可以做为联盟商城
		request.setAttribute("canbeLeagueShop", shopDetailDao.isBeLeagueShop(shopExists, userName, shopName));
		
		HttpSession session = request.getSession();
		Integer baskettotalCount = (Integer)session.getAttribute(Constants.BASKET_TOTAL_COUNT);
		if(baskettotalCount == null){
			Set<String> basketKeySet = new HashSet<String>();
			List<Basket> basketList = basketDao.getBasketByUserName(userName);
			if(AppUtils.isNotBlank(basketList)){
				for (Basket basket : basketList) {
					basketKeySet.add(CommonServiceUtil.getBasketKey(basket.getShopName(), basket.getProdId(), basket.getAttribute()));
				}
			}
			// 处理在session中的购物车,购物车合并
			Map<String, Basket> basketMap = (Map<String, Basket>) session.getAttribute(Constants.BASKET_KEY);
			if(AppUtils.isNotBlank(basketMap)){
				for (Basket basket : basketMap.values()) {
					basketKeySet.add(CommonServiceUtil.getBasketKey(basket.getShopName(), basket.getProdId(), basket.getAttribute()));
				}
			}
			session.setAttribute(Constants.BASKET_TOTAL_COUNT, basketKeySet.size());
		}
		
		// 返回页面
		return PathResolver.getPath(request, response, FrontPage.TOP_USER_INFO);
	}



}
