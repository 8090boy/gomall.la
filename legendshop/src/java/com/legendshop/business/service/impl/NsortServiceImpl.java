/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.dao.NsortDao;
import com.legendshop.spi.service.NsortService;
import com.legendshop.util.AppUtils;

/**
 * 产品子分类服务.
 */
public class NsortServiceImpl implements NsortService {

	/** The nsort dao. */
	private NsortDao nsortDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.NsortService#list(java.lang.String)
	 */
	@Override
	public List<Nsort> getNsortList(String userName) {
		return nsortDao.findByHQL("from Nsort where userName = ?", new Object[] { userName });
	}

	// parentNsortId is not null ：3级分类
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#listBySort(java.lang.Long)
	 */
	@Override
	public List<Nsort> getNSort3BySort(Long sortId) {
		return nsortDao.findByHQL("from Nsort where sortId = ? and parentNsortId is not null", sortId);
	}
	

	/**
	 * 得到二级分类.
	 *
	 * @param sortId the sort id
	 * @return the n sort2 by sort
	 */
	@Override
	public List<Nsort> getNSort2BySort(Long sortId) {
		return nsortDao.findByHQL("from Nsort where sortId = ? and parentNsortId is null", sortId);
	}
	
	/**
	 * 根据第二级分类找到第三季分类,按此类推.
	 *
	 * @param nsortId the nsort id
	 * @return the n sort3 by n sort2
	 */
	@Override
	public List<Nsort> getNSort3ByNSort2(Long nsortId) {
		return nsortDao.findByHQL("from Nsort where parentNsortId = ?", nsortId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#hasChildNsort(java.lang.
	 * Long)
	 */
	@Override
	public boolean hasChildNsort(String userName,Long id,Long parentNsortId) {
		if(parentNsortId != null){ //三级商品分类下面就没有分类的
			return false;
		}
		Long result = nsortDao.findUniqueBy("select count(*) from Nsort where parentNsortId = ?", Long.class, id);
		return result > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#hasChildNsortBrand(java.
	 * lang.Long)
	 */
	@Override
	public boolean hasChildNsortBrand(Long id) {
		Long result = nsortDao.findUniqueBy("select count(*) from NsortBrand n where n.id.nsortId = ?", Long.class, id);
		return result > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.NsortService#load(java.lang.Long)
	 */
	@Override
	public Nsort getNsort(Long id) {
		return nsortDao.get(Nsort.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#loadSort(java.lang.Long)
	 */
	@Override
	public Sort getSort(Long id) {
		return nsortDao.get(Sort.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.NsortService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		nsortDao.deleteNsortById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#save(com.legendshop.model
	 * .entity.Nsort)
	 */
	@Override
	public Long save(Nsort nsort) {
		if (!AppUtils.isBlank(nsort.getNsortId())) {
			Nsort origin = this.getNsort(nsort.getNsortId());
			if (origin != null) {
				origin.setNsortName(nsort.getNsortName());
				origin.setSeq(nsort.getSeq());
				origin.setSortDeputy(nsort.getSortDeputy());
				update(origin);
			}

			return nsort.getNsortId();
		}
		nsort.setStatus(Constants.ONLINE);
		return (Long) nsortDao.save(nsort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#update(com.legendshop.model
	 * .entity.Nsort)
	 */
	@Override
	public void update(Nsort nsort) {
		nsortDao.updateNsort(nsort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#getDataByCriteriaQuery(com
	 * .legendshop.core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getNsortList(CriteriaQuery cq) {
		return nsortDao.find(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#getDataByCriteriaQuery(com
	 * .legendshop.core.dao.support.HqlQuery)
	 */
	@Override
	public PageSupport getNsortList(HqlQuery hql) {
		return nsortDao.find(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#queryNsort(java.lang.Long)
	 */
	@Override
	public Nsort getNsortById(Long id) {
		return nsortDao.getNsort(id);
	}

	/**
	 * Sets the nsort dao.
	 * 
	 * @param nsortDao
	 *            the nsortDao
	 */
	@Required
	public void setNsortDao(NsortDao nsortDao) {
		this.nsortDao = nsortDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.NsortService#queryNsortBySortId(java.
	 * lang.Long)
	 */
	@Override
	public List<Nsort> getNsortBySortId(Long sortId) {
		return nsortDao.getNsortBySortId(sortId);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.NsortService#getNavigationNsort(java.lang.String)
	 */
	@Override
	public List<Nsort> getNavigationNsort(String userName) {
		return nsortDao.getNavigationNsort(userName);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.NsortService#turnOn(com.legendshop.model.entity.Nsort)
	 */
	@Override
	public void turnOn(Nsort nsort) {
		nsort.setStatus(Constants.ONLINE);
		update(nsort);

	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.NsortService#turnOff(com.legendshop.model.entity.Nsort)
	 */
	@Override
	public void turnOff(Nsort nsort) {
		nsort.setStatus(Constants.OFFLINE);
		update(nsort);
	}

	/*
	 *根据二级分类找到对应的三级分类.
	 */
	@Override
	public String getUserNameByNsortId(Long subNsortId) {
		return nsortDao.getUserNameByNsortId(subNsortId);
	}

	@Override
	public boolean hasChildProduct(String userName, Long id, Long parentNsortId) {
		return nsortDao.hasChildProduct(userName, id, parentNsortId);
	}


}
