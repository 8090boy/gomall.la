/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

import java.util.Map;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class Response extends DataTransferObject {

	/** The Constant SUCCESS. */
	public static final int SUCCESS = 0;

	/** The Constant PARTIAL_SUCCESS. */
	public static final int PARTIAL_SUCCESS = -1;

	/** The Constant APPLICATION_LEVEL_ERROR. */
	public static final int APPLICATION_LEVEL_ERROR = -2;

	/** The Constant SYSTEM_LEVEL_ERROR. */
	public static final int SYSTEM_LEVEL_ERROR = -3;

	/** The ret code. */
	private int retCode = SUCCESS;

	/** The state. */
	private State state;

	/**
	 * Instantiates a new response.
	 */
	public Response() {
		super();
		state = new StateImpl();
	}

	/**
	 * Instantiates a new response.
	 * 
	 * @param values
	 *            the values
	 */
	public Response(Map values) {
		super(values);
	}

	/**
	 * Sets the return code.
	 * 
	 * @param retCode
	 *            the new return code
	 */
	public void setReturnCode(int retCode) {
		this.retCode = retCode;
	}

	/**
	 * Gets the return code.
	 * 
	 * @return the return code
	 */
	public int getReturnCode() {
		return retCode;
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Checks if is successful.
	 * 
	 * @return true, if is successful
	 */
	public boolean isSuccessful() {
		return (SUCCESS == retCode);
	}

}
