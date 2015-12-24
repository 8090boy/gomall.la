/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.util.Date;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class Myfavorite implements BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4386671446122853240L;

	/** The id. */
	private String id;

	/** The prod id. */
	private Long prodId;

	/** The prod name. */
	private String prodName;

	/** The recDate. */
	private Date addtime;

	/** The user id. */
	private String userId;

	/** The user name. */
	private String userName;
	
	private String pic;
	
	private Integer comments;
	
	private Integer stocks;
	
	private Integer buys;
	
	private Double cash;
	
	/** The price. */
	private Double price;

	
	/**
	 * Instantiates a new myfavorite.
	 *
	 * @param id the id
	 * @param prodName the prod name
	 * @param price the price
	 * @param addtime the addtime
	 */
	public Myfavorite(String id, String prodName, Double price,Date addtime) {
		this.id = id;
		this.prodName = prodName;
		this.addtime = addtime;
		this.price = price;
	}
	
	public Myfavorite(String id, Long prodId,String prodName, Double price,Date addtime, String pic, Integer comments, Integer stocks, Integer buys, Double cash) {
		this.id = id;
		this.prodId = prodId;
		this.prodName = prodName;
		this.addtime = addtime;
		this.price = price;
		this.pic = pic;
		this.comments = comments;
		this.stocks = stocks;
		this.buys = buys;
		this.cash = cash;
	}

	

	// Constructors

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	/**
	 * default constructor.
	 */
	public Myfavorite() {
	}
	

	/**
	 * minimal constructor.
	 * 
	 * @param id
	 *            the id
	 */
	public Myfavorite(String id) {
		this.id = id;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the prod id.
	 * 
	 * @return the prod id
	 */
	public Long getProdId() {
		return this.prodId;
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
	 * Gets the prod name.
	 * 
	 * @return the prod name
	 */
	public String getProdName() {
		return this.prodName;
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
	 * @param userId the new user id
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
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}


	/**
	 * Sets the price.
	 *
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getStocks() {
		return stocks;
	}

	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}

	public Integer getBuys() {
		return buys;
	}

	public void setBuys(Integer buys) {
		this.buys = buys;
	}

	
}