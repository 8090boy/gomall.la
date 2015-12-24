/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * The Class Menu. 后台菜单
 */
public class Menu  implements BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8596054428957749988L;

	private Long menuId;
	// 名称
	/** The name. */
	private String name;

	// 国际化标签
	/** The label. */
	private String label;

	private String title;

	//菜单对应的Role
	private List<String> roleNameList;
	// 顺序
	/** The seq. */
	private Integer seq;

	// 连接
	/** The action. */
	private String action;

	// 所需要的权限，只要有其中一个全新即可访问
	/** The required any functions. */
	private List<String> requiredAnyFunctions;

	// 由那些插件提供的菜单
	private String providedPlugin;

	// 父菜单名称
	/** The parent name. */
	private Long parentId;

	private Integer level;

	// 子菜单
	/** The sub menu. */
	private Set<Menu> subMenu = new TreeSet<Menu>(new MenuComparator());
	
	//父菜单
	private Menu parentMenu;

	/**
	 * @return the menuId
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(Integer order) {
		this.seq = order;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the requiredAnyFunctions
	 */
	public List<String> getRequiredAnyFunctions() {
		return requiredAnyFunctions;
	}

	/**
	 * @param requiredAnyFunctions
	 *            the requiredAnyFunctions to set
	 */
	public void setRequiredAnyFunctions(List<String> requiredAnyFunctions) {
		this.requiredAnyFunctions = requiredAnyFunctions;
	}

	public void addRequiredAnyFunctions(String requiredFunction) {
		if (requiredAnyFunctions == null) {
			requiredAnyFunctions = new ArrayList<String>();
		}
		this.requiredAnyFunctions.add(requiredFunction);
	}

	/**
	 * @return the providedPlugin
	 */
	public String getProvidedPlugin() {
		return providedPlugin;
	}

	/**
	 * @param providedPlugin
	 *            the providedPlugin to set
	 */
	public void setProvidedPlugin(String provideredPlugin) {
		this.providedPlugin = provideredPlugin;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return the subMenu
	 */
	public Set<Menu> getSubMenu() {
		return subMenu;
	}

	/**
	 * @param subMenu
	 *            the subMenu to set
	 */
	public void addSubMenu(Menu menu) {
		if (!subMenu.contains(menu)) {
			this.subMenu.add(menu);
		}
	}

	@Override
	public int hashCode() {
		return menuId.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		Menu menu = (Menu)obj;
		return menuId.equals(menu.getMenuId());
	}

	public Serializable getId() {
		return menuId;
	}

	/**
	 * @return the parentMenu
	 */
	public Menu getParentMenu() {
		return parentMenu;
	}

	/**
	 * @param parentMenu the parentMenu to set
	 */
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	/**
	 * @return the roleNameList
	 */
	public List<String> getRoleNameList() {
		return roleNameList;
	}

	/**
	 * @param roleNameList the roleNameList to set
	 */
	public void addRoleNameList(String roleName) {
		if(roleNameList == null){
			roleNameList = new ArrayList<String>();
		}
		roleNameList.add(roleName);
	}
}
