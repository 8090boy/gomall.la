/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.News;
import com.legendshop.model.entity.NewsCategory;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.NewsPositionEnum;
import com.legendshop.spi.constants.PageADV;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.NewsCategoryService;
import com.legendshop.spi.service.NewsService;
import com.legendshop.util.AppUtils;

/**
 * 产品分类控制器。.
 */
@Controller
public class NewsController extends BaseController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsCategoryService newsCategoryService;

	/**
	 * Topnews.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/topnews")
	public String topnews(HttpServletRequest request, HttpServletResponse response) {
		String name = ThreadLocalContext.getCurrentShopName(request, response);

		String topsortnews = request.getParameter("topsortnews");
		if ((topsortnews != null)) {
			request.setAttribute(
					"newList",
					newsService.getNews(name, NewsPositionEnum.NEWS_NEWS,
							PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class)));
			return PathResolver.getPath(request, response, FrontPage.TOP_SORT_NEWS);
		} else {
			request.setAttribute("newList", newsService.getNews(name, NewsPositionEnum.NEWS_NEWS, 6));
			return PathResolver.getPath(request, response, FrontPage.TOP_NEWS);
		}
	}

	/**
	 * News.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param newsId
	 *            the news id
	 * @return the string
	 */
	@RequestMapping("/news/{newsId}")
	public String news(HttpServletRequest request, HttpServletResponse response, @PathVariable Long newsId) {
		if (newsId != null) {
			News news = newsService.getNewsById(newsId);
			if (news != null) {
				//ThreadLocalContext.setCurrentShopName(request, response, news.getUserName());
				newsService.getAndSetAdvertisement(request, response, news.getUserName(), PageADV.NEWS.name());
				request.setAttribute("news", news);

				// load news by category
				Map<KeyValueEntity, List<News>> newsCatList = newsService.getNewsByCategory(news.getUserName());
				request.setAttribute("newsCatList", newsCatList);
			}

		}
		newsService.getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response),
				Constants.USER_REG_ADV_740);

		return PathResolver.getPath(request, response, TilesPage.NEWS);
	}

	/**
	 * 新闻中心.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param newsCategory
	 *            the news category
	 * @return the string
	 */
	@RequestMapping("/allnews")
	public String allNews(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long newsCategoryId) {
		if (AppUtils.isBlank(curPageNO)) {
			curPageNO = "1";
		}
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);

		PageSupport ps = newsService.getNews(curPageNO, shopName, newsCategoryId);
		ps.savePage(request);
		request.setAttribute("newsCategoryId", newsCategoryId);
		newsService.getAndSetOneAdvertisement(request, response, shopName, Constants.USER_REG_ADV_740);
		if (newsCategoryId != null) {
			NewsCategory newsCategory = newsCategoryService.getNewsCategoryById(newsCategoryId);
			if (newsCategory != null) {
				request.setAttribute("newsCategoryName", newsCategory.getNewsCategoryName());
			}
		}
		// load news by category
		Map<KeyValueEntity, List<News>> newsCatList = newsService.getNewsByCategory(shopName);
		request.setAttribute("newsCatList", newsCatList);
		return PathResolver.getPath(request, response, TilesPage.ALL_NEWS);
	}

}
