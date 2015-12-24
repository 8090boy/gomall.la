/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.Pub;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.PubService;
import com.legendshop.util.AppUtils;

/**
 * The Class GroupController.
 */
@Controller
public class PubController extends BaseController {
	/** The log. */

	/** The pub service. */
	@Autowired
	private PubService pubService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/pub/{id}")
	public String pub(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		if(AppUtils.isNotBlank(id)){
			String shopName = ThreadLocalContext.getCurrentShopName(request, response);
			Pub pub = pubService.getPubById(id);
			if(pub.getUserName().equals(shopName)){ //only show public notice for current shop
				request.setAttribute("pub", pub);
			}
			
		}
		pubService.getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response),
				Constants.USER_REG_ADV_740);
		return PathResolver.getPath(request, response, TilesPage.PUB);
	}

}
