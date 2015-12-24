/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.sql;

import java.util.HashMap;

import com.legendshop.util.AppUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ParamsMap extends HashMap<String, String> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7720526189745315572L;

	/**
	 * Adds the params.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void addParams(String key, String value) {
		if (AppUtils.isBlank(key) || AppUtils.isBlank(value)) {
			return;
		} else {
			super.put(key, value);
		}
	}
}
