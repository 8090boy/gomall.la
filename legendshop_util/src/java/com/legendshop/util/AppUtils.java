/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class AppUtils {

	/** The logger. */
	private static Logger logger = Logger.getLogger(AppUtils.class);

	/**
	 * Get locale cookie.
	 * 
	 * @param request
	 *            the request
	 * @return Locale
	 */
	public static Locale getLocaleFromCookie(HttpServletRequest request) {
		Locale locale = null;
		Cookie cookie = null;
		String country = null;
		String language = null;
		Cookie[] cookies = request.getCookies();

		// Start locale processing.
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if ("Language".equals(cookie.getName())) {
					language = cookie.getValue();
					logger.debug("Found language cookie with value = " + language);
				} else if (cookie.getName().equals("Country")) {
					country = cookie.getValue();
					logger.debug("Found country cookie with value = " + country);
				}
			}
		}

		if (country != null && language != null) {
			locale = new Locale(language, country);
		}

		return locale;
	}

	/**
	 * <p>
	 * String representation of a calendar. Format: MM/DD/YYYY
	 * </p>
	 * 
	 * @param pCalendar
	 *            the calendar
	 * @return String
	 */
	public static String getDisplayDate(Calendar pCalendar) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		if (pCalendar != null)
			return format.format(pCalendar.getTime());
		else
			return "";
	}

	/**
	 * <p>
	 * Convert string representation of a date to calendar.
	 * </p>
	 * 
	 * @param str
	 *            the str
	 * @return Calendar
	 */
	public static Calendar str2Calendar(String str) {
		Calendar cal = null;
		if (isNotBlank(str)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date d = sdf.parse(str);
				cal = Calendar.getInstance();
				cal.setTime(d);
			} catch (ParseException e) {
			}
		}
		return cal;
	}

	/**
	 * <p>
	 * String representation of current date. Format: MM/DD/YYYY
	 * </p>
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		return getDisplayDate(GregorianCalendar.getInstance());
	}

	/**
	 * <p>
	 * Get localhost and port url root, i.e. http://localhost:port
	 * </p>
	 * 
	 * @param request
	 *            the request
	 * @return String
	 */
	public static String getUrlRoot(HttpServletRequest request) {
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String nextPage = "http://" + serverName + ":" + serverPort;
		logger.debug("URL root: " + nextPage);
		return nextPage;
	}

	/**
	 * Gets the gB string.
	 * 
	 * @param s
	 *            the s
	 * @return the gB string
	 */
	public static String getGBString(String s) {
		try {
			return new String(s.getBytes("ISO-8859-1"), "GB2312");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets the iSO string.
	 * 
	 * @param s
	 *            the s
	 * @return the iSO string
	 */
	public static String getISOString(String s) {
		try {
			return new String(s.getBytes("GB2312"), "ISO-8859-1");
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Checks if is blank.
	 * 
	 * @param str
	 *            the str
	 * @return true, if is blank
	 */
	public static boolean isBlank(final String str) {
		return (str == null) || (str.trim().length() <= 0);
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param str
	 *            the str
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final String str) {
		return !(isBlank(str));
	}

	/**
	 * Checks if is blank.
	 * 
	 * @param objs
	 *            the objs
	 * @return true, if is blank
	 */
	public static boolean isBlank(final Object[] objs) {
		return (objs == null) || (objs.length <= 0);
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param objs
	 *            the objs
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final Object[] objs) {
		return !(isBlank(objs));
	}

	/**
	 * Checks if is blank.
	 * 
	 * @param objs
	 *            the objs
	 * @return true, if is blank
	 */
	public static boolean isBlank(final Object objs) {
		return (objs == null) || "".equals(objs);
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param objs
	 *            the objs
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final Object objs) {
		return !(isBlank(objs));
	}

	/**
	 * Checks if is blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is blank
	 */
	public static boolean isBlank(final Collection obj) {
		return (obj == null) || (obj.size() <= 0);
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final Collection obj) {
		return !(isBlank(obj));
	}

	/**
	 * Checks if is blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is blank
	 */
	public static boolean isBlank(final Set obj) {
		return (obj == null) || (obj.size() <= 0);
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final Set obj) {
		return !(isBlank(obj));
	}

	/**
	 * Checks if is blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is blank
	 */
	public static boolean isBlank(final Serializable obj) {
		return obj == null;
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final Serializable obj) {
		return !(isBlank(obj));
	}

	/**
	 * Checks if is blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is blank
	 */
	public static boolean isBlank(final Map obj) {
		return (obj == null) || (obj.size() <= 0);
	}

	/**
	 * Checks if is not blank.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is not blank
	 */
	public static boolean isNotBlank(final Map obj) {
		return !(isBlank(obj));
	}

	/**
	 * List2 strings.
	 * 
	 * @param list
	 *            the list
	 * @return the string[]
	 */
	public static String[] list2Strings(List<String> list) {
		String[] value = null;
		try {
			if (list == null)
				return null;
			value = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				value[i] = (String) list.get(i);
			}
		} catch (Exception e) {
			logger.error("list is null: " + e);
		}
		return value;
	}

	/**
	 * List2 string.
	 * 
	 * @param list
	 *            the list
	 * @return the string
	 */
	public static String list2String(List<Object> list) {
		if (AppUtils.isBlank(list)) {
			return "";
		}
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(list.get(0));
		for (int idx = 1; idx < list.size(); idx++) {
			sbuf.append(",");
			sbuf.append(list.get(idx));
		}
		return sbuf.toString();
	}

	/**
	 * Strings2list.
	 * 
	 * @param args
	 *            the args
	 * @return the list
	 */
	public static List<String> Strings2List(String[] args) {
		List<String> list = new ArrayList<String>();
		try {
			if (args == null)
				return null;
			for (int i = 0; i < args.length; i++) {
				list.add(args[i]);
			}
		} catch (Exception e) {
			logger.error("list is null: " + e);
		}
		return list;
	}

	/**
	 * Gets the strings.
	 * 
	 * @param str
	 *            the str
	 * @return the strings
	 */
	public static String[] getStrings(String str) {
		List<String> values = getStringCollection(str);
		if (values.size() == 0) {
			return null;
		}
		return values.toArray(new String[values.size()]);
	}

	/**
	 * Gets the string collection.
	 * 
	 * @param str
	 *            the str
	 * @return the string collection
	 */
	public static List<String> getStringCollection(String str) {
		List<String> values = new ArrayList<String>();
		if (str == null)
			return values;
		StringTokenizer tokenizer = new StringTokenizer(str, ",");
		values = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			values.add(tokenizer.nextToken());
		}
		return values;
	}

	/**
	 * 实现：input框内可以输入多个字符，用空格或逗号分割查询条件，逗号和空格可以混用，最后把查询的条件一并列出 例如输入：中国，美国, 法国 德国，
	 * ，日本 ，可以将符合各国的条件一并查出，忽略不符合规则的输入
	 * 
	 * 需处理： 1.将多个空格格式化为一个空格 2.逗号分割既可用英文标点，又可以用中文标点 3.排除其他标点符号，如查询条件中有其他标点符号，则忽略掉
	 * 4.同时满足两个条件混查
	 * 
	 * @param keyword
	 *            the keyword
	 * @return the string[]
	 */
	public static String[] searchByKeyword(String keyword) {
		Pattern p = Pattern.compile("[' ']+").compile("[.。！？#@#￥$%&*()（）=《》<>‘、’；：\"\\?!:']");
		// 正则，过滤多余空格和其他中英文标点
		Matcher m = p.matcher(keyword);
		String list1 = m.replaceAll(" ").replaceAll("，", ",");
		// replaceAll 将中文逗号替换为英文逗号
		String list2 = StringUtils.replace(list1, " ", ",");
		// 将所有单个空格替换为英文逗号
		String[] list = StringUtils.split(list2, ",");
		// 英文逗号为界，分割为数组
		return list;
		// for (int j = 0; j < list.length; j++) {
		// System.out.println("list["+j+"]=" + list[j]);
		// //处理后的查询字符串
		// }
	}

	/**
	 * Format number.
	 * 
	 * @param number
	 *            the number
	 * @return the string
	 */
	public static String formatNumber(Long number) {
		if (number == null) {
			return null;
		}
		NumberFormat format = NumberFormat.getIntegerInstance();
		// 设置数字的位数 由实际情况的最大数字决定
		format.setMinimumIntegerDigits(8);
		// 是否按每三位隔开,如:1234567 将被格式化为 1,234,567。在这里选择 否
		format.setGroupingUsed(false);
		return format.format(number);
	}

	/**
	 * Gets the cR c32.
	 * 
	 * @param value
	 *            the value
	 * @return the cR c32
	 */
	public static Long getCRC32(String value) {
		CRC32 crc32 = new CRC32();
		crc32.update(value.getBytes());
		return crc32.getValue();
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(AppUtils.getCRC32("123"));
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
	public static String convertTemplate(String fileName, String pattern, Map values) throws MalformedPatternException {
		String record = null;
		StringBuffer sb = new StringBuffer();
		try {
			File inFile = new File(fileName);
			if (!inFile.exists()) {
				return sb.toString();
			}
			FileInputStream fileInputStream = new FileInputStream(inFile);
			BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

			record = new String();
			while ((record = inBufferedReader.readLine()) != null) {
				sb.append(StringUtil.convert(record, pattern, values) + "\n");
			}
			inBufferedReader.close();
			fileInputStream.close();
		} catch (IOException e) {
			System.out.println("got an IOException error!");
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Convert template.
	 * 
	 * @param fileName
	 *            the file name
	 * @param values
	 *            the values
	 * @return the string
	 * @throws MalformedPatternException
	 *             the malformed pattern exception
	 */
	public static String convertTemplate(String fileName, Map values) throws MalformedPatternException {
		return convertTemplate(fileName, "\\#[a-zA-Z]+\\#", values);
	}

	/**
	 * Array to string.
	 * 
	 * @param strs
	 *            the strs
	 * @return the string
	 */
	public static String arrayToString(String[] strs) {
		if (strs.length == 0) {
			return "";
		}
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(strs[0]);
		for (int idx = 1; idx < strs.length; idx++) {
			sbuf.append(",");
			sbuf.append(strs[idx]);
		}
		return sbuf.toString();
	}

	/**
	 * Gets the default value.
	 * 
	 * @param value
	 *            the value
	 * @param defaultValue
	 *            the default value
	 * @return the default value
	 */
	public static String getDefaultValue(String value, String defaultValue) {
		if (isNotBlank(value)) {
			return value;
		} else {
			return defaultValue;
		}
	}
}
