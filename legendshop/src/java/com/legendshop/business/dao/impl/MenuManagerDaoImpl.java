/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.legendshop.business.dao.MenuManagerDao;
import com.legendshop.model.entity.Menu;
import com.legendshop.model.entity.RoleMenu;
import com.legendshop.plugins.Plugin;
import com.legendshop.plugins.PluginStatusEnum;
import com.legendshop.util.AppUtils;
import com.legendshop.util.handler.PluginRepository;

/**
 * The Class MenuManagerDaoImpl.
 */
public class MenuManagerDaoImpl implements MenuManagerDao {
	
	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.legendshop.business.dao.MenuManagerDao#getMenu()
	 */
	@Override
	public List<Menu> getMenu() {
		List<Menu> result = new ArrayList<Menu>();
		List<Menu> menuList = jdbcTemplate.query("select * from ls_menu order by level,seq", new MenuRowMapper());
		//excludes the Menu provided by Plugin stopped
		if(AppUtils.isNotBlank(menuList)){
			List<Plugin> pluginList = PluginRepository.getInstance().getPlugins();
			if(AppUtils.isNotBlank(pluginList)){
				for (Menu menu : menuList) {
					if(AppUtils.isNotBlank(menu.getProvidedPlugin())){
						for (Plugin plugin : pluginList) {
							if(menu.getProvidedPlugin().equals(plugin.getPluginConfig().getPulginId()) && (plugin.getPluginConfig().getStatus().equals(PluginStatusEnum.Y))){
								result.add(menu);
							}
						}
					}else{
						result.add(menu);
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public List<RoleMenu> getRoleMenu() {
		return  jdbcTemplate.query("select rm.role_id,rm.menu_id,r.name as role_name from ls_role_menu rm, ls_role r where rm.role_id = r.id", new RoleMenuMapper());
	}


	private class MenuRowMapper implements RowMapper<Menu>{

		@Override
		public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
			Menu menu = new Menu();
			menu.setAction(rs.getString("action"));
			menu.setLabel(rs.getString("label"));
			menu.setLevel(rs.getInt("level"));
			menu.setMenuId(rs.getLong("menu_id"));
			menu.setName(rs.getString("name"));
			menu.setSeq(rs.getInt("seq"));
			menu.setParentId(rs.getLong("parent_id"));
			menu.setProvidedPlugin(rs.getString("provided_plugin"));
			menu.setTitle(rs.getString("title"));
			return menu;
		}
		
	}
	
	private class RoleMenuMapper implements RowMapper< RoleMenu>{

		@Override
		public RoleMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(rs.getLong("menu_id"));
			roleMenu.setRoleId(rs.getString("role_id"));
			roleMenu.setRoleName(rs.getString("role_name"));
			return roleMenu;
		}
		
	}
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<RoleMenu> getRoleMenu(List<Long> menuIdList) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < menuIdList.size() - 1; i++) {
			sb.append("?,");
		}
		sb.append("?)");
		String sql = "select rm.role_id,rm.menu_id,r.name as role_name from ls_role_menu rm, ls_role r where rm.role_id = r.id and rm.menu_id in (" + sb.toString();
		return  jdbcTemplate.query(sql,menuIdList.toArray(), new RoleMenuMapper());
	}
	
	@Override
	public List<RoleMenu> getRoleMenu(Long menuId) {
		String sql = "select rm.role_id,rm.menu_id,r.name as role_name from ls_role_menu rm, ls_role r where rm.role_id = r.id and rm.menu_id = ?" ;
		return  jdbcTemplate.query(sql,new Object[]{menuId}, new RoleMenuMapper());
	}

	/**
	 * 删除一个菜单所对应的角色
	 */
	@Override
	public void deleteRoleMenu(Long menuId) {
		jdbcTemplate.update("delete from ls_role_menu where menu_id = ?", menuId);
	}

}
