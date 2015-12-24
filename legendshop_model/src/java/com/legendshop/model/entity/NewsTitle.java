/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */

public class NewsTitle implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5834756315130185151L;

	/** The news id. */
	private Long newsId;

    /** The news title. */
    private String newsTitle;

	/**
	 * Gets the news id.
	 *
	 * @return the news id
	 */
	public Long getNewsId() {
		return newsId;
	}

	/**
	 * Sets the news id.
	 *
	 * @param newsId the new news id
	 */
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	/**
	 * Gets the news title.
	 *
	 * @return the news title
	 */
	public String getNewsTitle() {
		return newsTitle;
	}

	/**
	 * Sets the news title.
	 *
	 * @param newsTitle the new news title
	 */
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}


}