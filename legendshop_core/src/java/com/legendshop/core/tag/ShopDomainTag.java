/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.service.ShopService;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;

/**
 * The Class ShopDomainTag.
 * 
 * 当前域名，支持独立域名
 */
public class ShopDomainTag extends AbstratShopDomainTag {

	/** The key. */
	private String shopName;

	/** The shop service. */
	private static ShopService shopService;

	/**
	 * Instantiates a new shop domain tag.
	 */
	public ShopDomainTag() {
		shopService = (ShopService) ContextServiceLocator.getInstance().getBean("shopDetailService");
	}

	/**
	 * Do tag.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws IOException {
		String defaultDomainName = null;
		// 支持独立域名
		if (PropertiesUtil.getObject(SysParameterEnum.INDEPEND_DOMAIN, Boolean.class)) {
			HttpServletRequest request = (HttpServletRequest) pageContext().getRequest();
			HttpServletResponse response = (HttpServletResponse) pageContext().getResponse();
			String currentName = this.shopName;

			if (AppUtils.isBlank(currentName)) {
				currentName = ThreadLocalContext.getCurrentShopName(request, response);
			}
			ShopDetailView shopDetail = null;
			if (AppUtils.isNotBlank(currentName)) {
				shopDetail = shopService.getShopDetailView(currentName);
			}
			if (shopDetail != null && AppUtils.isNotBlank(shopDetail.getDomainName())) {
				defaultDomainName = getDomainNameFromShop(shopDetail);
			} else {
				defaultDomainName = getDomainName(shopName);
			}
		} else {
			defaultDomainName = getDomainName(shopName);
		}
		this.write(defaultDomainName);
	}

	/**
	 * Sets the shop name.
	 * 
	 * @param shopName
	 *            the new shop name
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
