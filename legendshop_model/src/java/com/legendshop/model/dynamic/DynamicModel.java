/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.dynamic;

import java.io.Serializable;

/**
 * The Class DynamicModel.
 */
public class DynamicModel implements Serializable{
	
	/** The temp id. */
	private Long tempId;
	
	/** The prod id. */
	private Long prodId;
	
	/** The temp name. */
	private String tempName;
	
	/** The type. */
	private Integer type;
	
	/** The sort id. */
	private Long sortId;
	
	/** The model. */
	private Model[] model;
	
	/**
	 * Gets the temp id.
	 *
	 * @return the tempId
	 */
	public Long getTempId() {
		return tempId;
	}
	
	/**
	 * Sets the temp id.
	 *
	 * @param tempId the tempId to set
	 */
	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**
	 * Gets the sort id.
	 *
	 * @return the sortId
	 */
	public Long getSortId() {
		return sortId;
	}
	
	/**
	 * Sets the sort id.
	 *
	 * @param sortId the sortId to set
	 */
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public Model[] getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the model to set
	 */
	public void setModel(Model[] model) {
		this.model = model;
	}

	/**
	 * Gets the temp name.
	 *
	 * @return the tempName
	 */
	public String getTempName() {
		return tempName;
	}

	/**
	 * Sets the temp name.
	 *
	 * @param tempName the tempName to set
	 */
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	/**
	 * Gets the prod id.
	 *
	 * @return the prodId
	 */
	public Long getProdId() {
		return prodId;
	}

	/**
	 * Sets the prod id.
	 *
	 * @param prodId the prodId to set
	 */
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	

}
