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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.entity.ProductProperty;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.ProductPropertyService;

/**
 * The Class ProductPropertyController
 *
 */
@Controller
@RequestMapping("/admin/productProperty")
public class ProductPropertyController extends BaseController implements AdminController<ProductProperty, Long> {
    @Autowired
    private ProductPropertyService productPropertyService;

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, ProductProperty productProperty) {
        CriteriaQuery cq = new CriteriaQuery(ProductProperty.class, curPageNO);
        //cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
        //cq = hasAllDataFunction(cq, request, StringUtils.trim(productProperty.getUserName()));
        /*
           //TODO add your condition
        */
        cq.add();
        PageSupport ps = productPropertyService.getProductProperty(cq);
        ps.savePage(request);
        request.setAttribute("productProperty", productProperty);
        
       // return "/productProperty/productPropertyList";
        //TODO, replace by next line, need to predefined BackPage parameter
       return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTY_LIST_PAGE);
    }

    @RequestMapping(value = "/save")
    public String save(HttpServletRequest request, HttpServletResponse response, ProductProperty productProperty) {
    	System.out.println("productProperty = "+ToStringBuilder.reflectionToString(productProperty));
        productPropertyService.saveProductProperty(productProperty);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
       // return "forward:/admin/productProperty/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
		return PathResolver.getPath(request, response, FowardPage.PRODUCTPROPERTY_LIST_QUERY);
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        ProductProperty productProperty = productPropertyService.getProductProperty(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productProperty.getUserName());
		//if(result!=null){
			//return result;
		//}
		productPropertyService.deleteProductProperty(productProperty);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
       // return "forward:/admin/productProperty/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
        return PathResolver.getPath(request, response, FowardPage.PRODUCTPROPERTY_LIST_QUERY);
    }

    @RequestMapping(value = "/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        ProductProperty productProperty = productPropertyService.getProductProperty(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productProperty.getUserName());
		//if(result!=null){
			//return result;
		//}
        request.setAttribute("productProperty", productProperty);
       // return "/productProperty/productProperty";
         //TODO, replace by next line, need to predefined FowardPage parameter
         return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTY_EDIT_PAGE);
    }
    
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		//return "/productProperty/productProperty";
		//TODO, replace by next line, need to predefined BackPage parameter
		return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTY_EDIT_PAGE);
	}

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {        
        ProductProperty productProperty = productPropertyService.getProductProperty(id);
		//String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productProperty.getUserName());
		//if(result!=null){
			//return result;
		//}
		request.setAttribute("productProperty", productProperty);
		//return "forward:/admin/productProperty/query.htm";
		//TODO, replace by next line, need to predefined BackPage parameter
		return PathResolver.getPath(request, response, BackPage.PRODUCTPROPERTY_EDIT_PAGE);
    }

}
