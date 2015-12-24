/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 * 
 */
public class CodeFilter {

	/**
	 * Instantiates a new code filter.
	 */
	public CodeFilter() {
	}

	// 特殊字符转为Html
	/**
	 * To html.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String toHtml(String s) {
		if (s == null) {
			s = "";
			return s;
		}
		s = Replace(s.trim(), "&", "&amp;");
		s = Replace(s.trim(), "<", "&lt;");
		s = Replace(s.trim(), ">", "&gt;");
		s = Replace(s.trim(), "\t", "    ");
		s = Replace(s.trim(), "\r\n", "\n");
		s = Replace(s.trim(), "\n", "<br>");
		s = Replace(s.trim(), "  ", " &nbsp;");
		s = Replace(s.trim(), "'", "&#39;");
		s = Replace(s.trim(), "\\", "&#92;");
		return s;
	}

	// 逆
	/**
	 * Un html.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String unHtml(String s) {
		s = Replace(s, "<br>", "\n");
		s = Replace(s, "</br>", "");
		s = Replace(s, "<p>", "");
		s = Replace(s, "</p>", "");
		s = Replace(s, "&nbsp;", "");
		s = Replace(s, "<strong>", "");
		s = Replace(s, "<div>", "");
		s = Replace(s, "</div>", "");
		s = Replace(s, "<span>", "");
		s = Replace(s, "</span>", "");
		s = Replace(s, "<", "");
		s = Replace(s, "/", "");
		s = Replace(s, ">", "");
		return s;
	}

	/**
	 * Html encode.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	public static String htmlEncode(String str) {
		if (str == null) {
			str = "";
			return str;
		}
		str = str.trim(); // 去掉首尾空格
		// str = replace(str, "<", "&#60;");
		// str = replace(str, ">", "&#62;");
		str = Replace(str, "\t", "    ");
		str = Replace(str, "\r\n", "\n");
		str = Replace(str, "\n", "<br>");
		str = Replace(str, "  ", " &nbsp;&nbsp;");
		str = Replace(str, "\"", "&#34;");
		return str;
	}

	/**
	 * Str encode.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	public static String strEncode(String str) {
		if (str == null) {
			str = "";
			return str;
		}
		str = str.trim(); // 去掉首尾空格
		str = Replace(str, "<", "&#60;");
		str = Replace(str, ">", "&#62;");
		str = Replace(str, "\t", "    ");
		str = Replace(str, "\r\n", "\n");
		str = Replace(str, "\n", "<br>");
		str = Replace(str, "  ", " &nbsp;&nbsp;");
		str = Replace(str, "'", "&#39;");
		str = Replace(str, "\\", "/");
		str = Replace(str, "\"", "&#34;");
		return str;
	}

	// Replace
	/**
	 * Replace.
	 * 
	 * @param source
	 *            the source
	 * @param oldString
	 *            the old string
	 * @param newString
	 *            the new string
	 * @return the string
	 */
	public static String Replace(String source, String oldString, String newString) {

		StringBuffer output = new StringBuffer();

		int lengthOfsource = source.length();// 源字符串长度
		int lengthOfold = oldString.length();// 老字符串长度

		int posStart = 0;// 开始搜索位置
		int pos;// 搜索到的老字符串的位置

		// source.indexOf(oldString,posStart)检索某子串在字符串postStart以后第一次出现的位置,如果未找到就返回一个-1。
		while ((pos = source.indexOf(oldString, posStart)) >= 0) {// 得到字符串的位置(eg:如果有<br>就执行，没有就跳出，不要处理。)

			// 将以posStart起始以pos-1结束之间的内容拷贝到另一个字符串中。因为posStar从0开始的。
			output.append(source.substring(posStart, pos));// append方法将文本添加到当前StringBuffer对象内容的结尾。
			output.append(newString);// 替换成新字符串

			posStart = pos + lengthOfold;// 位置也变为找到了之后的位置,pos为得到第一次出现字符的位置，lengthold为字符的长度

		}

		if (posStart < lengthOfsource) {
			// source.substring(posStart)以lengthOfsource开始的字符串拷贝到列一个字符串中
			output.append(source.substring(posStart));
		}
		// 这个方法将其内容转换成一个可以被用于输出的字符串对象。它允许操作对应的文本用于输出或数据存储。
		return output.toString();
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		CodeFilter filter = new CodeFilter();
		String str = "<p>ddd</p> <br>nsss</br>";
		// String result = filter.unHtml(str);
		String result = filter.unHtml(str);
		System.out.println(result);
	}
}
