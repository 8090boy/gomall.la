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
 * The Class ProductCommentCategory.
 */
public class ProductCommentCategory implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4592618215956934080L;

	/** The prod id. */
	private Long prodId;
	
	/** The total. */
	private int total;
	
	//好评
	/** The high. */
	private int high;
	
	//中评
	/** The medium. */
	private int medium;
	
	//差评
	/** The low. */
	private int low;
	
	//好评率
	/** The high rate. */
	private double highRate;
	
	//中评率
	/** The medium rate. */
	private double mediumRate;
	
	//差评率
	/** The low rate. */
	private double lowRate;

	/**
	 * @return the prodId
	 */
	public Long getProdId() {
		return prodId;
	}

	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the high
	 */
	public int getHigh() {
		return high;
	}

	/**
	 * @param high the high to set
	 */
	public void setHigh(int high) {
		this.high = high;
	}

	/**
	 * @return the medium
	 */
	public int getMedium() {
		return medium;
	}

	/**
	 * @param medium the medium to set
	 */
	public void setMedium(int medium) {
		this.medium = medium;
	}

	/**
	 * @return the low
	 */
	public int getLow() {
		return low;
	}

	/**
	 * @param low the low to set
	 */
	public void setLow(int low) {
		this.low = low;
	}

	/**
	 * @return the highRate
	 */
	public double getHighRate() {
		return highRate;
	}

	/**
	 * @param highRate the highRate to set
	 */
	public void setHighRate(double highRate) {
		this.highRate = highRate;
	}

	/**
	 * @return the mediumRate
	 */
	public double getMediumRate() {
		return mediumRate;
	}

	/**
	 * @param mediumRate the mediumRate to set
	 */
	public void setMediumRate(double mediumRate) {
		this.mediumRate = mediumRate;
	}

	/**
	 * @return the lowRate
	 */
	public double getLowRate() {
		return lowRate;
	}

	/**
	 * @param lowRate the lowRate to set
	 */
	public void setLowRate(double lowRate) {
		this.lowRate = lowRate;
	}

}
