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
public interface AppInit {

	/**
	 * Execute.
	 * 
	 * @param params
	 *            the params
	 * @param response
	 *            the response
	 * @return true, if successful
	 */
	public boolean execute(Map params, Map response);
}
