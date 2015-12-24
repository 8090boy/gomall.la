/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.model.entity.ShopDetailView;

/**
 * 用户每个request的数据，用完即清除.
 * 
 * 在Filter中初始化
 */
public class UserDataPerRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -858303753566417577L;

	/** 用户当前所位于的商城. */
	private ShopDetailView shopDetail;

	/** 前台风格. */
	private String frontType;

	/** 后台风格. */
	private String endType;

	private final HttpServletRequest request;

	private final HttpServletResponse response;

	/**
	 * Instantiates a new user data per request.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	public UserDataPerRequest(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * Gets the shop detail.
	 * 
	 * @return the shop detail
	 */
	public ShopDetailView getShopDetail() {
		return shopDetail;
	}

	/**
	 * Sets the shop detail.
	 * 
	 * @param shopDetail
	 *            the new shop detail
	 */
	public void setShopDetail(ShopDetailView shopDetail) {
		this.shopDetail = shopDetail;
	}

	/**
	 * Gets the front type.
	 * 
	 * @return the front type
	 */
	public String getFrontType() {
		return frontType;
	}

	/**
	 * Sets the front type.
	 * 
	 * @param frontEndStyle
	 *            the new front type
	 */
	public void setFrontType(String frontType) {
		this.frontType = frontType;
	}

	/**
	 * Gets the end type.
	 * 
	 * @return the end type
	 */
	public String getEndType() {
		return endType;
	}

	/**
	 * Sets the end type.
	 * 
	 * @param backEndStyle
	 *            the new end type
	 */
	public void setEndType(String endType) {
		this.endType = endType;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
