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
import com.legendshop.spi.service.SortService;

/**
 * 商品分类的 Select 元素生成器.
 */
public class SortOptionTag extends OptionTag {
	/** The log. */
	private static Log log = LogFactory.getLog(SortOptionTag.class);

	/** The sort service. */
	private SortService sortService;

	/**
	 * Instantiates a new sort tag.
	 */
	public SortOptionTag() {
		if (sortService == null) {
			sortService = (SortService) getBean("sortService");
		}
	}

	@Override
	public List<?> retrieveData(String type, String userName) {
		return sortService.getSort(shopName, type, false);
	}

}
