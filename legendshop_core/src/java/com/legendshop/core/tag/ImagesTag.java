/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.io.IOException;

import com.legendshop.core.helper.PropertiesUtil;

/**
 * The Class TemplateResourceTag.
 */
public class ImagesTag extends LegendShopTag {

	/** 用于缩略图. */
	private String item;

	/** Servlet 前缀. */
	private String imagePathPrefix;
	
	/** 缩略图尺寸,default is 0. */
	private int scale;

	/**
	 * Instantiates a new images tag.
	 */
	public ImagesTag() {
		imagePathPrefix = PropertiesUtil.getSmallImagePathPrefix();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws IOException {
		String path = new StringBuilder(64).append(this.request().getContextPath()).append(imagePathPrefix).append(scale).append("/").append(this.item)
				.toString();

		this.write(path);
	}

	/**
	 * Sets the item.
	 * 
	 * @param item
	 *            the resource to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
}
