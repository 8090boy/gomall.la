/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.sql;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.legendshop.util.AppUtils;

/**
 * 
 * 动态参数替换原则 1、每一行只有一对大括号{}，每个括号里有一个将要替换的参数
 * 2、用$$括起来的参数（key）将会用parameterMap中的value代替
 * 3、采用objectName.MethodName作为key放在parameterMap中
 * 
 * 动态参数替换用法 1、{之后跟?,表示如果不传值的话这个条件忽略
 * 2、{之后紧跟!表示这个条件即使没有传值过来就采用默认值"",如果带有||则将||之前的作为默认值 3、{之后直接是参数，有则整个替换，没有则忽略，例如：
 * { $haveShop$ }
 * 
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */

public class DynamicCode {

	/** The logger. */
	Logger logger = Logger.getLogger(DynamicCode.class);

	/** The Constant $PARAM_PATTERN. */
	private static final String $PARAM_PATTERN = "\\$.*?\\$"; // 外面套有$$
																// ,//"\\$+[a-zA-Z]+\\$";
																// 我们更需要懒惰匹配，也就是匹配尽可能少的字符。前面给出的限定符都可以被转化为懒惰匹配模式，只要在它后面加上一个问号?。这样.*?就意味着匹配任意数量的重复，但是在能使整个匹配成功的前提下使用最少的重复
	
	private static final String PLACEHOLDER_PATTERN = "\\#.*?\\#"; // 外面套有##
	/**
	 * The Constant BLOCK_PATTERN .
	 */
	private static final String BLOCK_PATTERN = "{.*?}"; // 外面包有{}的任何字符
	// "\\{.*\\}"

	/**
	 * The Constant NO_REPLACE_PATTERN .
	 */
	private static final String NO_REPLACE_PATTERN = "{!";

	/** The Constant QUESTION_MARK_PATTERN. */
	private static final String QUESTION_MARK_PATTERN = "{?";

	private static final String PREFIX_PATTERN = "{";

	private static final String SUFFIX_PATTERN = "}";

	/** 默认值 */
	private static final String DEFAULT_PATTERN = "||";

	/**
	 * Instantiates a new dynamic code.
	 */
	public DynamicCode() {
		// 构造函数
	}

	/**
	 * 转换SQL，填入参数 patternString 外面套有大括号.
	 * 
	 * @param text
	 *            the text
	 * @param patternString
	 *            the pattern string
	 * @param map
	 *            the map
	 * @return the string
	 * @throws MalformedPatternException
	 *             the malformed pattern exception
	 */
	public String convert(String text, String patternString, Map<String, Object> map) throws MalformedPatternException {
		PatternMatcher matcher;
		PatternCompiler compiler;
		Pattern pattern;
		PatternMatcherInput input;
		MatchResult result;
		StringBuffer textBuf = new StringBuffer(text);
		compiler = new Perl5Compiler();
		pattern = compiler.compile(patternString);
		matcher = new Perl5Matcher();
		input = new PatternMatcherInput(text.toString());
		int offset = 0;
		while (matcher.contains(input, pattern)) {
			// System.out.println("符合{}条件的记录");
			result = matcher.getMatch();
			int len = result.length();
			String key = result.toString();
			String value = replaceParameter(key, map);
			textBuf.replace(result.beginOffset(0) + offset, result.endOffset(0) + offset, value);
			offset += value.length() - len;
		}

		return textBuf.toString();
	}
	
	//递归调用替换##的内容
	public void fillPlaceHolder(String codeKey, Map<String, String> parameters, Map<String,Boolean> recursionChecking) throws MalformedPatternException {
			PatternMatcher matcher;
			PatternCompiler compiler;
			Pattern pattern;
			PatternMatcherInput input;
			MatchResult result;
			String code = parameters.get(codeKey);
			StringBuffer textBuf = new StringBuffer(code);
			compiler = new Perl5Compiler();
			pattern = compiler.compile(PLACEHOLDER_PATTERN);
			matcher = new Perl5Matcher();
			input = new PatternMatcherInput(code);
			int offset = 0;
			while (matcher.contains(input, pattern)) {
				result = matcher.getMatch();
				int len = result.length();
				String wrappedkey = result.toString();
				String key = wrappedkey.replace("#", "").trim();
				String value = parameters.get(key);
				//System.out.println("codeKey = "+ codeKey + ",  key = " + key + ",  value = " + value);
				if(!recursionChecking.containsKey(key)){
					recursionChecking.put(key, true);
				}else{
					throw new RuntimeException("ConfigCode recursion detected, codeKey = " + codeKey + ",  key = " + key + ", value = " + value);
				}
				if(AppUtils.isNotBlank(value)){
					fillPlaceHolder(key, parameters,recursionChecking);
				}
				value = parameters.get(key);
				if(AppUtils.isBlank(value)){
					throw new RuntimeException("can not get value from key = " + key);
				}
				textBuf.replace(result.beginOffset(0) + offset, result.endOffset(0) + offset, value);
				parameters.put(codeKey, textBuf.toString());
				offset += value.length() - len;
		}


	}

