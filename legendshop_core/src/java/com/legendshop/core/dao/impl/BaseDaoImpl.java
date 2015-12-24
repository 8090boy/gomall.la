/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.legendshop.core.dao.BaseDao;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SqlQuery;
import com.legendshop.core.page.PagerUtil;
import com.legendshop.util.AppUtils;

/**
 * Hibernate Dao的基类，支持缓存. LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class BaseDaoImpl implements BaseDao {

	/** The hibernate template. */
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#get(java.lang.Class,
	 * java.io.Serializable)
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#getByLockMode(java.lang.Class,
	 * java.io.Serializable, org.hibernate.LockMode)
	 */

	public <T> T getByLockMode(Class<T> entityClass, Serializable id, LockMode lockMode) {
		return getHibernateTemplate().get(entityClass, id, lockMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#load(java.lang.Class,
	 * java.io.Serializable)
	 */

	public <T> T load(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().load(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#loadByLockMode(java.lang.Class,
	 * java.io.Serializable, org.hibernate.LockMode)
	 */

	public <T> T loadByLockMode(Class<T> entityClass, Serializable id, LockMode lockMode) {
		return getHibernateTemplate().load(entityClass, id, lockMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#getAll(java.lang.Class)
	 */

	public <T> List<T> getAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#getAll(java.lang.Class,
	 * java.lang.String, boolean)
	 */

	public <T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc) {
			return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
		} else {
			return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#save(java.lang.Object)
	 */
	public Serializable save(Object o) {
		return getHibernateTemplate().save(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#saveOrUpdate(java.lang.Object)
	 */

	public void saveOrUpdate(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#saveOrUpdateAll(java.util.Collection)
	 */

	public void saveOrUpdateAll(Collection collection) {
		getHibernateTemplate().saveOrUpdateAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#update(java.lang.Object)
	 */

	protected void update(Object o) {
		getHibernateTemplate().update(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#delete(java.lang.Object)
	 */

	protected void delete(Object o) {
		getHibernateTemplate().delete(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#delete(java.lang.Class,
	 * java.io.Serializable)
	 */

	protected boolean delete(Class c, Serializable id) {
		Object o = getHibernateTemplate().get(c, id);
		if (o != null) {
			delete(o);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#deleteById(java.lang.Class,
	 * java.io.Serializable)
	 */

	protected <T> void deleteById(Class<T> entityClass, Serializable id) {
		T t = get(entityClass, id);
		if (t != null) {
			delete(t);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#deleteAll(java.util.Collection)
	 */

	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#flush()
	 */

	public void flush() {
		getHibernateTemplate().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#clear()
	 */

	public void clear() {
		getHibernateTemplate().clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#findByHQL(java.lang.String,
	 * java.lang.Object)
	 */

	public List findByHQL(String strHQL, Object... parameters) {
		return getHibernateTemplate().find(strHQL, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#findUniqueBy(java.lang.String,
	 * java.lang.Class, java.lang.Object)
	 */

	public <T> T findUniqueBy(final String strHQL, final Class<T> entityClass, final Object... params) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = createQuery(strHQL, session, params);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#findByHQLLimit(java.lang.String,
	 * int, int, java.lang.Object)
	 */

	public List findByHQLLimit(final String strHQL, final int offset, final int limit, final Object... params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = createQuery(strHQL, session, params);
				query.setFirstResult(offset);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}

	/**
	 * @param strHQL
	 *            SQL
	 * @param session
	 *            Hibernate Session
	 * @param params
	 *            参数
	 * @return
	 */
	private Query createQuery(final String strHQL, Session session, final Object... params) {
		Query query = session.createQuery(strHQL);
		if (AppUtils.isNotBlank(params)) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#findUniqueByHQLLimit(java.lang.String,
	 * java.lang.Class, int, int, java.lang.Object)
	 */

	public <T> T findUniqueByHQLLimit(final String strHQL, final Class<T> entityClass, final int offset, final int limit,
			final Object... params) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = createQuery(strHQL, session, params);
				query.setFirstResult(offset);
				query.setMaxResults(limit);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#findBySQL(java.lang.String)
	 */

	public List findBySQL(final String strSQL) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(strSQL);
				return query.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#merge(java.lang.Object)
	 */

	public Object merge(Object o) {
		return getHibernateTemplate().merge(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#executeBySql(java.lang.String)
	 */

	public void executeBySql(final String sql) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Connection conn = session.connection();
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.execute();
					ps.close();
					session.flush();
					session.clear();
					return null;
				} catch (RuntimeException e) {
					e.printStackTrace();
					return e;
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#executeByProcedure(java.lang.String,
	 * java.lang.String)
	 */

	public String executeByProcedure(final String procedure, final String... ins) {
		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					Connection con = session.connection();
					CallableStatement cstmt = con.prepareCall(procedure);
					cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
					int index = 2;
					for (String in : ins) {
						cstmt.setString(index, in);
						index++;
					}
					cstmt.executeUpdate();
					String ret = cstmt.getString(1);
					if (cstmt != null) {
						cstmt.close();
					}
					return ret;
				} catch (RuntimeException e) {
					e.printStackTrace();
					return e;
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#exeByHQL(java.lang.String,
	 * java.lang.Object)
	 */

	public Integer exeByHQL(final String strHQL, final Object... params) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = createQuery(strHQL, session, params);
				return query.executeUpdate();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.dao.BaseDao#getHibernateTemplate()
	 */

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#setHibernateTemplate(org.springframework
	 * .orm.hibernate3.HibernateTemplate)
	 */

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#find(com.legendshop.core.dao.support.
	 * HqlQuery)
	 */

	public PageSupport find(final HqlQuery hqlQuery) {
		return (PageSupport) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hqlQuery.getQueryString());
				Query allCountQuery = session.createQuery(hqlQuery.getAllCountString());

				createSQLQuery(query, allCountQuery, hqlQuery.getParams(), hqlQuery.getParam(), hqlQuery.getTypes());

				// 得到总行数
				long allCounts = ((Number) allCountQuery.uniqueResult()).longValue();
				int curPageNO = PagerUtil.getCurPageNO(hqlQuery.getCurPage());
				int offset = PagerUtil.getOffset(allCounts, curPageNO, hqlQuery.getPageSize());
				query.setFirstResult(offset);
				query.setMaxResults(hqlQuery.getPageSize());
				return new PageSupport(query.list(), hqlQuery.getMyaction(), offset, curPageNO, allCounts, hqlQuery.getPageSize(),
						hqlQuery.getPageProvider());
			}
		});
	}

	// 设置参数
	private void createSQLQuery(Query query, Query allCountQuery, List params, Object[] param, Type[] type) {
		if (AppUtils.isNotBlank(params)) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
				allCountQuery.setParameter(i, params.get(i));
			}
		} else {
			if (AppUtils.isNotBlank(param)) {
				if (AppUtils.isNotBlank(type)) {
					query.setParameters(param, type);
					allCountQuery.setParameters(param, type);
				} else {
					for (int i = 0; i < param.length; i++) {
						query.setParameter(i, param[i]);
						allCountQuery.setParameter(i, param[i]);
					}
				}
			}
		}
	}

	// 多页查询
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#find(com.legendshop.core.dao.support.
	 * SqlQuery)
	 */

	public PageSupport find(final SqlQuery sqlQuery) {
		return (PageSupport) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sqlQuery.getQueryString());
				if (AppUtils.isNotBlank(sqlQuery.getEntityClass())) {
					for (String name : sqlQuery.getEntityClass().keySet()) {
						// query.addEntity(name,sqlQuery.getEntityClass().get(name));
						query.setResultTransformer(Transformers.aliasToBean(sqlQuery.getEntityClass().get(name)));
					}
				}
				SQLQuery allCountQuery = session.createSQLQuery(sqlQuery.getAllCountString());
				createSQLQuery(query, allCountQuery, sqlQuery.getParams(), sqlQuery.getParam(), sqlQuery.getTypes());

				// 得到总行数
				long allCounts = ((Number) allCountQuery.uniqueResult()).longValue();
				int curPageNO = PagerUtil.getCurPageNO(sqlQuery.getCurPage());
				int offset = PagerUtil.getOffset(allCounts, curPageNO, sqlQuery.getPageSize());
				query.setFirstResult(offset);
				query.setMaxResults(sqlQuery.getPageSize());
				return new PageSupport(query.list(), sqlQuery.getMyaction(), offset, curPageNO, allCounts, sqlQuery.getPageSize(),
						sqlQuery.getPageProvider());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#find(com.legendshop.core.dao.support.
	 * CriteriaQuery, boolean)
	 */

	public PageSupport find(final CriteriaQuery cq, final boolean isOffset) {

		return (PageSupport) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				cq.add();// 增加条件
				Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(session);
				// 得到总行数
				Projection projection = Projections.rowCount();
				Object result =  criteria.setProjection(projection).uniqueResult();
				long allCounts =  ((Number) result).longValue();
				criteria.setProjection(null);// 还原
				int curPageNO = PagerUtil.getCurPageNO(cq.getCurPage());// 当前页
				int offset = PagerUtil.getOffset(allCounts, curPageNO, cq.getPageSize());
				if (cq.getOrderList() != null) {
					for (Order order : cq.getOrderList()) {
						criteria.addOrder(order);
					}
				}
				if (isOffset) {
					criteria.setFirstResult(offset);
					criteria.setMaxResults(cq.getPageSize());
				}
				return new PageSupport(criteria.list(), cq.getMyaction(), offset, curPageNO, allCounts, cq.getPageSize(), cq
						.getPageProvider());
			}
		});

	}

	// 分页查询，采用form的形式
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#find(com.legendshop.core.dao.support.
	 * CriteriaQuery)
	 */

	public PageSupport find(final CriteriaQuery cq) {
		return find(cq, true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.BaseDao#findListByCriteria(com.legendshop.core
	 * .dao.support.CriteriaQuery, int, int)
	 */

	public List findListByCriteria(final CriteriaQuery cq, final int offset, final int limit) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			/* (non-Javadoc)
			 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
			 */
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				cq.add();// 增加条件
				Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(session);
				if (cq.getOrderList() != null) {
					for (Order order : cq.getOrderList()) {
						criteria.addOrder(order);
					}
				}
				criteria.setFirstResult(offset);
				criteria.setMaxResults(limit);
				return criteria.list();
			}
		});
	}

}