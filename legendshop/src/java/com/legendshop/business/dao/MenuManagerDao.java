/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import java.util.List;

import com.legendshop.model.entity.Menu;
import com.legendshop.model.entity.RoleMenu;

/**
 * The Interface MenuManagerDao.
 */
public interface MenuManagerDao {

	/**
	 * Gets the menu.
	 *
	 * @return the menu
	 */
	List<Menu> getMenu();
	
	/**
	 * Gets the role menu.
	 *
	 * @return the role menu
	 */
	public List<RoleMenu> getRoleMenu();
	
	/**
	 * Gets the role menu.
	 *
	 * @param menuIdList the menu id list
	 * @return the role menu
	 */
	public List<RoleMenu> getRoleMenu(List<Long> menuIdList);
	
	/**
	 * Gets the role menu.
	 *
	 * @param menuId the menu id
	 * @return the role menu
	 */
	public List<RoleMenu> getRoleMenu(Long menuId) ;

	/**
	 * Delete role menu.
	 *
	 * @param menuId the menu id
	 */
	void deleteRoleMenu(Long menuId);


}
