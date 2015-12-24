/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.regex.MalformedPatternException;

import com.legendshop.util.AppUtils;
import com.legendshop.util.StringUtil;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class StringConverter {

	/**
	 * Instantiates a new string converter.
	 */
	private StringConverter() {
	}

	/**
	 * Sets the first char upcase.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String setFirstCharUpcase(String s) {
		if (s == null || s.length() < 1)
			return s;
		char c[] = s.toCharArray();
		if (c.length > 0 && c[0] >= 'a' && c[0] <= 'z')
			c[0] = (char) ((short) c[0] - 32);
		return String.valueOf(c);
	}

	/**
	 * Sets the first char lowercase.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String setFirstCharLowercase(String s) {
		if (s == null || s.length() < 1)
			return s;
		char c[] = s.toCharArray();
		if (c.length > 0 && c[0] >= 'A' && c[0] <= 'Z')
			c[0] = (char) ((short) c[0] + 32);
		return String.valueOf(c);
	}

	/**
	 * To data base string.
	 * 
	 * @param strs
	 *            the strs
	 * @return the string[]
	 */
	public static String[] toDataBaseString(String[] strs) {
		if (strs == null || strs.length <= 0)
			return null;
		String[] newStr = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			newStr[i] = toDataBaseString(strs[i]);
		}
		return newStr;
	}

	/**
	 * To data base string.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	public static String toDataBaseString(String str) {
		if (str == null || str.trim().length() <= 0)
			return null;
		StringBuffer sb = new StringBuffer();
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (Character.isUpperCase(cs[i])) {// 大写
				sb.append("_").append(cs[i]);
			} else if (Character.isLowerCase(cs[i])) {// 小写
				sb.append(Character.toUpperCase(cs[i]));
			} else {// 其他的数字
				sb.append(cs[i]);
			}

		}

		return sb.toString();
	}

	/**
	 * To bo string.
	 * 
	 * @param strs
	 *            the strs
	 * @return the string[]
	 */
	public static String[] toBoString(String[] strs) {
		if (strs == null || strs.length <= 0)
			return null;
		String[] newStr = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			newStr[i] = toBoString(strs[i]);
		}
		return newStr;
	}

	/**
	 * To bo string.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	public static String toBoString(String str) {
		if (str == null || str.trim().length() <= 0)
			return null;
		StringBuffer sb = new StringBuffer();
		char[] cs = str.toLowerCase().toCharArray();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '_' && i == cs.length - 1) {
				sb.append(cs[i]);
			} else if (cs[i] == '_' && i < cs.length - 1) {
				sb.append(Character.toUpperCase(cs[i + 1]));
				i++;
			} else {
				sb.append(cs[i]);
			}

		}

		return sb.toString();
	}

	/**
	 * 读入模版并将参数代进去.
	 * 
	 * @param fileName
	 *            the file name
	 * @param pattern
	 *            the pattern
	 * @param values
	 *            the values
	 * @return the string
	 * @throws MalformedPatternException
	 *             the malformed pattern exception
	 */
	public static String convertTemplate(String fileName, String pattern, HashMap values) throws MalformedPatternException {
		String record = null;
		StringBuffer sb = new StringBuffer();
		// int recCount = 0;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			while ((record = br.readLine()) != null) {
				// recCount++;
				// System.out.println(recCount + ": " + record);
				sb.append(StringUtil.convert(record, pattern, values) + "\n");
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("oh! no, got an IOException error!");
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Replace place holder of template.
	 * 
	 * @param templateString
	 *            the template string
	 * @param placeHolder
	 *            the place holder
	 * @param values
	 *            the values
	 * @return the string
	 */
	public static String replacePlaceHolderOfTemplate(String templateString, String placeHolder, String... values) {
		if (StringUtils.isEmpty(templateString) || StringUtils.isEmpty(placeHolder) || AppUtils.isBlank(values)) {
			return templateString;
		}
		StringBuilder sb = new StringBuilder(templateString);
		int indexOfPlaceHolder = -1;
		int indexOfValues = 0;
		while ((indexOfPlaceHolder = sb.indexOf(placeHolder)) >= 0) {
			sb.replace(indexOfPlaceHolder, indexOfPlaceHolder + placeHolder.length(), values[indexOfValues++]);
		}

		return sb.toString();
	}

	public static void replacePlaceHolderOfTemplate(StringBuilder templateString, String placeHolder, String value) {
		int indexOfPlaceHolder = -1;
		if ((indexOfPlaceHolder = templateString.indexOf(placeHolder)) >= 0) {
			templateString.replace(indexOfPlaceHolder, indexOfPlaceHolder + placeHolder.length(), value);
		}
	}

	/**
	 * Replace place holder of template from splitted value.
	 * 
	 * @param templateString
	 *            the template string
	 * @param placeHolder
	 *            the place holder
	 * @param values
	 *            the values
	 * @param splitter
	 *            the splitter
	 * @return the string
	 */
	public static String replacePlaceHolderOfTemplateFromSplittedValue(String templateString, String placeHolder, String values,
			String splitter) {
		if (StringUtils.isEmpty(templateString) || StringUtils.isEmpty(placeHolder) || StringUtils.isEmpty(values)) {
			return templateString;
		} else {
			return replacePlaceHolderOfTemplate(templateString, placeHolder, StringUtils.split(values, splitter));
		}
	}

}