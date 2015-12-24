/*
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
package com.legendshop.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.business.dao.ExternalLinkDao;
import com.legendshop.business.dao.ImgFileDao;
import com.legendshop.business.dao.NewsDao;
import com.legendshop.business.dao.PubDao;
import com.legendshop.business.dao.TagDao;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.model.entity.Indexjpg;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.spi.constants.NewsPositionEnum;
import com.legendshop.spi.constants.PageADV;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.dao.SortDao;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.HomeService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;

/**
 * @author George Guo
 * 
 */
public class HomeServiceImpl extends BaseServiceImpl implements HomeService {

	/** The log. */
	private static Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

	/** The product dao. */
	private ProductDao productDao;

	/** The img file dao. */
	private ImgFileDao imgFileDao;

	/** The sort dao. */
	private SortDao sortDao;

	/** The pub dao. */
	private PubDao pubDao;
	
	/** The tag dao. */
	private TagDao tagDao;
	
	/** The news dao. */
	private NewsDao newsDao;
	
	/** The external link dao. */
	private ExternalLinkDao externalLinkDao;


	@Override
	public String getHome(HttpServletRequest request, HttpServletResponse response, ShopDetailView shopDetail) {

		String shopName = shopDetail.getUserName();
		//推荐商品
		request.setAttribute("commendProdList", productDao.getGlobalCommendProd(40));
		
		// 最新商品
		request.setAttribute("newestProdList", productDao.getGlobalNewestProd(11));

		request.setAttribute("pubList", pubDao.getPub(shopName));
		getAndSetAdvertisement(request, response, shopName, PageADV.INDEX.name());
		// 普通新闻
		request.setAttribute("newList", newsDao.getNews(shopName, NewsPositionEnum.NEWS_NEWS, 6));

		List<Indexjpg> indexJpgList = imgFileDao.getIndexJpeg(shopName);
		request.setAttribute("indexJpgList", indexJpgList);
		if (!AppUtils.isBlank(indexJpgList)) {
			//最大的广告数
			request.setAttribute("maxScreen", indexJpgList.size());
			request.setAttribute("indexJSON", JSONUtil.getJson(indexJpgList));
		} else {
			request.setAttribute("maxScreen", 0);
		}

		//外部友情连接
		request.setAttribute("externalLinkList", externalLinkDao.getExternalLink(shopName));

		String userName = UserManager.getUserName(request.getSession());

		//标签管理
		request.setAttribute("tagList", tagDao.getPageTag(shopName, PageADV.INDEX.name()));
		
		//是否显示菜单
		request.setAttribute("showMenu", true);
		logUserAccess(request, shopName, userName);
		return PathResolver.getPath(request, response, TilesPage.HOME);
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setImgFileDao(ImgFileDao imgFileDao) {
		this.imgFileDao = imgFileDao;
	}

	public void setSortDao(SortDao sortDao) {
		this.sortDao = sortDao;
	}

	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public void setExternalLinkDao(ExternalLinkDao externalLinkDao) {
		this.externalLinkDao = externalLinkDao;
	}

}
