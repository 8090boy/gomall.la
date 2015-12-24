/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.Navigation;
import com.legendshop.model.entity.NavigationItem;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.NavigationItemService;
import com.legendshop.spi.service.NavigationService;
import com.legendshop.util.AppUtils;


/**
 * The Class NavigationController
 *
 */
@Controller
@RequestMapping("/admin/system/navigation")
public class NavigationController extends BaseController implements AdminController<Navigation, Long> {
    @Autowired
    private NavigationService navigationService;
    @Autowired
    private NavigationItemService navigationItemService;

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Navigation navigation) {
        CriteriaQuery cq = new CriteriaQuery(Navigation.class, curPageNO);
        cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
        PageSupport ps = navigationService.getNavigation(cq);
		List<Navigation> list = ps.getResultList();// 1级导航
		if(AppUtils.isNotBlank(list)){
			List<NavigationItem> navigationItemList = navigationItemService.getAllNavigationItem(); // 2级导航
			//将2级导航加入1级导航中
			for (Navigation navi : list) {
				for (NavigationItem navigationItem : navigationItemList) {
					navi.addSubItems(navigationItem);
				}
			}
		}
		
        ps.savePage(request);
        request.setAttribute("navigation", navigation);
        
        String path = PathResolver.getPath(request, response, BackPage.NAVIGATION_LIST_PAGE);
        return path;
    }

    @RequestMapping(value = "/save")
    public String save(HttpServletRequest request, HttpServletResponse response, Navigation navigation) {
        navigationService.saveNavigation(navigation);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.NAVIGATION_LIST_QUERY);
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        Navigation navigation = navigationService.getNavigation(id);
		navigationService.deleteNavigation(navigation);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
        return PathResolver.getPath(request, response, FowardPage.NAVIGATION_LIST_QUERY);
    }
    
    @RequestMapping(value = "/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        Navigation navigation = navigationService.getNavigation(id);
        request.setAttribute("navigation", navigation);
         return PathResolver.getPath(request, response, BackPage.NAVIGATION_EDIT_PAGE);
    }

    
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.NAVIGATION_EDIT_PAGE);
	}

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {        
        Navigation navigation = navigationService.getNavigation(id);
		request.setAttribute("navigation", navigation);

		return PathResolver.getPath(request, response, BackPage.NAVIGATION_EDIT_PAGE);
    }

    @RequestMapping(value = "/updatestatus/{naviId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Integer updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable Long naviId,
			@PathVariable Integer status) {
    	Navigation navigation = navigationService.getNavigation(naviId);
		if (navigation == null) {
			return -1;
		}
		if (!status.equals(navigation.getStatus())) {
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				String loginName = UserManager.getUserName(request.getSession());
				// user
				if (!loginName.equals(navigation.getName())) {
					return -1;
				}
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status)) {
					navigation.setStatus(status);
					navigationService.updateNavigation(navigation);
				}
			} else {
				// admin
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status) || Constants.STOPUSE.equals(status)) {
					navigation.setStatus(status);
					navigationService.updateNavigation(navigation);
				}
			}
		}
		return navigation.getStatus();
	}
    
    /**
     * 添加二级导航
     */
    @RequestMapping("/querysearth/{id}")
    public String querysearth(HttpServletRequest request, HttpServletResponse response, @PathVariable
    	    Long id) {
    	Navigation navigation = navigationService.getNavigation(id);
    	 
       request.setAttribute("navigation", navigation);

       return PathResolver.getPath(request, response, BackPage.NAVIGATIONITEM_EDIT_PAGE);
    }
    
    
}
