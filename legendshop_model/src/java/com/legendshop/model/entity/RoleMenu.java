/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;


/**
 * The Class RoleMenu.
 * 角色和菜单的关系表
 */
public class RoleMenu implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8675364817753905504L;
	
	private Long rmId;

	/** The menu id. */
	private Long menuId;
	
	/** The role id. */
	private String roleId;
	
	/** The role name. */
	private String roleName;

	/**
	 * @return the menuId
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the rmId
	 */
	public Long getRmId() {
		return rmId;
	}

	/**
	 * @param rmId the rmId to set
	 */
	public void setRmId(Long rmId) {
		this.rmId = rmId;
	}


}
