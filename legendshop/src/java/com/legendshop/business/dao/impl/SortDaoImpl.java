/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.Brand;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.dao.SortDao;
import com.legendshop.util.AppUtils;
import com.legendshop.util.sql.ConfigCode;

/**
 * 产品分类Dao.
 */
@SuppressWarnings("unchecked")
public class SortDaoImpl extends BaseDaoImpl implements SortDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(SortDaoImpl.class);

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;


	/**
	 * 加载一级商品分类
	 */
	@Override
	@Cacheable(value = "Sort", key = "#sortId")
	public Sort getSort(Long sortId) {
		return get(Sort.class, sortId);
	}


	/**
	 * 删除一级商品分类
	 */
	@Override
	@CacheEvict(value = "Sort", key = "#sortId")
	public void deleteSortById(Long sortId) {
		deleteById(Sort.class, sortId);
	}

	/**
	 * 更新一级商品分类
	 */
	@Override
	@CacheEvict(value = "Sort", key = "#sort.sortId")
	public void updateSort(Sort sort) {
		update(sort);
	}

	/**
	 * 保存一级商品分类
	 */
	@Override
	public Long saveSort(Sort sort) {
		return (Long) save(sort);
	}

	/**
	 * 查询一级商品分类下的产品
	 */
	@Override
	public List<Product> getProductBySortId(Long sortId) {
		return findByHQL("from Product where sortId = ?", sortId);
	}

	/**
	 * 根据一级分类Id找对应的二级商品分类
	 */
	@Override
	public List<Nsort> getNsortBySortId(Long sortId) {
		return findByHQL("from Nsort where sortId = ?", sortId);
	}

	/**
	 * 删除一级商品分类
	 */
	@Override
	@CacheEvict(value = "Sort", key = "#sort.sortId")
	public void deleteSort(Sort sort) {
		delete(sort);
	}
	

	/**
	 * 
	 * 得到一级商品分类列表
	 */
	@Override
	@Cacheable(value = "SortList",key = "#shopName + #sortType + #headerMenu + #navigationMenu  + #loadAll")
	public List<Sort> getSort( String shopName,  String sortType,  Integer headerMenu,  Integer navigationMenu,  Boolean loadAll) {
		log.debug("getSort, shopName = {}, loadAll = {}", shopName, loadAll);
		List<Object> paramList = new ArrayList<Object>();
		String hql = "from Sort where userName = ? ";
		paramList.add(shopName);

		// ,sortType,headerMenu,navigationMenu
		if (sortType != null) {
			hql += " and sortType = ? ";
			paramList.add(sortType);
		}
		if (headerMenu != null) {
			hql += " and headerMenu = ? ";
			paramList.add(headerMenu);
		}
		if (navigationMenu != null) {
			hql += " and navigationMenu = ? ";
			paramList.add(navigationMenu);
		}
		hql += " order by seq ";
		List<Sort> list = findByHQLLimit(hql,0,1000, paramList.toArray());
		if (loadAll) {
			// 找出所有的Nsort
			loadAllNsort(shopName, sortType, list);
		}
		return list;

	}
	
	/**
	 * 找出所有的下属分类
	 * @param shopName
	 * @param sortType
	 * @param list
	 */
		private void loadAllNsort(final String shopName, final String sortType, List<Sort> list) {
			if(list == null){
				return;
			}
			// 找出所有的Nsort
			List<Nsort> nsortList = null;
			if (AppUtils.isNotBlank(sortType)) {
				nsortList = findByHQL(
						"select n from Nsort n, Sort s where n.sortId = s.sortId  and n.status = 1 and s.status = 1 and  s.userName = ? and s.sortType = ?",
						shopName, sortType);
			} else {
				nsortList = findByHQL(
						"select n from Nsort n, Sort s where n.sortId = s.sortId  and n.status = 1 and s.status = 1 and  s.userName = ? ",
						shopName);
			}

			for (int i = 0; i < nsortList.size(); i++) {
				Nsort n1 = nsortList.get(i);
				for (int j = i; j < nsortList.size(); j++) {
					Nsort n2 = nsortList.get(j);
					n1.addSubSort(n2);
					n2.addSubSort(n1);
				}
			}

			for (Sort sort : list) {
				for (Nsort nsort : nsortList) {
					sort.addSubSort(nsort);
				}
			}
		}


	/**
	 * 得到商品品牌列表
	 */
	@Override
	public List<Brand> getBrandList(Long sortId) {
		String sql = ConfigCode.getInstance().getCode("biz.QueryBrandInSort");
		return jdbcTemplate.query(sql, new Object[] { sortId }, new BrandRowMapper());
	}

	class BrandRowMapper implements RowMapper<Brand> {
		@Override
		public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
			Brand brand = new Brand();
			brand.setBrandId(rs.getLong("brandId"));
			brand.setSortId(rs.getLong("sortId"));
			brand.setBrandName(rs.getString("brandName"));
			return brand;
		}

	}

	/**
	 * Sets the jdbc template.
	 * 
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 *  根据名称和类型找到对应的商品分类
	 * 发布产品是选择分类
	 * 最多找1000条记录
	 */
	@Override
	public List<Sort> getSort(String userName, String sortType, String sortName) {
		List list = null;
		if(AppUtils.isBlank(sortName)){
			 list =  findByHQLLimit("from Sort s where  s.status = 1 and  s.userName = ? and s.sortType = ?", 0,1000,
						userName, sortType);
		}else{
			 list =  findByHQLLimit("from Sort s where  s.status = 1 and  s.userName = ? and s.sortType = ? and s.sortName like ?", 0,1000,
						userName, sortType, "%" + sortName +  "%");
		}
		// 找出所有的Nsort
		loadAllNsort(userName, sortType, list);
		return list;
	}

	/**
	 * 获取二级商品分类
	 */
	@Override
	public List<Nsort> getNsortBySortId(Long sortId, String nsortName) {
		if(AppUtils.isBlank(nsortName)){
			return findByHQL("from Nsort where status = 1 and parentNsortId is null and sortId = ?", sortId);
		}else{
			return findByHQL("from Nsort where  status = 1 and sortId = ? and  parentNsortId is null and nsortName like ?", sortId, "%" + nsortName + "%");
		}

	}


	@Override
	public boolean hasChildNsort(Long sortId) {
		Long result = findUniqueBy("select count(*) from Nsort where sortId = ?", Long.class, sortId);
		return result > 0;
	}


	@Override
	public boolean hasChildProduct(String userName, Long sortId) {
		Long result = 0l;
		if(PropertiesUtil.isDefaultShopName(userName)){
			result = findUniqueBy("select count(*) from Product where globalSort = ?", Long.class, sortId);
		}else{
			result = findUniqueBy("select count(*) from Product where sortId = ?", Long.class, sortId);
		}
		return result > 0;
	}
	


}
