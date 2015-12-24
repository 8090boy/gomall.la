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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.legendshop.business.dao.UserDetailDao;
import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.event.FireEvent;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.event.EventHome;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.spi.cache.ShopDetailUpdate;
import com.legendshop.spi.dao.ShopDetailDao;
import com.legendshop.spi.event.ShopDetailDeleteEvent;
import com.legendshop.spi.event.ShopDetailSaveEvent;
import com.legendshop.spi.event.ShopDetailUpdateEvent;
import com.legendshop.spi.service.CommonUtil;
import com.legendshop.util.AppUtils;
import com.legendshop.util.constant.ShopStatusEnum;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * ------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------
 * -------------------------------------------------------------------------
 * 
 * 
 * 官方网站：http://www.legendesign.net
 */
public abstract class ShopDetailDaoImpl extends BaseDaoImpl implements ShopDetailDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ShopDetailDaoImpl.class);

	/** The common util. */
	private CommonUtil commonUtil;
	
	/** The user detail dao. */
	private UserDetailDao userDetailDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#isShopExists(java.lang
	 * .String)
	 */
	@Override
	@Cacheable(value = "ShopDetailView")
	public Boolean isShopExists(final String userName) {
		if (AppUtils.isBlank(userName)) {
			return false;
		}
		List list = findByHQL("select userName from ShopDetail where  status = 1 and userName = ?", userName);
		return list != null && list.size() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#getShopDetailForUpdate
	 * (java.lang.String)
	 */
	@Override
	public ShopDetail getShopDetailForUpdate(final String userName) {
		ShopDetail shopDetail = findUniqueBy("from ShopDetail sd where sd.userName = ?", ShopDetail.class, userName);
		return shopDetail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#getShopDetail(java.lang
	 * .String)
	 */
	@Override
	public ShopDetail getShopDetail(final String userName) {
		return findUniqueBy("from ShopDetail sd where sd.userName = ?", ShopDetail.class, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#isLeagueShopExists(java
	 * .lang.String)
	 */
	@Override
	@Cacheable(value = "ShopDetailView")
	public Boolean isLeagueShopExists(final String userName) {
		if (userName == null)
			return false;
		Long num = findUniqueBy("select count(*) from Myleague where userId = ? ", Long.class, userName);
		return num > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#canbeLeagueShop(boolean,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Cacheable(value = "ShopDetailView")
	public Boolean isBeLeagueShop(final boolean isShopExists, final String userName, final String friendName) {
		if (!isShopExists || AppUtils.isBlank(userName) || userName.equals(friendName)) {
			return false;
		}
		Long result = findUniqueBy("select count(*) from Myleague where userId = ? and friendId = ?", Long.class, userName,
				friendName);
		return result <= 0;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#getProductNum(java.lang
	 * .String)
	 */
	@Override
	public Integer getProductNum(String userName) {
		String sql = "select count(*) from Product prod where prod.status = 1 and prod.userName = ?";
		return findUniqueBy(sql, Long.class, userName).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ShopDetailDao#getOffProductNum(java.
	 * lang.String)
	 */
	@Override
	public Integer getOffProductNum(String userName) {
		String sql = "select count(*) from Product prod where prod.status = 0 and prod.userName = ?";
		return findUniqueBy(sql, Long.class, userName).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.impl.ShopDetailDao#updateShopDetail(com.
	 * legendshop.model.entity.ShopDetail)
	 */
	@Override
	@ShopDetailUpdate
	public void updateShopDetail(ShopDetail shopDetail) {
		EventHome.publishEvent(new ShopDetailUpdateEvent(shopDetail));
		update(shopDetail);
	}

	/**
	 * Save or update shop detail.
	 *
	 * @param shopDetail the shop detail
	 */
	@ShopDetailUpdate
	private void saveOrUpdateShopDetail(ShopDetail shopDetail) {
		saveOrUpdate(shopDetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.impl.ShopDetailDao#
	 * updateShopDetailWhenProductChange(com.legendshop.model.entity.Product)
	 */
	@Override
	@Caching(evict = { @CacheEvict(value = "ShopDetailView", key = "#product.userName") })
	public void updateShopDetailWhenProductChange(Product product) {
		ShopDetail shopdetail = getShopDetailForUpdate(product.getUserName());
		if (shopdetail == null) {
			throw new NotFoundException("ShopDetail is null, UserName = " + product.getUserName());
		}
		shopdetail.setProductNum(getProductNum(product.getUserName()));
		shopdetail.setOffProductNum(getOffProductNum(product.getUserName()));
		updateShopDetail(shopdetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ShopDetailDao#getShopDetailByUserId(java.
	 * lang.String)
	 */
	@Override
	public ShopDetail getShopDetailByUserId(String userId) {
		return findUniqueBy("from ShopDetail sd where sd.userId = ?", ShopDetail.class, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ShopDetailDao#updateShopDetail(java.lang.
	 * String)
	 */
	@Override
	@Caching(evict = { @CacheEvict(value = "ShopDetailView", key = "#userName") })
	public void updateShopDetail(String userName) {
		ShopDetail shopdetail = getShopDetailForUpdate(userName);
		if (shopdetail == null) {
			throw new NotFoundException("ShopDetail is null, UserName = " + userName);
		}
		shopdetail.setProductNum(getProductNum(userName));
		shopdetail.setOffProductNum(getOffProductNum(userName));
		updateShopDetail(shopdetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ShopDetailDao#updateShop(java.lang.String,
	 * java.lang.String, com.legendshop.model.entity.ShopDetail,
	 * java.lang.Integer)
	 */
	@Override
	@Caching(evict = { @CacheEvict(value = "ShopDetailView", key = "#shopDetail.userName") })
	public boolean updateShop(String userId, ShopDetail shopDetail, Integer status) {

		boolean result = true;
		try {
			if (ShopStatusEnum.REJECT.value().equals(status)
					|| ShopStatusEnum.CLOSE.value().equals(status)) {
				// 拒绝开店
				commonUtil.removeAdminRight(userId);
			} else if (ShopStatusEnum.ONLINE.value().equals(status)) {
				commonUtil.saveAdminRight(userId);

			}
			shopDetail.setStatus(status);
			shopDetail.setModifyDate(new Date());
			EventHome.publishEvent(new FireEvent(shopDetail, OperationTypeEnum.UPDATE_STATUS));
			saveOrUpdateShopDetail(shopDetail);

		} catch (Exception e) {
			log.error("auditShop ", e);
			result = false;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ShopDetailDao#saveShopDetail(com.legendshop
	 * .model.entity.ShopDetail)
	 */
	@Override
	public void saveShopDetail(ShopDetail shopDetail) {
		EventHome.publishEvent(new ShopDetailSaveEvent(shopDetail));
		save(shopDetail);
		// save right
		// 保存管理员角色
		commonUtil.saveAdminRight(shopDetail.getUserId());

	}
	

	/**
	 * 删除商城.
	 *
	 * @param shopDetail the shop detail
	 */
	@Override
	@ShopDetailUpdate
	public void deleteShopDetail(ShopDetail shopDetail) {
		userDetailDao.deleteShopDetail(shopDetail.getUserId(), shopDetail.getUserName(), true);
		EventHome.publishEvent(new ShopDetailDeleteEvent(shopDetail));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ShopDetailDao#getShopDetailByShopId(java.
	 * lang.Long)
	 */
	@Override
	public ShopDetail getShopDetailByShopId(final Long shopId) {
		return get(ShopDetail.class, shopId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.ShopDetailDao#getAllShopCount()
	 */
	@Override
	public Long getAllShopCount() {
		return findUniqueBy("select count(*) from ShopDetail", Long.class);
	}

	/**
	 * Sets the common util.
	 * 
	 * @param commonUtil
	 *            the new common util
	 */
	@Required
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}

}
