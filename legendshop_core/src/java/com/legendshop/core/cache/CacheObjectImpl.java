/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.dao.DataRetrievalFailureException;

import com.legendshop.util.AppUtils;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class CacheObjectImpl implements CacheObject {

	/** The logger. */
	private static Logger logger = Logger.getLogger(CacheObjectImpl.class);

	/** The obejct cache. */
	protected Cache obejctCache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.base.cache.CacheObject#getCache(net.sf.ehcache.Cache,
	 * java.lang.String)
	 */
	public Object getCache(final Cache cache, final String cahceName) {
		Element element = null;
		try {
			element = cache.get(cahceName);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage());
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Cache hit: " + (element != null) + "; Cache name: " + cahceName + " Name: " + cache.getName()
					+ " ,size: " + cache.getSize());
		}

		if (element == null) {
			return null;
		} else {
			return element.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.base.cache.CacheObject#putInCache(net.sf.ehcache.Cache,
	 * java.lang.String, java.lang.Object)
	 */
	public void putInCache(Cache cache, String cahceName, Object cacheObject) {
		if (cacheObject != null) {
			Element element = new Element(cahceName, (Serializable) cacheObject);

			if (logger.isTraceEnabled()) {
				logger.trace("Cache put: " + element.getKey());
			}

			cache.put(element);
		} else {
			logger.info("Cache is null with cahceName " + cahceName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.base.cache.CacheObject#removeFromCache(net.sf.ehcache.
	 * Cache, java.lang.String)
	 */
	public void removeFromCache(Cache cache, String cahceName) {
		cache.remove(cahceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.base.cache.CacheObject#removeFromCacheStartWithName(net
	 * .sf.ehcache.Cache, java.lang.String)
	 */
	public void removeFromCacheStartWithName(Cache cache, String cahceName) throws Throwable {
		List list = cache.getKeys();
		if (AppUtils.isNotBlank(list)) {
			for (int i = 0; i < list.size(); i++) {
				String cacheKey = String.valueOf(list.get(i));
				if (cacheKey.startsWith(cahceName)) {
					cache.remove(cacheKey);
				}
			}
		}

	}

	/**
	 * methodName should includes module name and method name.
	 * 
	 * @param methodName
	 *            the method name
	 * @param parameters
	 *            the parameters
	 * @return the key
	 */
	public String getKey(String methodName, Object... parameters) {
		if (AppUtils.isBlank(methodName)) {
			return null;
		}
		StringBuffer sb = new StringBuffer().append(methodName);
		if (AppUtils.isNotBlank(parameters)) {
			for (Object param : parameters) {
				sb.append(param);
			}
		}
		return sb.toString();
	}

	/**
	 * Sets the obejct cache.
	 * 
	 * @param obejctCache
	 *            the new obejct cache
	 */
	public void setObejctCache(Cache obejctCache) {
		this.obejctCache = obejctCache;
	}

	// 从缓存中拿数据先,采用泛型
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.cache.CacheObject#getObjectFromCache(java.lang.String
	 * , com.legendshop.core.cache.CacheCallBack)
	 */
	public Object getObjectFromCache(final String cahceName, final CacheCallBack callback) {
		if (AppUtils.isBlank(cahceName) || obejctCache == null) {
			return null;
		}
		Object object = getCache(obejctCache, cahceName);
		if (object == null) {
			object = callback.doInCache(cahceName, obejctCache);
			putInCache(obejctCache, cahceName, object);
		}
		return object;
	}

}
