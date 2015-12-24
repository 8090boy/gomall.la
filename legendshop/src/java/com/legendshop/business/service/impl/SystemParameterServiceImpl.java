package com.legendshop.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.SystemParameterDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.SystemParameter;
import com.legendshop.spi.service.SystemParameterService;

 
public class SystemParameterServiceImpl implements SystemParameterService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(SystemParameterServiceImpl.class);

	/** The base dao. */
	private SystemParameterDao systemParameterDao;



	/**
	 * List.
	 * 
	 * @return the list
	 */
	private List<SystemParameter> list() {
		return systemParameterDao.findByHQL("from SystemParameter");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SystemParameterService#load(java.lang
	 * .String)
	 */
	@Override
	public SystemParameter getSystemParameter(String id) {
		return systemParameterDao.get(SystemParameter.class, id);
	}

 
	@Override
	public void delete(String id) {
		systemParameterDao.deleteSystemParameterById(id);
	}

 
	@Override
	public void update(SystemParameter systemParameter) {
		systemParameterDao.updateSystemParameter(systemParameter);
	}

	 
	@Override
	public PageSupport getSystemParameterList(CriteriaQuery cq) {
		return systemParameterDao.find(cq);
	}

 
	@Override
	public void initSystemParameter() {
		List<SystemParameter> list = list();
		for (SystemParameter parameter : list) {
			PropertiesUtil.setParameter(parameter);
		}
		log.info("System Parameter size = {}", list.size());
	}
	
	/**
	 * Sets the base dao.
	 * 
	 * @param systemParameterDao
	 *            the new base dao
	 */
	@Required
	public void setBaseDao(SystemParameterDao systemParameterDao) {
		this.systemParameterDao = systemParameterDao;
	}

}
