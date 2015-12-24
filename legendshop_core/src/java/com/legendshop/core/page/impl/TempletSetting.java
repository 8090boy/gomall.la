/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.page.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.legendshop.model.StatusKeyValueEntity;

/**
 * The Class TempletSetting.
 * 封装了前后台的模版设定
 */
public class TempletSetting  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5161478210297452879L;

	/** The templet map. */
	private Map<String, String> templetMap;
	
	/** The templet map. */
	private List<StatusKeyValueEntity> templetList;

	/**
	 * Gets the templet map.
	 *
	 * @return the templetMap
	 */
	public Map<String, String> getTempletMap() {
		return templetMap;
	}

	/**
	 * Sets the templet map.
	 *
	 * @param templetMap the templetMap to set
	 */
	public void setTempletMap(Map<String, String> templetMap) {
		this.templetMap = templetMap;
	}

	/**
	 * Gets the templet list.
	 *
	 * @return the templetList
	 */
	public List<StatusKeyValueEntity> getTempletList() {
		return templetList;
	}

	/**
	 * Sets the templet list.
	 *
	 * @param templetList the templetList to set
	 */
	public void addTempletList(StatusKeyValueEntity entity) {
		if(templetList == null){
			 templetList = new ArrayList<StatusKeyValueEntity>();
		}
		this.templetList.add(entity);
	}
	
}
