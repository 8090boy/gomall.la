/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

import java.util.List;

import javax.servlet.ServletContext;

/**
 * The Interface PluginManager. 插件管理器
 */
public interface PluginManager {

	/**
	 * Gets the plugins. 在线的插件
	 * 
	 * @return the plugins
	 */
	public List<Plugin> getPlugins();

	/**
	 * Register plugins. 注册插件
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public void registerPlugins(Plugin plugin);

	/**
	 * 插件上线.
	 * 
	 * @param pluginId
	 *            the plugin id
	 */
	public String turnOn(String pluginId);

	/**
	 * 插件是否在运行
	 * 
	 * @param pluginId
	 * @return
	 */
	public boolean isPluginRunning(String pluginId);

	/**
	 * 插件下线.
	 * 
	 * @param pluginId
	 *            the plugin id
	 */
	public String turnOff(String pluginId);

	/**
	 * Start plugins. 启动插件
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	public void startPlugins(ServletContext servletContext);

	/**
	 * Stop plugins. 停止插件
	 * 
	 * @param servletContext
	 *            the servlet context
	 */
	public void stopPlugins(ServletContext servletContext);

	/**
	 * Save plugin. 保存插件
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public void savePlugin(PluginConfig plugin);

	/**
	 * Gets the plugin config from db. 得到数据库中的插件
	 * 
	 * @return the plugin config from db
	 */
	public List<PluginConfig> getPluginConfigFromDb();
}
