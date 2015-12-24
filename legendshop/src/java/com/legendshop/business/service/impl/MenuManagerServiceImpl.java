/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.business.dao.MenuManagerDao;
import com.legendshop.model.entity.Menu;
import com.legendshop.model.entity.RoleMenu;
import com.legendshop.spi.service.MenuManagerService;
import com.legendshop.util.AppUtils;

/**
 * The Class MenuManagerServiceImpl.
 */
public class MenuManagerServiceImpl implements MenuManagerService {

	/** The menu dao. */
	private MenuManagerDao menuManagerDao;
	
	/**
	 * 拿到已经组装好的Menu
	 */
	@Override
	public List<Menu> getMenu() {
		List<Menu> menuList = menuManagerDao.getMenu();
		List<RoleMenu> roleMenuList = menuManagerDao.getRoleMenu();
		if(AppUtils.isNotBlank(menuList) && AppUtils.isNotBlank(roleMenuList)){
			for (int i = 0; i < menuList.size(); i++) {
				Menu menu = menuList.get(i);
				for (int j = 0; j < roleMenuList.size(); j++) {
					RoleMenu roleMenu = roleMenuList.get(j);
					if(menu.getMenuId().equals(roleMenu.getMenuId())){
						menu.addRequiredAnyFunctions(roleMenu.getRoleName());
					}
				}
			}
		}
		return menuList;
	}

	/**
	 * @param menuManagerDao the menuManagerDao to set
	 */
	public void setMenuManagerDao(MenuManagerDao menuManagerDao) {
		this.menuManagerDao = menuManagerDao;
	}

	@Override
	public List<RoleMenu> getRoleMenu(List<Long> menuIdList) {
		return menuManagerDao.getRoleMenu(menuIdList);
	}

}
