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
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.entity.Brand;

/**
 * 品牌服务.
 */
public interface BrandService {

	/**
	 * List.
	 * 
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<Brand> getBrand(String userName);

	/**
	 * Load.
	 * 
	 * @param id
	 *            the id
	 * @return the brand
	 */
	public abstract Brand getBrand(Long id);

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
	 * @param brand
	 *            the brand
	 * @return the long
	 */
	public abstract Long save(Brand brand);

	/**
	 * Update.
	 * 
	 * @param brand
	 *            the brand
	 */
	public abstract void update(Brand brand);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getDataByCriteriaQuery(CriteriaQuery cq);

	/**
	 * Save brand item.
	 * 
	 * @param idList
	 *            the id list
	 * @param nsortId
	 *            the nsort id
	 * @param userName
	 *            the user name
	 * @return the string
	 */
	public abstract String saveBrandItem(List<String> idList, Long nsortId, String userName);

	/**
	 * Save brand item.
	 *
	 * @param idJson the id json
	 * @param nameJson the name json
	 * @param nsortId the nsort id
	 * @param userName the user name
	 * @return the string
	 */
	public abstract String saveBrandItem(String idJson, String nameJson, Long nsortId, String userName);

	/**
	 * Gets the usable brand by name.
	 *
	 * @param nsortId the nsort id
	 * @param userName the user name
	 * @param brandName the brand name
	 * @return the usable brand by name
	 */
	public abstract List<Item> getUsableBrandByName(Long nsortId, String userName, String brandName);

	/**
	 * Gets the usable brand.
	 *
	 * @param nsortId the nsort id
	 * @param userName the user name
	 * @return the usable brand
	 */
	public abstract List<Item> getUsableBrand(Long nsortId, String userName);

	/**
	 * Gets the used brand.
	 *
	 * @param nsortId the nsort id
	 * @param userName the user name
	 * @return the used brand
	 */
	public abstract List<Item> getUsedBrand(Long nsortId, String userName);


	/**
	 * Load brand by sub sort id.
	 *
	 * @param subNsortId the sub nsort id
	 * @param userName the user name
	 * @return the list
	 */
	public abstract List<KeyValueEntity> loadBrandEntityBySubSortId(Long subNsortId,String userName);
	
	/**
	 * 装载三级分类下的品牌.
	 *
	 * @param subNsortId the sub nsort id
	 * @param userName the user name
	 * @return the list
	 */
	public abstract List<Brand> loadBrandBySubSortId(Long subNsortId,String userName);

	/**
	 * 装载三级分类下的品牌.
	 *
	 * @param subNsortId the sub nsort id
	 * @param shopName the shop name
	 * @param brandName the brand name
	 * @return the list
	 */
	public abstract List<Brand> loadBrandByName(Long subNsortId, String shopName, String brandName);

	/**
	 * Checks for child product.
	 *
	 * @param userName the user name
	 * @param brandId the brand id
	 * @return true, if successful
	 */
	public abstract boolean hasChildProduct(String userName, Long brandId);

}