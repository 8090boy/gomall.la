/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.template;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legendshop.model.StatusKeyValueEntity;

/**
 * The Class TempletEntity.
 */
public class TempletEntity  implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4076388169340185759L;

	/** The front end templet list. */
	private List<StatusKeyValueEntity> frontEndTempletList;
	
	/** The back end templet list. */
	private List<StatusKeyValueEntity> backEndTempletList;
	
	/** The front end templet map. */
	private Map<String, Templet> frontEndTempletMap;
	
	/** The back end templet map. */
	private Map<String, Templet> backEndTempletMap;

	/**
	 * Gets the front end templet list.
	 *
	 * @return the frontEndTempletList
	 */
	public List<StatusKeyValueEntity> getFrontEndTempletList() {
		return frontEndTempletList;
	}

	/**
	 * Sets the front end templet list.
	 *
	 * @param frontEndTempletList the frontEndTempletList to set
	 */
	public void setFrontEndTempletList(List<StatusKeyValueEntity> frontEndTempletList) {
		this.frontEndTempletList = frontEndTempletList;
	}

	/**
	 * Gets the back end templet list.
	 *
	 * @return the backEndTempletList
	 */
	public List<StatusKeyValueEntity> getBackEndTempletList() {
		return backEndTempletList;
	}

	/**
	 * Sets the back end templet list.
	 *
	 * @param backEndTempletList the backEndTempletList to set
	 */
	public void setBackEndTempletList(List<StatusKeyValueEntity> backEndTempletList) {
		this.backEndTempletList = backEndTempletList;
	}

	/**
	 * Gets the front end templet map.
	 *
	 * @return the frontEndTempletMap
	 */
	public Map<String, Templet> getFrontEndTempletMap() {
		return frontEndTempletMap;
	}

	/**
	 * Sets the front end templet map.
	 *
	 * @param frontEndTempletMap the frontEndTempletMap to set
	 */
	public void addFrontEndTempletMap(Templet templet) {
		if(frontEndTempletMap == null){
			frontEndTempletMap = new HashMap<String, Templet>();
		}
		frontEndTempletMap.put(templet.getId(), templet);
	}

	/**
	 * Gets the back end templet map.
	 *
	 * @return the backEndTempletMap
	 */
	public Map<String, Templet> getBackEndTempletMap() {
		return backEndTempletMap;
	}

	/**
	 * Sets the back end templet map.
	 *
	 * @param backEndTempletMap the backEndTempletMap to set
	 */
	public void addBackEndTempletMap(Templet templet) {
		if(backEndTempletMap == null){
			backEndTempletMap = new HashMap<String, Templet>();
		}
		backEndTempletMap.put(templet.getId(), templet);
	}

	
	
}
