/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import com.legendshop.core.AttributeKeys;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.util.AppUtils;

/**
 * The Class AbstratShopDomainTag.
 */
public abstract class AbstratShopDomainTag extends LegendShopTag {

	/**
	 * Gets the domain name from shop.
	 * 
	 * @param shopDetail
	 *            the shop detail
	 * @return the domain name from shop
	 */
	protected String getDomainNameFromShop(ShopDetailView shopDetail) {
		String defaultDomainName = shopDetail.getDomainName();
		if (defaultDomainName != null) {
			// add prefix
			defaultDomainName = "http://www." + defaultDomainName;
		}
		return defaultDomainName;
	}

	/**
	 * Gets the domain name.
	 * 
	 * @param shopName
	 *            the shop name
	 * @return the domain name
	 */
	protected String getDomainName(String shopName) {
		if (AppUtils.isBlank(shopName)) {
			return (String) pageContext().getServletContext().getAttribute(AttributeKeys.DOMAIN_NAME);
		} else {
			return (String) pageContext().getServletContext().getAttribute(AttributeKeys.DOMAIN_NAME) + "/shop/" + shopName;
		}
	}
}
