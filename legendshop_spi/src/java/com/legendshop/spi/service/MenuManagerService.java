/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service;

import java.util.List;

import com.legendshop.model.entity.Menu;
import com.legendshop.model.entity.RoleMenu;

/**
 * The Class AskService.
 */
public interface MenuManagerService {

	public List<Menu> getMenu();
	
	public List<RoleMenu> getRoleMenu(List<Long> menuIdList);

}
