/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Required;

/**
 * The Class SimplePlugin.
 */
public class SimplePlugin implements Plugin {


	/** The plugin config. */
	private PluginConfig pluginConfig;
	
	public SimplePlugin() {
	}
	
	public SimplePlugin(PluginConfig pluginConfig) {
		this.pluginConfig = pluginConfig;
	}

	/**
	 * Sets the plugin config.
	 * 
	 * @param pluginConfig
	 *            the new plugin config
	 */
	@Required
	public void setPluginConfig(PluginConfig pluginConfig) {
		this.pluginConfig = pluginConfig;
	}

	/**
	 * 更改插件状态
	 */
	public void updateStatus(PluginStatusEnum status) {
		pluginConfig.setStatus(status);

	}

	/**
	 * 系统关闭
	 */
	public void unbind(ServletContext servletContext) {

	}

	/**
	 * 系统启动
	 */
	public void bind(ServletContext servletContext) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.plugins.Plugin#getPluginConfig()
	 */
	public PluginConfig getPluginConfig() {
		return pluginConfig;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Plugin)) {
			return false;
		}
		Plugin plug = (Plugin) obj;
		PluginConfig config = plug.getPluginConfig();
		if (config == null) {
			throw new PluginRuntimeException("PluginConfig not found for " + plug.getClass().getSimpleName());
		}
		String pluginId = config.getPulginId();
		if (pluginId == null) {
			throw new PluginRuntimeException("pluginId not found for " + plug.getClass().getSimpleName());
		}
		return pluginId.equals(this.getPluginConfig().getPulginId());
	}

	@Override
	public int hashCode() {
		if (this.getPluginConfig() != null && this.getPluginConfig().getPulginId() != null) {
			return this.getPluginConfig().getPulginId().hashCode();
		}
		return super.hashCode();
	}

}
