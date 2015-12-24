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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.model.entity.Area;
import com.legendshop.model.entity.City;
import com.legendshop.model.entity.DistrictEntity;
import com.legendshop.model.entity.Province;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.LocationService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;


/**
 * 地区管理控制器
 *
 */
@Controller
@RequestMapping("/admin/system/district")
public class DistrictController extends BaseController {
    @Autowired
    private LocationService locationService;


    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response) {
        return PathResolver.getPath(request, response, BackPage.DISTRICT_LIST_PAGE);
    }
    
    /**
     * Ajax Call 查找省份
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/queryProvince")
    public String queryProvince(HttpServletRequest request, HttpServletResponse response) {
    	List<Province> provinceList = locationService.getAllProvince();
    	request.setAttribute("provinceList", provinceList);
        String path = PathResolver.getPath(request, response, BackPage.PROVINCE_CONTENT_LIST_PAGE);
        return path;
    }
    /**
     * Ajax Call 查找城市
     * @param request
     * @param response
     * @param curPageNO
     * @param provinceid
     * @return
     */
    @RequestMapping("/queryCities/{provinceid}")
    public String queryCities(HttpServletRequest request, HttpServletResponse response, String curPageNO,@PathVariable Integer provinceid) {
    	List<City> cityList = locationService.getCity(provinceid);
    	request.setAttribute("cityList", cityList);
        String path = PathResolver.getPath(request, response, BackPage.CITY_CONTENT_LIST_PAGE);
        return path;
    }
    /**
     * Ajax Call 查找地区
     * @param request
     * @param response
     * @param curPageNO
     * @param cityid
     * @return
     */
    @RequestMapping("/queryAreas/{cityid}")
    public String queryAreas(HttpServletRequest request, HttpServletResponse response, String curPageNO,@PathVariable Integer cityid) {
    	List<Area> areaList = locationService.getArea(cityid);
    	request.setAttribute("areaList", areaList);
        String path = PathResolver.getPath(request, response, BackPage.AREA_CONTENT_LIST_PAGE);
        return path;
    }
    
    @RequestMapping(value = "/save")
    public  String  save(HttpServletRequest request, HttpServletResponse response,Province province) {
    	if(AppUtils.isBlank(province.getProvince()) || AppUtils.isBlank(province.getProvinceid())){
    		throw new BusinessException("province name or Provinceid can not be empty ");
    	}
    	if(province.getId() != null){ //update
    		locationService.updateProvince(province);
    	}else{//save
    		locationService.saveProvince(province);
    	}
    	
    	DistrictEntity entity = new DistrictEntity();
    	entity.setProvinceid(province.getId());
    	request.setAttribute("entity", entity);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
        String path = PathResolver.getPath(request, response, FowardPage.DISTRICT_LIST_PAGE);
        return path;
    }

    @RequestMapping(value = "/savecity")
    public  String  saveCity(HttpServletRequest request, HttpServletResponse response, City city) {
    	if(AppUtils.isBlank(city.getCity()) || AppUtils.isBlank(city.getProvinceid())){
    		throw new BusinessException("City name or cityid can not be empty ");
    	}
    	if(city.getId() !=null){//update
    		locationService.updateCity(city);
    	}else{//save
    		locationService.saveCity(city);
    	}
    	DistrictEntity entity = new DistrictEntity();
    	entity.setCityid(city.getId());
    	entity.setProvinceid(city.getProvinceid());
    	request.setAttribute("entity", entity);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
        String path = PathResolver.getPath(request, response, FowardPage.DISTRICT_LIST_PAGE);
        return path;
    }
    
    @RequestMapping(value = "/savearea")
    public String  saveArea(HttpServletRequest request, HttpServletResponse response, Area area) {
    	if(AppUtils.isBlank(area.getArea()) || AppUtils.isBlank(area.getAreaid())){
    		throw new BusinessException("Area name or areaid can not be empty ");
    	}
    	if( area.getId() != null){// update
    		locationService.updateArea(area);
    	}else{//save
    		locationService.saveArea(area);
    	}
    	City city = locationService.getCityById(area.getCityid());
    	DistrictEntity entity = new DistrictEntity();
    	entity.setProvinceid(city.getProvinceid());
    	entity.setCityid(area.getCityid());
    	entity.setAreaid(area.getId());
    	request.setAttribute("entity", entity);
        saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
        String path = PathResolver.getPath(request, response, FowardPage.DISTRICT_LIST_PAGE);
        return path;
    }
    
    @RequestMapping(value = "/deleteprovince/{provinceid}")
    public @ResponseBody String  deleteProvince(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer provinceid) {
    	locationService.deleteProvince(provinceid);
    	DistrictEntity entity = new DistrictEntity();
    	entity.setProvinceid(provinceid);
    	request.setAttribute("entity", entity);
        return Constants.SUCCESS;
    }
    
    /**
     * 同时删除多个省份
     * @param request
     * @param response
     * @param provinceid
     * @return
     */
    @RequestMapping(value = "/deleteprovince")
    public @ResponseBody String  deleteMultiProvince(HttpServletRequest request, HttpServletResponse response, String ids) {
    	List<Integer> idList = JSONUtil.getArray(ids, Integer.class);
    	if(AppUtils.isNotBlank(idList)){
    		for (Integer provinceid : idList) {
    			locationService.deleteProvince(provinceid);
			}
    	}
        return Constants.SUCCESS;
    }
    
    @RequestMapping(value = "/deletecity/{cityid}")
    public @ResponseBody String  deleteCity(HttpServletRequest request, HttpServletResponse response, @PathVariable  Integer cityid) {
    	locationService.deleteCity(cityid);
    	DistrictEntity entity = new DistrictEntity();
    	entity.setCityid(cityid);
    	request.setAttribute("entity", entity);
       return Constants.SUCCESS;
    }
    
    /**
     * 同时删除多个地区
     * @param request
     * @param response
     * @param cityids
     * @return
     */
    @RequestMapping(value = "/deletecity")
    public @ResponseBody String  deleteMultiCity(HttpServletRequest request, HttpServletResponse response,  String ids) {
    	List<Integer> idList = JSONUtil.getArray(ids, Integer.class);
    	if(AppUtils.isNotBlank(idList)){
    		for (Integer cityid : idList) {
    		  	locationService.deleteCity(cityid);
			}
    	}
       return Constants.SUCCESS;
    }
    
    @RequestMapping(value = "/deletearea/{areaid}")
    public @ResponseBody String  deleteArea(HttpServletRequest request, HttpServletResponse response,  @PathVariable Integer areaid) {
    	Area area = locationService.getAreaById(areaid);
    	if(AppUtils.isNotBlank(area)){
        	City city = locationService.getCityById(area.getCityid());
        	locationService.deleteArea(areaid);
        	DistrictEntity entity = new DistrictEntity();
        	entity.setProvinceid(city.getProvinceid());
        	entity.setCityid(city.getId());
        	entity.setAreaid(areaid);
        	request.setAttribute("entity", entity);
        	return Constants.SUCCESS;
    	}else{
    		return Constants.FAIL;
    	}
    }
    
    @RequestMapping(value = "/deletearea")
    public @ResponseBody String  deleteMultiArea(HttpServletRequest request, HttpServletResponse response, String ids) {
    	List<Integer> idList = JSONUtil.getArray(ids, Integer.class);
    	if(AppUtils.isNotBlank(idList)){
    		for (Integer areaid : idList) {
    		 	locationService.deleteArea(areaid);
			}
    	}
    	return Constants.SUCCESS;
    }
    
    @RequestMapping("/load/{id}")
    public String load(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
    	Province province=locationService.getProvinceById(id);
    	request.setAttribute("province", province);
        return PathResolver.getPath(request, response, BackPage.RPOVINCE_PAGE);
    }
    
    @RequestMapping("/addprovince")
    public String addProvince(HttpServletRequest request, HttpServletResponse response) {
        return PathResolver.getPath(request, response, BackPage.RPOVINCE_PAGE);
    }
    
    @RequestMapping("/update/{id}")
    public String update(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
    	Province province=locationService.getProvinceById(id);
    	locationService.updateProvince(province);
        return PathResolver.getPath(request, response, BackPage.DISTRICT_LIST_PAGE);
    }
    
    @RequestMapping("/loadcity/{id}")
    public String loadcity(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
    	City city=locationService.getCityById(id);
    	request.setAttribute("city", city);
    	if(city != null){
    		Province province = locationService.getProvinceById(city.getProvinceid());
    		request.setAttribute("province", province);
    	}
        return PathResolver.getPath(request, response, BackPage.CITY_PAGE);
    }
    
    @RequestMapping("/addcity/{provinceid}")
    public String addCity(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer provinceid) {
    	if(provinceid != null){
    		Province province = locationService.getProvinceById(provinceid);
    		request.setAttribute("province", province);
    	}
        return PathResolver.getPath(request, response, BackPage.CITY_PAGE);
    }
    
    @RequestMapping("/updatecity/{id}")
    public String updatecity(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
    	City city=locationService.getCityById(id);
    	locationService.updateCity(city);
        return PathResolver.getPath(request, response, BackPage.DISTRICT_LIST_PAGE);
    }
    
    @RequestMapping("/loadarea/{id}")
    public String loadarea(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
    	Area area=locationService.getAreaById(id);
    	if(area != null){
    		City city = locationService.getCityById(area.getCityid());
    		request.setAttribute("city", city);
    		Province province = locationService.getProvinceById(city.getProvinceid());
    		request.setAttribute("province", province);
    	}
    	
    	request.setAttribute("area", area);
        return PathResolver.getPath(request, response, BackPage.AREA_PAGE);
    }
    
    @RequestMapping("/addarea/{cityid}")
    public String addArea(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer cityid) {
    	if(cityid != null){
    		City city = locationService.getCityById(cityid);
    		request.setAttribute("city", city);
    		Province province = locationService.getProvinceById(city.getProvinceid());
    		request.setAttribute("province", province);
    	}
    return PathResolver.getPath(request, response, BackPage.AREA_PAGE);
}
    
    @RequestMapping("/updatearea/{id}")
    public String updatearea(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id) {
    	Area area=locationService.getAreaById(id);
    	locationService.updateArea(area);
        return PathResolver.getPath(request, response, BackPage.DISTRICT_LIST_PAGE);
    }
}
