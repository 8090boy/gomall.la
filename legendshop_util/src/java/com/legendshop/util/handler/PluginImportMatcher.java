/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;

import java.util.List;

import com.legendshop.plugins.Plugin;
import com.legendshop.plugins.PluginConfig;
import com.legendshop.plugins.PluginManager;
import com.legendshop.plugins.PluginStatusEnum;
import com.legendshop.util.AppUtils;

/**
 * The Class PluginImportMatcher.
 */
public class PluginImportMatcher extends ImportMatcher {

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
			PluginManager pluginManager = getPluginManager();
			if (pluginManager != null && pluginManager.getPlugins() != null) {
				for (Plugin plugin : pluginManager.getPlugins()) {
					if (isThePluginNormal(plugin, pluginName)) {
						PluginConfig pluginConfig = resolvePluginConfig(plugin,false);
						// found record
						if (pluginConfig.getStatus().equals(PluginStatusEnum.Y)) {
							return true;
						} else {
							return false;
						}
					}
				}
		}
		return false;
	}

	protected boolean isThePluginNormal(Plugin plugin, String pluginName) {
		return PluginStatusEnum.Y.equals(plugin.getPluginConfig().getStatus())
				&& pluginName.equals(plugin.getPluginConfig().getPulginId());
	}

	protected PluginConfig resolvePluginConfig(Plugin plugin,boolean isPlugin) {
		boolean foundPlugin = false;
		PluginConfig result = plugin.getPluginConfig();
		List<PluginConfig> configList = getDbPluginConfigList();
		if (configList != null) {
			for (PluginConfig dbpluginConfig : configList) {
				if (dbpluginConfig.getPulginId().equals(plugin.getPluginConfig().getPulginId())) {
					// 以数据库的插件状态为准
					result = dbpluginConfig;
					foundPlugin = true;
					plugin.updateStatus(result.getStatus());
					break;
				}
			}
		}
		if (!foundPlugin && isPlugin) {
			// 新建plugin
			 getPluginManager().savePlugin(result);
		}
		return result;
	}

	public List<PluginConfig> getDbPluginConfigList() {
		return PluginRepository.getInstance().getDbPluginConfigList();
	}

	public PluginManager getPluginManager() {
		return PluginRepository.getInstance();
	}

}