/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import org.springframework.cache.annotation.CacheEvict;

import com.legendshop.business.dao.IndexJpgDao;
import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.event.FireEvent;
import com.legendshop.event.EventHome;
import com.legendshop.model.entity.Indexjpg;
import com.legendshop.spi.service.IndexJpgService;

/**
 * 首页广告服务.
 */
public class IndexJpgServiceImpl implements IndexJpgService {

	/** 首页广告Dao. */
	private IndexJpgDao indexJpgDao;

	/**
	 * Sets the index jpg dao.
	 * 
	 * @param indexJpgDao
	 *            the new index jpg dao
	 */
	public void setIndexJpgDao(IndexJpgDao indexJpgDao) {
		this.indexJpgDao = indexJpgDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexJpgService#queryIndexJpg(com.legendshop
	 * .core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getIndexJpg(CriteriaQuery cq) {
		return indexJpgDao.queryIndexJpg(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexJpgService#queryIndexJpg(java.lang
	 * .Long)
	 */
	@Override
	public Indexjpg getIndexJpgById(Long id) {
		return indexJpgDao.queryIndexJpg(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexJpgService#deleteIndexJpg(com.legendshop
	 * .model.entity.Indexjpg)
	 */
	@Override
	@CacheEvict(value = "Indexjpg", key = "#indexjpg.id")
	public void deleteIndexJpg(Indexjpg indexjpg) {
		EventHome.publishEvent(new FireEvent(indexjpg, OperationTypeEnum.DELETE));
		indexJpgDao.deleteIndexJpg(indexjpg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexJpgService#getIndexJpgNum(java.lang
	 * .String)
	 */
	@Override
	public Long getIndexJpgNum(String name) {
		return indexJpgDao.getIndexJpgNum(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexJpgService#updateIndexjpg(com.legendshop
	 * .model.entity.Indexjpg)
	 */
	@Override
	@CacheEvict(value = "Indexjpg", key = "#indexjpg.id")
	public void updateIndexjpg(Indexjpg indexjpg) {
		EventHome.publishEvent(new FireEvent(indexjpg, OperationTypeEnum.UPDATE));
		indexJpgDao.updateIndexjpg(indexjpg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.IndexJpgService#saveIndexjpg(com.legendshop
	 * .model.entity.Indexjpg)
	 */
	@Override
	public void saveIndexjpg(Indexjpg indexjpg) {
		indexJpgDao.saveIndexjpg(indexjpg);
	}

}
