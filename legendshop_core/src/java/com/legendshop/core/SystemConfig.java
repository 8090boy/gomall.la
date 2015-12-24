/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core;

import java.io.Serializable;

/**
 * 系统配置
 */
public class SystemConfig implements Serializable{

	private static final long serialVersionUID = 460061477804527117L;
	//商城名字
	private String shopName;
	//商城地址
	private String url;
	//商城logo
	private String logo;
	//备案信息
	private String icpInfo;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIcpInfo() {
		return icpInfo;
	}
	public void setIcpInfo(String icpInfo) {
		this.icpInfo = icpInfo;
	}


}
