/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * The Class JSONUtil.
 */
@SuppressWarnings("unchecked")
public class JSONUtil {
	
	// 从普通的Bean转换为字符串
	/**
	 * Gets the json.
	 *
	 * @param o the o
	 * @return the json
	 */
	public static String getJson(Object o) {
		JSONObject jo = JSONObject.fromObject(o);
		return jo.toString();
	}

	// 从Java的列表转换为字符串
	/**
	 * Gets the json.
	 *
	 * @param list the list
	 * @return the json
	 */
	public static String getJson(List<?> list) {
		JSONArray ja = JSONArray.fromObject(list);
		return ja.toString();
	}

	// 从Java对象数组转换为字符串
	/**
	 * Gets the json.
	 *
	 * @param arry the arry
	 * @return the json
	 */
	public static String getJson(Object[] arry) {
		JSONArray ja = JSONArray.fromObject(arry);
		return ja.toString();
	}

	// 从json格式的字符串转换为Map对象
	/**
	 * Gets the object.
	 *
	 * @param s the s
	 * @return the object
	 */
	@SuppressWarnings("rawtypes")
	public static Map getMap(String s) {
		return JSONObject.fromObject(s);
	}

	// 从json格式的字符串转换为某个Bean
	/**
	 * Gets the object.
	 *
	 * @param <T> the generic type
	 * @param s the s
	 * @param entityClass the entity class
	 * @return the object
	 */
	public static <T> T getObject(String s, Class<T> entityClass) {
		JSONObject jo = JSONObject.fromObject(s);
		return (T)JSONObject.toBean(jo, entityClass);
	}

	// 从json格式的字符串转换为某类对象的数组
	/**
	 * Gets the array.
	 *
	 * @param <T> the generic type
	 * @param s the s
	 * @param cls the cls
	 * @return the array
	 */
	// 从json格式的字符串转换为List数组
	public static <T> List<T> getArray(String s, Class<T> cls) {
		JSONArray ja = JSONArray.fromObject(s);
		T[] obj =(T[]) JSONArray.toArray(ja, cls);
		return Arrays.asList(obj);
	}
	
	public static Object[] getArray(String s) {
		JSONArray ja = JSONArray.fromObject(s);
		return ja.toArray();
	}
}