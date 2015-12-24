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
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.model.ProdSearchArgs;
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.dynamic.Model;
import com.legendshop.model.entity.DynamicTemp;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductDetail;
import com.legendshop.model.entity.RelProduct;

/**
 * The Interface ProductDao.
 */
public interface ProductDao extends BaseDao {

	/**
	 * 获得推荐产品.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param total
	 *            the total
	 * @return the commend prod
	 */
	public abstract List<Product> getCommendProd(final String shopName, final int total);
	
	/**
	 * 全局的推荐商品.
	 *
	 * @param total the total
	 * @return the global commend prod
	 */
	public abstract List<Product> getGlobalCommendProd(final int total);

	/**
	 * 获得摸个产品分类下的推荐产品.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param sortId
	 *            the sort id
	 * @param total
	 *            the total
	 * @return the commend prod by sort
	 */
	public abstract List<Product> getCommendProdBySort(final String shopName, final Long sortId, final int total);

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

	/**
	 * Gets the newest prod.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param total
	 *            the total
	 * @return the newest prod
	 */
	public abstract List<Product> getNewestProd(final String shopName, final int total);
	
	/**
	 * Gets the global newest prod.
	 *
	 * @param total the total
	 * @return the global newest prod
	 */
	public abstract List<Product> getGlobalNewestProd(final int total);

	/**
	 * Gets the hotsale.
	 * 
	 * @param shopName
	 *            the shopName
	 * @return the hotsale
	 */
	public abstract List<Product> gethotsale(final String shopName);

	/**
	 * Gets the prod detail.
	 *
	 * @param curPageNO the cur page no
	 * @param sortId the sort id
	 * @param nsortId the nsort id
	 * @param subNsortId the sub nsort id
	 * @param isGlobal the is global
	 * @return the prod detail
	 */
	public abstract PageSupport getProdDetail(String curPageNO, Long sortId, Long nsortId, Long subNsortId , Boolean isGlobal);

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
	 * Gets the prod detail.
	 *
	 * @param curPageNO the cur page no
	 * @param sortId the sort id
	 * @param nsortId the nsort id
	 * @param subNsortId the sub nsort id
	 * @param args the args
	 * @return the prod detail
	 */
	public abstract PageSupport getProdDetail(String curPageNO, Long sortId, Long nsortId, Long subNsortId, ProdSearchArgs args);

	/**
	 * Gets the prod detail.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the prod detail
	 */
	public abstract ProductDetail getProdDetail(final Long prodId);

	/**
	 * Gets the prod detail.
	 *
	 * @param prodIds the prod ids
	 * @return the prod detail
	 */
	public abstract List<ProductDetail> getProdDetail(final List<Object> prodIds);

	/**
	 * Gets the search prod detail.
	 * 
	 * @param cq
	 *            the cq
	 * @return the search prod detail
	 */
	public abstract PageSupport getProdDetail(CriteriaQuery cq);

	/**
	 * Gets the prod detail.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the prod detail
	 */
	public abstract List<ProductDetail> getProdDetail(final Long[] prodId);

	// 热门商品
	/**
	 * Gets the hot on.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param sortId
	 *            the sort id
	 * @return the hot on
	 */
	public abstract List<Product> getHotOn(String shopName, Long sortId);

	// 查看某个商城的热门商品
	/**
	 * Gets the hot view prod.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param number
	 *            the number
	 * @return the hot view prod
	 */
	public abstract List<Product> getHotViewProd(String shopName, Integer number);

	/**
	 * 根据ID获取一个产品.
	 * 
	 * @param id
	 *            the id
	 * @return the product
	 */
	public abstract Product getProduct(Long id);

	// 更新商品查看数
	/**
	 * Update prod views.
	 * 
	 * @param prodId
	 *            the prod id
	 */
	public abstract void updateProdViews(Long prodId);

	/**
	 * 更新产品.
	 * 
	 * @param product
	 *            the product
	 */
	public abstract void updateProduct(Product product);
	
	
	/**
	 * 更新产品.
	 * 
	 * @param product
	 *            the product
	 */
	public abstract void updateOnly(Product product);

	/**
	 * Save product.
	 * 
	 * @param product
	 *            the product
	 * @return the long
	 */
	public abstract Long saveProduct(Product product);

	/**
	 * Delete prod by id.
	 *
	 * @param loginedUser the logined user
	 * @param prodId the prod id
	 * @return the string
	 */
	public abstract String deleteProd(String loginedUser, Long prodId);
	
	/**
	 * Delete prod.
	 *
	 * @param product the product
	 * @return the string
	 */
	public abstract void deleteProd(Product product);

	/**
	 * Load dynamic attribute.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the list
	 */
	public abstract List<Model> loadDynamicAttribute(Long prodId, String userName);

	/**
	 * Gets the prod.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the prod
	 */
	public abstract Product getProd(Long prodId, String userName);

	/**
	 * Gets the dynamic temp.
	 * 
	 * @param tempId
	 *            the temp id
	 * @return the dynamic temp
	 */
	public abstract DynamicTemp getDynamicTemp(Long tempId);

	/**
	 * Gets the dynamic temp.
	 * 
	 * @param cq
	 *            the cq
	 * @return the dynamic temp
	 */
	public abstract PageSupport getDynamicTemp(CriteriaQuery cq);

	/**
	 * Save dynamic temp.
	 * 
	 * @param dynamicTemp
	 *            the dynamic temp
	 * @return Id, if successful, Id will not null
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
	 * Delete dynamic temp.
	 * 
	 * @param temp
	 *            the temp
	 * @return true, if successful
	 */
	public abstract boolean deleteDynamicTemp(DynamicTemp temp);

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
	 * Gets the prod parameter.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the prod parameter
	 */
	public abstract String getProdParameter(Long prodId);

	/**
	 * Gets the hot comment.
	 * 
	 * @param shopName
	 *            the shop name
	 * @param sortId
	 *            the sort id
	 * @param maxNum
	 *            the max num
	 * @return the hot comment
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
	 * @param hql
	 *            the hql
	 * @return the dynamic temp
	 */
	public abstract PageSupport getDynamicTemp(SimpleHqlQuery hql);

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
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the usable product item
	 */
	public abstract List<Item> getUsableProductItem(Long prodId, String userName);
	
	/**
	 * Gets the used prod.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the used prod
	 */
	public abstract List<Item> getUsedProd(Long prodId, String userName);

	/**
	 * 得到全局的商品分类下的产品.
	 *
	 * @param curPageNO the cur page no
	 * @param sortId the sort id
	 * @return the global prod detail
	 */
	public abstract PageSupport getGlobalProdDetail(String curPageNO, Long sortId);

	/**
	 * 该商品是否被订购过
	 *
	 * @param prodId the prod id
	 * @return true, if successful
	 */
	public abstract boolean hasOrder(Long prodId);
}
