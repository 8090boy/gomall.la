/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.Sku;
import com.legendshop.spi.service.SkuService;

/**
 * The Class SkuController
 *
 */
@Controller
@RequestMapping("/admin/sku")
public class SkuController extends BaseController implements AdminController<Sku, Long> {
    @Autowired
    private SkuService skuService;

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sku sku) {
        CriteriaQuery cq = new CriteriaQuery(Sku.class, curPageNO);
        //cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
        //cq = hasAllDataFunction(cq, request, StringUtils.trim(sku.getUserName()));
        /*
           //TODO add your condition
        */
        cq.add();
        PageSupport ps = skuService.getSku(cq);
        ps.savePage(request);
        request.setAttribute("sku", sku);
        
        return "/sku/skuList";
        //TODO, replace by next line, need to predefined BackPage parameter
       // return PathResolver.getPath(request, response, BackPage.SKU_LIST_PAGE);
    }

    @RequestMapping(value = "/save")
    public String save(HttpServletRequest request, HttpServletResponse response, Sku sku) {
        skuService.saveSku(sku);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
        return "forward:/admin/sku/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
		//return PathResolver.getPath(request, response, FowardPage.SKU_LIST_QUERY);
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        Sku sku = skuService.getSku(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), sku.getUserName());
		//if(result!=null){
			//return result;
		//}
		skuService.deleteSku(sku);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
        return "forward:/admin/sku/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
        //return PathResolver.getPath(request, response, FowardPage.SKU_LIST_QUERY);
    }

    @RequestMapping(value = "/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        Sku sku = skuService.getSku(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), sku.getUserName());
		//if(result!=null){
			//return result;
		//}
        request.setAttribute("sku", sku);
        return "/sku/sku";
         //TODO, replace by next line, need to predefined FowardPage parameter
         //return PathResolver.getPath(request, response, BackPage.SKU_EDIT_PAGE);
    }
    
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return "/sku/sku";
		//TODO, replace by next line, need to predefined BackPage parameter
		//return PathResolver.getPath(request, response, BackPage.SKU_EDIT_PAGE);
	}

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {        
        Sku sku = skuService.getSku(id);
		//String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), sku.getUserName());
		//if(result!=null){
			//return result;
		//}
		request.setAttribute("sku", sku);
		return "forward:/admin/sku/query.htm";
		//TODO, replace by next line, need to predefined BackPage parameter
		//return PathResolver.getPath(request, response, BackPage.SKU_EDIT_PAGE);
    }

}
