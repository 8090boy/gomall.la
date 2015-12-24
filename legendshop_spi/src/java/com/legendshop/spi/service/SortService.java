/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.ProdSearchArgs;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Sort;

/**
 * 商品分类服务.
 */
public interface SortService extends BaseService {

	/**
	 * Query sort.
	 * 
	 * @param id
	 *            the id
	 * @return the sort
	 */
	public abstract Sort getSortById(Long id);

	/**
	 * Delete sort.
	 * 
	 * @param sortId
	 *            the sort id
	 */
	public abstract void deleteSort(Long sortId);

	/**
	 * Save.
	 * 
	 * @param sort
	 *            the sort
	 * @return the long
	 */
	public abstract Long save(Sort sort);

	/**
	 * Update sort.
	 * 
	 * @param sort
	 *            the sort
	 */
	public abstract void updateSort(Sort sort);

	/**
	 * Gets the data by criteria query.
	 * 
	 * @param cq
	 *            the cq
	 * @return the data by criteria query
	 */
	public abstract PageSupport getSortList(CriteriaQuery cq);

	/**
	 * Gets the sort.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param loadAll
	 *            是否加载 nsort 1、由部分加载改为全部加载，在jsp中再做限制 2、只通过top.jsp获得sort，避免多处查找
	 * @return the sort
	 */
	public abstract List<Sort> getSort(String shopName, Boolean loadAll);

	/**
	 * Delete.
	 * 
	 * @param sort
	 *            the sort
	 */
	public abstract void delete(Sort sort);

	/**
	 * Gets the sort.
	 * 
	 * @param name
	 *            the name
	 * @param sortType
	 *            the sort type
	 * @param loadAll
	 *            the load all
	 * @return the sort
	 */
	public abstract List<Sort> getSort(String name, String sortType, Boolean loadAll);

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
	 * Gets the sec sort. 获得二级商品分类的商品
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sort
	 *            the sort
	 * @param nsortId
	 *            the nsort id
	 * @param subNsortId
	 *            the sub nsort id
	 * @return the sec sort
	 */
	public abstract String getSecSort(HttpServletRequest request, HttpServletResponse response, Sort sort, Long nsortId,
			Long subNsortId);

	/**
	 * Parses the sort.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sort
	 *            the sort
	 * @return the string
	 */
	public abstract String parseSort(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sort sort);

	/**
	 * Parses the sort.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @param args
	 *            the args
	 * @return the string
	 */
	public abstract String parseSort(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long sortId,
			ProdSearchArgs args);

	/**
	 * Parses the sec sort. 处理二级分类的商品
	 *
	 * @param request the request
	 * @param response the response
	 * @param curPageNO the cur page no
	 * @param sortId the sort id
	 * @param nsortId the nsort id
	 * @param subNsortId the sub nsort id
	 * @param args the args
	 * @return the string
	 */
	public abstract String parseSecSort(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long sortId,
			Long nsortId, Long subNsortId, ProdSearchArgs args);

	/**
	 * Gets the sort and brand.
	 * 
	 * @param id
	 *            the id
	 * @return the sort and brand
	 */
	public abstract Sort getSortAndBrand(Long id);

	/**
	 * Sort list.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sort
	 *            the sort
	 * @return the string
	 */
	public abstract String sortList(HttpServletRequest request, HttpServletResponse response, Sort sort);

	/**
	 * 装载第一级商品分类.
	 *
	 * @param shopName the shop name
	 * @param sortType the sort type
	 * @return the list
	 */
	public abstract List<KeyValueEntity> loadSorts(String shopName, String sortType);

	/**
	 *  装载第二级商品分类.
	 *
	 * @param sortId the sort id
	 * @return the list
	 */
	public abstract List<KeyValueEntity> loadNSorts(Long sortId);

	/**
	 * 装载第三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @return the list
	 */
	public abstract List<KeyValueEntity> loadSubNSorts(Long nsortId);
	
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
	 * 装载第二级商品分类
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
	 * 装载第三级商品分类
	 *
	 * @param nsortId the nsort id
	 * @param nsortName the nsort name
	 * @return the sub nsort by sort id
	 */
	public abstract List<Nsort> getSubNsortBySortId(final Long nsortId, String nsortName);

}