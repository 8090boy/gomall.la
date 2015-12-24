/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.page;

import java.util.List;
import java.util.Map;

import com.legendshop.model.StatusKeyValueEntity;



/**
 * The Interface TempletManager.
 */
public interface TempletManager {
	

	/**
	 * Gets the front end templet list.
	 *
	 * @return the front end templet list
	 */
	public List<StatusKeyValueEntity> getFrontEndTempletList() ;
	
	/**
	 * Gets the back end templet list.
	 *
	 * @return the back end templet list
	 */
	public List<StatusKeyValueEntity> getBackEndTempletList() ;
	
	/**
	 * Gets the front end templet by id.
	 *
	 * @param templetId the templet id
	 * @return the front end templet by id
	 */
	public String getFrontEndTempletById(String templetId) ;
	
	/**
	 * Gets the back end templet by id.
	 *
	 * @param templetId the templet id
	 * @return the back end templet by id
	 */
	public String getBackEndTempletById(String templetId) ;

	/**
	 * Ge front endt templet map.
	 *
	 * @return the map
	 */
	public Map<String, String> getFrontEndtTempletMap();
	
	/**
	 * Gets the back end templet map.
	 *
	 * @return the back end templet map
	 */
	public Map<String, String> getBackEndTempletMap();
	
	
}
