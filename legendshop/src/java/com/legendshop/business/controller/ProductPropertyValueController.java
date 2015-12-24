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
import com.legendshop.model.entity.ProductPropertyValue;
import com.legendshop.spi.service.ProductPropertyValueService;

/**
 * The Class ProductPropertyValueController
 *
 */
@Controller
@RequestMapping("/admin/productPropertyValue")
public class ProductPropertyValueController extends BaseController implements AdminController<ProductPropertyValue, Long> {
    @Autowired
    private ProductPropertyValueService productPropertyValueService;

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, ProductPropertyValue productPropertyValue) {
        CriteriaQuery cq = new CriteriaQuery(ProductPropertyValue.class, curPageNO);
        //cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
        //cq = hasAllDataFunction(cq, request, StringUtils.trim(productPropertyValue.getUserName()));
        /*
           //TODO add your condition
        */
        cq.add();
        PageSupport ps = productPropertyValueService.getProductPropertyValue(cq);
        ps.savePage(request);
        request.setAttribute("productPropertyValue", productPropertyValue);
        
        return "/productPropertyValue/productPropertyValueList";
        //TODO, replace by next line, need to predefined BackPage parameter
       // return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTYVALUE_LIST_PAGE);
    }

    @RequestMapping(value = "/save")
    public String save(HttpServletRequest request, HttpServletResponse response, ProductPropertyValue productPropertyValue) {
        productPropertyValueService.saveProductPropertyValue(productPropertyValue);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
        return "forward:/admin/productPropertyValue/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
		//return PathResolver.getPath(request, response, FowardPage.PRODUCTPROPERTYVALUE_LIST_QUERY);
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        ProductPropertyValue productPropertyValue = productPropertyValueService.getProductPropertyValue(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productPropertyValue.getUserName());
		//if(result!=null){
			//return result;
		//}
		productPropertyValueService.deleteProductPropertyValue(productPropertyValue);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
        return "forward:/admin/productPropertyValue/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
        //return PathResolver.getPath(request, response, FowardPage.PRODUCTPROPERTYVALUE_LIST_QUERY);
    }

    @RequestMapping(value = "/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        ProductPropertyValue productPropertyValue = productPropertyValueService.getProductPropertyValue(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productPropertyValue.getUserName());
		//if(result!=null){
			//return result;
		//}
        request.setAttribute("productPropertyValue", productPropertyValue);
        return "/productPropertyValue/productPropertyValue";
         //TODO, replace by next line, need to predefined FowardPage parameter
         //return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTYVALUE_EDIT_PAGE);
    }
    
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return "/productPropertyValue/productPropertyValue";
		//TODO, replace by next line, need to predefined BackPage parameter
		//return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTYVALUE_EDIT_PAGE);
	}

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {        
        ProductPropertyValue productPropertyValue = productPropertyValueService.getProductPropertyValue(id);
		//String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productPropertyValue.getUserName());
		//if(result!=null){
			//return result;
		//}
		request.setAttribute("productPropertyValue", productPropertyValue);
		return "forward:/admin/productPropertyValue/query.htm";
		//TODO, replace by next line, need to predefined BackPage parameter
		//return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTYVALUE_EDIT_PAGE);
    }

}
