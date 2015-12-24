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
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.entity.DynamicTemp;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductDetail;
import com.legendshop.model.entity.RelProduct;

/**
 * The Interface ProductService.
 */
public interface ProductService extends BaseService {

	/**
	 * Find product.
	 * 
	 * @param hql
	 *            the hql
	 * @return the page support
	 */
	public abstract PageSupport getProductList(HqlQuery hql);

	/**
	 * Find product.
	 * 
	 * @param cq
	 *            the cq
	 * @return the page support
	 */
	public abstract PageSupport getProductList(CriteriaQuery cq);

	/**
	 * 根据ID获取一个产品.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the product
	 */
	public abstract Product getProductById(Long prodId);

	/**
	 * 更新产品.
	 * 
	 * @param product
	 *            the product
	 * @param origin
	 *            the origin
	 */
	public abstract void updateProduct(Product product, Product origin);

	/**
	 * 保存产品.
	 * 
	 * @param product
	 *            the product
	 * @param prodType
	 *            the prod type
	 * @return 产品ID
	 */
	public abstract Long saveProduct(Product product, String prodType);

	// 商品动态属性
	/**
	 * Load attributeprod attribute.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	public abstract String getAttributeprodAttribute(Long prodId);

	// 得到商品
	/**
	 * Load prod.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the product
	 */
	public abstract Product getProd(Long prodId, String userName);

	// 商品动态参数
	/**
	 * Load prod parameter.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	public abstract String getProdParameter(Long prodId);

	/**
	 * Save dynamic temp.
	 * 
	 * @param dynamicTemp
	 *            the dynamic temp
	 * @return true, if successful
	 */
	public abstract Long saveDynamicTemp(DynamicTemp dynamicTemp);

	/**
	 * Update dynamic temp.
	 * 
	 * @param dynamicTemp
	 *            the dynamic temp
	 * @return true, if successful
	 */
	public abstract boolean updateDynamicTemp(DynamicTemp dynamicTemp);

	/**
	 * Load dynamic temp.
	 * 
	 * @param tempId
	 *            the temp id
	 * @return the dynamic temp
	 */
	public abstract DynamicTemp getDynamicTemp(Long tempId);

	/**
	 * Gets the dynamic temp.
	 * 
	 * @param type
	 *            the type
	 * @param userName
	 *            the user name
	 * @return the dynamic temp
	 */
	public abstract List<DynamicTemp> getDynamicTemp(Integer type, String userName);

	/**
	 * Gets the dynamic temp.
	 * 
	 * @param type
	 *            the type
	 * @param sortId
	 *            the sort id
	 * @param userName
	 *            the user name
	 * @return the dynamic temp
	 */
	public abstract List<DynamicTemp> getDynamicTemp(Integer type, Long sortId, String userName);

	/**
	 * Delete dynamic temp.
	 * 
	 * @param tempId
	 *            the temp id
	 * @param userName
	 *            the user name
	 * @return true, if successful
	 */
	public abstract int deleteDynamicTemp(Long tempId, String userName);

	/**
	 * Save prod item.
	 * 
	 * @param idList
	 *            the id list
	 * @param nameList
	 *            the name list
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the string
	 */
	public abstract String saveProdItem(List<String> idList, List<String> nameList, Long prodId, String userName);

	/**
	 * 更新商品.
	 * 
	 * @param product
	 *            the product
	 */
	public abstract void updateProd(Product product);

	/**
	 * Gets the prod detail.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the prod detail
	 */
	public abstract ProductDetail getProdDetail(final Long prodId);

	/**
	 * Gets the releation prod.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param prodId
	 *            the prod id
	 * @param total
	 *            the total
	 * @return the releation prod
	 */
	public abstract List<Product> getReleationProd(final String shopName, final Long prodId, final int total);

	/**
	 * Gets the releation prod.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the releation prod
	 */
	public abstract List<RelProduct> getReleationProd(Long prodId, String userName);

