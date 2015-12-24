/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.io.File;
import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;
import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.cache.LegendCache;
import com.legendshop.core.cache.MemCachedManager;
import com.legendshop.core.cache.MemcachedCache;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.LegendFilter;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.util.AppUtils;
import com.legendshop.util.FileTimeWrapper;

/**
 * 系统缓存控制器.
 */
@Controller
public class SecondLevelCacheController extends BaseController {

	/** The cache manager. */
	@Autowired(required = false)
	private CacheManager cacheManager;

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/admin/system/cache/query")
	public String query(HttpServletRequest request, HttpServletResponse response) {
		List<KeyValueEntity> cacheList = null;
		if (cacheManager instanceof MemCachedManager) {
			cacheList = parseMemcache(cacheManager);
		} else {
			cacheList = parseEhcache(cacheManager);
		}
		request.setAttribute("cacheList", cacheList);

		return PathResolver.getPath(request, response, BackPage.CACHE_LIST_PAGE);
	}

	/**
	 * Query html.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/admin/cache/queryhtml")
	public String queryHtml(HttpServletRequest request, HttpServletResponse response) {
		String requestFolder = request.getParameter("requestFolder");
		String currentUserName = "";
		if (requestFolder == null) {
			requestFolder = LegendFilter.HTML_PATH;
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				// common user
				currentUserName = UserManager.getUserName(request);
				requestFolder = requestFolder + currentUserName;
			}
		} else {
			String direct = request.getParameter("direct");
			if (AppUtils.isNotBlank(direct)) {
				requestFolder = requestFolder.substring(0, requestFolder.lastIndexOf("/"));
			}
		}

		File dir = new File(requestFolder);
		File[] files = null;
		if (dir.isDirectory()) {
			files = dir.listFiles();
		}
		if (files != null) {
			FileTimeWrapper[] fileWrappers = new FileTimeWrapper[files.length];
			for (int j = 0; j < files.length; j++) {
				File file = files[j];
				FileTimeWrapper fileTimeWrapper = new FileTimeWrapper(file);
				fileTimeWrapper.setIsFile(file.isFile());
				fileTimeWrapper.setFileName(file.getName());
				fileWrappers[j] = fileTimeWrapper;
			}
			Arrays.sort(fileWrappers);
			request.setAttribute("fileList", fileWrappers);
		}
		request.setAttribute("requestFolder", requestFolder);
		String requestPath = null;
		if (AppUtils.isNotBlank(currentUserName)) {
			requestPath = LegendFilter.HTML_PATH + currentUserName;
		} else {
			requestPath = LegendFilter.HTML_PATH;
		}
		if (requestPath.equals(requestFolder)) {
			// 根路径
			request.setAttribute("requestPath", currentUserName);
			request.setAttribute("rootPath", true);
		} else {
			// 子路径
			request.setAttribute("requestPath", requestFolder.substring(LegendFilter.HTML_PATH.length()));
		}
		return PathResolver.getPath(request, response, BackPage.HTML_LIST_PAGE);
	}

	/**
	 * Delete.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/admin/system/cache/clear")
	public String clear(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		return PathResolver.getPath(request, response, FowardPage.PARAM_LIST_QUERY);
	}
	
	/**
	 * 清空缓存列表
	 * @return
	 */
	@RequestMapping(value = "/admin/system/cache/removeall")
		public @ResponseBody String clearSecondLevelCache() {
			try {
				Collection<String> cacheNames = cacheManager.getCacheNames();
				for (String cacheName : cacheNames) {
					Cache cache = cacheManager.getCache(cacheName);
					if (cache != null) {
						System.out.println("clear cache " + cache.getName());
						cache.clear();
					}
				}
				return null;
			} catch (Exception e) {
				return "fail";
			}

		}

	/**
	 * 处理 memcache.
	 * 
	 * @param cacheManager
	 *            the cache manager
	 * @return the list
	 */
	private List<KeyValueEntity> parseMemcache(CacheManager cacheManager) {
		List<KeyValueEntity> result = new ArrayList<KeyValueEntity>();
		Collection<String> cacheNames = cacheManager.getCacheNames();

		if (AppUtils.isNotBlank(cacheNames)) {
			for (String cacheName : cacheNames) {
				MemcachedClient cache = ((MemcachedCache) cacheManager.getCache(cacheName)).getNativeCache();
				if (cache != null) {
					Map<SocketAddress, Map<String, String>> map = cache.getStats();
					Set<SocketAddress> socketAddresses = map.keySet();
					// Collection<Map<String, String>> values = map.values();
					for (SocketAddress address : socketAddresses) {
						KeyValueEntity entity = new KeyValueEntity();
						entity.setKey(cacheName + ":" + address.toString());
						Map<String, String> valueMap = map.get(address);
						Set<String> keyValue = valueMap.keySet();
						if (keyValue != null) {
							StringBuilder sb = new StringBuilder();
							for (String key : keyValue) {
								String valueValue = valueMap.get(key);
								sb.append(valueValue).append(",");
							}
							entity.setValue(sb.toString());
						}
						result.add(entity);

					}
				}
			}
		}
		return result;
	}

	/**
	 * Parses the ehcache.
	 * 
	 * @param cacheManager
	 *            the cache manager
	 * @return the list
	 */
	private List<KeyValueEntity> parseEhcache(CacheManager cacheManager) {
		List<KeyValueEntity> result = new ArrayList<KeyValueEntity>();
		Collection<String> cacheNames = cacheManager.getCacheNames();
		if (AppUtils.isNotBlank(cacheNames)) {
			for (String cacheName : cacheNames) {
				KeyValueEntity entity = new KeyValueEntity();
				entity.setKey(cacheName);
				Ehcache cache = ((LegendCache) cacheManager.getCache(cacheName)).getNativeCache();
				List<Serializable> keys = cache.getKeys();
				if (AppUtils.isNotBlank(keys)) {
					StringBuilder sb = new StringBuilder(keys.get(0).toString());
					for (int i = 1; i < keys.size(); i++) {
						sb.append(",").append(keys.get(i));
					}
					entity.setValue(sb.toString());
				}

				result.add(entity);
			}
		}
		return result;
	}
	
	

}
