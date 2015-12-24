/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.sql;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ObjectSignature {

	/** The obj class name. */
	protected String objClassName;

	/** The method name. */
	protected String methodName = null;

	/**
	 * Instantiates a new object signature.
	 * 
	 * @param objClassName
	 *            the obj class name
	 */
	public ObjectSignature(String objClassName) {
		this.objClassName = objClassName;
	}

	/**
	 * Gets the object class name.
	 * 
	 * @return the object class name
	 */
	public String getObjectClassName() {
		return objClassName;
	}

	/**
	 * 计算内部名字（内部名字＝对象名＋方法名＋参数类型）.
	 * 
	 * @param objClassName
	 *            对象名
	 * @param methodName
	 *            方法名
	 * @return 内部名字
	 */
	public static String toSignature(String objClassName, String methodName) {
		return  new StringBuffer(objClassName).append('.').append(methodName).toString();
	}

	/**
	 * Gets the method name.
	 * 
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets the method name.
	 * 
	 * @param name
	 *            the new method name
	 */
	public void setMethodName(String name) {
		this.methodName = name;
	}

	/**
	 * 计算内部名字（内部名字＝对象名＋方法名＋参数类型）.
	 * 
	 * @return 内部名字
	 */
	public String toSignature() {
		return toSignature(getObjectClassName(), methodName);
	}

	/**
	 * Instantiates a new object signature.
	 */
	protected ObjectSignature() {
		objClassName = null;
	}

}
