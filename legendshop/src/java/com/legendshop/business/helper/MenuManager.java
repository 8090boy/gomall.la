/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.legendshop.core.UserManager;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.model.entity.Menu;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.service.MenuManagerService;
import com.legendshop.util.AppUtils;

/**
 * The Class MenuManager.
 * 后台菜单容器
 */
public class MenuManager {
	
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	private MenuManagerService menuManagerService;
	
	/** The menus. */
	private List<Menu> menus;
	
	public void init(){
		List<Menu> menuList = menuManagerService.getMenu();
		log.debug("total menu with size {}" , menuList.size());
		this.menus =parseMenu(menuList);
		log.debug("parsed menu with size {}" , menus.size());
	}

	/**
	 * @return the menus
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getMenus(HttpSession session) {
		List<Menu> menuList = (List<Menu>)session.getAttribute(Constants.MENU_LIST);
		if(menuList == null){
			menuList = new ArrayList<Menu>();
			Collection<GrantedAuthority> useRoleList =  UserManager.getUser(session).getAuthorities();
			for (Menu menu : this.menus) {  
				List<String> requiredRoleList =menu.getRequiredAnyFunctions();
				if(AppUtils.isNotBlank(requiredRoleList)){
					label:for (String requiredRole : requiredRoleList) {
						Iterator<GrantedAuthority> userRole = useRoleList.iterator();
						while(userRole.hasNext()){
							GrantedAuthority role = userRole.next();
							if(role.getAuthority().equals(requiredRole)){
								menuList.add(menu);
								break label;
							}
						}
					}
				}else{
					menuList.add(menu);
				}

			}
			session.setAttribute(Constants.MENU_LIST, menuList);
		}
		
		return menuList;
	}

	/**
	 * @param menuManagerService the menuManagerService to set
	 */
	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}

	private  List<Menu> parseMenu( List<Menu> menuList){
		 Map<Long, Menu> menuMap = new LinkedHashMap<Long, Menu>();
		 for (Menu menu : menuList) {
			 if(menu.getLevel() == 1){	 //for 顶级菜单
				 menuMap.put(menu.getMenuId(), menu);
			 }else if(menu.getLevel() == 2){ //二级菜单
				 //拿到一级菜单先
				 Menu menuLevel1 =  menuMap.get(menu.getParentId());
				 if(menuLevel1 == null){
					 throw new NotFoundException(menu.getParentId() + " menu can not load level1 menu");
				 }
				 menuLevel1.addSubMenu(menu);
			 }else if(menu.getLevel() == 3){ //三级菜单
				//拿到二级菜单
				 Menu secondMenu = getParentMenu(menuList, menu);
				 if(secondMenu != null){
						//拿到一级菜单先
					 Menu menuLevel1 =  menuMap.get(secondMenu.getParentId());
					 if(menuLevel1 == null){
						 throw new NotFoundException(secondMenu.getParentId() + " secondMenu can not load level1 menu");
					 }
					 Set<Menu> menuLevel2 = menuLevel1.getSubMenu();
					 for (Menu menu2 : menuLevel2) {
						if(menu2.getMenuId().equals(menu.getParentId())){
							//找到对应的二级菜单
							menu2.addSubMenu(menu);
							break;
						}
					}
				 }
			 }
		}
		 return new ArrayList<Menu>(menuMap.values());
	}
	
	private Menu getParentMenu( List<Menu> menuList, Menu menu){
		for (Menu item : menuList) {
			if(item.getMenuId().equals(menu.getParentId())){
				return item;
			}
		}
		return null;
	}
	
}
