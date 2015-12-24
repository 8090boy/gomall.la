/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import com.legendshop.business.dao.LocationDao;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.Area;
import com.legendshop.model.entity.City;
import com.legendshop.model.entity.Province;
import com.legendshop.spi.service.LocationService;
import com.legendshop.spi.service.impl.AbstractService;

/**
 * The Class LocationServiceImpl.
 */
public class LocationServiceImpl extends AbstractService  implements LocationService {
	
	/** The location dao. */
	private LocationDao locationDao;

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.LocationService#loadProvinces()
	 */
	@Override
	public List<KeyValueEntity> loadProvinces() {
		return locationDao.getProvincesList();
	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.LocationService#loadCities(java.lang.String)
	 */
	@Override
	public List<KeyValueEntity> loadCities(Integer provinceid) {
		return locationDao.getCitiesList(provinceid);
	}

	/* (non-Javadoc)
	 * @see com.legendshop.spi.service.LocationService#loadAreas(java.lang.String)
	 */
	@Override
	public List<KeyValueEntity> loadAreas(Integer cityid) {
		return locationDao.getAreaList(cityid);
	}

	@Override
	public List<Province> getAllProvince() {
		return locationDao.getAllProvince();
	}
	/**
	 * Sets the location dao.
	 *
	 * @param locationDao the locationDao to set
	 */
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	@Override
	public List<City> getCity(Integer provinceid) {
		return locationDao.getCity(provinceid);
	}

	@Override
	public List<Area> getArea(Integer cityid) {
		return locationDao.getArea(cityid);
	}

	@Override
	public Province getProvinceById(Integer id) {
		return locationDao.getProvinceById(id);
	}

	@Override
	public void deleteProvince(Integer provinceid) {
	locationDao.deleteProvince(provinceid);	
	}

	@Override
	public void deleteCity(Integer cityid) {
	locationDao.deleteCity(cityid);	
	}

	@Override
	public void deleteArea(Integer areaid) {
	locationDao.deleteArea(areaid);
	}

	@Override
	public void saveProvince(Province province) {
	locationDao.saveProvince(province);
	}

	@Override
	public City getCityById(Integer id) {
		return locationDao.getCityById(id);
	}

	@Override
	public Area getAreaById(Integer id) {
		return locationDao.getAreaById(id);
	}

	@Override
	public void updateProvince(Province province) {
		locationDao.updateProvince(province);
	}

	@Override
	public void updateCity(City city) {
		locationDao.updateCity(city);
		
	}

	@Override
	public void updateArea(Area area) {
		locationDao.updateArea(area);
	}

	@Override
	public void saveCity(City city) {
		locationDao.saveCity(city);
		
	}

	@Override
	public void saveArea(Area area) {
		locationDao.saveArea(area);
		
	}

}
