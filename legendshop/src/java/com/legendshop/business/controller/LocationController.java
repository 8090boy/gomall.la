/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.spi.service.LocationService;


/**
 * The Class LocationController.
 */
@Controller
public class LocationController extends BaseController{
	
	/** The location service. */
	@Autowired
	private LocationService locationService;
	/**
	 * Load provinces entity.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the list
	 */
	@RequestMapping("/common/loadProvinces")
	public @ResponseBody
	List<KeyValueEntity> loadProvincesEntity(HttpServletRequest request, HttpServletResponse response) {
		return locationService.loadProvinces();
	}

	/**
	 * Load cities.
	 *
	 * @param request the request
	 * @param response the response
	 * @param provinceid the provinceid
	 * @return the list
	 */
	@RequestMapping("/common/loadCities/{provinceid}")
	public @ResponseBody
	List<KeyValueEntity> loadCities(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer provinceid) {
		return locationService.loadCities(provinceid);
	}

	/**
	 * Load areas.
	 *
	 * @param request the request
	 * @param response the response
	 * @param cityid the cityid
	 * @return the list
	 */
	@RequestMapping("/common/loadAreas/{cityid}")
	public @ResponseBody List<KeyValueEntity> loadAreas(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer cityid) {
		return locationService.loadAreas(cityid);
	}
}
