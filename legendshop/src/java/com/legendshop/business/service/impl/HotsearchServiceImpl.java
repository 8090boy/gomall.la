/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.HotsearchDao;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.model.entity.Hotsearch;
import com.legendshop.spi.service.HotsearchService;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * ------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------
 * -------------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public class HotsearchServiceImpl implements HotsearchService {

	/** The log. */
	Logger log = LoggerFactory.getLogger(HotsearchServiceImpl.class);

	/** The hotsearch dao. */
	private HotsearchDao hotsearchDao;

	private BaseJdbcDao baseJdbcDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#list(java.lang.String)
	 */
	@Override
	public List<Hotsearch> getHotsearch(String userName) {
		return hotsearchDao.getHotsearch(userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#load(java.lang.Long)
	 */
	@Override
	public Hotsearch getHotsearchById(Long id) {
		return hotsearchDao.get(Hotsearch.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#load(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public Hotsearch getHotsearchByIdAndName(Integer id, String userName) {
		return hotsearchDao.getHotsearchByIdAndName(id, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		hotsearchDao.deleteHotsearchById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#save(com.legendshop.
	 * model.entity.Hotsearch, java.lang.String, boolean)
	 */
	@Override
	public Long saveHotsearch(Hotsearch hotsearch) {
		return (Long) hotsearchDao.save(hotsearch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#update(com.legendshop
	 * .model.entity.Hotsearch)
	 */
	@Override
	public void updateHotsearch(Hotsearch hotsearch) {
		hotsearchDao.updateHotsearch(hotsearch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.HotsearchService#getSearch(java.lang.
	 * String, java.lang.Long)
	 */
	@Override
	public List<Hotsearch> getHotsearch(Long sortId) {
		return hotsearchDao.getHotsearch(sortId);
	}

	/**
	 * Sets the hotsearch dao.
	 * 
	 * @param hotsearchDao
	 *            the new hotsearch dao
	 */
	@Required
	public void setHotsearchDao(HotsearchDao hotsearchDao) {
		this.hotsearchDao = hotsearchDao;
	}

	@Override
	public PageSupport query(SimpleSqlQuery query) {
		return baseJdbcDao.find(query);
	}

	public void setBaseJdbcDao(BaseJdbcDao baseJdbcDao) {
		this.baseJdbcDao = baseJdbcDao;
	}
}
