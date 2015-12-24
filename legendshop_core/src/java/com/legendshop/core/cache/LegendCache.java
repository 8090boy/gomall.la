/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

/**
 * The Class LegendCache.
 */
public class LegendCache extends AbstractLegendCache {

	private final Logger log = LoggerFactory.getLogger(LegendCache.class);
	/** The cache. */
	private final Ehcache cache;

	/**
	 * Create an {@link EhCacheCache} instance.
	 * 
	 * @param cacheManager
	 *            the cache manager
	 * @param ehcache
	 *            backing Ehcache instance
	 */
	public LegendCache(LegendCacheManager cacheManager, Ehcache ehcache) {
		Assert.notNull(ehcache, "Ehcache must not be null");
		Status status = ehcache.getStatus();
		Assert.isTrue(Status.STATUS_ALIVE.equals(status), "An 'alive' Ehcache is required - current cache is " + status.toString());
		this.cacheManager = cacheManager;
		this.cache = ehcache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.Cache#getName()
	 */

	public String getName() {
		return this.cache.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.Cache#getNativeCache()
	 */

	public Ehcache getNativeCache() {
		return this.cache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.Cache#clear()
	 */

	public void clear() {
		this.cache.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.Cache#get(java.lang.Object)
	 */

	public ValueWrapper get(Object key) {
		
		Element element = this.cache.get(key);
		if(log.isDebugEnabled()){
			log.debug("----->Get cache by name {}, key {}", getName(), key );
			 if (element != null) {
				  log.debug("<----- Cache  result {}",  element.getObjectValue());
				 }
		}
		return (element != null ? new SimpleValueWrapper(element.getObjectValue()) : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.Cache#put(java.lang.Object,
	 * java.lang.Object)
	 */

	public void put(Object key, Object value) {
		if(log.isDebugEnabled()){
			log.debug("Put into cache {} by key {}, value {}", new Object[] { getName(), key, value });
		}
		this.cache.put(new Element(key, value));
		putObject(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.Cache#evict(java.lang.Object)
	 */

	public void evict(Object key) {
		evictObject(key);
		Boolean result = cache.remove(key);
		// evict effect only in entity cache  getName(), key, result
		if(log.isDebugEnabled()){
			log.debug("Evict from cache {} by key {}, RESULT = {}", new Object[] { getName(), key, result} );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.cache.AbstractLegendCache#generateRelCacheKey(java
	 * .lang.String)
	 */
	@Override
	public String generateRelCacheKey(String relCacheKey) {
		return relCacheKey;
	}

}
