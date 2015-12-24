/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.NewsDao;
import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.event.FireEvent;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.event.EventHome;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.News;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.NewsPositionEnum;
import com.legendshop.util.AppUtils;

/**
 * 新闻Dao.
 */
@SuppressWarnings("unchecked")
public class NewsDaoImpl extends BaseDaoImpl implements NewsDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(NewsDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.impl.NewsDao#getNews(java.lang.String,
	 * com.legendshop.business.common.NewsPositionEnum, java.lang.Integer)
	 */
	@Override
	@Cacheable(value = "NewsList")
	public List<News> getNews(final String shopName, final NewsPositionEnum newsPositionEnum, final Integer num) {
		if (shopName == null) {
			log.warn("shopName is null");
			return null;
		}
		List<News> list = parseNews(shopName,newsPositionEnum, num);
		if (AppUtils.isBlank(list)) {//如果商家没有新闻，则采用默认商家的新闻填充页面
			list = parseNews(PropertiesUtil.getDefaultShopName(),newsPositionEnum, num);
		}
		return list;
	}

	/**
	 * 处理新闻
	 * @param shopName
	 * @param newsPositionEnum
	 * @param num
	 * @return
	 */
	private List<News> parseNews(final String shopName, final NewsPositionEnum newsPositionEnum, final Integer num){
		CriteriaQuery cq = new CriteriaQuery(News.class);
		cq = new CriteriaQuery(News.class);
		cq.eq("userName", shopName);
		cq.eq("position", newsPositionEnum.value());
		cq.eq("status", Constants.ONLINE);
		cq.addOrder("desc", "newsDate");
		List<News> list = findListByCriteria(cq, 0, num);
		return list;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.impl.NewsDao#getNewsById(java.lang.Long)
	 */
	@Override
	public News getNewsById(Long newsId) {
		return get(News.class, newsId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.NewsDao#getNews(com.legendshop.core.
	 * dao.support.CriteriaQuery)
	 */
	@Override
	@Cacheable(value = "NewsList", condition = "T(Integer).parseInt(#curPageNO) < 3")
	public PageSupport getNews(String curPageNO, String userName, Long newsCategoryId) {
		// Qbc查找方式
		CriteriaQuery cq = new CriteriaQuery(News.class, curPageNO, PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		cq.eq("status", Constants.ONLINE);
		cq.eq("position", NewsPositionEnum.NEWS_NEWS.value());
		cq.eq("userName", userName);
		cq.eq("newsCategory.newsCategoryId", newsCategoryId);
		cq.addOrder("desc", "highLine");
		cq.addOrder("desc", "newsDate");
		PageSupport ps = find(cq);
		return ps;
	}

	@Override
	@CacheEvict(value = "News", key = "#news.newsId")
	public void updateNews(News news) {
		EventHome.publishEvent(new FireEvent(news, OperationTypeEnum.UPDATE));
		update(news);
	}

	@Override
	@CacheEvict(value = "News", key = "#id")
	public void deleteNewsById(Long id) {
		News news = getNewsById(id);
		if (news != null) {
			EventHome.publishEvent(new FireEvent(news, OperationTypeEnum.DELETE));
			delete(news);
		}
	}

	@Override
	public Long getAllNews(String userName) {
		return findUniqueBy("select count(*) from News where userName = ?", Long.class, userName);
	}

	@Override
	@Cacheable(value = "NewsList")
	public Map<KeyValueEntity, List<News>> getNewsByCategory(String userName) {
		String sql = "select new News(n.newsId, c.newsCategoryId,c.newsCategoryName, n.newsTitle) from News n,NewsCategory c where c.newsCategoryId = n.newsCategory.newsCategoryId and n.status = 1 and c.status = 1 and  n.position = 1 and n. userName =?  order by c.newsCategoryId,n.highLine, n.newsDate desc";
		List<News> newsList = findByHQLLimit(sql, 0, 50, userName);
		Map<KeyValueEntity, List<News>> newsMap = null;
		if (AppUtils.isNotBlank(newsList)) {
			newsMap = new HashMap<KeyValueEntity, List<News>>();

			for (News news : newsList) {
				KeyValueEntity keyValueEntity = new KeyValueEntity(news.getNewsCategoryId().toString(), news.getNewsCategoryName());
				List<News> newsCatList = newsMap.get(keyValueEntity);
				if (newsCatList == null) {
					newsCatList = new ArrayList<News>();
				}
				newsCatList.add(news);
				newsMap.put(keyValueEntity, newsCatList);
			}
		}
		return newsMap;
	}

}
