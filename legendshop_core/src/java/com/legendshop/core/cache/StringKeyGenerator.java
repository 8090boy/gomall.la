/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 实现新的key generator基于两点原因： 1.memcached的键都是string; 2.分布式环境不能用hashcode.
 * 
 */
public class StringKeyGenerator implements KeyGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.cache.interceptor.KeyGenerator#generate(java.lang
	 * .Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object generate(Object target, Method method, Object... params) {
		StringBuilder sb = new StringBuilder(50);
		sb.append(target.getClass().getName());
		sb.append(method.getName());
		for (Object o : params) {
			if (o != null) {
				sb.append(o.toString());
			}
			sb.append(".");
		}
		return sb.toString();
	}

}