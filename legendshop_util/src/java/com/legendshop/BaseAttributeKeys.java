/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop;

/**
 * The Interface BaseAttributeKeys.
 */
public interface BaseAttributeKeys {

	/** The Constant TRUE_INDICATOR. */
	public static final String TRUE_INDICATOR = "Y";

	/** The Constant FALSE_INDICATOR. */
	public static final String FALSE_INDICATOR = "N";

	/** The Constant ONLINE. */
	public static final Integer ONLINE = 1;

	/** The Constant OFFLINE. */
	public static final Integer OFFLINE = 0;

	/** The Constant STOPUSE. 停止使用，普通用户不能改为 ONLINE 或者 OFFLINE */
	public static final Integer STOPUSE = -1;

}
