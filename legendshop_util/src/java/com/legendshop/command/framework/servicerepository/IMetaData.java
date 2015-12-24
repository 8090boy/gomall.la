/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

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
public interface IMetaData {

	/**
	 * Gets the.
	 * 
	 * @param urlName
	 *            the url name
	 * @return the object
	 */
	public Object get(String urlName);

	/**
	 * Gets the one.
	 * 
	 * @return the one
	 */
	public Object getOne();

	/**
	 * Gets the.
	 * 
	 * @return the map
	 */
	public Map get();
}
