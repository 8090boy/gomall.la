/**
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
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.legendshop.model.entity.ProductDetail;
import com.legendshop.util.AppUtils;
import com.legendshop.util.sql.ConfigCode;

/**
 * 产品Dao.
 */
public class ProductJdbcDaoImpl extends ProductDaoImpl {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ProductJdbcDaoImpl.class);

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#getProdDetail(java.lang.Long)
	 */
	@Override
	@Cacheable(value = "ProductDetail", key = "#prodId")
	public ProductDetail getProdDetail(final Long prodId) {
		List<ProductDetail> list = null;
		String sql = ConfigCode.getInstance().getCode("prod.getProdDetail");
		log.debug("getProdDetail run sql {}, prodId = {}", sql, prodId);
		list = jdbcTemplate.query(sql, new Object[] { prodId }, new ProductDetailRowMapper());
		if (AppUtils.isBlank(list)) {
			return null;
		}
		return list.get(0);
	}

	// 得到商品列表
	public List<ProductDetail> getProdDetail(final List<Object> prodIds) {
		List<ProductDetail> products = new ArrayList<ProductDetail>();
		for (Object prodId : prodIds) {
			products.add(getProdDetail(Long.parseLong(prodId.toString())));
		}
		return products;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDaoImpl#getProdDetail(java.lang
	 * .Long[])
	 */
	@Override
	public List<ProductDetail> getProdDetail(Long[] prodId) {
		List<Long> postIdList = new ArrayList<Long>();
		StringBuffer sql = new StringBuffer(ConfigCode.getInstance().getCode("prod.getProdDetailList"));
		for (int i = 0; i < prodId.length; i++) {
			if (prodId[i] != null) {
				sql.append("?,");
				postIdList.add(prodId[i]);
			}
		}
		if (postIdList.isEmpty()) {
			return new ArrayList<ProductDetail>();
		}

		sql.setLength(sql.length() - 1);
		sql.append(")");
		if (log.isDebugEnabled()) {
			log.debug("getProdDetail run sql {}, param {}", sql.toString(), postIdList.toArray());
		}

		return jdbcTemplate.query(sql.toString(), postIdList.toArray(), new ProductDetailRowMapper());
	}

	/**
	 * The Class ProductDetailRowMapper.
	 */
	class ProductDetailRowMapper implements RowMapper<ProductDetail> {

	 
		@Override
		public ProductDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductDetail product = new ProductDetail();
			product.setProdId(rs.getLong("prod_id"));
			product.setSortId(rs.getLong("sort_id"));
			product.setNsortId(rs.getLong("nsort_id"));
			product.setSubNsortId(rs.getLong("sub_nsort_id"));
			product.setName(rs.getString("name"));
			product.setPrice(rs.getDouble("price"));
			product.setCash(rs.getDouble("cash"));
			product.setProxyPrice(rs.getDouble("proxy_price"));
			product.setCarriage(rs.getDouble("carriage"));
			product.setBrief(rs.getString("brief"));
			product.setContent(rs.getString("content"));
			product.setViews(rs.getInt("views"));
			product.setBuys(rs.getInt("buys"));
			product.setRecDate(rs.getDate("rec_date"));
			product.setPic(rs.getString("pic"));
			product.setCommend(rs.getString("commend"));
			product.setStatus(rs.getInt("status"));
			product.setModifyDate(rs.getDate("modify_date"));
			product.setUserId(rs.getString("user_id"));
			product.setUserName(rs.getString("user_name"));
			product.setStartDate(rs.getDate("start_date"));
			product.setEndDate(rs.getDate("end_date"));
			product.setStocks(rs.getInt("stocks"));
			product.setProdType(rs.getString("prod_type"));
			product.setKeyWord(rs.getString("key_word"));
			product.setAttribute(rs.getString("attribute"));
			product.setParameter(rs.getString("parameter"));
			product.setBrandId(rs.getLong("brand_id"));
			product.setSortName(rs.getString("sort_name"));
			product.setNsortName(rs.getString("nsort_name"));
			product.setSubNsortName(rs.getString("sub_nsort_name"));
			product.setBrandName(rs.getString("brand_name"));
			product.setProdType(rs.getString("prod_type"));
			product.setModelId(rs.getString("model_id"));
			product.setPoints(rs.getInt("points"));
			return product;
		}
	}
	
	/**
	 * 产品是否被订购过
	 */
	@Override
	public boolean hasOrder(Long prodId) {
		int result = jdbcTemplate.queryForInt("select count(*) from ls_sub s, ls_basket b where s.sub_number = b. sub_number and b.prod_id = ?", prodId);
		return result > 0;
	}

	/**
	 * Sets the jdbc template.
	 * 
	 * @param jdbcTemplate
	 *            the new jdbc template
	 */
	@Required
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
