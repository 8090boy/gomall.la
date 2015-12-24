/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.legendshop.util.AppUtils;
import com.legendshop.util.EnvironmentConfig;
import com.legendshop.util.FileConfig;

/**
 * The Class PluginManagerImpl.
 * @deprecated
 */
public class PluginManagerImpl implements PluginManager {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(PluginManagerImpl.class);

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate = null;

	/** The plugins. */
	private List<Plugin> plugins = new ArrayList<Plugin>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.plugins.PluginManager#getPlugins()
	 */
	public List<Plugin> getPlugins() {
		return plugins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.plugins.PluginManager#startPlugins(javax.servlet.
	 * ServletContext)
	 */
	public synchronized void startPlugins(ServletContext servletContext) {
		for (Plugin plugin : plugins) {
			if (plugin.getPluginConfig().getStatus().equals(PluginStatusEnum.Y)) {
				log.info("start to init plugin {}, version {}", plugin.getPluginConfig().getPulginId(), plugin.getPluginConfig()
						.getPulginVersion());
				plugin.bind(servletContext);
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.plugins.PluginManager#registerPlugins(com.legendshop
	 * .core.plugins.Plugin)
	 */
	public synchronized void registerPlugins(Plugin plugin) {
		// 在线或停止状态也要加入
		if (PluginStatusEnum.S.equals(plugin.getPluginConfig().getStatus())
				|| PluginStatusEnum.Y.equals(plugin.getPluginConfig().getStatus())) {
			if (!plugins.contains(plugin)) {
				plugins.add(plugin);
			} else {
				throw new PluginRuntimeException("Plugin '" + plugin.getClass().getSimpleName() + "' had been registed");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.plugins.PluginManager#stoptPlugins(javax.servlet.
	 * ServletContext)
	 */
	public void stopPlugins(ServletContext servletContext) {
		for (Plugin plugin : plugins) {
			if (plugin.getPluginConfig().getStatus().equals(PluginStatusEnum.Y)) {
				log.info("start to destory plugin {}, version {}", plugin.getPluginConfig().getPulginId(), plugin.getPluginConfig()
						.getPulginVersion());
				plugin.unbind(servletContext);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.plugins.PluginManager#turnOn(java.lang.String)
	 */
	public synchronized String turnOn(String pluginId) {
		for (Plugin plugin : plugins) {
			if (plugin.getPluginConfig().getPulginId().equals(pluginId)) {
				log.info("turnOn plugin {}, version {}", plugin.getPluginConfig().getPulginId(), plugin.getPluginConfig()
						.getPulginVersion());
				plugin.getPluginConfig().setStatus(PluginStatusEnum.Y);
				updatePluginStatus(pluginId, PluginStatusEnum.Y.name());
			}
		}
		return PluginStatusEnum.Y.name();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.plugins.PluginManager#turnOff(java.lang.String)
	 */
	public synchronized String turnOff(String pluginId) {
		for (Plugin plugin : plugins) {
			if (plugin.getPluginConfig().getPulginId().equals(pluginId)) {
				log.info("turnOn plugin {}, version {}", plugin.getPluginConfig().getPulginId(), plugin.getPluginConfig()
						.getPulginVersion());
				plugin.getPluginConfig().setStatus(PluginStatusEnum.N);
				updatePluginStatus(pluginId, PluginStatusEnum.N.name());
			}
		}
		return PluginStatusEnum.N.name();
	}

	/**
	 * Save plugin.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public void savePlugin(PluginConfig plugin) {
		try {
			jdbcTemplate.update(
					"insert into ls_plugin(plugin_id, plugin_version,provider,status,is_required,description) values(?,?,?,?,?,?)",
					new Object[] { plugin.getPulginId(), plugin.getPulginVersion(), plugin.getProvider(),
							plugin.getStatus().name(), plugin.isRequired(), plugin.getDescription() });
		} catch (Exception e) {
			log.error("savePlugin into DB error", e);
		}

	}

	public void updatePluginStatus(String pluginId, String status) {
		try {
			jdbcTemplate.update("update ls_plugin set status = ? where plugin_id = ?", new Object[] { status, pluginId });
		} catch (Exception e) {
			log.error("savePlugin into DB error", e);
		}
	}

	/**
	 * Gets the plugin config from db.
	 * 
	 * @return the plugin config from db
	 */
	public List<PluginConfig> getPluginConfigFromDb() {
		if (!isSystemInstalled()) {
			return null;
		}

		List<PluginConfig> result = null;
		try {
			result = jdbcTemplate.query("select * from ls_plugin", new ConfigRowMapper());
		} catch (Exception e) {
			log.error("getPluginConfigFromDb error", e);
			return null;
		}
		return result;
	}

	/**
	 * The Class ConfigRowMapper.
	 */
	private class ConfigRowMapper implements RowMapper<PluginConfig> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
		 * int)
		 */
		public PluginConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
			PluginConfig plugin = new PluginConfig();
			plugin.setPulginId(rs.getString("plugin_id"));
			plugin.setPulginVersion(rs.getString("plugin_version"));
			plugin.setProvider(rs.getString("provider"));
			try {
				PluginStatusEnum pluginStatus = PluginStatusEnum.valueOf(rs.getString("status"));
				plugin.setStatus(pluginStatus);
			} catch (Exception e) {
				plugin.setStatus(PluginStatusEnum.N);
			}
			Boolean isRequired = false;
			try {
				isRequired = Boolean.valueOf(rs.getString("is_required"));
			} catch (Exception e) {
			}
			plugin.setRequired(isRequired);
			plugin.setDescription(rs.getString("description"));
			return plugin;
		}

	}

	private boolean isSystemInstalled() {
		return "true".equals(EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, "INSTALLED"));
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean isPluginRunning(String pluginId) {
		if (AppUtils.isBlank(pluginId)) {
			return false;
		}
		for (Plugin plugin : plugins) {
			if (pluginId.equals(plugin.getPluginConfig().getPulginId())
					&& (plugin.getPluginConfig().getStatus().equals(PluginStatusEnum.Y))) {
				return true;
			}
		}
		return false;
	}

}
