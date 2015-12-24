/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.page.PagerUtil;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class PageSupport implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7058040526668991342L;

	/** The cur page no. */
	private int curPageNO;

	/** The offset. */
	private int offset;

	/** The page size. */
	private int pageSize;

	/** The total. */
	private long total = 0;

	/** The tool bar. */
	private String toolBar;

	/** The myaction. */
	private String myaction;

	/** The result list. */
	private List<?> resultList = null;

	/**
	 * Instantiates a new page support.
	 */
	public PageSupport() {

	}

	/**
	 * Instantiates a new page support.
	 * 
	 * @param resultList
	 *            the result list
	 * @param myaction
	 *            the myaction
	 * @param offset
	 *            the offset
	 * @param curPageNO
	 *            the cur page no
	 * @param total
	 *            the total
	 * @param pageSize
	 *            the page size
	 */
	public PageSupport(List<?> resultList, String myaction, int offset, int curPageNO, long total, int pageSize,
			PageProviderEnum pageProvider) {
		this.curPageNO = curPageNO;
		this.offset = offset;
		this.myaction = myaction;
		this.resultList = resultList;
		this.total = total;
		this.pageSize = pageSize;
		if (pageProvider != null) {
			initAndSetToRequest(pageProvider);
		}
	}

	/**
	 * Gets the result list.
	 * 
	 * @return the result list
	 */
	public List getResultList() {
		return resultList;
	}

	/**
	 * Sets the result list.
	 * 
	 * @param resultList
	 *            the new result list
	 */
	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	/**
	 * Gets the tool bar.
	 * 
	 * @param locale
	 *            the locale
	 * @return the tool bar
	 */
	public String getToolBar() {
		return toolBar;
	}

	/**
	 * Sets the tool bar. 应该在Dao里面拿到数据
	 * 
	 * @param locale
	 *            the locale
	 * @param pageProvider
	 *            the page provider
	 */
	public void initAndSetToRequest(PageProviderEnum pageProvider) {
		if (toolBar == null && hasMutilPage()) {
			this.toolBar = PagerUtil.getLocaleBar(ThreadLocalContext.getLocale(), myaction, total, curPageNO, pageSize, offset,
					pageProvider.value());
		}
	}

	public void savePage(HttpServletRequest request) {
		request.setAttribute("offset", offset + 1);
		request.setAttribute("list", resultList);
		request.setAttribute("curPageNO", curPageNO);
		request.setAttribute("toolBar", toolBar);
	}

	/**
	 * Gets the offset.
	 * 
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset.
	 * 
	 * @param offset
	 *            the new offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Gets the cur page no.
	 * 
	 * @return the cur page no
	 */
	public int getCurPageNO() {
		return curPageNO;
	}

	/**
	 * Sets the cur page no.
	 * 
	 * @param curPageNO
	 *            the new cur page no
	 */
	public void setCurPageNO(int curPageNO) {
		this.curPageNO = curPageNO;
	}

	/**
	 * Gets the total.
	 * 
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	// 是否有分页
	/**
	 * Checks for mutil page.
	 * 
	 * @return true, if successful
	 */
	public boolean hasMutilPage() {
		return total > pageSize;
	}

	/**
	 * Gets the myaction.
	 * 
	 * @return the myaction
	 */
	public String getMyaction() {
		return myaction;
	}

	/**
	 * Sets the myaction.
	 * 
	 * @param myaction
	 *            the new myaction
	 */
	public void setMyaction(String myaction) {
		this.myaction = myaction;
	}
}
