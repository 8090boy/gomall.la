/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import java.util.Collection;
import java.util.LinkedHashSet;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;

import org.springframework.cache.Cache;
import org.springframework.util.Assert;

/**
 * The Class EhCacheCacheManager.
 */
public class EhCacheCacheManager extends AbstractLegendCacheManager implements LegendCacheManager {

	/** The cache manager. */
	private net.sf.ehcache.CacheManager cacheManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.support.AbstractCacheManager#loadCaches()
	 */
	@Override
	protected Collection<Cache> loadCaches() {
		Assert.notNull(this.cacheManager, "A backing EhCache CacheManager is required");
		Status status = this.cacheManager.getStatus();
		Assert.isTrue(Status.STATUS_ALIVE.equals(status), "An 'alive' EhCache CacheManager is required - current cache is "
				+ status.toString());

		String[] names = this.cacheManager.getCacheNames();
		Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
		for (String name : names) {
			caches.add(new LegendCache(this, this.cacheManager.getEhcache(name)));
		}
		return caches;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.cache.support.AbstractCacheManager#getCache(java.
	 * lang.String)
	 */

	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			// check the EhCache cache again
			// (in case the cache was added at runtime)
			Ehcache ehcache = this.cacheManager.getEhcache(name);
			if (ehcache != null) {
				cache = new LegendCache(this, ehcache);
				addCache(cache);
			}
		}
		return cache;
	}

	/**
	 * Set the backing EhCache {@link net.sf.ehcache.CacheManager}.
	 * 
	 * @param cacheManager
	 *            the new cache manager
	 */
	public void setCacheManager(net.sf.ehcache.CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}