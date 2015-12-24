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
import com.legendshop.model.entity.ProductSpec;
import com.legendshop.spi.service.ProductSpecService;

/**
 * The Class ProductSpecController
 *
 */
@Controller
@RequestMapping("/admin/productSpec")
public class ProductSpecController extends BaseController implements AdminController<ProductSpec, Long> {
    @Autowired
    private ProductSpecService productSpecService;

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, ProductSpec productSpec) {
        CriteriaQuery cq = new CriteriaQuery(ProductSpec.class, curPageNO);
        //cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
        //cq = hasAllDataFunction(cq, request, StringUtils.trim(productSpec.getUserName()));
        /*
           //TODO add your condition
        */
        cq.add();
        PageSupport ps = productSpecService.getProductSpec(cq);
        ps.savePage(request);
        request.setAttribute("productSpec", productSpec);
        
        return "/productSpec/productSpecList";
        //TODO, replace by next line, need to predefined BackPage parameter
       // return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_LIST_PAGE);
    }

    @RequestMapping(value = "/save")
    public String save(HttpServletRequest request, HttpServletResponse response, ProductSpec productSpec) {
        productSpecService.saveProductSpec(productSpec);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
        return "forward:/admin/productSpec/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
		//return PathResolver.getPath(request, response, FowardPage.PRODUCTSPEC_LIST_QUERY);
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        ProductSpec productSpec = productSpecService.getProductSpec(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productSpec.getUserName());
		//if(result!=null){
			//return result;
		//}
		productSpecService.deleteProductSpec(productSpec);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
        return "forward:/admin/productSpec/query.htm";
        //TODO, replace by next line, need to predefined FowardPage parameter
        //return PathResolver.getPath(request, response, FowardPage.PRODUCTSPEC_LIST_QUERY);
    }

    @RequestMapping(value = "/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        ProductSpec productSpec = productSpecService.getProductSpec(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productSpec.getUserName());
		//if(result!=null){
			//return result;
		//}
        request.setAttribute("productSpec", productSpec);
        return "/productSpec/productSpec";
         //TODO, replace by next line, need to predefined FowardPage parameter
         //return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_EDIT_PAGE);
    }
    
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return "/productSpec/productSpec";
		//TODO, replace by next line, need to predefined BackPage parameter
		//return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_EDIT_PAGE);
	}

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {        
        ProductSpec productSpec = productSpecService.getProductSpec(id);
		//String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), productSpec.getUserName());
		//if(result!=null){
			//return result;
		//}
		request.setAttribute("productSpec", productSpec);
		return "forward:/admin/productSpec/query.htm";
		//TODO, replace by next line, need to predefined BackPage parameter
		//return PathResolver.getPath(request, response, BackPage.PRODUCTSPEC_EDIT_PAGE);
    }

}
