/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.TagDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.model.entity.Advertisement;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.Sort;
import com.legendshop.model.entity.Tag;
import com.legendshop.spi.dao.AdvertisementDao;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.dao.SortDao;
import com.legendshop.util.AppUtils;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * The Class TagDaoImpl.
 */

public class TagDaoImpl extends BaseDaoImpl implements TagDao {
	
	/** The sort dao. */
	private SortDao sortDao;

	/** The tag dao. */
	private AdvertisementDao advertisementDao;

	private ProductDao productDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.TagDao#getTag(java.lang.String)
	 */
	@Cacheable(value = "TagList")
	public List<Tag> getPageTag(String userName, String page) {
		List<Tag> tagList = findByHQL("from Tag where userName = ?", userName);
		// 用户的产品分类
		List<Sort> sortList = sortDao.getSort(userName, ProductTypeEnum.PRODUCT.value(), null, null, true);
		Map<String, List<Advertisement>> advList = advertisementDao.getAdvertisement(userName, page);
		if (AppUtils.isNotBlank(tagList)) {
			for (int i = 0; i < tagList.size(); i++) {
				Tag tag = tagList.get(i);

				// inject sort
				for (Sort sort : sortList) {
					if (tag.getSortId().equals(sort.getSortId())) {
						tag.setSort(sort);
					}
				}

				// inject 广告
				List<Advertisement> pageAdv = advList.get(page + "_ADV_" + (i + 1));
				if (pageAdv != null) {
					tag.setAdvertisementList(pageAdv);
				}

				// inject 产品
				List<Product> commendProdList = productDao.getCommendProdBySort(userName, tag.getSortId(), 8);
				tag.setCommendProdList(commendProdList);
			}
		}
		return tagList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.TagDao#getTag(java.lang.Long)
	 */
	public Tag getTag(Long id) {
		return get(Tag.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.TagDao#deleteTag(com.legendshop.model.entity
	 * .Tag)
	 */
	@CacheEvict(value = "Tag", key = "#tag.tagId")
	public void deleteTag(Tag tag) {
		delete(tag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.TagDao#saveTag(com.legendshop.model.entity
	 * .Tag)
	 */
	public Long saveTag(Tag tag) {
		return (Long) save(tag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.TagDao#updateTag(com.legendshop.model.entity
	 * .Tag)
	 */
	@CacheEvict(value = "Tag", key = "#tag.tagId")
	public void updateTag(Tag tag) {
		update(tag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.TagDao#getTag(com.legendshop.core.dao.support
	 * .CriteriaQuery)
	 */
	public PageSupport getTag(CriteriaQuery cq) {
		return find(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.TagDao#getTag(com.legendshop.core.dao.support
	 * .SimpleHqlQuery)
	 */
	@Override
	public PageSupport getTag(SimpleHqlQuery hql) {
		hql.initSQL("biz.QueryTag", "biz.QueryTagCount");
		return find(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.TagDao#getTag(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Tag getTag(String tagName, String userName) {
		List<Tag> result = findByHQL("from Tag where name = ? and userName = ?", tagName, userName);
		if (AppUtils.isNotBlank(result)) {
			return result.get(0);
		}
		return null;
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

	public void setAdvertisementDao(AdvertisementDao advertisementDao) {
		this.advertisementDao = advertisementDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

}
