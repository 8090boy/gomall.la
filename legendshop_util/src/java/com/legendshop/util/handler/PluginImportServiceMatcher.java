/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;

import com.legendshop.plugins.Plugin;
import com.legendshop.plugins.PluginConfig;
import com.legendshop.plugins.PluginStatusEnum;
import com.legendshop.util.AppUtils;

/**
 * The Class PluginImportMatcher.
 */
public class PluginImportServiceMatcher extends PluginImportMatcher {

	private Plugin plugin = null;
	/**
	 * 解析conditional:plugin,根据数据库配置来决定是否加载插件.
	 * 
	 * @return true, if is match
	 */
	@Override
	public boolean isMatch() {
		String pluginName = this.getValue();
		if (AppUtils.isBlank(pluginName)) {
			return false;
		}
		
		if (isThePluginNormal(plugin, pluginName)) {
			//如果不明确在xml中填写N的插件都要注册，方便做状态管理，即是正常状态和下线状态的插件可以更改状态。
			if (!(PluginStatusEnum.N.equals(plugin.getPluginConfig().getStatus()))) {
				PluginRepository.getInstance().registerPlugin(plugin);
			}
			
			PluginConfig pluginConfig = resolvePluginConfig(plugin,true);
			if(pluginConfig == null){
				return false;
			}
			// found record
			if (PluginStatusEnum.Y.equals(pluginConfig.getStatus())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	/**
	 * @return the plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}
	/**
	 * @param plugin the plugin to set
	 */
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}



}