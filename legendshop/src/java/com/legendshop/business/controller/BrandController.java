/*
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
package com.legendshop.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.BrandService;
import com.legendshop.spi.service.NsortService;

/**
 * The brands controller.
 * 
 * @author George
 * 
 */

@Controller
public class BrandController extends BaseController {
	
	@Autowired
	private BrandService brandService;

	@Autowired
	private NsortService nsortService;
	
	/** The log. */
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/allbrands")
	public String allBrands(HttpServletRequest request, HttpServletResponse response) {
		try {
			// TODO
			logger.debug("toAllBrand calling...");
		} catch (Exception e) {
			logger.error("invoking toAllBrand", e);
		}
		return PathResolver.getPath(request, response, TilesPage.ALL_BRAND);
	}
	
	/**
	 * 用于填充三级商品下的品牌分类的select options
	 * @param request
	 * @param response
	 * @param sortId
	 * @return
	 */
	@RequestMapping("/brand/loadBrands/{subNsortId}")
	public @ResponseBody
	List<KeyValueEntity> loadBrands(HttpServletRequest request, HttpServletResponse response, @PathVariable Long subNsortId) {
		String shopName = nsortService.getUserNameByNsortId(subNsortId);
		return brandService.loadBrandEntityBySubSortId(subNsortId,shopName);
	}

	public void setNsortService(NsortService nsortService) {
		this.nsortService = nsortService;
	}
	

}
