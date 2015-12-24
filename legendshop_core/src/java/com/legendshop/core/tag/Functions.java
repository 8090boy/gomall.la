/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.util.List;

import com.legendshop.util.AppUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class Functions {

	/**
	 * 替换模板中占位符“#” arguments 为 参数值, 多值用“,”分隔.
	 * 
	 * @param templateString
	 *            the template string
	 * @param arguments
	 *            the arguments
	 * @return the string
	 */
	public static String replacePlaceHolder(String templateString, String arguments) {
		return StringConverter.replacePlaceHolderOfTemplateFromSplittedValue(templateString, "#", arguments, ",");
	}

	/**
	 * Replace place holder of template.
	 * 
	 * @param templateString
	 *            the template string
	 * @param arguments
	 *            the arguments
	 * @return the string
	 */
	public static String replacePlaceHolderOfTemplate(String templateString, String... arguments) {
		if (AppUtils.isBlank(arguments)) {
			return null;
		}
		StringBuilder result = new StringBuilder(templateString);
		for (int i = 0; i < arguments.length; i++) {
			StringConverter.replacePlaceHolderOfTemplate(result, "{" + i + "}", arguments[i]);
		}
		return result.toString();
	}

	/**
	 * Contains.
	 * 
	 * @param list
	 *            the list
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	public static boolean contains(List list, Object element) {
		return list.contains(element);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(Functions.replacePlaceHolder("123 # 456 # 789 # 0", "aa,bb,cc"));
		System.out.println(Functions.replacePlaceHolderOfTemplate("I {0} {2} newway {1} ", "am,123", "good morning", "ok"));
	}
}
