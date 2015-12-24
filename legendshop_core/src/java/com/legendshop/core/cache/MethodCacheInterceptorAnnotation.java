/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.legendshop.util.DateUtil;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
@Component("methodCacheInterceptor")
public class MethodCacheInterceptorAnnotation implements MethodInterceptor {

	/** The logger. */
	private static transient Log logger = LogFactory.getLog(MethodCacheInterceptor.class);

	/** The Constant CACHENAME_SPLIT_CHAR. */
	private static final String CACHENAME_SPLIT_CHAR = "#";

	/** The method cache. */
	private Cache methodCache;

	/**
	 * Sets the method cache.
	 * 
	 * @param methodCache
	 *            the new method cache
	 */
	public void setMethodCache(Cache methodCache) {
		this.methodCache = methodCache;
	}

	/**
	 * Make sure that every bean which has been intercepted must has a
	 * interface, pls do not use CGLIB to PROXY class.
	 * 
	 * @param invocation
	 *            the invocation
	 * @return the object
	 * @throws Throwable
	 *             the throwable
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@SuppressWarnings("unchecked")
	public Object invoke(MethodInvocation invocation) throws Throwable {

		String targetName = invocation.getThis().getClass().getInterfaces()[0].getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		Class[] cs = new Class[arguments.length];
		for (int k = 0; k < arguments.length; k++) {
			cs[k] = arguments[k].getClass();
		}

		if (invocation.getThis().getClass().getCanonicalName().contains("$Proxy")) {
			if (logger.isWarnEnabled()) {
				logger.warn("----- The object has been proxyed and method " + "cache interceptor can't get the target, "
						+ "so the method result can't be cached which name is ------" + methodName);
			}

			return invocation.proceed();
		} else {
			if (invocation.getThis().getClass().isAnnotationPresent(ObjectCache.class)) {
				ObjectCache oc = invocation.getThis().getClass().getAnnotation(ObjectCache.class);
				return getResult(targetName, methodName, arguments, invocation, oc.expire());
			} else {

				Method[] mss = invocation.getThis().getClass().getMethods();
				Method ms = null;
				for (Method m : mss) {
					if (m.getName().equals(methodName)) {
						boolean argMatch = true;
						Class[] tmpCs = m.getParameterTypes();
						if (tmpCs.length != cs.length) {
							argMatch = false;
							continue;
						}
						for (int k = 0; k < cs.length; k++) {
							if (!cs[k].equals(tmpCs[k])) {
								argMatch = false;
								break;
							}
						}

						if (argMatch) {
							ms = m;
							break;
						}
					}
				}

				if (ms != null && ms.isAnnotationPresent(MethodCache.class)) {
					MethodCache mc = ms.getAnnotation(MethodCache.class);
					return getResult(targetName, methodName, arguments, invocation, mc.expire());
				} else {
					return invocation.proceed();
				}
			}
		}
	}

	/**
	 * Gets the result.
	 * 
	 * @param targetName
	 *            the target name
	 * @param methodName
	 *            the method name
	 * @param arguments
	 *            the arguments
	 * @param invocation
	 *            the invocation
	 * @param expire
	 *            the expire
	 * @return the result
	 * @throws Throwable
	 *             the throwable
	 */
	private Object getResult(String targetName, String methodName, Object[] arguments, MethodInvocation invocation, int expire)
			throws Throwable {
		Object result;

		String cacheKey = getCacheKey(targetName, methodName, arguments);
		Element element = methodCache.get(cacheKey);
		if (element == null) {
			synchronized (this) {
				element = methodCache.get(cacheKey);
				if (element == null) {
					result = invocation.proceed();

					element = new Element(cacheKey, (Serializable) result);

					// annotation没有设expire值则使用ehcache.xml中自定义值
					if (expire > 0) {
						element.setTimeToIdle(expire);
						element.setTimeToLive(expire);
					}
					methodCache.put(element);
				}
			}
		}

		return element.getValue();
	}

	/**
	 * Gets the cache key.
	 * 
	 * @param targetName
	 *            the target name
	 * @param methodName
	 *            the method name
	 * @param arguments
	 *            the arguments
	 * @return the cache key
	 */
	private String getCacheKey(String targetName, String methodName, Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(CACHENAME_SPLIT_CHAR).append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i] instanceof Date) {
					sb.append(CACHENAME_SPLIT_CHAR).append(DateUtil.DateToString((Date) arguments[i], "yyyy-MM-dd:HH:mm:ss"));
				} else {
					sb.append(CACHENAME_SPLIT_CHAR).append(arguments[i]);
				}
			}
		}

		return sb.toString();
	}

}
