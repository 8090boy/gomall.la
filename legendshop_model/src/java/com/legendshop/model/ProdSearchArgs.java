/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

import java.io.Serializable;

/**
 * The Class ProdSearchArgs.
 */
public class ProdSearchArgs implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9071833733004312945L;

	/** The order dir. */
	private String orderDir;

	/** The order by. */
	private String orderBy;

	/** The has prod. */
	private boolean hasProd;

	/**
	 * Gets the order dir.
	 * 
	 * @return the order dir
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * Sets the order dir.
	 * 
	 * @param orderDir
	 *            the new order dir
	 */
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	/**
	 * Gets the order by.
	 * 
	 * @return the order by
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the order by.
	 * 
	 * @param orderBy
	 *            the new order by
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Checks if is checks for prod.
	 * 
	 * @return true, if is checks for prod
	 */
	public boolean isHasProd() {
		return hasProd;
	}

	/**
	 * Sets the checks for prod.
	 * 
	 * @param hasProd
	 *            the new checks for prod
	 */
	public void setHasProd(boolean hasProd) {
		this.hasProd = hasProd;
	}

}
