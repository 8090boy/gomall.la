/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.CacheEvict;

import com.legendshop.business.dao.BrandDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.entity.Brand;
import com.legendshop.spi.service.BrandService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class BrandServiceImpl implements BrandService {

	/** The Brand dao. */
	private BrandDao brandDao;

	/**
	 * Sets the base dao.
	 * 
	 * @param brandDao
	 *            the brandDao
	 */
	@Required
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.BrandService#list(java.lang.String)
	 */
	@Override
	public List<Brand> getBrand(String userName) {
		return brandDao.findByHQL("from Brand where userName = ?", new Object[] { userName });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.BrandService#load(java.lang.Long)
	 */
	@Override
	public Brand getBrand(Long id) {
		return brandDao.get(Brand.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.service.BrandService#delete(java.lang.Long)
	 */
	@Override
	@CacheEvict(value = "Brand", key = "#id")
	public void delete(Long id) {
		brandDao.exeByHQL("delete from NsortBrand where id.brandId = ?", id);
		brandDao.deleteBrandById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.BrandService#save(com.legendshop.model
	 * .entity.Brand)
	 */
	@Override
	public Long save(Brand brand) {
		if (!AppUtils.isBlank(brand.getBrandId())) {
			update(brand);
			return brand.getBrandId();
		}
		return (Long) brandDao.save(brand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.BrandService#update(com.legendshop.model
	 * .entity.Brand)
	 */
	@Override
	@CacheEvict(value = "Brand", key = "#brand.brandId")
	public void update(Brand brand) {
		brandDao.updateBrand(brand);
	}
	
	@Override
	public List<KeyValueEntity> loadBrandEntityBySubSortId(Long subNsortId,String userName) {
		return convertToEntity(brandDao.loadBrandBySubSortId(subNsortId, userName));
	}


	@Override
	public List<Brand> loadBrandBySubSortId(Long subNsortId, String userName) {
		return brandDao.loadBrandBySubSortId(subNsortId, userName);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.BrandService#getDataByCriteriaQuery(com
	 * .legendshop.core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getDataByCriteriaQuery(CriteriaQuery cq) {
		return brandDao.find(cq);
	}

	@Override
	public String saveBrandItem(List<String> idList, Long nsortId, String userName) {

		return brandDao.saveBrandItem(idList, nsortId, userName);

	}

	@Override
	public String saveBrandItem(String idJson, String nameJson, Long nsortId, String userName) {
		List<String> idList = JSONUtil.getArray(idJson, String.class);
		return brandDao.saveBrandItem(idList, nsortId, userName);
	}

	@Override
	public List<Item> getUsableBrandByName(Long nsortId, String userName, String brandName) {
		return brandDao.getUsableBrandByName(nsortId, userName, brandName);
	}

	@Override
	public List<Item> getUsableBrand(Long nsortId, String userName) {
		List<Item> list = new ArrayList<Item>();
		try {
			list = brandDao.getUsableBrand(nsortId, userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Item> getUsedBrand(Long nsortId, String userName) {
		return brandDao.getUsedBrand(nsortId, userName);
	}

	
	private List<KeyValueEntity> convertToEntity(List<Brand> list) {
		 if(AppUtils.isBlank(list)){
			 return null;
		 }
		List<KeyValueEntity> result = new ArrayList<KeyValueEntity>(list.size());
		 for (Brand brand : list) {
			 KeyValueEntity entity = new KeyValueEntity();
			 entity.setKey(String.valueOf(brand.getBrandId()));
			 entity.setValue(brand.getBrandName());
			 result.add(entity);
		}
		return result;
	}

	@Override
	public List<Brand> loadBrandByName(Long subNsortId, String shopName, String brandName) {
		return brandDao.loadBrandByName(subNsortId, shopName, brandName);
	}

	@Override
	public boolean hasChildProduct(String userName, Long brandId) {
		return brandDao.hasChildProduct(userName, brandId);
	}


}
