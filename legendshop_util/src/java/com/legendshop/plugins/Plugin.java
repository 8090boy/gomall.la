/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

import javax.servlet.ServletContext;

/**
 * The Interface Plugin.
 */
public interface Plugin {

	/**
	 * Bind.
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	public void bind(ServletContext servletContext);

	/**
	 * Unbind.
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	public void unbind(ServletContext servletContext);

	/**
	 * 更改插件状态.
	 *
	 * @param status the status
	 */
	public void updateStatus(PluginStatusEnum status);

	/**
	 * Gets the plugin config.
	 * 
	 * @return the plugin config
	 */
	public PluginConfig getPluginConfig();
	
	/**
	 * Sets the plugin config.
	 *
	 * @param pluginConfig the new plugin config
	 */
	public void setPluginConfig(PluginConfig pluginConfig);

}
