/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.util.AppUtils;

/**
 * The Class AbstractQuery.
 */
public abstract class AbstractQuery {
	/** The cur page. */
	protected String curPage = null;

	/** The page size. */
	protected int pageSize = 10;

	/** The myaction. */
	protected String myaction = "javascript:pager";

	/** The page provider. */
	protected PageProviderEnum pageProvider;

	/**
	 * Gets the cur page.
	 * 
	 * @return the cur page
	 */
	public String getCurPage() {
		return curPage;
	}

	/**
	 * Sets the cur page.
	 * 
	 * @param curPage
	 *            the new cur page
	 */
	public void setCurPage(String curPage) {
		if (AppUtils.isBlank(curPage)) {
			this.curPage = "1";
		} else {
			this.curPage = curPage;
		}
	}

	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the page size.
	 * 
	 * @param pageSize
	 *            the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	/**
	 * Gets the page provider.
	 * 
	 * @return the page provider
	 */
	public PageProviderEnum getPageProvider() {
		return pageProvider;
	}

	/**
	 * Sets the page provider.
	 * 
	 * @param pageProvider
	 *            the new page provider
	 */
	public void setPageProvider(PageProviderEnum pageProvider) {
		this.pageProvider = pageProvider;
	}
	
	public void parseExportPageSize(){
		if (!FoundationUtil.isDataForExport(this, ThreadLocalContext.getRequest())) {// 非导出情况
			this.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		}
	}

}