	/**
	 * Convert.
	 * 
	 * @param text
	 *            the text
	 * @param map
	 *            the map
	 * @return the string
	 */
	public String convert(String text, Map<String, Object> map) {
		try {
			if (AppUtils.isBlank(text))
				return null;
			return convert(text, BLOCK_PATTERN, map);
		} catch (Exception e) {
			logger.error("获取动态SQL出错" + e);
			return null;
		}
	}
	
	/**
	 * 代替$$中的参数，如果参数为null ，则返回空字符串.
	 * 
	 * @param text
	 *            the text
	 * @param map
	 *            the map
	 * @return the string
	 * @throws MalformedPatternException
	 *             the malformed pattern exception
	 */
	private String replaceParameter(String text, Map<String, Object> map) throws MalformedPatternException {
		PatternMatcher matcher;
		PatternCompiler compiler;
		Pattern pattern;
		PatternMatcherInput input;
		MatchResult result;
		boolean noRepace = text.startsWith(NO_REPLACE_PATTERN);
		boolean mark = text.startsWith(QUESTION_MARK_PATTERN);
		StringBuffer textBuf = null;
		String prefix = null;
		if (noRepace) {
			int prefixpos = text.indexOf(DEFAULT_PATTERN);
			if (prefixpos != -1) {
				prefix = text.substring(0, text.indexOf(DEFAULT_PATTERN) + 2);
			} else {
				prefix = NO_REPLACE_PATTERN;
			}
			textBuf = new StringBuffer(getSubString(text, prefix, SUFFIX_PATTERN));// 去掉大括号,采用不替换整句的模式
		} else if (mark) {// 以问号开始，把参数换成?
			textBuf = new StringBuffer(getSubString(text, QUESTION_MARK_PATTERN, SUFFIX_PATTERN));
		} else {
			textBuf = new StringBuffer(getSubString(text, PREFIX_PATTERN, SUFFIX_PATTERN));// 去掉大括号
		}
		compiler = new Perl5Compiler();
		pattern = compiler.compile($PARAM_PATTERN);
		matcher = new Perl5Matcher();
		input = new PatternMatcherInput(textBuf.toString());
		int offset = 0;
		while (matcher.contains(input, pattern)) {
			result = matcher.getMatch();
			int len = result.length();
			String key = result.toString();
			String value = null;
			Object obj = map.get(key.substring(1, key.length() - 1));// 去掉外套$$
			if (obj != null) {
				value = obj.toString();
			}
			if (noRepace && value == null) {
				String def = getSubString(prefix, NO_REPLACE_PATTERN, DEFAULT_PATTERN);// 如果没有设值则用默认字符串代替
				if (!(def.startsWith(NO_REPLACE_PATTERN) || def.endsWith(DEFAULT_PATTERN))) {
					value = def.trim();
				} else {
					value = "";
				}

			}
			if (value != null) {// 找不到对应的值，要整个条件忽略,找到则替换
				if (!mark) {
					textBuf.replace(result.beginOffset(0) + offset, result.endOffset(0) + offset, value);
					offset += value.length() - len;
				} else {// 如果是{?开头的，在有值的情况下则替换为问号
					textBuf.replace(result.beginOffset(0) + offset, result.endOffset(0) + offset, "?");
					offset += value.length() - len;
				}

			} else {
				return "";
			}

		}

		return textBuf.toString();
	}

	/**
	 * 去掉前缀和后缀.
	 * 
	 * @param text
	 *            之前已经去掉空格，此处不再处理空格问题。
	 * @param prefix
	 *            the prefix
	 * @param endTag
	 *            the end tag
	 * @return the sub string
	 */
	private String getSubString(String text, String prefix, String endTag) {
		if (text == null || text == prefix || text == endTag)
			return text;
		if (!(text.startsWith(prefix) && text.endsWith(endTag))) {
			return text;
		}
		return text.substring(prefix.length(), text.length() - endTag.length());
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws MalformedPatternException
	 *             the malformed pattern exception
	 */
	public static void main(String[] args) throws MalformedPatternException {
		DynamicCode test = new DynamicCode();
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			test.testNumber(test);
		}
		long t2 = System.currentTimeMillis();

		System.out.println("total time ：" + (t2 - t1));
		System.out.println("avage time ：" + (float) (t2 - t1) / 100);
	}

	/**
	 * Test number.
	 * 
	 * @param test
	 *            the test
	 * @throws MalformedPatternException
	 *             the malformed pattern exception
	 */
	private void testNumber(DynamicCode test) throws MalformedPatternException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		map.put("id1", "2");
		map.put("name", "hewq");
		map.put("condition", "and moc = 1");
		map.put("memo2", "df");
		// String pattern = "\\$+[a-zA-Z]+\\$";
		String text = " select * from t_scheme where 1==1 \n { and id = $id$   id1 = $id$ } \n { and name = $name$ } \n  {$condition$} \n {!     default value   || and  memo1 = $memo1$} \n { and memo2 = $memo2$}  \n order by id ";
		// String text = " select * from t_scheme where 1==1 { and id = $id$ } {
		// and name = $name$ } {! default value || and memo1 = $memo1$}";
		// String subString = test.getSubString(text, prefix,"}");
		// System.out.println("subString = "+subString);
		// String pattern = "\\#[a-zA-Z]+\\#";
		String newText = test.convert(text, BLOCK_PATTERN, map);
		System.out.println(newText);
	}

}
