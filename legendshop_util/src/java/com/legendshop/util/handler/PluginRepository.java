/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.jdbc.DBManager;
import com.legendshop.plugins.Plugin;
import com.legendshop.plugins.PluginConfig;
import com.legendshop.plugins.PluginManager;
import com.legendshop.plugins.PluginRuntimeException;
import com.legendshop.plugins.PluginStatusEnum;
import com.legendshop.util.AppUtils;
import com.legendshop.util.EnvironmentConfig;
import com.legendshop.util.FileConfig;

/**
 * A factory for creating DBPlugin objects.
 */
public class PluginRepository implements PluginManager {
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(PluginRepository.class);

	/** The instance. */
	private static PluginRepository instance = null;

	/** 存在DB中的插件记录 *. */
	private List<PluginConfig> dbPluginConfigList = null;

	/** The plugins. */
	private List<Plugin> plugins = null;

	/**
	 * Instantiates a new dB plugin factory.
	 */
	private PluginRepository() {
		if (!isSystemInstalled()) {
			return;
		}
		if (dbPluginConfigList == null) {
			// dbPluginConfigList = pluginManager.getPluginConfigFromDb();
			List<PluginConfig> pluginConfigList = new ArrayList<PluginConfig>();
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			String sql = "select * from ls_plugin";
			if (log.isDebugEnabled()) {
				log.debug("Running SQL:\n" + sql);
			}
			try {
				conn = DBManager.getInstance().getConnection();
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					PluginConfig plugin = mapRow(rs);
					pluginConfigList.add(plugin);
				}
			} catch (Exception se) {
				log.error("SQLException in DBManager.exceuteQuery, sql is :\r\n" + sql, 2);
				log.error("executeQuery", se);
			} finally {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
			dbPluginConfigList = pluginConfigList;
		}

	}

	/**
	 * Gets the single instance of PluginRepository.
	 * 
	 * @return single instance of PluginRepository
	 */
	public static PluginRepository getInstance() {
		if (instance == null) {
			instance = new PluginRepository();
		}
		return instance;
	}

	/**
	 * Gets the db plugin config list.
	 * 
	 * @return the dbPluginConfigList
	 */
	public List<PluginConfig> getDbPluginConfigList() {
		return dbPluginConfigList;
	}

	/**
	 * Map row.
	 * 
	 * @param rs
	 *            the rs
	 * @return the plugin config
	 * @throws SQLException
	 *             the sQL exception
	 */
	private PluginConfig mapRow(ResultSet rs) throws SQLException {
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

	/**
	 * Adds the plugin list.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public void registerPlugin(Plugin plugin) {
		if (plugins == null) {
			plugins = new ArrayList<Plugin>();
		}
		plugins.add(plugin);
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
		if (!isSystemInstalled()) {
			return;
		}
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "insert into ls_plugin(plugin_id, plugin_version,provider,status,is_required,description) values(?,?,?,?,?,?)";
		if (log.isDebugEnabled()) {
			log.debug("Running SQL:\n" + sql.replace("?", "{}"), new Object[] { plugin.getPulginId(), plugin.getPulginVersion(),
					plugin.getProvider(), plugin.getStatus().name(), plugin.isRequired(), plugin.getDescription() });
		}
		try {
			conn = DBManager.getInstance().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, plugin.getPulginId());
			stmt.setString(2, plugin.getPulginVersion());
			stmt.setString(3, plugin.getProvider());
			stmt.setString(4, plugin.getStatus().name());
			stmt.setBoolean(5, plugin.isRequired());
			stmt.setString(6, plugin.getDescription());
			int result = stmt.executeUpdate();
			if (result != 1) {
				throw new RuntimeException("can not inster plugin " + plugin.getPulginId());
			}
		} catch (Exception se) {
			log.error("SQLException in DBManager.exceuteUpdate, sql is :\r\n" + sql, 2);
			log.error("executeQuery", se);
		} finally {
			DBManager.getInstance().cleanup(conn, stmt, rs);
		}

	}

	public void updatePluginStatus(String pluginId, String status) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "update ls_plugin set status = ? where plugin_id = ?";
		if (log.isDebugEnabled()) {
			log.debug("Running SQL:\n" + sql.replace("?", "{}"), status, pluginId);
		}
		try {
			conn = DBManager.getInstance().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setString(2, pluginId);
			int result = stmt.executeUpdate();
			if (result != 1) {
				throw new RuntimeException("can not update plugin " + pluginId);
			}
		} catch (Exception se) {
			log.error("SQLException in DBManager.exceuteUpdate, sql is :\r\n" + sql, 2);
			log.error("executeQuery", se);
		} finally {
			DBManager.getInstance().cleanup(conn, stmt, rs);
		}
	}

	/**
	 * @return the plugins
	 */
	public List<Plugin> getPlugins() {
		return plugins;
	}

	/**
	 * @param plugins
	 *            the plugins to set
	 */
	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public boolean isPluginRunning(String pluginId) {
		if (AppUtils.isBlank(pluginId) || plugins == null) {
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

	public List<PluginConfig> getPluginConfigFromDb() {
		if (!isSystemInstalled()) {
			return null;
		}

		List<PluginConfig> pluginConfigList = new ArrayList<PluginConfig>();
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select * from ls_plugin";
		if (log.isDebugEnabled()) {
			log.debug("Running SQL:\n" +sql);
		}
		try {
			conn = DBManager.getInstance().getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PluginConfig plugin = mapRow(rs);
				pluginConfigList.add(plugin);
			}
		} catch (Exception se) {
			log.error("SQLException in DBManager.exceuteQuery, sql is :\r\n" + sql, 2);
			log.error("executeQuery", se);
		} finally {
			DBManager.getInstance().cleanup(conn, stmt, rs);
		}
		return pluginConfigList;
	}

	public boolean isSystemInstalled() {
		return "true".equals(EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, "INSTALLED"));
	}

}
