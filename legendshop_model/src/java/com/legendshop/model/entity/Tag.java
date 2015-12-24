/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 标签类，包括对应的产品分类和新闻分类，便于在页面中显示对应的产品分类和新闻等信息，
 * 在产品中要设置"精品推荐"属性，在新闻中要设置“高亮”属性。.
 */
public class Tag implements BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8233024988103164755L;

	/** The tag id. */
	private Long tagId;

	/** The name. */
	private String name;

	/** The sort id. */
	private Long sortId;
	
	/** The sort. */
	private Sort sort;
	
	/** The sort name. */
	private String sortName;

	/** The news category id. */
	private Long newsCategoryId;
	
	/** The news category name. */
	private String newsCategoryName;
	
	/** The news category. */
	private List<NewsTitle>  newsTitles;

	/** The type. */
	private String type;

	/** The status. */
	private Integer status;

	/** The create time. */
	private Date createTime;

	/** The user id. */
	private String userId;

	/** The user name. */
	private String userName;
	
	/** The advertisement list. */
	private List<Advertisement> advertisementList;
	
	/** The commend prod list. */
	private List<Product> commendProdList;

	/**
	 * Instantiates a new tag.
	 */
	public Tag() {
	}

	/**
	 * Instantiates a new tag.
	 *
	 * @param tagId the tag id
	 * @param name the name
	 * @param sortName the sort name
	 * @param newsCategoryName the news category name
	 * @param type the type
	 * @param status the status
	 * @param createTime the create time
	 * @param userId the user id
	 * @param userName the user name
	 */
	public Tag(Long tagId,String name, String sortName, String newsCategoryName, String type, Integer status, Date createTime,String userId, String userName) {
		super();
		this.tagId = tagId;
		this.name = name;
		this.sortName = sortName;
		this.newsCategoryName = newsCategoryName;
		this.type = type;
		this.status = status;
		this.createTime = createTime;
		this.userId = userId;
		this.userName = userName ;
	}

	/**
	 * Gets the tag id.
	 * 
	 * @return the tag id
	 */
	public Long getTagId() {
		return tagId;
	}

	/**
	 * Sets the tag id.
	 * 
	 * @param tagId
	 *            the new tag id
	 */
	public void setTagId(Long tagId) {
		this.tagId = tagId;
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
	 * @param sortId
	 *            the new sort id
	 */
	public void setSortId(Long sortId) {
		this.sortId = sortId;
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
	 * Gets the news category id.
	 * 
	 * @return the news category id
	 */
	public Long getNewsCategoryId() {
		return newsCategoryId;
	}

	/**
	 * Sets the news category id.
	 * 
	 * @param newsCategoryId
	 *            the new news category id
	 */
	public void setNewsCategoryId(Long newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}

	/**
	 * Gets the news category name.
	 *
	 * @return the news category name
	 */
	public String getNewsCategoryName() {
		return newsCategoryName;
	}

	/**
	 * Sets the news category name.
	 *
	 * @param newsCategoryName the new news category name
	 */
	public void setNewsCategoryName(String newsCategoryName) {
		this.newsCategoryName = newsCategoryName;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @param status
	 *            the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the creates the time.
	 * 
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 * 
	 * @param createTime
	 *            the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
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

	/* (non-Javadoc)
	 * @see com.legendshop.model.entity.BaseEntity#getId()
	 */
	public Serializable getId() {
		return tagId;
	}

	/**
	 * Gets the sort.
	 *
	 * @return the sort
	 */
	public Sort getSort() {
		return sort;
	}

	/**
	 * Sets the sort.
	 *
	 * @param sort the new sort
	 */
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	/**
	 * Gets the news titles.
	 *
	 * @return the news titles
	 */
	public List<NewsTitle> getNewsTitles() {
		return newsTitles;
	}

	/**
	 * Sets the news titles.
	 *
	 * @param newsTitles the new news titles
	 */
	public void setNewsTitles(List<NewsTitle> newsTitles) {
		this.newsTitles = newsTitles;
	}

	/**
	 * Gets the advertisement list.
	 *
	 * @return the advertisement list
	 */
	public List<Advertisement> getAdvertisementList() {
		return advertisementList;
	}

	/**
	 * Sets the advertisement list.
	 *
	 * @param advertisementList the new advertisement list
	 */
	public void setAdvertisementList(List<Advertisement> advertisementList) {
		this.advertisementList = advertisementList;
	}

	/**
	 * Gets the commend prod list.
	 *
	 * @return the commend prod list
	 */
	public List<Product> getCommendProdList() {
		return commendProdList;
	}

	/**
	 * Sets the commend prod list.
	 *
	 * @param commendProdList the new commend prod list
	 */
	public void setCommendProdList(List<Product> commendProdList) {
		this.commendProdList = commendProdList;
	}

}
