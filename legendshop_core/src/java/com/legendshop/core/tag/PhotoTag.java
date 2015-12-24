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
public class PhotoTag extends LegendShopTag {

	/** 原来的大图片. */
	private String item;

	private String imagePathPrefix;

	public PhotoTag() {
		imagePathPrefix = PropertiesUtil.getPhotoPathPrefix();
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
		String path = new StringBuilder(64).append(this.request().getContextPath()).append(imagePathPrefix).append(this.item)
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
}
