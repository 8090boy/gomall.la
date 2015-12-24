/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.List;

import com.legendshop.business.dao.LocationDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.Area;
import com.legendshop.model.entity.City;
import com.legendshop.model.entity.Province;

/**
 * The Class LocationDaoImpl.
 */
public class LocationDaoImpl extends BaseDaoImpl implements LocationDao {

	/** The jdbc dao. */
	private BaseJdbcDao baseJdbcDao;
	
	/* (non-Javadoc)
	 * @see com.legendshop.business.dao.LocationDao#getProvincesList()
	 */
	public List<KeyValueEntity> getProvincesList(){
		return baseJdbcDao.query(" select id as 'key' ,province as 'value' from ls_provinces ", KeyValueEntity.class);
	}
	
	/* (non-Javadoc)
	 * @see com.legendshop.business.dao.LocationDao#getCitiesList(java.lang.String)
	 */
	public List<KeyValueEntity> getCitiesList(Integer provinceid){
		return baseJdbcDao.query("select id as 'key' ,city as 'value' from ls_cities where provinceid=? ", KeyValueEntity.class, provinceid);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.business.dao.LocationDao#getAreaList(java.lang.String)
	 */
	public List<KeyValueEntity> getAreaList(Integer cityid){
		List<KeyValueEntity> result =  baseJdbcDao.query(" select id as 'key' ,area as 'value' from ls_areas where cityid=?", KeyValueEntity.class, cityid);
		return result;
	}

	/**
	 * Sets the base jdbc dao.
	 *
	 * @param baseJdbcDao the baseJdbcDao to set
	 */
	public void setBaseJdbcDao(BaseJdbcDao baseJdbcDao) {
		this.baseJdbcDao = baseJdbcDao;
	}

	/**
	 * 装载所有的省份
	 *
	 * @return the all province
	 */
	public List<Province> getAllProvince() {
		List<Province> list=baseJdbcDao.query("select * from ls_provinces",Province.class);
		return list;
	}

	/**
	 * 根据省份装载城市
	 * @return the city
	 */
	public List<City> getCity(Integer provinceid) {
		List<City> list=baseJdbcDao.query("select * from ls_cities where provinceid=?",City.class, provinceid);
		return list;
	}

	/**
	 * 根据城市Id装载地区
	 * @param cityid
	 * @return the Area
	 */
	public List<Area> getArea(Integer cityid) {
		List<Area> list=baseJdbcDao.query("select * from ls_areas where cityid=?",Area.class,cityid);
		return list;
	}

	/**
	 * 根据省份Id查找省份
	 * @param provinceid
	 * @return the  province
	 */
	public Province getProvinceById(Integer id) {
		List<Province> province=baseJdbcDao.query("select * from ls_provinces where id=?",Province.class,id);
		for(Province p:province){
			return p;
		}
		return null;
	}

	/**
	 * 删除省份
	 * @param province
	 */
	public void deleteProvince(Integer id) {
		//1.删除省份下的地区
		int result = baseJdbcDao.update("delete from ls_areas where cityid in (select id from ls_cities where provinceid = ?)", id);
		//2.删除省份下的城市
		result = baseJdbcDao.update("delete from ls_cities where provinceid = ?", id);
		//3.删除省份
		result = baseJdbcDao.update("delete from ls_provinces where id=?",id);
	}

	/**
	 * 删除城市
	 * @param cityid
	 */
	public void deleteCity(Integer id) {
		//1.删除城市下的地区
		baseJdbcDao.update("delete from ls_areas where cityid  = ?", id);
		//2.删除城市
		baseJdbcDao.update("delete from ls_cities where id=?",id);
	}

	/**
	  * 删除地区
	  * @param areaid
	  */
	public void deleteArea(Integer id) {
		baseJdbcDao.update("delete from ls_areas where id=?",id);
	}

	/**
	 * 添加省份
	 * @param province
	 */
	public void saveProvince(Province province) {
		save(province);
	}
	
	/**
	 * 添加城市
	 * @param province
	 */
	public void saveCity(City city) {
		save(city);
	}
	
	/**
	 * 添加地区
	 * @param province
	 */
	public void saveArea(Area area) {
		save(area);
	}

	/**
	 * 根据城市Id查找城市
	 * @param cityid
	 * @return
	 */
	public City getCityById(Integer id) {
		return baseJdbcDao.get("select * from ls_cities where id=?",City.class,id);
	}

	/**
	 * 根据地区Id查找地区
	 * @param areaid
	 * @return
	 */
	public Area getAreaById(Integer id) {
		return baseJdbcDao.get("select * from ls_areas where id=?",Area.class,id);
	}

	/**
	 * 更新省份
	 */
	public void updateProvince(Province province) {
		baseJdbcDao.update("update ls_provinces set province= ? ,provinceid= ? where id= ? ",province.getProvince(),province.getProvinceid(),province.getId());	
	}

	/**
	 * 更新城市
	 */
	public void updateCity(City city) {
		baseJdbcDao.update("update ls_cities set city=?,cityid=? where id=? ",city.getCity(),city.getCityid(),city.getId());	
	}

	/**
	 * 更新地区
	 */
	public void updateArea(Area area) {
		baseJdbcDao.update("update ls_areas set area=?,areaid=? where id=? ",area.getArea(),area.getAreaid(),area.getId());	
	}

}
