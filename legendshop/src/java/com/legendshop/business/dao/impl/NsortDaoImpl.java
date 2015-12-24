/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.entity.Nsort;
import com.legendshop.spi.dao.NsortDao;
import com.legendshop.util.AppUtils;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品子分类Dao.
 */

public class NsortDaoImpl extends BaseDaoImpl implements NsortDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(NsortDaoImpl.class);
	
	/** The base jdbc dao. */
	private BaseJdbcDao baseJdbcDao;
	
	/**
	 * 得到二级商品分类和其下面的三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @return the nsort
	 */
	@Override
	@Cacheable(value = "Nsort", key = "#nsortId")
	public Nsort getNsort(final Long nsortId) {
		log.debug("queryNsort,nsortId = {} ", nsortId);
		Nsort nsort = get(Nsort.class, nsortId);
		if (nsort != null) {
			nsort.setSubSort(new HashSet<Nsort>(findByHQL("from Nsort where parent_nsort_id = ? and status = 1", nsort.getNsortId())));
		}
		return nsort;
	}

	/**
	 * 得到一级分类下不等于nsortId的二级商品分类.
	 *
	 * @param sortId the sort id
	 * @param nsortId the nsort id
	 * @return the other nsort list
	 */
	@Override
	@Cacheable(value = "NsortList")
	public List<Nsort> getOtherNsortList(final Long sortId, final Long nsortId) {
		return findByHQL("from Nsort where sortId = ? and nsortId <> ? and status = 1", sortId, nsortId);
	}

	/**
	 * 得到相关二级小类.
	 *
	 * @param list the list
	 * @return the othor nsort
	 */
	@Override
	public List<Nsort> getOthorNsort(List<Nsort> list) {
		List<Nsort> result = new ArrayList<Nsort>();
		for (Nsort nsort : list) {
			if (nsort.getParentNsortId() == null) {
				result.add(nsort);
			}
		}
		return result;
	}
	
	/**
	 * 得到二级商品分类.
	 *
	 * @param sortId the sort id
	 * @return the nsort list
	 */
	@Override
	@Cacheable(value = "NsortList")
	public List<Nsort> getNsortList(final Long sortId) {
		return findByHQL("from Nsort where sortId = ? and status = 1", sortId);
	}

	
	/**
	 * 得到nsortId下面的三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @param list the list
	 * @return the othor sub nsort
	 */
	@Override
	public List<Nsort> getOthorSubNsort(Long nsortId, List<Nsort> list) {
		List<Nsort> result = new ArrayList<Nsort>();
		for (Nsort nsort : list) {
			if ((nsort.getParentNsortId() != null) && nsort.getParentNsortId().equals(nsortId)) {
				result.add(nsort);
			}
		}
		return result;
	}

	/**
	 * 得到sortId下的二级商品分类.
	 *
	 * @param sortId the sort id
	 * @return the nsort by sort id
	 */
	@Override
	@Cacheable(value = "NsortList")
	public List<Nsort> getNsortBySortId(final Long sortId) {
		return findByHQL("from Nsort where sortId = ? and status = 1 and parent_nsort_id is null", sortId);
	}

	
	/**
	 * 得到sortId下的三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @return the sub nsort by sort id
	 */
	@Override
	@Cacheable(value = "NsortList", key ="#nsortId")
	public List<Nsort> getSubNsortBySortId(final Long nsortId) {
		return findByHQL("from Nsort where status = 1 and parent_nsort_id = ?", nsortId);
	}
	
	/**
	 * 得到二级商品分类.
	 *
	 * @param userName the user name
	 * @return the navigation nsort
	 */
	@Override
	@Cacheable(value = "NsortList", key = "#userName")
	public List<Nsort> getNavigationNsort(String userName) {
		return findByHQL(
				"select n from Nsort n,Sort s where n.sortId=s.id and n.status =1 and s.status =1 and s.userName=? and s.sortType=? and s.navigationMenu=?",
				userName, ProductTypeEnum.PRODUCT.value(), 1);
	}

	/**
	 * 更新二级商品分类.
	 *
	 * @param nsort the nsort
	 */
	@Override
	@CacheEvict(value = "Nsort", key = "#nsort.nsortId")
	public void updateNsort(Nsort nsort) {
		update(nsort);

	}

	/**
	 * 删除二级商品分类.
	 *
	 * @param id the id
	 */
	@Override
	@CacheEvict(value = "Nsort", key = "#id")
	public void deleteNsortById(Long id) {
		exeByHQL("delete from NsortBrand where id.brandId = ?", id);
		delete(Nsort.class, id);
	}

	/**
	 * 用于填充二级商品分类.
	 *
	 * @param sortId the sort id
	 * @return the list
	 */
	@Override
	public List<KeyValueEntity> loadNSorts(Long sortId) {
		 List<Nsort> list = getNsortBySortId(sortId);
		 return convertToEntity(list);
	}


	/**
	 * 用于填充三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @return the list
	 */
	@Override
	public List<KeyValueEntity> loadSubNSorts(Long nsortId) {
		 List<Nsort> list = getSubNsortBySortId(nsortId);
		 return convertToEntity(list);
	}
	
	/**
	 * Convert to entity.
	 *
	 * @param list the list
	 * @return the list
	 */
	private List<KeyValueEntity> convertToEntity(List<Nsort> list) {
		 if(AppUtils.isBlank(list)){
			 return null;
		 }
		List<KeyValueEntity> result = new ArrayList<KeyValueEntity>(list.size());
		 for (Nsort nsort : list) {
			 KeyValueEntity entity = new KeyValueEntity();
			 entity.setKey(String.valueOf(nsort.getNsortId()));
			 entity.setValue(nsort.getNsortName());
			 result.add(entity);
		}
		return result;
	}

	/**
	 * 根据名称装载三级商品分类.
	 *
	 * @param nsortId the nsort id
	 * @param nsortName the nsort name
	 * @return the sub nsort by sort id
	 */
	@Override
	public List<Nsort> getSubNsortBySortId(Long nsortId, String nsortName) {
		if(AppUtils.isBlank(nsortName)){
			return getSubNsortBySortId(nsortId);
		}else{
			return findByHQL("from Nsort where status = 1 and parent_nsort_id = ? and nsortName like ?", nsortId,  "%" + nsortName  + "%");
		}
	}

	/**
	 * 根据二级分类找到对应的三级分类.
	 */
	@Override
	public String getUserNameByNsortId(Long subNsortId) {
		String sql = "select s.user_name as userName from ls_sort s inner join ls_nsort ns on s.sort_id = ns.sort_id where nsort_id  = ?";
		String result =baseJdbcDao.getJdbcTemplate().queryForObject(sql, String.class, subNsortId);
		return result;
	}

	/**
	 * Sets the base jdbc dao.
	 *
	 * @param baseJdbcDao the new base jdbc dao
	 */
	public void setBaseJdbcDao(BaseJdbcDao baseJdbcDao) {
		this.baseJdbcDao = baseJdbcDao;
	}

	/**
	 * 查找二级商品分类下的商品
	 */
	@Override
	public boolean hasChildProduct(String userName, Long id, Long parentNsortId) {
		Long result = 0l;
		if(PropertiesUtil.isDefaultShopName(userName)){
			if(AppUtils.isBlank(parentNsortId)){//二级域名
				result = findUniqueBy("select count(*) from Product where globalNsort = ?", Long.class, id);
			}else{//三级域名
				result = findUniqueBy("select count(*) from Product where globalSubSort = ?", Long.class, id);
			}
		}else{
			if(AppUtils.isBlank(parentNsortId)){//二级域名
				result = findUniqueBy("select count(*) from Product where nsortId = ?", Long.class, id);
			}else{//三级域名
				result = findUniqueBy("select count(*) from Product where subNsortId = ?", Long.class, id);
			}
		}
		return result > 0;
	}
}
