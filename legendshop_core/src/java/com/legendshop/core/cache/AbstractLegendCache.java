/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.BaseEntity;

/**
 * The Class AbstractLegendCache.
 */
public abstract class AbstractLegendCache implements Cache {
	
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(AbstractLegendCache.class);

	/** 查询缓存后缀. */
	protected String SUFFIX = "List";

	/** The cache manager. */
	protected LegendCacheManager cacheManager;

	/**
	 * Evict object.
	 * 
	 * @param key
	 *            删除相关的条目
	 */
	protected void evictObject(Object key) {
		if(log.isDebugEnabled()){
			log.debug("Evict by key {}", key);
		}
		// for list
		if (cacheManager.isSupportQueryCache() && !this.getName().endsWith(SUFFIX) && !this.getName().equals(cacheManager.getRelCacheName())) {
			// clean entity
			Cache relCache = cacheManager.getCache(cacheManager.getRelCacheName());
			if (relCache != null) {
				String relCacheKey = generateRelCacheKey(this.getName() + key);
				SimpleValueWrapper valueWrapper = (SimpleValueWrapper) relCache.get(relCacheKey);
				if (valueWrapper != null) {
					IdListRel idListRel = (IdListRel) valueWrapper.get();
					if (idListRel != null && idListRel.getRelObject() != null) {
						for (CacheNameAndItemWrapper relationShop : idListRel.getRelObject()) {
							
							if(log.isDebugEnabled()){
								log.debug("Evict from cache {} by key {}", relationShop.getCacheName(),  relationShop.getKey());
							}
							Cache relCache2 = cacheManager.getCache(relationShop.getCacheName());
							relCache2.evict(relationShop.getKey());
						}
					}

				}
				// remove relcache
				if(log.isDebugEnabled()){
					log.debug("Evict key {}  from cache {}",  relCacheKey,cacheManager.getRelCacheName());
				}
				relCache.evict(relCacheKey);
			}
		} else if (cacheManager.isRemoveAllEntries()) {
			Cache listCache = cacheManager.getCache(this.getName() + SUFFIX);
			if (listCache != null) {
				if(log.isDebugEnabled()){
					log.debug("Evict all cache from {}", listCache.getName());
				}
				listCache.clear();
			}
		}
	}

	/**
	 * Put object.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	protected void putObject(Object key, Object value) {
		// for list
		// 如果是列表，则保存以ID为主键的关系
		if (cacheManager.isSupportQueryCache() && this.getName().endsWith(SUFFIX)
				&& !this.getName().equals(cacheManager.getRelCacheName()) && value != null) {
			Collection<BaseEntity> coll = null;
			if (Collection.class.isAssignableFrom(value.getClass())) {
				coll = (Collection<BaseEntity>) value;
			} else if (PageSupport.class.isAssignableFrom(value.getClass())) {
				coll = ((PageSupport) value).getResultList();
			}
			if (coll != null) {
				for (BaseEntity entity : coll) {

					Cache relCache = cacheManager.getCache(cacheManager.getRelCacheName());

					String relCacheKey = generateRelCacheKey(this.getName().substring(0, this.getName().length() - 4)
							+ entity.getId());

					SimpleValueWrapper valueWrapper = (SimpleValueWrapper) relCache.get(relCacheKey);
					IdListRel idListRel = null;
					if (valueWrapper != null) {
						idListRel = (IdListRel) valueWrapper.get();
					}
					if (valueWrapper == null) {
						idListRel = new IdListRel(entity.getId());
					}
					if (idListRel.addRelObject(this.getName(), key)) {
						if(log.isDebugEnabled()){
							log.debug("put into rel cache {} by key {}, value {}",  new Object[] {relCacheKey, relCacheKey, valueWrapper });
						}
						relCache.put(relCacheKey, idListRel);
					}
				}
			}
		}
	}

	/**
	 * Generate rel cache key.
	 * 
	 * @param relCacheKey
	 *            the rel cache key
	 * @return the string
	 */
	public abstract String generateRelCacheKey(String relCacheKey);

	/**
	 * Sets the cache manager.
	 * 
	 * @param cacheManager
	 *            the new cache manager
	 */
	public void setCacheManager(LegendCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
