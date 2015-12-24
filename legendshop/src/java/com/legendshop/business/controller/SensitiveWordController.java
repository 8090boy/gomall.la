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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.legendshop.core.UserManager;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.base.AdminController;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.service.SensitiveWordService;

import com.legendshop.model.entity.SensitiveWord;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.constant.SysParameterEnum;

/**
 * The Class SensitiveWordController
 *
 */
@Controller
@RequestMapping("/admin/system/sensitiveWord")
public class SensitiveWordController extends BaseController implements AdminController<SensitiveWord, Long> {
    @Autowired
    private SensitiveWordService sensitiveWordService;

    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, SensitiveWord sensitiveWord) {
        CriteriaQuery cq = new CriteriaQuery(SensitiveWord.class,curPageNO);
        //cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
        //cq = hasAllDataFunction(cq, request, StringUtils.trim(sensitiveWord.getUserName()));
        /*
           //TODO add your condition
        */
        cq.eq("sortId", sensitiveWord.getSortId());
		cq.eq("nsortId", sensitiveWord.getNsortId());
		cq.eq("subNsortId", sensitiveWord.getSubNsortId());
        cq.eq("words",sensitiveWord.getWords());
        PageSupport ps = sensitiveWordService.getSensitiveWord(cq);
        ps.savePage(request);
        request.setAttribute("sensitiveWord", sensitiveWord);
        
        return PathResolver.getPath(request, response, BackPage.SENSITIVEWORD_LIST_PAGE);
    }

    @RequestMapping(value = "/save")
    public String save(HttpServletRequest request, HttpServletResponse response, SensitiveWord sensitiveWord) {
    	
        sensitiveWordService.saveSensitiveWord(sensitiveWord);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.SENSITIVEWORD_LIST_QUERY);
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        SensitiveWord sensitiveWord = sensitiveWordService.getSensitiveWord(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), sensitiveWord.getUserName());
		//if(result!=null){
			//return result;
		//}
		sensitiveWordService.deleteSensitiveWord(sensitiveWord);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
        return PathResolver.getPath(request, response, FowardPage.SENSITIVEWORD_LIST_QUERY);
    }

    @RequestMapping(value = "/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable
    Long id) {
        SensitiveWord sensitiveWord = sensitiveWordService.getSensitiveWord(id);
        //String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), sensitiveWord.getUserName());
		//if(result!=null){
			//return result;
		//}
        request.setAttribute("sensitiveWord", sensitiveWord);
         return PathResolver.getPath(request, response, BackPage.SENSITIVEWORD_EDIT_PAGE);
    }
    
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.SENSITIVEWORD_EDIT_PAGE);
	}

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {        
        SensitiveWord sensitiveWord = sensitiveWordService.getSensitiveWord(id);
		//String result = checkPrivilege(request, UserManager.getUsername(request.getSession()), sensitiveWord.getUserName());
		//if(result!=null){
			//return result;
		//}
		request.setAttribute("sensitiveWord", sensitiveWord);
		return PathResolver.getPath(request, response, BackPage.SENSITIVEWORD_EDIT_PAGE);
    }

}
