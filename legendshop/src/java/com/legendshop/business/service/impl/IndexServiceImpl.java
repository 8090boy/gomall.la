/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.ExternalLinkDao;
import com.legendshop.business.dao.ImgFileDao;
import com.legendshop.business.dao.NewsDao;
import com.legendshop.business.dao.PubDao;
import com.legendshop.business.dao.SubDao;
import com.legendshop.business.dao.TagDao;
import com.legendshop.business.dao.UserCommentDao;
import com.legendshop.business.dao.UserDetailDao;
import com.legendshop.core.UserManager;
import com.legendshop.model.UserInfo;
import com.legendshop.model.entity.Indexjpg;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.spi.constants.NewsPositionEnum;
import com.legendshop.spi.constants.PageADV;
import com.legendshop.spi.constants.TemplateEnum;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.service.IndexService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;

/**
 * 首页相关服务.
 */
public class IndexServiceImpl extends BaseServiceImpl implements IndexService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);

	/** The user comment dao. */
	private UserCommentDao userCommentDao;

	/** The user detail dao. */
	private UserDetailDao userDetailDao;

	/** The news dao. */
	private NewsDao newsDao;

	/** The external link dao. */
	private ExternalLinkDao externalLinkDao;

	/** The product dao. */
	private ProductDao productDao;

	/** The img file dao. */
	private ImgFileDao imgFileDao;

	/** The sub dao. */
	private SubDao subDao;

	/** The pub dao. */
	private PubDao pubDao;

	/** The tag dao. */
	private TagDao tagDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#index(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void getIndex(HttpServletRequest request, HttpServletResponse response, ShopDetailView shopDetail) {

		String shopName = shopDetail.getUserName();
		List<Product> commendProdList = productDao.getCommendProd(shopName, 40);
		request.setAttribute("commendProdList", commendProdList);
		// 最新商品
		request.setAttribute("newestProdList", productDao.getNewestProd(shopName, 11));

		request.setAttribute("pubList", pubDao.getPub(shopName));
		getAndSetAdvertisement(request, response, shopName, PageADV.INDEX.name());
		// 普通新闻
		request.setAttribute("newList", newsDao.getNews(shopName, NewsPositionEnum.NEWS_NEWS, 6));

		List<Indexjpg> indexJpgList = imgFileDao.getIndexJpeg(shopName);
		if (!AppUtils.isBlank(indexJpgList)) {
			//最大的广告数
			request.setAttribute("maxScreen", indexJpgList.size());
			request.setAttribute("indexJSON", JSONUtil.getJson(indexJpgList));
		} else {
			request.setAttribute("maxScreen", 0);
		}

		request.setAttribute("externalLinkList", externalLinkDao.getExternalLink(shopName));

		String userName = UserManager.getUserName(request.getSession());

		if (!TemplateEnum.DEFAULT.equals(shopDetail.getFrontEndStyle())) {
			request.setAttribute("tagList", tagDao.getPageTag(shopName, PageADV.INDEX.name()));
		}
		request.setAttribute("showMenu", true);
		logUserAccess(request, shopName, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexService#indexAdmin(java.lang.String,
	 * com.legendshop.model.entity.ShopDetailView)
	 */
	@Override
	public UserInfo getAdminIndex(String userName, ShopDetailView shopDetail) {
		UserInfo userInfo = new UserInfo();

		if (shopDetail != null) { // 已有商城的用户

			userInfo.setArticleNum(newsDao.getAllNews(userName));

			userInfo.setProcessingOrderNum(subDao.getTotalProcessingOrder(userName));
			userInfo.setUnReadMessageNum(userCommentDao.getTotalUnReadMessage(userName));

			userInfo.setShopDetail(shopDetail);
		} else {// 管理员
			userInfo.setUserTotalNum(userDetailDao.getAllUserCount());

			userInfo.setShopTotalNum(shopDetailDao.getAllShopCount());
		}

		return userInfo;
	}

	/**
	 * Sets the news dao.
	 * 
	 * @param newsDao
	 *            the new news dao
	 */
	@Required
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	/**
	 * Sets the sub dao.
	 * 
	 * @param subDao
	 *            the new sub dao
	 */
	@Required
	public void setSubDao(SubDao subDao) {
		this.subDao = subDao;
	}

	/**
	 * Sets the user comment dao.
	 * 
	 * @param userCommentDao
	 *            the new user comment dao
	 */
	@Required
	public void setUserCommentDao(UserCommentDao userCommentDao) {
		this.userCommentDao = userCommentDao;
	}

	/**
	 * Sets the user detail dao.
	 * 
	 * @param userDetailDao
	 *            the new user detail dao
	 */
	@Required
	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}

	/**
	 * Gets the user comment dao.
	 * 
	 * @return the user comment dao
	 */
	public UserCommentDao getUserCommentDao() {
		return userCommentDao;
	}

	/**
	 * Sets the external link dao.
	 * 
	 * @param externalLinkDao
	 *            the new external link dao
	 */
	public void setExternalLinkDao(ExternalLinkDao externalLinkDao) {
		this.externalLinkDao = externalLinkDao;
	}

	/**
	 * Sets the product dao.
	 * 
	 * @param productDao
	 *            the new product dao
	 */
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * Sets the img file dao.
	 * 
	 * @param imgFileDao
	 *            the new img file dao
	 */
	public void setImgFileDao(ImgFileDao imgFileDao) {
		this.imgFileDao = imgFileDao;
	}

	/**
	 * Sets the pub dao.
	 * 
	 * @param pubDao
	 *            the new pub dao
	 */
	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	/**
	 * Sets the tag dao.
	 * 
	 * @param tagDao
	 *            the new tag dao
	 */
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

}
