/**
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.util.List;

import com.legendshop.model.dynamic.Model;

/**
 * 产品动态属性模板.
 */
public class DynamicTemp implements NamedEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8083024182117947985L;
	
	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;
	
	/** The content. */
	private String content;
	
	/** The type. */
	private Integer type;
	
	/** The user name. */
	private String userName;
	
	/** The sort id. */
	private Long sortId;
	
	/** The sort name. */
	private String sortName;
	
	/** The status. */
	private Integer status;
	
	/** The mode list. */
	private List<Model> modelList;

	// Constructors

	/**
	 * default constructor.
	 */
	public DynamicTemp() {
	}

	/**
	 * Instantiates a new dynamic temp.
	 *
	 * @param id the id
	 * @param name the name
	 * @param content the content
	 * @param type the type
	 * @param userName the user name
	 * @param sortId the sort id
	 * @param status the status
	 * @param sortName the sort name
	 */
	public DynamicTemp(Long id, String name, String content, Integer type, String userName, Long sortId, Integer status, String sortName) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.type = type;
		this.userName = userName;
		this.sortId = sortId;
		this.status = status;
		this.sortName = sortName;
	}
	
	/**
	 * full constructor.
	 * 
	 * @param content
	 *            the content
	 * @param type
	 *            the type
	 * @param userName
	 *            the user name
	 */
	public DynamicTemp(String content, Integer type, String userName) {
		this.content = content;
		this.type = type;
		this.userName = userName;
	}

	// Property accessors

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param content
	 *            the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the sort id.
	 *
	 * @return the sort id
	 */
	public Long getSortId() {
		return sortId;
	}

	/**
	 * Sets the sort id.
	 *
	 * @param sortId the new sort id
	 */
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the sort name.
	 *
	 * @return the sort name
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * Sets the sort name.
	 *
	 * @param sortName the new sort name
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * Gets the model list.
	 *
	 * @return the modelList
	 */
	public List<Model> getModelList() {
		return modelList;
	}

	/**
	 * Sets the model list.
	 *
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}



}