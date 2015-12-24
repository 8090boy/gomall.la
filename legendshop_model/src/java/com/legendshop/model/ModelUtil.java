/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

/**
 * The Class ModelUtil. 检查Model的属性
 */
public class ModelUtil {

	/**
	 * Checks if is blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is blank
	 */
	public void isNull(Object obj) {
		boolean result = (obj == null) || "".equals(obj);
		if (!result) {
			throw new ValidationException(obj + " is Not Null");
		}
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is not blank
	 */
	public void isNotNull(Object obj, String message) {
		boolean result = (obj == null) || "".equals(obj);
		if (result) {
			throw new ValidationException(message + " is Null");
		}
	}

	/**
	 * Range.
	 * 
	 * @param str
	 *            the str
	 * @param minLength
	 *            the min length
	 * @param maxLength
	 *            the max length
	 * @return true, if successful
	 */
	public void range(String str, int minLength, int maxLength, String message) {
		boolean result = (str == null) || "".equals(str);
		if (result) {
			throw new ValidationException(message + " is Null");
		} else {
			if (str.length() >= minLength && str.length() <= maxLength) {
				// normal
			} else {
				throw new ValidationException(str + "' length is not in range [ " + minLength + ", " + maxLength + " ]");
			}
		}
	}

	/**
	 * Lt.
	 * 
	 * @param str
	 *            the str
	 * @param maxLength
	 *            the max length
	 * @return true, if successful
	 */
	public void lt(String str, int maxLength) {
		boolean result = (str == null) || "".equals(str);
		if (result) {
			throw new ValidationException("Object " + str + " is Null");
		} else {
			if (str.length() <= maxLength) {
			} else {
				throw new ValidationException(str + "' length is max then  " + maxLength);
			}
		}
	}

	/**
	 * Gt.
	 * 
	 * @param str
	 *            the str
	 * @param minLength
	 *            the min length
	 * @return true, if successful
	 */
	public void gt(String str, int minLength) {
		boolean result = (str == null) || "".equals(str);
		if (result) {
			throw new ValidationException("Object " + str + " is Null");
		} else {
			if (str.length() >= minLength) {
			} else {
				throw new ValidationException(str + "' length is less then  " + minLength);
			}
		}
	}

}
