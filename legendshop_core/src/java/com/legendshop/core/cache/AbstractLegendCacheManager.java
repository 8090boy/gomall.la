/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import org.springframework.cache.support.AbstractCacheManager;

/**
 * The Class AbstractLegendCacheManager.
 */
public abstract class AbstractLegendCacheManager extends AbstractCacheManager {
	/** The support query cache. */
	protected boolean supportQueryCache;

	/** The remove all entries. */
	protected boolean removeAllEntries;

	/** The rel cache name. */
	protected final String relCacheName = "LEGENDSHOP_CACHE";

	/**
	 * Checks if is support query cache.
	 * 
	 * @return true, if is support query cache
	 */
	public boolean isSupportQueryCache() {
		return supportQueryCache;
	}

	/**
	 * Sets the support query cache.
	 * 
	 * @param supportQueryCache
	 *            the new support query cache
	 */
	public void setSupportQueryCache(boolean supportQueryCache) {
		this.supportQueryCache = supportQueryCache;
	}

	/**
	 * Checks if is removes the all entries.
	 * 
	 * @return true, if is removes the all entries
	 */
	public boolean isRemoveAllEntries() {
		return removeAllEntries;
	}

	/**
	 * Sets the removes the all entries.
	 * 
	 * @param removeAllEntries
	 *            the new removes the all entries
	 */
	public void setRemoveAllEntries(boolean removeAllEntries) {
		this.removeAllEntries = removeAllEntries;
	}

	/**
	 * Gets the rel cache name.
	 * 
	 * @return the rel cache name
	 */
	public String getRelCacheName() {
		return relCacheName;
	}

}
