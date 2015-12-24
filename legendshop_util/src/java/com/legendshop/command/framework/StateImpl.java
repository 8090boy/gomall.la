/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class StateImpl implements State {

	/** The err code. */
	private String errCode = OK;

	/** The throwable. */
	private Throwable throwable = null;

	/**
	 * Instantiates a new state impl.
	 */
	public StateImpl() {
	}

	/**
	 * Gets the err code.
	 * 
	 * @return String
	 */
	public String getErrCode() {
		return this.errCode;
	}

	/**
	 * Sets the err code.
	 * 
	 * @param errCode
	 *            the new err code
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * Gets the throwable.
	 * 
	 * @return Throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * Sets the throwable.
	 * 
	 * @param throwable
	 *            the new throwable
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	/**
	 * Checks if is oK.
	 * 
	 * @return boolean
	 */
	public boolean isOK() {
		return OK.equalsIgnoreCase(errCode) && (throwable == null);
	}

}
