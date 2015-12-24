/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.legendshop.core.security.dao.ResourcesDao;
import com.legendshop.core.security.model.Resource;

/*
 * 
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 注意，我例子中使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，
 * 事实上你还要用正则的方式来匹配，或者自己实现一个matcher。
 * 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 * 说明：对于方法的spring注入，只能在方法和成员变量里注入，
 * 如果一个类要进行实例化的时候，不能注入对象和操作对象，
 * 所以在构造函数里不能进行操作注入的数据。
 */
/**
 * The Class InvocationSecurityMetadataSourceService.
 */
public class InvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	
	/** The resources dao. */
	private ResourcesDao resourcesDao;

	// resourceMap及为key-url，value-Collection<ConfigAttribute>,资源权限对应Map
	/** The resource map. */
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	/**
	 * Instantiates a new invocation security metadata source service.
	 *
	 * @param resourcesDao the resources dao
	 */
	public InvocationSecurityMetadataSourceService(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
		System.out.println("加载MyInvocationSecurityMetadataSourceService..." + resourcesDao);
		loadResourceDefine();
	}

	// 加载所有资源与权限的关系
	/**
	 * Load resource define.
	 */
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resource> resources = resourcesDao.findAll();
			// 加载资源对应的权限
			for (Resource resource : resources) {
				Collection<ConfigAttribute> auths = resourcesDao.loadRoleByResource(resource.getResString());
				System.out.println("权限=" + auths);
				resourceMap.put(resource.getResString(), auths);
			}
		}
	}

	// 加载所有资源与权限的关系
	/* (non-Javadoc)
	 * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// object是一个URL，被用户请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);

		int firstQuestionMarkIndex = requestUrl.indexOf("?");

		if (firstQuestionMarkIndex != -1) {
			requestUrl = requestUrl.substring(0, firstQuestionMarkIndex);
		}

		if (resourceMap == null) {
			loadResourceDefine();
		}
		//
		Iterator<String> ite = resourceMap.keySet().iterator();

		while (ite.hasNext()) {
			String resURL = ite.next();

			if (resURL.equals(requestUrl)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.SecurityMetadataSource#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> arg0) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes()
	 */
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}