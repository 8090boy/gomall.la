/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.process;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.RealPathUtil;
import com.legendshop.model.entity.Product;
import com.legendshop.spi.event.ProductThreadEventProcessor;


/**
 * 删除产品图片，包括所有的缩略图
 */
public class ProductDeleteFileProcessor extends ProductThreadEventProcessor<Product> {
	
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ProductDeleteFileProcessor.class);

	private Map<String, List<Integer>> scaleList;

	/* (non-Javadoc)
	 * @see com.legendshop.event.processor.AbstractProcessor#process(java.lang.Object)
	 */
	@Override
	public void process(Product product) {
		log.debug("ProductDeleteFileProcessor performed" );
		String picUrl = RealPathUtil.getBigPicRealPath() + "/" + product.getPic();
		// 删除原图
		log.debug("delete Big Image file {}", picUrl);
		FileProcessor.deleteFile(picUrl);
		//删除所有缩略图规格
		for (String scaleValue : scaleList.keySet()) {
			String smallPicUrl = RealPathUtil.getSmallPicRealPath() + "/" + scaleValue+ "/" + product.getPic();
			log.debug("delete small Image file {}", smallPicUrl);
			FileProcessor.deleteFile(smallPicUrl);
		}

	}

	
	
	public void setScaleList(Map<String, List<Integer>> scaleList) {
		this.scaleList = scaleList;
	}


}
