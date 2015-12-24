package com.legendshop.core.security.model;

import java.io.Serializable;

public class Resource implements Serializable {

	private static final long serialVersionUID = -6474624754451676966L;

	private Integer id;
	
	private String name;
	
	private String resType;
	
	private String resString;
	
	private String descn;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the resType
	 */
	public String getResType() {
		return resType;
	}

	/**
	 * @param resType the resType to set
	 */
	public void setResType(String resType) {
		this.resType = resType;
	}

	/**
	 * @return the resString
	 */
	public String getResString() {
		return resString;
	}

	/**
	 * @param resString the resString to set
	 */
	public void setResString(String resString) {
		this.resString = resString;
	}

	/**
	 * @return the descn
	 */
	public String getDescn() {
		return descn;
	}

	/**
	 * @param descn the descn to set
	 */
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
