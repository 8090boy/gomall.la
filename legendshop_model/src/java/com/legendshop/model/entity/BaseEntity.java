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
 * Entity的基类，Id用于Cache中找到主键.
 */
public interface BaseEntity extends Serializable {
	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Serializable getId();


}
