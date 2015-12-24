/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Sort;

/**
 * The Interface NsortService.
 */
public interface NsortService {

	/**
	 * List.
	 * 
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<Nsort> getNsortList(String userName);

	// parentNsortId is not null ：3级分类
	/**
	 * List by sort.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the list
	 */
	public abstract List<Nsort> getNSort3BySort(Long sortId);
	
	/**
	 * 根据二级分类找到对应的三级分类.
	 *
	 * @param nsortId the nsort id
	 * @return the n sort3 by n sort2
	 */
	public abstract List<Nsort> getNSort3ByNSort2(Long nsortId) ;
	
	/**
	 * Gets the n sort2 by sort.
	 * 得到商品二级分类
	 *
	 * @param sortId the sort id
	 * @return the n sort2 by sort
	 */
	public abstract List<Nsort> getNSort2BySort(Long sortId);

	/**
	 * Checks for child nsort.
	 * 
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public abstract boolean hasChildNsort(String userName,Long id, Long parentNsortId);

	/**
	 * Checks for child nsort brand.
	 * 
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public abstract boolean hasChildNsortBrand(Long id);

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @return the nsort
	 */
	public abstract Nsort getNsort(Long id);

	/**
	 * Load sort.
	 * 
	 * @param id
	 *            the id
	 * @return the sort
	 */
	public abstract Sort getSort(Long id);

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void delete(Long id);

	/**
	 * Save.
	 * 
	 * @param nsort
	 *            the nsort
	 * @return the long
	 */
	public abstract Long save(Nsort nsort);

	/**
	 * Update.
	 * 
	 * @param nsort
	 *            the nsort
	 */
	public abstract void update(Nsort nsort);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getNsortList(CriteriaQuery cq);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param hql
	 *            the hql
	 * @return the data by criteria query
	 */
	public abstract PageSupport getNsortList(HqlQuery hql);

	/**
	 * Query nsort.
	 * 
	 * @param id
	 *            the id
	 * @return the nsort
	 */
	public abstract Nsort getNsortById(Long id);

	/**
	 * Query nsort by sort id.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the list
	 */
	public abstract List<Nsort> getNsortBySortId(Long sortId);

	/**
	 * Gets the navigation nsort.
	 * 
	 * @param userName
	 *            the user name
	 * @return the navigation nsort
	 */
	public abstract List<Nsort> getNavigationNsort(String userName);

	/**
	 * Turn on.
	 * 
	 * @param nsort
	 *            the nsort
	 */
	public abstract void turnOn(Nsort nsort);

	/**
	 * Turn off.
	 * 
	 * @param nsort
	 *            the nsort
	 */
	public abstract void turnOff(Nsort nsort);
	
	/**
	 * 根据第三级菜单查找对应的用户名
	 *
	 * @param subNsortId the sub nsort id
	 * @return the user name by nsort id
	 */
	public abstract String getUserNameByNsortId(Long subNsortId);

	/**
	 * 判断产商品分类下面是否有产品
	 * @param userName
	 * @param id
	 * @param parentNsortId
	 * @return
	 */
	public abstract boolean hasChildProduct(String userName, Long id, Long parentNsortId);

}