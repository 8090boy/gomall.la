/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.page.TempletManager;
import com.legendshop.model.template.Templet;
import com.legendshop.model.template.TempletEntity;
import com.legendshop.util.ContextServiceLocator;
import com.legendshop.util.JSONUtil;


/**
 * The Class LocationController.
 */
@Controller
public class TempletController extends BaseController{
	
	/** The location service. */
	@Autowired
	private TempletManager templetManager;

	@RequestMapping("/common/loadTempletsEntity")
	public String loadTempletsEntity(HttpServletRequest request, HttpServletResponse response) {
	
		TempletEntity templet =  new TempletEntity();
		templet.setFrontEndTempletList(CommonServiceUtil.convertKeyValueEntity(templetManager.getFrontEndTempletList()));
		templet.setBackEndTempletList(CommonServiceUtil.convertKeyValueEntity(templetManager.getBackEndTempletList()));
		
		//front end
		Map<String,String> frontEndTempletMap = templetManager.getFrontEndtTempletMap();
		for (String templetId : frontEndTempletMap.keySet()) {
			Templet newTemplet = getFrontEndTemplet(templetId);
			convertTemplet(templetId,newTemplet );
			templet.addFrontEndTempletMap(newTemplet);
		}
		//back end
		Map<String,String> backEndTempletMap = templetManager.getBackEndTempletMap();
		for (String templetId : backEndTempletMap.keySet()) {
			Templet newTemplet = getBackEndTemplet(templetId);
			convertTemplet(templetId,newTemplet );
			templet.addBackEndTempletMap(newTemplet);
		}
		
		request.setAttribute("templetEntity", JSONUtil.getJson(templet));
		return "/pages/backend/default/linkage_templet";
	}
	
	private void convertTemplet(String templetId, Templet newTemplet){
		newTemplet.setId(templetId);
		newTemplet.setStyles(CommonServiceUtil.convertKeyValueEntity(newTemplet.getStyles()));
		newTemplet.setLanguages(CommonServiceUtil.convertKeyValueEntity(newTemplet.getLanguages()));
	}
	
	private Templet getFrontEndTemplet(String templetId){
		return (Templet)ContextServiceLocator.getInstance().getBean(templetManager.getFrontEndTempletById(templetId));
	}
	
	private Templet getBackEndTemplet(String templetId){
		return (Templet)ContextServiceLocator.getInstance().getBean(templetManager.getBackEndTempletById(templetId));
	}


}
