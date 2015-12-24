/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.event;

import com.legendshop.core.UserManager;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.LegendFilter;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.Product;
import com.legendshop.util.AppUtils;

/**
 * The Class ProductThreadEventProcessor.
 *
 * @param <T> the generic type
 */
public abstract class ProductThreadEventProcessor<T> extends ThreadEventProcessor<T> {
	
	/** The real path. */
	private String realPath = LegendFilter.HTML_PATH;

	/**
	 * Process product.
	 *
	 * @param product the product
	 */
	public void clearProductStaticPage(Product product) {
		if (AppUtils.isBlank(UserManager.getUserName())) {
			return;
		}
		if (PropertiesUtil.getObject(SysParameterEnum.STATIC_PAGE_SUPPORT, Boolean.class)) {
			FileProcessor.deleteFile(realPath + product.getUserName() + "/views/" + product.getId() + ".html", false);
			FileProcessor.deleteFile(realPath + product.getUserName() + "/group/views/" + product.getId() + ".html", false);
		}
		
	}
	
}
