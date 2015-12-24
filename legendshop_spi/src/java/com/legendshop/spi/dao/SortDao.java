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
import com.legendshop.model.entity.Brand;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.Sort;

/**
 * 商品分类Dao.
 */
public interface SortDao extends BaseDao {


	/**
	 * Gets the sort.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the sort
	 */
	public abstract Sort getSort(Long sortId);


	/**
	 * Delete sort by id.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract void deleteSortById(Long id);

	/**
	 * Update sort.
	 * 
	 * @param sort
	 *            the sort
	 */
	public abstract void updateSort(Sort sort);

	/**
	 * Save sort.
	 * 
	 * @param sort
	 *            the sort
	 * @return the long
	 */
	public abstract Long saveSort(Sort sort);

	/**
	 * Query product by sort id.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the list
	 */
	public abstract List<Product> getProductBySortId(Long sortId);

	/**
	 * Query nsort by sort id.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the list
	 */
	public abstract List<Nsort> getNsortBySortId(Long sortId);
	
	
	/**
	 * Gets the nsort by sort id.
	 *
	 * @param sortId the sort id
	 * @param nsortName the nsort name
	 * @return the nsort by sort id
	 */
	public abstract List<Nsort> getNsortBySortId(Long sortId,String nsortName);

	/**
	 * Delete sort.
	 * 
	 * @param sort
	 *            the sort
	 */
	public abstract void deleteSort(Sort sort);

	/**
	 * Gets the sort.
	 * 
	 * @param name
	 *            the name
	 * @param sortType
	 *            the sort type
	 * @param headerMenu
	 *            the header menu
	 * @param navigationMenu
	 *            the navigation menu
	 * @param loadAll
	 *            the load all
	 * @return the sort
	 */
	public abstract List<Sort> getSort(String name, String sortType, Integer headerMenu, Integer navigationMenu, Boolean loadAll);
	
	

	/**
	 * 根据名称和类型找到对应的商品分类
	 * 发布产品是选择分类.
	 *
	 * @param userName the user name
	 * @param sortType the sort type
	 * @param sortName the sort name
	 * @return the sort
	 */
	public abstract List<Sort> getSort( String userName,  String sortType,  String sortName); 

	/**
	 * Gets the brand list.
	 * 
	 * @param sortId
	 *            the sort id
	 * @return the brand list
	 */
	public abstract List<Brand> getBrandList(Long sortId);


	/**
	 * Checks for child nsort.
	 *
	 * @param sortId the sort id
	 * @return true, if successful
	 */
	public abstract boolean hasChildNsort(Long sortId);


	/**
	 * Checks for child product.
	 *
	 * @param userName the user name
	 * @param sortId the sort id
	 * @return true, if successful
	 */
	public abstract boolean hasChildProduct(String userName, Long sortId);

}