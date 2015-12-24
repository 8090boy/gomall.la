/*
 * LegendShop 多用户商城系统
 * 
 *  版权所有, 并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.util.Date;

import com.legendshop.model.ModelUtil;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品对象.
 */
public class Product extends AbstractProduct {

	private GroupProduct groupProduct;
	private static final long serialVersionUID = -7571396124663475715L;
	public Product() { 	}

 
	public Product(Long prodId, Long sortId, Long nsortId, Long subNsortId, String prodName, String pic) {
		this.prodId = prodId;
		this.sortId = sortId;
		this.nsortId = nsortId;
		this.subNsortId = subNsortId;
		this.name = prodName;
		this.pic = pic;
	}

	public Product(Long prodId, String name, Double cash, String userId, Integer cityid, Integer provinceid, Integer areaid,
			Date recDate, String content, Integer buys, Integer commentNum) {
		this.prodId = prodId;
		this.name = name;
		this.cash = cash;
		this.userId = userId;
		this.cityid = cityid;
		this.provinceid = provinceid;
		this.areaid = areaid;
		this.recDate = recDate;
		this.content = content;
		this.buys = buys;
		this.commentNum = commentNum;
	}

 
	public Product(Long prodId, Long sortId, Long nsortId, Long subNsortId, String prodName) {
		this.prodId = prodId;
		this.sortId = sortId;
		this.nsortId = nsortId;
		this.subNsortId = subNsortId;
		this.name = prodName;
	}

 
	public Product(Long prodId, Long sortId, Long nsortId, Long subNsortId, String prodName, String pic, Double price, Double cash,
			Double proxyPrice, Integer views, Integer buys, Integer comments) {
		this.prodId = prodId;
		this.sortId = sortId;
		this.nsortId = nsortId;
		this.subNsortId = subNsortId;
		this.name = prodName;
		this.pic = pic;
		this.price = price;
		this.cash = cash;
		this.proxyPrice = proxyPrice;
		this.views = views;
		this.buys = buys;
		this.comments = comments;
	}

 
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" [prodId=").append(prodId).append(", sortId=").append(sortId).append(", nsortId=").append(nsortId)
				.append(", name=").append(name).append(", userName=").append(userName).append(",points=").append(points).append("] ");
		return sb.toString();
	}

	/**
	 * Gets the group product.
	 * 
	 * @return the group product
	 */
	public GroupProduct getGroupProduct() {
		return groupProduct;
	}

	/**
	 * Sets the group product.
	 * 
	 * @param groupProduct
	 *            the new group product
	 */
	public void setGroupProduct(GroupProduct groupProduct) {
		this.groupProduct = groupProduct;
	}
	
	/**
	 * 验证对象是否满足需求
	 */
	public void validate(){
		ModelUtil util = new ModelUtil();
		util.isNotNull(this.getSortId(),"sortId");
		if(ProductTypeEnum.PRODUCT.value().equals(this.getProdType())){
			util.isNotNull(this.getNsortId(), "nsortId");
			util.isNotNull(this.getSubNsortId(), "subNsort");
		}
		util.isNotNull(this.getPic(), "Pic");
		util.isNotNull(this.getPic(), "Pic");
//		util.isNotNull(this.getUserId(), "userId");
//		util.isNotNull(this.getUserName(), "userName");
		util.isNotNull(this.getContent(), "Content");

		
	}

}