	// 更新商品查看数
	/**
	 * Update prod views.
	 * 
	 * @param prodId
	 *            the prod id
	 */
	public abstract void updateProdViews(Long prodId);

	/**
	 * hot sell product.
	 * 
	 * @param shopName
	 *            the shop name
	 * @return the hot sale
	 */
	public List<Product> getHotSale(String shopName);

	/**
	 * Gets the hot on.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param sortId
	 *            the sort id
	 * @return the hot on
	 */
	public List<Product> getHotOn(String shopName, Long sortId);

	/**
	 * Gets the hot view prod.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param maxNum
	 *            the max num
	 * @return the hot view prod
	 */
	public abstract List<Product> getHotViewProd(String shopName, int maxNum);

	/**
	 * Gets the prod detail.
	 * 
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @return the prod detail
	 */
	public abstract PageSupport getProdDetail(String curPageNO, Long sortId);

	/**
	 * Product gallery.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the action forward
	 */
	public abstract String getProductGallery(HttpServletRequest request, HttpServletResponse response, Long prodId);

	/**
	 * Gets the hot comment list.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param sortId
	 *            the sort id
	 * @param maxNum
	 *            the max num
	 * @return the hot comment list
	 */
	public abstract List<Product> getHotComment(String shopName, Long sortId, int maxNum);

	/**
	 * Gets the hot recommend.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param sortId
	 *            the sort id
	 * @param maxNum
	 *            the max num
	 * @return the hot recommend
	 */
	public abstract List<Product> getHotRecommend(String shopName, Long sortId, int maxNum);

	/**
	 * Gets the dynamic temp.
	 * 
	 * @param cq
	 *            the cq
	 * @return the dynamic temp
	 */
	public abstract PageSupport getDynamicTemp(CriteriaQuery cq);

	/**
	 * Gets the dynamic temp.
	 * 
	 * @param hql
	 *            the hql
	 * @return the dynamic temp
	 */
	public abstract PageSupport getDynamicTemp(SimpleHqlQuery hql);

	/**
	 * Visited prod. 商品查看历史
	 *
	 * @param request the request
	 * @return the string
	 */
	public abstract List<ProductDetail> getVisitedProd(HttpServletRequest request);

	/**
	 * Visited prod. 商品推荐算法
	 *
	 * @param prodId the prod id
	 * @return the string
	 */
	public abstract List<ProductDetail> getRecommendProd(Long prodId);

	/**
	 * Delete dynamic temp.
	 *
	 * @param temp the temp
	 * @return true, if successful
	 */
	public boolean deleteDynamicTemp(DynamicTemp temp);

	/**
	 * Delete.
	 *
	 * @param loginedUser the logined user
	 * @param prodId the prod id
	 * @return the string
	 */
	public abstract String delete(String loginedUser, Long prodId);


	/**
	 * Save rel prod.
	 *
	 * @param idJson the id json
	 * @param nameJson the name json
	 * @param prodId the prod id
	 * @param userName the user name
	 * @return the string
	 */
	public abstract String saveRelProd(String idJson, String nameJson, Long prodId, String userName);


	/**
	 * Gets the usable product item by name.
	 *
	 * @param prodId the prod id
	 * @param userName the user name
	 * @param prodName the prod name
	 * @return the usable product item by name
	 */
	public abstract List<Item> getUsableProductItemByName(Long prodId, String userName, String prodName);

	/**
	 * Gets the usable product item.
	 *
	 * @param prodId the prod id
	 * @param userName the user name
	 * @return the usable product item
	 */
	public abstract List<Item> getUsableProductItem(Long prodId, String userName);

	/**
	 * Gets the used product item.
	 *
	 * @param prodId the prod id
	 * @param userName the user name
	 * @return the used product item
	 */
	public abstract List<Item> getUsedProductItem(Long prodId, String userName);
}