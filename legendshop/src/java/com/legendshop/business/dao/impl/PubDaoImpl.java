/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.legendshop.business.dao.PubDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.Pub;
import com.legendshop.spi.constants.Constants;
import com.legendshop.util.AppUtils;

/**
 * 公告Dao
 */
@SuppressWarnings("unchecked")
public class PubDaoImpl extends BaseDaoImpl implements PubDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(PubDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.impl.PubDao#getPub(java.lang.String)
	 */
	@Override
	@Cacheable(value = "PubList", key = "#shopName")
	public List<Pub> getPub(final String shopName) {
		log.debug("getPub, shopName = {}", shopName);
		if (shopName == null) {
			return null;
		}
		List<Pub> list = getPubList(shopName);
		if (AppUtils.isBlank(list)) {// 默认商城
			list = getPubList(PropertiesUtil.getDefaultShopName());
		}
		return list;
	}

	private List<Pub> getPubList(String userName) {
		CriteriaQuery cq = new CriteriaQuery(Pub.class);
		Date today = new Date();
		cq.eq("status", Constants.ONLINE);
		cq.eq("userName", userName);
		// 有效期检查
		cq.or(Restrictions.ge("endDate", today), Restrictions.isNull("endDate"));
		cq.or(Restrictions.le("startDate", today),
				Restrictions.isNull("startDate"));
		cq.addOrder("desc", "recDate");
		return findListByCriteria(cq, 0, 9);
	}

	@Override
	@CacheEvict(value = "Pub", key = "#pub.id")
	public void deletePub(Pub pub) {
		deleteById(Pub.class, pub.getId());
	}

	/**
	 * 更新公告
	 */
	@Override
	@Caching(evict = { 
			@CacheEvict(value = "PubList", key = "#pub.userName"),
			@CacheEvict(value = "Pub", key = "#pub.id") })
	public void updatePub(Pub pub) {
		update(pub);

	}

	@Override
	@CacheEvict(value = "PubList", key = "#pub.userName")
	public Long savePub(Pub pub) {
		return (Long) save(pub);
	}

}
