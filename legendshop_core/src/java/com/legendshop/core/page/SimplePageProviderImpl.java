/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.page;

import java.util.Locale;
import java.util.ResourceBundle;

import com.legendshop.core.AttributeKeys;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------.
 */
public class SimplePageProviderImpl extends Pager implements PageProvider {
	// 生成工具条，采用数字形式和POST方式
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.page.Pager#getBar(java.util.Locale,
	 * java.lang.String)
	 */
	@Override
	public String getBar(Locale locale, String url) {
		// String path = PagerUtil.getPath();
		StringBuilder str = new StringBuilder();
		if (isFirst()) {
			// str.append(getString(locale, "pager.previous", "Previous"));
		} else {
			str.append("<a class='pagerDirectionLeft' href='").append(url).append("(").append(previous()).append(")'>")
					.append("&nbsp;").append(getString(locale, "pager.previous", "Previous")).append("&nbsp;").append("</a>");
		}

		int begin = (getCurPageNO() >= 10) ? getCurPageNO() - 9 : 1;
		int end = (getPageCount() - getCurPageNO() >= 10) ? getCurPageNO() + 9 : getPageCount();

		for (int i = begin; i <= end; i++) {
			if (i == getCurPageNO()) {
				str.append("<span class='pagerSelected'>").append(i).append("</span>");
			} else {
				str.append("<a class='pagerUnselected' href='").append(url).append("(").append(i).append(")'>");
				str.append(i).append("</a>");
			}
		}

		if (isLast() || (getRowsCount() == 0)) {
			str.append("&nbsp;&nbsp;");
		} else {
			str.append("<a class='pagerDirectionRight' href='").append(url).append("(").append(next()).append(")'>")
					.append("&nbsp;").append(getString(locale, "pager.next", "Next")).append("&nbsp;").append("</a>");
		}

		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.page.Pager#getBar(java.lang.String)
	 */
	@Override
	public String getBar(String url) {
		return getBar(null, url);
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
	private String getString(Locale locale, String key, String defaultValue) {
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
}
