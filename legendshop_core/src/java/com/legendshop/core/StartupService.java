package com.legendshop.core;

import javax.servlet.ServletContext;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public interface StartupService {
	/**
	 * 初始化
	 * 
	 * @param servletContext
	 */
	public void startup(ServletContext servletContext);

	/**
	 * 系统关闭
	 * 
	 * @param servletContext
	 */
	public void destory(ServletContext servletContext);
}
