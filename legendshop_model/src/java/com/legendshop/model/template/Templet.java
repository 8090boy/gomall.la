/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.template;

import java.io.Serializable;
import java.util.List;

import com.legendshop.model.StatusKeyValueEntity;

/**
 * The Class Templet.
 * 系统支持的模版
 */
public class Templet implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7955507406362239884L;

	/** The id. */
	private String id;
	
	/** The languages. 模版所支持的语言*/
	private List<StatusKeyValueEntity> languages;
	
	/** The styles 模版风格. */
	private List<StatusKeyValueEntity> styles;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the languages.
	 *
	 * @return the languages
	 */
	public List<StatusKeyValueEntity> getLanguages() {
		return languages;
	}

	/**
	 * Sets the languages.
	 *
	 * @param languages the languages to set
	 */
	public void setLanguages(List<StatusKeyValueEntity> languages) {
		this.languages = languages;
	}

	/**
	 * Gets the styles.
	 *
	 * @return the styles
	 */
	public List<StatusKeyValueEntity> getStyles() {
		return styles;
	}

	/**
	 * Sets the styles.
	 *
	 * @param styles the styles to set
	 */
	public void setStyles(List<StatusKeyValueEntity> styles) {
		this.styles = styles;
	}
	
}
