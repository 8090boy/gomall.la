/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.page;

import java.util.Locale;

import com.legendshop.util.ContextServiceLocator;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class PagerUtil {

	/** The path. */
	private static String path;

	/**
	 * Gets the bar.
	 * 
	 * @param action
	 *            the action
	 * @param form
	 *            the form
	 * @param allCounts
	 *            总记录数
	 * @param curPageNO
	 *            the cur page no
	 * @param pageSize
	 *            每页显示的记录数目
	 * @return the bar
	 */
	public static String getBar(String action, String form, int allCounts, int curPageNO, int pageSize) {
		Pager pager = null;
		try {
			if (curPageNO > (int) Math.ceil((double) allCounts / pageSize)) {
				curPageNO = (int) Math.ceil((double) allCounts / pageSize);
			}
			if (curPageNO < 1) {
				curPageNO = 1;
			}
			// 得到offset
			int offset = (curPageNO - 1) * pageSize;// 从第几条开始取值
			// 生成工具条
			pager = new Pager(allCounts, offset, pageSize);
			pager.setCurPageNO(curPageNO);// 设置当前的页码
		} catch (Exception e) {
			System.out.println("生成工具条出错!");
		}
		return pager.getToolBar(action, form);
	}

	/**
	 * Gets the bar.
	 * 
	 * @param url
	 *            the url
	 * @param allCounts
	 *            the all counts
	 * @param curPageNO
	 *            the cur page no
	 * @param pageSize
	 *            the page size
	 * @return the bar
	 */
	public static String getBar(String url, long allCounts, int curPageNO, int pageSize) {
		Pager pager = null;
		try {
			if (curPageNO > (int) Math.ceil((double) allCounts / pageSize)) {
				curPageNO = (int) Math.ceil((double) allCounts / pageSize);
			}
			if (curPageNO < 1) {
				curPageNO = 1;
			}
			// 得到offset
			int offset = (curPageNO - 1) * pageSize;// 从第几条开始取值
			// 生成工具条
			pager = new Pager(allCounts, offset, pageSize);
			pager.setCurPageNO(curPageNO);// 设置当前的页码
		} catch (Exception e) {
			System.out.println("生成工具条出错!");
		}
		return pager.getToolBar(url);
	}

	/**
	 * Gets the locale bar.
	 * 
	 * @param locale
	 *            the locale
	 * @param url
	 *            the url
	 * @param allCounts
	 *            the all counts
	 * @param curPageNO
	 *            the cur page no
	 * @param pageSize
	 *            the page size
	 * @param pageProvider
	 *            the page provider
	 * @return the locale bar
	 */
	public static String getLocaleBar(Locale locale, String url, long allCounts, int curPageNO, int pageSize, int offset,
			String pageProvider) {
		Pager pager = (Pager) ContextServiceLocator.getInstance().getBean(pageProvider);
		try {
			// 生成工具条
			if (pager == null) {
				pager = new Pager(allCounts, offset, pageSize);
			} else {
				pager.init(allCounts, offset, pageSize);
			}
			pager.setCurPageNO(curPageNO);// 设置当前的页码
		} catch (Exception e) {
			System.out.println("生成工具条出错!");
		}
		return pager.getBar(locale, url);
	}

	/**
	 * Gets the offset.
	 * 
	 * @param rowCounts
	 *            the row counts
	 * @param curPageNO
	 *            the cur page no
	 * @param pageSize
	 *            the page size
	 * @return the offset
	 */
	public static int getOffset(long rowCounts, int curPageNO, int pageSize) {
		int offset = 0;
		try {
			// 得到第几页

			if (curPageNO > (int) Math.ceil((double) rowCounts / pageSize)) {
				curPageNO = (int) Math.ceil((double) rowCounts / pageSize);
			}
			if (curPageNO < 1) {
				curPageNO = 1;
			}
			// 得到offset
			offset = (curPageNO - 1) * pageSize;
		} catch (Exception e) {
			System.out.println("getOffset出错!");
		}
		return offset;
	}

	/**
	 * Gets the cur page no.
	 * 
	 * @param curPage
	 *            the cur page
	 * @return the cur page no
	 */
	public static int getCurPageNO(String curPage) {
		int curPageNO;
		if ((curPage == null) || "".equals(curPage.trim())) {
			curPageNO = 1;// 第一次处于第一页
		} else {
			try {
				curPageNO = Integer.parseInt(curPage);// 得到当前页
			} catch (Exception e) {
				curPageNO = 1;
			}
		}
		return curPageNO;
	}

	/**
	 * Gets the path.
	 * 
	 * @return the path
	 */
	public static String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 * 
	 * @param path
	 *            the new path
	 */
	public static void setPath(String path) {
		PagerUtil.path = path;
	}
}
