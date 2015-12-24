/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.legendshop.model.entity.Basket;

/**
 * 订单信息列表对象.
 */
public class SubListForm implements Serializable {

	private static final long serialVersionUID = 7550068509283340968L;

	/** The sub id. */
	private Long subId;
	
	/** The user name. */
	private String userName;

	/** The order name. */
	private String orderName;

	/** The sub date. */
	private Date subDate; // 下订单的时间
	
	/** The sub number. */
	private String subNumber;
	
	/** The sub check. */
	private String subCheck;
	
	/** The sub type. */
	private String subType;

	// 订单总钱数
	/** The total. */
	private Double subTotal;
	
	// 订单积分
	/** The total. */
	private Integer points;
	
	// 订单总积分
	/** The total. */
	private Integer pointsSubtotal;
	
	/** The pay id. */
	private Long payId;

	/** The pay type id. */
	private String payTypeId;

	/** The status. */
	private Integer status;

	// 支付方式名称
	/** The pay type name. */
	private String payTypeName;
	
	/** The shop name. */
	private String shopName;

	/////// 购物车，每个basket代表一个商品 //////////
	private List<Basket> basketList;
	
    /** The basket id. */
    private Long basketId;
	
	/** The total. */
	private Double total;//=单价*数量
	
    /** The cash. */
    private Double cash;
    
    /** The carriage. */
    private Double carriage;//运费
    
    /** The prod id. */
    private Long prodId;
    
    /** The pic. */
    private String pic;
    
    /** The basket count. */
    private Integer basketCount;
    
    /** 产品名称. */
    private String prodName;
    
    /** The attribute. */
    private String attribute; //产品属性
    

 

	public Integer getPointsSubtotal() {
		return pointsSubtotal;
	}

	public void setPointsSubtotal(Integer pointsSubtotal) {
		this.pointsSubtotal = pointsSubtotal;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Date getSubDate() {
		return subDate;
	}

	public void setSubDate(Date subDate) {
		this.subDate = subDate;
	}

	public String getSubNumber() {
		return subNumber;
	}

	public void setSubNumber(String subNumber) {
		this.subNumber = subNumber;
	}

	public String getSubCheck() {
		return subCheck;
	}

	public void setSubCheck(String subCheck) {
		this.subCheck = subCheck;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getCarriage() {
		return carriage;
	}

	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getBasketCount() {
		return basketCount;
	}

	public void setBasketCount(Integer basketCount) {
		this.basketCount = basketCount;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Long getBasketId() {
		return basketId;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
	}

	public List<Basket> getBasketList() {
		return basketList;
	}

	public void setBasketList(List<Basket> basketList) {
		this.basketList = basketList;
	}

	//增加购物车相关信息
	public void addBasket(Basket basket) {
		if(basketList == null){
			basketList = new ArrayList<Basket>();
		}
		this.basketList.add(basket);
	}
	
	//拷贝订单部分
	public SubListForm clone(){
		SubListForm sub = new SubListForm();
		sub.setSubId(subId);
		sub.setUserName(userName);
		sub.setSubTotal(subTotal);
		sub.setOrderName(orderName);
		sub.setSubDate(subDate);
		sub.setSubNumber(subNumber);
		sub.setSubCheck(subCheck);
		sub.setSubType(subType);
		sub.setPayId(payId);
		sub.setPayTypeId(payTypeId);
		sub.setStatus(status);
		sub.setPayTypeName(payTypeName);
		sub.setShopName(shopName);
		sub.setPoints(points);
		sub.setPointsSubtotal(pointsSubtotal);
		return sub;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
}