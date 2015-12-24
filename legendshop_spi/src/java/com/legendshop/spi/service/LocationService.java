package com.legendshop.spi.service;

import java.util.List;

import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.Area;
import com.legendshop.model.entity.City;
import com.legendshop.model.entity.Province;

public interface LocationService extends BaseService {

	/**
	 * Load province.
	 *
	 * @return the list
	 */
	List<KeyValueEntity>loadProvinces();
	
	/**
	 * Load cities.
	 *
	 * @param provinceid the provinceid
	 * @return the list
	 */
	List<KeyValueEntity> loadCities(Integer provinceid);
	
	/**
	 * Load areas.
	 *
	 * @param cityid the cityid
	 * @return the list
	 */
	List<KeyValueEntity> loadAreas(Integer cityid);
	
	/**
	 * 装载所有的省份
	 *
	 * @return the all province
	 */
	List<Province> getAllProvince();
	
	/**
	 * 根据省份装载城市
	 * @return the city
	 */
	List<City> getCity(Integer provinceid);
	
	/**
	 * 根据城市Id装载地区
	 * @param cityid
	 * @return the Area
	 */
	List<Area> getArea(Integer cityid);
	
	/**
	 * 根据省份Id查找省份
	 * @param provinceid
	 * @return the  province
	 */
	Province getProvinceById(Integer id);
	
	/**
	 * 根据城市Id查找城市
	 * @param cityid
	 * @return
	 */
	City getCityById(Integer id);
	
	/**
	 * 根据地区Id查找地区
	 * @param areaid
	 * @return
	 */
	Area getAreaById(Integer id);
	
	/**
	 * 删除省份
	 * @param province
	 */
	void deleteProvince(Integer provinceid);
	
	/**
	 * 删除城市
	 * @param cityid
	 */
	void deleteCity(Integer cityid);
	
	/**
	 * 删除地区
	 * @param areaid
	 */
	void deleteArea(Integer areaid);
	
	/**
	 * 添加省份
	 * @param province
	 */
	void saveProvince(Province province);
	
	/**
	 * 添加城市
	 */
	void saveCity(City city);
	
	/**
	 * 添加地区
	 * @param area
	 */
	void saveArea(Area area);
	/**
	 * 更新省份
	 */
	void updateProvince(Province province);
	
	/**
	 * 更新城市
	 */
	 void updateCity(City city);
	
	/**
	 * 更新地区
	 */
	 void updateArea(Area area);
}
