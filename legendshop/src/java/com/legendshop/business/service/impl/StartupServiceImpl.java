/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import com.legendshop.core.StartupService;
import com.legendshop.core.helper.SpringBeanManager;
import com.legendshop.page.TemplatePage;
import com.legendshop.page.TemplatePageManager;
import com.legendshop.util.AppUtils;
import com.legendshop.util.handler.PluginRepository;

/**
 * 系统启动时初始化
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 * 
 * ----------------------------------------------------------------------------
 */
public class StartupServiceImpl implements StartupService {

	/** The template manager. */
	private TemplatePageManager templatePageManager;

	private SpringBeanManager springBeanManager;

	/** The is inited. */
	private Boolean isInited = false;

	/**
	 * 初始化,注意顺序不能调转.
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	@Override
	public void startup(ServletContext servletContext) {
		synchronized (isInited) {
			// init all plugins
			if (!isInited) {
				PluginRepository.getInstance().startPlugins(servletContext);

				// initial template pages, order by priority
				Map<String, TemplatePage> templatePages = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(TemplatePage.class);
				
				if (AppUtils.isNotBlank(templatePages)) {
					List<TemplatePage> templatePageList = createTemplatePageList(templatePages.values());
					Collections.sort(templatePageList);
					for (TemplatePage tp : templatePageList) {
						templatePageManager.registerTemplatePage(tp);
					}

					// remove spring bean
					springBeanManager.removeBean(servletContext, templatePages.keySet());
				}
				
				//init Menu
				
				isInited = true;
			}
		}
	}

	private List<TemplatePage> createTemplatePageList(Collection<TemplatePage> templatePages) {
		if (templatePages == null) {
			return null;
		}
		List<TemplatePage> templatePageList = new ArrayList<TemplatePage>();
		for (TemplatePage templatePage : templatePages) {
			templatePageList.add(templatePage);
		}
		return templatePageList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.StartupService#destory(javax.servlet.ServletContext)
	 */
	@Override
	public void destory(ServletContext servletContext) {
		synchronized (isInited) {
			if (!isInited) {
				return;
			}
			PluginRepository.getInstance().stopPlugins(servletContext);
		}
	}

	/**
	 * Sets the template manager.
	 * 
	 * @param templatePageManager
	 *            the new template manager
	 */
	public void setTemplatePageManager(TemplatePageManager templatePageManager) {
		this.templatePageManager = templatePageManager;
	}

	public void setSpringBeanManager(SpringBeanManager springBeanManager) {
		this.springBeanManager = springBeanManager;
	}

}
