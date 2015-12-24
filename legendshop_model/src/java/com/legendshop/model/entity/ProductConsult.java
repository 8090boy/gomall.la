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

/**
 * The Class ProductConsult.
 */
public class ProductConsult implements BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2645969133712434418L;

	/** The cons id. */
	private Long consId;
	
	//咨询类型
	/** The point type. */
	private Integer pointType;
	
	/** The prod id. */
	private Long prodId;
	
	/** The prod name. */
	private String prodName;

	/** The user id. */
	private String userId;

	/** The user name. */
	private String userName;

	/** The content. */
	private String content;

	/** The answer. */
	private String answer;

	/** The rec date. */
	private Date recDate;

	/** The postip. */
	private String postip;

	/** The answertime. */
	private Date answertime;
	
	/** The ask user name. */
	private String askUserName;
	
	/** The ans user name. */
	private String ansUserName;
	
	/** The start time. */
	private Date startTime;
	
	/** The end date. */
	private Date endTime;
	
	//用户等级名称
	/** The grade name. */
	private String gradeName;
	
	/** The replyed. */
	private Integer replyed;

	/**
	 * Instantiates a new product consult.
	 */
	public ProductConsult() {
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

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
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
	 * Gets the answer.
	 * 
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Sets the answer.
	 * 
	 * @param answer
	 *            the new answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * Gets the rec date.
	 * 
	 * @return the rec date
	 */
	public Date getRecDate() {
		return recDate;
	}

	/**
	 * Sets the rec date.
	 * 
	 * @param recDate
	 *            the new rec date
	 */
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	/**
	 * Gets the postip.
	 * 
	 * @return the postip
	 */
	public String getPostip() {
		return postip;
	}

	/**
	 * Sets the postip.
	 * 
	 * @param postip
	 *            the new postip
	 */
	public void setPostip(String postip) {
		this.postip = postip;
	}

	/**
	 * Gets the answertime.
	 * 
	 * @return the answertime
	 */
	public Date getAnswertime() {
		return answertime;
	}

	/**
	 * Sets the answertime.
	 * 
	 * @param answertime
	 *            the new answertime
	 */
	public void setAnswertime(Date answertime) {
		this.answertime = answertime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.model.entity.BaseEntity#getId()
	 */
	public Serializable getId() {
		return consId;
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

	/**
	 * Gets the ask user name.
	 *
	 * @return the askUserName
	 */
	public String getAskUserName() {
		return askUserName;
	}

	/**
	 * Sets the ask user name.
	 *
	 * @param askUserName the askUserName to set
	 */
	public void setAskUserName(String askUserName) {
		this.askUserName = askUserName;
	}

	/**
	 * Gets the ans user name.
	 *
	 * @return the ansUserName
	 */
	public String getAnsUserName() {
		return ansUserName;
	}

	/**
	 * Sets the ans user name.
	 *
	 * @param ansUserName the ansUserName to set
	 */
	public void setAnsUserName(String ansUserName) {
		this.ansUserName = ansUserName;
	}

	/**
	 * Gets the prod name.
	 *
	 * @return the prodName
	 */
	public String getProdName() {
		return prodName;
	}

	/**
	 * Sets the prod name.
	 *
	 * @param prodName the prodName to set
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the cons id.
	 *
	 * @return the consId
	 */
	public Long getConsId() {
		return consId;
	}

	/**
	 * Sets the cons id.
	 *
	 * @param consId the consId to set
	 */
	public void setConsId(Long consId) {
		this.consId = consId;
	}

	/**
	 * Gets the grade name.
	 *
	 * @return the gradeName
	 */
	public String getGradeName() {
		return gradeName;
	}

	/**
	 * Sets the grade name.
	 *
	 * @param gradeName the gradeName to set
	 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	/**
	 * Gets the point type.
	 *
	 * @return the pointType
	 */
	public Integer getPointType() {
		return pointType;
	}

	/**
	 * Sets the point type.
	 *
	 * @param pointType the pointType to set
	 */
	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}

	/**
	 * Gets the replyed.
	 *
	 * @return the replyed
	 */
	public Integer getReplyed() {
		return replyed;
	}

	/**
	 * Sets the replyed.
	 *
	 * @param replyed the replyed to set
	 */
	public void setReplyed(Integer replyed) {
		this.replyed = replyed;
	}



}
