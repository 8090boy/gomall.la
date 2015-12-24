/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao;

import java.util.List;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.Area;
import com.legendshop.model.entity.City;
import com.legendshop.model.entity.Province;

/**
 * The Interface LocationDao.
 */
public interface LocationDao  extends BaseDao {
	
	/**
	 * Gets the provinces list.
	 *
	 * @return the provinces list
	 */
	abstract List<KeyValueEntity> getProvincesList();
	
	/**
	 * Gets the cities list.
	 *
	 * @param provinceid the provinceid
	 * @return the cities list
	 */
	abstract List<KeyValueEntity> getCitiesList(Integer provinceid);
	
	/**
	 * Gets the area list.
	 *
	 * @param cityid the cityid
	 * @return the area list
	 */
	abstract List<KeyValueEntity> getAreaList(Integer cityid);

	/**
	 * Gets the all province.
	 *
	 * @return the all province
	 */
	abstract List<Province> getAllProvince();
	
	/**
	 * 根据省份装载城市
	 * @return the city
	 */
	abstract List<City> getCity(Integer provinceid);
	
	/**
	 * 根据城市Id装载地区
	 * @param cityid
	 * @return the Area
	 */
	abstract List<Area> getArea(Integer cityid);
	
	
	/**
	 * 根据省份Id查找省份
	 * @param provinceid
	 * @return the  province
	 */
	abstract Province getProvinceById(Integer id);
	
	/**
	 * 删除省份
	 * @param province
	 */
	abstract void deleteProvince(Integer id);
	
	/**
	 * 删除城市
	 * @param cityid
	 */
	 abstract void deleteCity(Integer id);
	 
	 /**
	  * 删除地区
	  * @param areaid
	  */
	abstract void deleteArea(Integer id);
	
	/**
	 * 添加省份
	 * @param province
	 */
	abstract void saveProvince(Province province);
	
	/**
	 * 添加城市
	 * @param province
	 */
	abstract void saveCity(City city);
	
	/**
	 * 添加地区
	 * @param province
	 */
	abstract void saveArea(Area area);
	
	/**
	 * 根据城市Id查找城市
	 * @param cityid
	 * @return
	 */
	abstract City getCityById(Integer id);
	
	/**
	 * 根据地区Id查找地区
	 * @param areaid
	 * @return
	 */
	abstract Area getAreaById(Integer id);
	
	/**
	 * 更新省份
	 */
	abstract void updateProvince(Province province);
	
	/**
	 * 更新城市
	 */
	abstract void updateCity(City city);
	
	/**
	 * 更新地区
	 */
	abstract void updateArea(Area area);
}
