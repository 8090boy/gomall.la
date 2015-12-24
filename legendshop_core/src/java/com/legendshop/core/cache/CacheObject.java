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
 * 
 * ----------------------------------------------------------------------------
 * -------- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * --------
 * ----------------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public interface CacheObject {

	/**
	 * Gets the cache.
	 * 
	 * @param cache
	 *            the cache
	 * @param cahceName
	 *            the cahce name
	 * @return the cache
	 */
	public abstract Object getCache(final Cache cache, final String cahceName);

	/**
	 * Put in cache.
	 * 
	 * @param cache
	 *            the cache
	 * @param cahceName
	 *            the cahce name
	 * @param cacheObject
	 *            the cache object
	 */
	public abstract void putInCache(Cache cache, String cahceName, Object cacheObject);

	/**
	 * Removes the from cache.
	 * 
	 * @param cache
	 *            the cache
	 * @param cahceName
	 *            the cahce name
	 */
	public abstract void removeFromCache(Cache cache, String cahceName);

	/**
	 * Removes the from cache start with name.
	 * 
	 * @param cache
	 *            the cache
	 * @param cahceName
	 *            the cahce name
	 * @throws Throwable
	 *             the throwable
	 */
	public abstract void removeFromCacheStartWithName(Cache cache, String cahceName) throws Throwable;

	/**
	 * Gets the key.
	 * 
	 * @param methodName
	 *            the method name
	 * @param parameters
	 *            the parameters
	 * @return the key
	 */
	public abstract String getKey(String methodName, Object... parameters);

	/**
	 * Gets the object from cache.
	 * 
	 * @param cahceName
	 *            the cahce name
	 * @param callback
	 *            the callback
	 * @return the object from cache
	 */
	public abstract Object getObjectFromCache(final String cahceName, final CacheCallBack callback);

}