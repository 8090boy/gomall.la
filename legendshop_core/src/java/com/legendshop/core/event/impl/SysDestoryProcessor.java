/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.event.impl;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.StartupService;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.event.processor.BaseProcessor;

/**
 * 系统关闭事件
 */
public class SysDestoryProcessor extends BaseProcessor<ServletContextEvent> {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(SysDestoryProcessor.class);

	/** The startup service. */
	private StartupService startupService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.event.processor.AbstractProcessor#process(java.lang.Object
	 * )
	 */
	@Override
	public void process(ServletContextEvent event) {
		if (PropertiesUtil.isSystemInstalled()) {
			log.info("********* system destory *************");
			startupService.destory(event.getServletContext());
		}
	}

	public void setStartupService(StartupService startupService) {
		this.startupService = startupService;
	}

}
