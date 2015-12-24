/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.legendshop.business.dao.ProductConsultDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.model.entity.ProductConsult;
import com.legendshop.util.DateUtil;

/**
 * The Class ProductConsultDaoImpl.
 */
public class ProductConsultDaoImpl extends BaseDaoImpl implements ProductConsultDao {

	private BaseJdbcDao baseJdbcDao;

	private int interval = -5; // 单位为分钟

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductConsultDao#getProductConsult(java.
	 * lang.String)
	 */
	@Override
	public List<ProductConsult> getProductConsultList(Long prodId) {
		return findByHQL("from ProductConsult where id = ?", prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductConsultDao#getProductConsult(java.
	 * lang.Long)
	 */
	@Override
	public ProductConsult getProductConsult(Long id) {
		return get(ProductConsult.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductConsultDao#deleteProductConsult(java
	 * .lang.String)
	 */
	@Override
	public void deleteProductConsult(Long id) {
		ProductConsult pc = getProductConsult(id);
		if (pc != null) {
			delete(pc);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductConsultDao#saveProductConsult(com.
	 * legendshop.model.entity.ProductConsult)
	 */
	@Override
	public Long saveProductConsult(ProductConsult productConsult) {
		return (Long) save(productConsult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductConsultDao#updateProductConsult(com
	 * .legendshop.model.entity.ProductConsult)
	 */
	@Override
	public void updateProductConsult(ProductConsult productConsult) {
		update(productConsult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductConsultDao#getPartner(com.legendshop
	 * .core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getProductConsult(CriteriaQuery cq) {
		return find(cq);
	}

	@Override
	public PageSupport getProductConsult(SimpleSqlQuery query) {
		return baseJdbcDao.find(query);
	}

	/**
	 * @param baseJdbcDao
	 *            the baseJdbcDao to set
	 */
	public void setBaseJdbcDao(BaseJdbcDao baseJdbcDao) {
		this.baseJdbcDao = baseJdbcDao;
	}

	@Override
	public void deleteProductConsult(ProductConsult consult) {
		delete(consult);
	}

	@Override
	public long checkFrequency(ProductConsult consult) {
		// interval 个分钟前
		Date date = DateUtil.add(new Date(), Calendar.MINUTE, interval);
		String sql = "select count(*) from ls_prod_cons where point_type = ? and prod_id = ? and ask_user_name = ? and rec_date > ?";
		return baseJdbcDao.stat(sql, new Object[] { consult.getPointType(), consult.getProdId(), consult.getAskUserName(), date });
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

}
