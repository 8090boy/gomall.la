/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.util.Date;

import com.legendshop.model.ModelUtil;

/**
 * 产品评论表.
 */

public class ProductComment extends AbstractEntity implements BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3118765177322131488L;
	// Fields

	/** The id. */
	private Long id;
	
	/** The prod id. */
	private Long prodId;
	
	/** The owner name. */
	private String ownerName;
	
	/** The user name. */
	private String userName;
	
	/** The title. */
	private String title;
	
	/** The content. */
	private String content;
	
	/** The recDate. */
	private Date addtime;
	
	/** The postip. */
	private String postip;
	
	/** The score. */
	private Integer score;
	
	/** The reply content. */
	private String replyContent;
	
	/** The reply name. */
	private String replyName;
	
	/** The reply time. */
	private Date replyTime;
	
	/** The prod name. */
	private String prodName;
	
	/** 优点 *. */
	private String advtge;
	
	/** 缺点 *. */
	private String defect;
	
	/** 购买时间 *. */
	private Date buyTime;

	/**
	 * Instantiates a new product comment.
	 */
	public ProductComment(){
		
	}

	/**
	 * Instantiates a new product comment.
	 *
	 * @param id the id
	 * @param prodId the prod id
	 * @param ownerName the owner name
	 * @param userName the user name
	 * @param content the content
	 * @param addtime the addtime
	 * @param postip the postip
	 * @param score the score
	 * @param replyContent the reply content
	 * @param replyName the reply name
	 * @param replyTime the reply time
	 * @param prodName the prod name
	 */
	public ProductComment(Long id, Long prodId,String ownerName, String userName, String content,
			Date addtime, String postip, Integer score, String replyContent,
			String replyName, Date replyTime, String prodName) {
		super();
		this.id = id;
		this.prodId = prodId;
		this.ownerName = ownerName;
		this.userName = userName;
		this.content = content;
		this.addtime = addtime;
		this.postip = postip;
		this.score = score;
		this.replyContent = replyContent;
		this.replyName = replyName;
		this.replyTime = replyTime;
		this.prodName = prodName;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * Gets the prod id.
	 * 
	 * @return the prod id
	 */
	public Long getProdId() {
		return prodId;
	}

	/**
	 * Sets the prod id.
	 * 
	 * @param prodId
	 *            the new prod id
	 */
	public void setProdId(Long prodId) {
		this.prodId = prodId;
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
	 * Gets the recDate.
	 * 
	 * @return the recDate
	 */
	public Date getAddtime() {
		return this.addtime;
	}

	/**
	 * Sets the recDate.
	 *
	 * @param addtime the new addtime
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	/**
	 * Gets the postip.
	 * 
	 * @return the postip
	 */
	public String getPostip() {
		return this.postip;
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
	 * Gets the score.
	 * 
	 * @return the score
	 */
	public Integer getScore() {
		return this.score;
	}

	/**
	 * Sets the score.
	 * 
	 * @param score
	 *            the new score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * Gets the reply content.
	 * 
	 * @return the reply content
	 */
	public String getReplyContent() {
		return this.replyContent;
	}

	/**
	 * Sets the reply content.
	 * 
	 * @param replyContent
	 *            the new reply content
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	/**
	 * Gets the reply name.
	 * 
	 * @return the reply name
	 */
	public String getReplyName() {
		return this.replyName;
	}

	/**
	 * Sets the reply name.
	 * 
	 * @param replyName
	 *            the new reply name
	 */
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	/**
	 * Gets the reply time.
	 * 
	 * @return the reply time
	 */
	public Date getReplyTime() {
		return this.replyTime;
	}

	/**
	 * Sets the reply time.
	 * 
	 * @param replyTime
	 *            the new reply time
	 */
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	/**
	 * Gets the prod name.
	 * 
	 * @return the prod name
	 */
	public String getProdName() {
		return prodName;
	}

	/**
	 * Sets the prod name.
	 * 
	 * @param prodName
	 *            the new prod name
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	/**
	 * Gets the owner name.
	 * 
	 * @return the owner name
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * Sets the owner name.
	 * 
	 * @param ownerName
	 *            the new owner name
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the advtge.
	 *
	 * @return the advtge
	 */
	public String getAdvtge() {
		return advtge;
	}

	/**
	 * Sets the advtge.
	 *
	 * @param advtge the advtge to set
	 */
	public void setAdvtge(String advtge) {
		this.advtge = advtge;
	}

	/**
	 * Gets the defect.
	 *
	 * @return the defect
	 */
	public String getDefect() {
		return defect;
	}

	/**
	 * Sets the defect.
	 *
	 * @param defect the defect to set
	 */
	public void setDefect(String defect) {
		this.defect = defect;
	}
	
	/**
	 * 验证对象是否满足需求
	 */
	public void validate(){
		ModelUtil util = new ModelUtil();
		util.isNotNull(this.getTitle(),"Title");
		util.range(this.getTitle(), 4, 20, "Title");
		util.range(this.getContent(), 5, 100, "Content");
		util.range(this.getAdvtge(), 5, 100, "Advtge");
		util.range(this.getDefect(), 5, 100, "Defect");
		util.isNotNull(this.getProdId(), "ProdId");
		util.isNotNull(this.getUserName(), "UserName");
		
	}

	/**
	 * @return the buyTime
	 */
	public Date getBuyTime() {
		return buyTime;
	}

	/**
	 * @param buyTime the buyTime to set
	 */
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

}