/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.legendshop.core.AttributeKeys;

/**
 * 得到国际化的消息
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class ResourceBundleHelper {

	/**
	 * Gets the string.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public static String getString(String key) {
		return ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, parseLocale()).getString(key);
	}

	/**
	 * Gets the string.
	 * 
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 */
	public static String getString(String key, String defaultValue) {
		String result;
		try {
			result = ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, parseLocale()).getString(key);
			if (result == null) {
				result = defaultValue;
			}
		} catch (Exception e) {
			result = defaultValue;
		}

		return result;
	}

	/**
	 * Gets the delete string.
	 * 
	 * @return the delete string
	 */
	public static String getDeleteString() {
		return ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, parseLocale()).getString("entity.deleted");
	}

	/**
	 * Gets the sucessful string.
	 * 
	 * @return the sucessful string
	 */
	public static String getSucessfulString() {
		return ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, parseLocale())
				.getString("operation.successful");
	}

	/**
	 * Gets the error string.
	 * 
	 * @return the error string
	 */
	public static String getErrorString() {
		return ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, parseLocale()).getString("SYSTEM_ERROR");
	}

	/**
	 * Gets the string.
	 * 
	 * @param locale
	 *            the locale
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 */
	public static String getString(Locale locale, String key, String defaultValue) {
		String value;
		try {
			if (locale != null) {
				value = ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, locale).getString(key);
			} else {
				value = ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE).getString(key);
			}
			if (value == null) {
				value = defaultValue;
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			value = defaultValue;
		}

		return value;
	}

	/**
	 * Gets the string.
	 * 
	 * @param locale
	 *            the locale
	 * @param key
	 *            the key
	 * @return the string
	 */
	public static String getString(Locale locale, String key) {
		String value;
		if (locale != null) {
			value = ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE, locale).getString(key);
		} else {
			value = ResourceBundle.getBundle(AttributeKeys.LOCALE_FILE).getString(key);
		}
		if (value == null) {
			throw new IllegalArgumentException(key + " was not found");
		}

		return value;
	}

	public static String getString(Locale locale, String key, Object[] array) {
		return MessageFormat.format(getString(locale, key), array);
	}
	
	private static Locale parseLocale(){
		Locale locale = ThreadLocalContext.getLocale();
		if(locale == null){
			locale = Locale.getDefault();
		}
		return locale;
	}

}
