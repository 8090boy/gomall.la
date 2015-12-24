/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.dao;

import java.util.List;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.Nsort;

/**
 * 二级商品分类Dao.
 */
public interface NsortDao extends BaseDao {

	/**
	 * Query nsort.
	 * 
	 * @param nsortId
	 *            the nsort id
	 * @return the nsort
	 */
	public abstract Nsort getNsort(final Long nsortId);

	// 得到其他的相关小类
	/**
	 * Query nsort list.
	 * 
	 * @param sortId
	 *            the sort id
	 * @param nsortId
	 *            the nsort id
	 * @return the list
	 */
	public abstract List<Nsort> getOtherNsortList(final Long sortId, final Long nsortId);

	/**
	 * 得到二级商品分类.
	 *
	 * @param sortId the sort id
	 * @return the nsort list
	 */
	public abstract List<Nsort> getNsortList(final Long sortId);
	
	/**
	 * 得到三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @return the sub nsort by sort id
	 */
	public List<Nsort> getSubNsortBySortId(final Long nsortId);
	
	/**
	 * 得到三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @param nsortName the nsort name
	 * @return the sub nsort by sort id
	 */
	public List<Nsort> getSubNsortBySortId(final Long nsortId, String nsortName);

	// 得到相关二级小类
	/**
	 * Query othor nsort.
	 * 
	 * @param list
	 *            the list
	 * @return the list
	 */
	public abstract List<Nsort> getOthorNsort(List<Nsort> list);

	// 得到相关三级小类
	/**
	 * Query othor sub nsort.
	 * 
	 * @param InsortId
	 *            the insort id
	 * @param list
	 *            the list
	 * @return the list
	 */
	public abstract List<Nsort> getOthorSubNsort(Long InsortId, List<Nsort> list);

	/**
	 * Query nsort by sort id.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the list
	 */
	public abstract List<Nsort> getNsortBySortId(final Long sortId);

	/**
	 * Gets the navigation nsort.
	 * 
	 * @param userName
	 *            the user name
	 * @return the navigation nsort
	 */
	public abstract List<Nsort> getNavigationNsort(String userName);

	/**
	 * Update nsort.
	 * 
	 * @param nsort
	 *            the nsort
	 */
	public abstract void updateNsort(Nsort nsort);

	/**
	 * Delete nsort by id.
	 *
	 * @param id the id
	 */
	public abstract void deleteNsortById(Long id);

	/**
	 * Load n sorts.
	 *
	 * @param sortId the sort id
	 * @return the list
	 */
	public abstract List<KeyValueEntity> loadNSorts(Long sortId);

	/**
	 * Load sub n sorts.
	 *
	 * @param nsortId the nsort id
	 * @return the list
	 */
	public abstract List<KeyValueEntity> loadSubNSorts(Long nsortId);

	/**
	 * 根据二级分类找到对应的三级分类.
	 *
	 * @param subNsortId the sub nsort id
	 * @return the user name by nsort id
	 */
	public abstract String getUserNameByNsortId(Long subNsortId);

	/**
	 * Checks for child product.
	 *
	 * @param userName the user name
	 * @param id the id
	 * @param parentNsortId the parent nsort id
	 * @return true, if successful
	 */
	public abstract boolean hasChildProduct(String userName, Long id, Long parentNsortId);

}