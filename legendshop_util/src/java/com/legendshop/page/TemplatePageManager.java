/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.page;

import java.util.List;

/**
 * The Interface TemplatePageManager.
 */
public interface TemplatePageManager {

	/**
	 * Gets the template by page.
	 * 
	 * @param pageName
	 *            the page name
	 * @return the template by page
	 */
	public List<String> getTemplateByPage(String pageName);

	/**
	 * Register template page.
	 * 
	 * @param templatePage
	 *            the template page
	 */
	public void registerTemplatePage(TemplatePage templatePage);

}
