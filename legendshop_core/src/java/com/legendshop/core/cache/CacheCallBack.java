/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import net.sf.ehcache.Cache;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 * 
 * @param <T>
 *            the generic type
 */
public interface CacheCallBack<T> {

	/**
	 * Do in cache.
	 * 
	 * @param cahceName
	 *            the cahce name
	 * @param cache
	 *            the cache
	 * @return the t
	 */
	public T doInCache(String cahceName, Cache cache);

}
