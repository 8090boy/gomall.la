/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.page.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.page.TempletManager;
import com.legendshop.model.StatusKeyValueEntity;

/**
 * The Class TempletManagerImpl.
 */
public class TempletManagerImpl implements TempletManager {
	
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(TempletManagerImpl.class);
	
	/** The templet map. */
	private TempletSetting frontEnd;
	
	/** The back end. */
	private TempletSetting backEnd;
	
	/**
	 * Inits the.
	 */
	public void init(){
		log.debug("TempletManager initializing");
		init(frontEnd);
		init(backEnd);
	}
	
	/**
	 * Inits the.
	 *
	 * @param setting the setting
	 */
	public void init(TempletSetting setting){
			Set<String> nameSet = setting.getTempletMap().keySet();
			for (String name : nameSet) {
				StatusKeyValueEntity entity = new StatusKeyValueEntity();
				entity.setKey(name);
				entity.setValue("templet_" + name); //for NLS, look up translation from properties
				setting.addTempletList(entity);
		}
	}

	
	/**
	 * Gets the front end.
	 *
	 * @return the frontEnd
	 */
	public TempletSetting getFrontEnd() {
		return frontEnd;
	}

	/**
	 * Sets the front end.
	 *
	 * @param frontEnd the frontEnd to set
	 */
	public void setFrontEnd(TempletSetting frontEnd) {
		this.frontEnd = frontEnd;
	}

	/**
	 * Gets the back end.
	 *
	 * @return the backEnd
	 */
	public TempletSetting getBackEnd() {
		return backEnd;
	}

	/**
	 * Sets the back end.
	 *
	 * @param backEnd the backEnd to set
	 */
	public void setBackEnd(TempletSetting backEnd) {
		this.backEnd = backEnd;
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.page.TempletManager#getFrontEndTempletList()
	 */
	public List<StatusKeyValueEntity> getFrontEndTempletList() {
		return frontEnd.getTempletList();
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.page.TempletManager#getBackEndTempletList()
	 */
	public List<StatusKeyValueEntity> getBackEndTempletList() {
		return backEnd.getTempletList();
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.page.TempletManager#getFrontEndTempletById(java.lang.String)
	 */
	public String getFrontEndTempletById(String templetId) {
		return frontEnd.getTempletMap().get(templetId);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.page.TempletManager#getBackEndTempletById(java.lang.String)
	 */
	public String getBackEndTempletById(String templetId) {
		return backEnd.getTempletMap().get(templetId);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.page.TempletManager#geFrontEndtTempletMap()
	 */
	public Map<String, String> getFrontEndtTempletMap() {
		return frontEnd.getTempletMap();
	}

	/* (non-Javadoc)
	 * @see com.legendshop.core.page.TempletManager#getBackEndTempletMap()
	 */
	public Map<String, String> getBackEndTempletMap() {
		return backEnd.getTempletMap();
	}


}
