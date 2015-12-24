/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.UserDetailDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.ShopStatusChecker;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.spi.dao.ShopDetailDao;
import com.legendshop.spi.service.ShopDetailService;

/**
 * 商城详细信息服务.
 */
public class ShopDetailServiceImpl extends BaseServiceImpl implements ShopDetailService {

	/** The shop detail dao. */
	private ShopDetailDao shopDetailDao;

	/** The user detail dao. */
	private UserDetailDao userDetailDao;

	/** The shop status checker. */
	private ShopStatusChecker shopStatusChecker;

	/**
	 * Sets the shop detail dao.
	 * 
	 * @param shopDetailDao
	 *            the new shopDetailDao
	 */
	@Override
	public void setShopDetailDao(ShopDetailDao shopDetailDao) {
		this.shopDetailDao = shopDetailDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ShopDetailService#findShopDetail(java
	 * .lang.Long)
	 */
	@Override
	public ShopDetail getShopDetailById(Long id) {
		return shopDetailDao.get(ShopDetail.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ShopDetailService#findUserDetailByName
	 * (java.lang.String)
	 */
	@Override
	public UserDetail getShopDetailByName(String userName) {
		return userDetailDao.getUserDetailByName(userName);
	}
	
	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.ShopDetailService#getShopDetailByShopId(java.lang.Long)
	 */
	public  ShopDetail getShopDetailByShopId(final Long shopId){
		return shopDetailDao.getShopDetailByShopId(shopId);
	}

	/**
	 * 删除商城相关信息
	 * 
	 */
	@Override
	public void delete(ShopDetail shopDetail) {
		//删除该商城的所有信息
		shopDetailDao.deleteShopDetail(shopDetail);
	}

	// UserId 一定不能为空
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ShopDetailService#save(com.legendshop
	 * .model.entity.ShopDetail)
	 */
	@Override
	public void save(ShopDetail shopDetail) {
		shopDetailDao.saveShopDetail(shopDetail);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.service.ShopService#getShopDetailView(java.lang.String)
	 */
	@Override
	@Cacheable(value = "ShopDetailView", key = "#currentShopName")
	public ShopDetailView getShopDetailView(String currentShopName) {
		ShopDetailView shopDetail = shopDetailDao.getShopDetailView(currentShopName);
		if (shopDetail != null && !shopStatusChecker.check(shopDetail, ThreadLocalContext.getRequest())) {
			return null;
		}
		return shopDetail;
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.service.ShopService#getShopNameByDomain(java.lang.String)
	 */
	@Override
	@Cacheable(value = "ShopDetailView", key = "'DM_' + #domainName")
	public String getShopNameByDomain(String domainName) {
		return shopDetailDao.getShopNameByDomain(domainName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ShopDetailService#update(com.legendshop
	 * .model.entity.ShopDetail)
	 */
	@Override
	public void update(ShopDetail shopDetail) {
		shopDetailDao.updateShopDetail(shopDetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.ShopDetailService#getShopDetail(com.
	 * legendshop.core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getShopDetail(CriteriaQuery cq) {
		return shopDetailDao.find(cq);
	}

	/**
	 * Sets the user detail dao.
	 * 
	 * @param userDetailDao
	 *            the new user detail dao
	 */
	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}


	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.ShopDetailService#getShopDetailByUserId(java.lang.String)
	 */
	@Override
	public ShopDetail getShopDetailByUserId(String userId) {
		return shopDetailDao.getShopDetailByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.ShopDetailService#updateShopDetail(com.legendshop.model.entity.Product)
	 */
	@Override
	public void updateShopDetail(Product product) {
		ShopDetail shopdetail = shopDetailDao.getShopDetailForUpdate(product.getUserName());
		if (shopdetail == null) {
			throw new NotFoundException("ShopDetail is null, UserName = " + product.getUserName());
		}
		shopdetail.setProductNum(shopDetailDao.getProductNum(product.getUserName()));
		shopdetail.setOffProductNum(shopDetailDao.getOffProductNum(product.getUserName()));
		shopDetailDao.updateShopDetail(shopdetail);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.ShopDetailService#updateShop(java.lang.String, java.lang.String, com.legendshop.model.entity.ShopDetail, java.lang.Integer)
	 */
	@Override
	public boolean updateShop(String userId, ShopDetail shopDetail, Integer status) {
		return shopDetailDao.updateShop(userId, shopDetail, status);
	}
	

	/**
	 * Sets the shop status checker.
	 *
	 * @param shopStatusChecker the new shop status checker
	 */
	public void setShopStatusChecker(ShopStatusChecker shopStatusChecker) {
		this.shopStatusChecker = shopStatusChecker;
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.service.ShopService#isShopExists(java.lang.String)
	 */
	@Override
	public Boolean isShopExists(String userName) {
		return shopDetailDao.isShopExists(userName);
	}

	@Override
	public Long getShopIdByName(String userName) {
		return shopDetailDao.getShopIdByName(userName);
	}



}
