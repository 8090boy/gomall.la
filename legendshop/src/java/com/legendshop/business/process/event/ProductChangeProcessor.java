/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.process.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.UserManager;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.LegendFilter;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.event.processor.BaseProcessor;
import com.legendshop.model.entity.GroupProduct;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.UserEvent;
import com.legendshop.util.AppUtils;

/**
 * The Class ProductChangeProcessor.
 * @deprecated
 */
public class ProductChangeProcessor extends BaseProcessor<UserEvent> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ProductChangeProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.event.processor.AbstractProcessor#process(java.lang.Object
	 * )
	 */
	@Override
	public void process(UserEvent userEvent) {
		Object obj = userEvent.getEntity();
		String realPath = LegendFilter.HTML_PATH;
		if (AppUtils.isBlank(UserManager.getUserName())) {
			return;
		}
		if (PropertiesUtil.getObject(SysParameterEnum.STATIC_PAGE_SUPPORT, Boolean.class)) {
			if (obj instanceof Product) {
				Product product = (Product) obj;
				FileProcessor.deleteFile(realPath + product.getUserName() + "/views/" + product.getId() + ".html", false);
			}
			if (obj instanceof GroupProduct) {
				GroupProduct product = (GroupProduct) obj;
				FileProcessor.deleteFile(realPath + product.getUserName() + "/views/" + product.getId() + ".html", false);
				FileProcessor.deleteFile(realPath + product.getUserName() + "group/views/" + product.getId() + ".html", false);
			}
		}
	}

}
