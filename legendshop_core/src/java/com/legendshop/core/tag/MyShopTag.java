/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import javax.servlet.jsp.tagext.TagSupport;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.BusinessModeEnum;
import com.legendshop.core.service.ShopService;
import com.legendshop.util.ContextServiceLocator;

/**
 * 
 * 
 * 我的商城 如果我是商家就显示对应的信息
 */
public class MyShopTag extends TagSupport {

	private static final long serialVersionUID = -4870270970591649109L;
	/** The shop service. */
	private static ShopService shopService;

	/**
	 * Instantiates a new shop domain tag.
	 */
	public MyShopTag() {
		shopService = (ShopService) ContextServiceLocator.getInstance().getBean("shopDetailService");
	}

	public int doStartTag() {
		String currentName = UserManager.getUserName(pageContext.getSession());
		if (currentName != null) {
			Boolean c2cMode = BusinessModeEnum.C2C.name().equals(
					pageContext.getServletContext().getAttribute(AttributeKeys.BUSINESS_MODE));
			if (c2cMode) {
				if (shopService.isShopExists(currentName)) {
					return TagSupport.EVAL_BODY_INCLUDE;
				}
			}
		}
		return TagSupport.SKIP_BODY;
	}

}
