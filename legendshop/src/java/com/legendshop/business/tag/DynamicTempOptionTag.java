/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.tag;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.legendshop.core.tag.OptionTag;
import com.legendshop.spi.service.ProductService;
import com.legendshop.util.AppUtils;

/**
 * 商品动态属性的 Select 元素生成器.
 */
public class DynamicTempOptionTag extends OptionTag {

	/** The log. */
	private static Log log = LogFactory.getLog(DynamicTempOptionTag.class);

	private Long sortId;

	/** The sort service. */
	private ProductService productService;

	/**
	 * Instantiates a new sort tag.
	 */
	public DynamicTempOptionTag() {
		if (productService == null) {
			productService = (ProductService) getBean("productService");
		}
	}

	@Override
	public List<?> retrieveData(String type, String userName) {
		if (AppUtils.isBlank(type)) {
			return null;
		}
		Integer dynTempType = Integer.valueOf(type);
		if (AppUtils.isBlank(sortId)) {
			return productService.getDynamicTemp(dynTempType, shopName);
		} else {
			return productService.getDynamicTemp(dynTempType, sortId, userName);
		}
	}

	/**
	 * @return the sortId
	 */
	public Long getSortId() {
		return sortId;
	}

	/**
	 * @param sortId
	 *            the sortId to set
	 */
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

}
