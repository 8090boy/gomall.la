/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.ExternalLinkDao;
import com.legendshop.business.dao.MyleagueDao;
import com.legendshop.business.dao.NewsDao;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.helper.VisitHistoryHelper;
import com.legendshop.model.entity.ExternalLink;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductDetail;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.NewsPositionEnum;
import com.legendshop.spi.dao.NsortDao;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.dao.ShopDetailDao;
import com.legendshop.spi.dao.SortDao;
import com.legendshop.spi.form.SearchForm;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.BusinessService;
import com.legendshop.util.AppUtils;

/**
 * 
 * 业务Service，主要对前台
 * 
 * 官方网站：http://www.legendesign.net
 */
public class BusinessServiceImpl extends BaseServiceImpl implements BusinessService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

	/** The news dao. */
	private NewsDao newsDao;

	/** The external link dao. */
	private ExternalLinkDao externalLinkDao;

	/** The product dao. */
	private ProductDao productDao;

	/** The myleague dao. */
	private MyleagueDao myleagueDao;

	/** The default int. */
	private final Long defaultInt = 0l;

	/** The sort dao. */
	private SortDao sortDao;

	/** The nsort dao. */
	private NsortDao nsortDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#friendlink(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String getFriendlink(HttpServletRequest request, HttpServletResponse response) {
		String name = ThreadLocalContext.getCurrentShopName(request, response);
		List<ExternalLink> adList = externalLinkDao.getExternalLink(name);
		if (!AppUtils.isBlank(adList)) {
			request.setAttribute("adList", adList);
		}
		return PathResolver.getPath(request, response, FrontPage.FRIEND_LINK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#league(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String getLeague(HttpServletRequest request, HttpServletResponse response) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		String curPageNO = AppUtils.getDefaultValue(request.getParameter("curPageNO"), "1");
		PageSupport ps = myleagueDao.getLeague(shopName, curPageNO);
		ps.savePage(request);
		getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response),
				Constants.USER_REG_ADV_740);
		return PathResolver.getPath(request, response, TilesPage.LEAGUE);
	}

	/**
	 * 包括： 1. 商城查看历史 2. 产品查看历史
	 */
	@Override
	public void getVisitedShop(HttpServletRequest request) {
		List<Object> shopIds = VisitHistoryHelper.getVisitedShopDetail(request);
		List<ShopDetail> shopDetails = new ArrayList<ShopDetail>();
		for (Object userName : shopIds) {
			shopDetails.add(shopDetailDao.getShopDetail((String) userName));
		}
		request.setAttribute("visitedShop", shopDetails);

		// 产品查看历史
		List<Object> prodIds = VisitHistoryHelper.getVisitedProd(request);
		List<ProductDetail> products = productDao.getProdDetail(prodIds);
		request.setAttribute("visitedProd", products);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#setShopDetailDao
	 * (com.legendshop.business.dao.ShopDetailDao)
	 */
	/**
	 * Sets the shop detail dao.
	 * 
	 * @param shopDetailDao
	 *            the new shop detail dao
	 */
	@Override
	@Required
	public void setShopDetailDao(ShopDetailDao shopDetailDao) {
		this.shopDetailDao = shopDetailDao;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#setExternalLinkDao
	 * (com.legendshop.business.dao.ExternalLinkDao)
	 */
	/**
	 * Sets the external link dao.
	 * 
	 * @param externalLinkDao
	 *            the new external link dao
	 */
	@Required
	public void setExternalLinkDao(ExternalLinkDao externalLinkDao) {
		this.externalLinkDao = externalLinkDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#copyAll(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String getNewsforCommon(HttpServletRequest request, HttpServletResponse response) {
		// 采用公用帐号的信息
		String shopName = PropertiesUtil.getDefaultShopName();
		request.setAttribute("newsBottomList", newsDao.getNews(shopName, NewsPositionEnum.NEWS_BOTTOM, 8));
		return PathResolver.getPath(request, response, FrontPage.COPY);
	}

	/**
	 * Sets the product dao.
	 * 
	 * @param productDao
	 *            the new product dao
	 */
	@Required
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * Sets the myleague dao.
	 * 
	 * @param myleagueDao
	 *            the new myleague dao
	 */
	@Required
	public void setMyleagueDao(MyleagueDao myleagueDao) {
		this.myleagueDao = myleagueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#search(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * com.legendshop.business.action.form.SearchForm)
	 */
	@Override
	public String search(HttpServletRequest request, HttpServletResponse response, SearchForm searchForm) {
		Sort sort = null;
		List<Nsort> nsortList = null;
		// 1、关键字不能为空
		if (AppUtils.isBlank(searchForm.getKeyword())) {
			log.error("search keyword can't be null!");
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		// 2、查找对应的Sort,defaultValue=0表示没有选择类型
		if (!AppUtils.isBlank(searchForm.getSortId()) && !defaultInt.equals(searchForm.getSortId())) {
			sort = sortDao.getSort(searchForm.getSortId());
			if (sort != null) {
				//ThreadLocalContext.setCurrentShopName(request, response, sort.getUserName());
				request.setAttribute("sort", sort);
				request.setAttribute("CurrentSortId", sort.getSortId());
				nsortList = nsortDao.getNsortBySortId(searchForm.getSortId());
				request.setAttribute("nsortList", nsortList);
			}
		}

		try {
			// Qbc查找方式
			CriteriaQuery cq = new CriteriaQuery(Product.class, searchForm.getCurPageNOTop(), PageProviderEnum.SIMPLE_PAGE_PROVIDER);
			cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class) * 2);
			String shopName = ThreadLocalContext.getCurrentShopName(request, response);
			if(!PropertiesUtil.getDefaultShopName().equals(shopName)){ //默认商城可以查看所有的产品
				cq.eq("userName", ThreadLocalContext.getCurrentShopName(request, response));
			}
			
			Criterion c = null;
			if (!AppUtils.isBlank(searchForm.getKeyword())) {
				String[] keywords = AppUtils.searchByKeyword(searchForm.getKeyword());
				for (String word : keywords) {
					Criterion temp = Restrictions.like("name", "%" + word + "%");
					if (c == null) {
						c = temp;
					} else {
						c = Restrictions.or(c, temp);
					}
				}
			}
			if (!AppUtils.isBlank(searchForm.getSortId()) && !defaultInt.equals(searchForm.getSortId())) {
				if (c == null) {
					c = Restrictions.eq("sortId", searchForm.getSortId());
				} else {
					c = Restrictions.and(c, Restrictions.eq("sortId", searchForm.getSortId()));
				}

			}
			if (c != null) {
				cq.add(c);
			}
			cq.addOrder("desc", "buys");//订购数
			cq.addOrder("desc", "views");//查看数
			cq.addOrder("desc", "modifyDate");//修改时间

			PageSupport ps = productDao.getProdDetail(cq);
			ps.savePage(request);
			request.setAttribute("searchForm", searchForm);

		} catch (Exception e) {
			log.error("getProdDetail", e);
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		return PathResolver.getPath(request, response, TilesPage.PRODUCT_SORT);
	}

	/**
	 * Sets the sort dao.
	 * 
	 * @param sortDao
	 *            the new sort dao
	 */
	public void setSortDao(SortDao sortDao) {
		this.sortDao = sortDao;
	}

	/**
	 * Sets the nsort dao.
	 * 
	 * @param nsortDao
	 *            the new nsort dao
	 */
	public void setNsortDao(NsortDao nsortDao) {
		this.nsortDao = nsortDao;
	}

}
