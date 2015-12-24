/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.MyleagueDao;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.model.entity.Myleague;
import com.legendshop.util.sql.ConfigCode;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class MyleagueDaoImpl extends BaseDaoImpl implements MyleagueDao {

	private BaseJdbcDao baseJdbcDao;

	@Override
	@Cacheable(value = "MyleagueList", condition = "T(Integer).parseInt(#curPageNO) < 3")
	public PageSupport getLeague(String shopName, String curPageNO) {
		Map<String, Object> map = new HashMap<String, Object>();
		String queryAllSQL =null;
		String querySQL = null;
		if (!PropertiesUtil.isInDefaultShop(shopName)) {//一般的商家
			map.put("shopName", shopName);
			 queryAllSQL = ConfigCode.getInstance().getCode("biz.QueryLeagueCount", map);
			 querySQL = ConfigCode.getInstance().getCode("biz.QueryLeague", map);
		}else{//总商家
			 queryAllSQL = ConfigCode.getInstance().getCode("biz.QueryAllLeagueCount", map);
			 querySQL = ConfigCode.getInstance().getCode("biz.QueryAllLeague", map);
		}
		SimpleSqlQuery query = new SimpleSqlQuery(Myleague.class, querySQL, queryAllSQL, map.size() > 0 ? new Object[] { shopName }
				: null);

		query.setPageSize(15);
		query.setCurPage(curPageNO);
		query.setPageProvider(PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		return baseJdbcDao.find(query);
	}

	@Override
	@CacheEvict(value = "Myleague", key = "#id")
	public void deleteMyleagueById(Long id) {
		deleteById(Myleague.class, id);
	}

	@Override
	@CacheEvict(value = "Myleague", key = "#myleague.id")
	public void updateMyleague(Myleague myleague) {
		update(myleague);
	}

	/**
	 * @param baseJdbcDao
	 *            the baseJdbcDao to set
	 */
	public void setBaseJdbcDao(BaseJdbcDao baseJdbcDao) {
		this.baseJdbcDao = baseJdbcDao;
	}

	@Override
	public Myleague getMyleague(String userName, String shopName) {
		return findUniqueBy("from Myleague where userId = ? and friendId = ?", Myleague.class, userName, shopName);
	}
}
