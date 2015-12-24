/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.page.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legendshop.page.TemplatePageManager;
import com.legendshop.page.TemplatePage;

/**
 * The Class TemplatePageManagerImpl.
 */
public class TemplatePageManagerImpl implements TemplatePageManager {

	/** format: pages, <templates>. */
	private Map<String, List<String>> templates = new HashMap<String, List<String>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.page.TemplatePageManager#getTemplateByPage(java.lang.String)
	 */
	public List<String> getTemplateByPage(String pageName) {
		return templates.get(pageName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.page.TemplatePageManager#registerTemplatePage(com.legendshop
	 * .page.TemplatePage)
	 */
	public void registerTemplatePage(TemplatePage templatePage) {
		for (String page : templatePage.getPageNames()) {
			List<String> template = templates.get(page);
			if (template == null) {
				List<String> temps = new ArrayList<String>();
				temps.add(templatePage.getTemplate());
				templates.put(page, temps);
			} else {
				template.add(templatePage.getTemplate());
				templates.put(page, template);
			}
		}

	}

}
