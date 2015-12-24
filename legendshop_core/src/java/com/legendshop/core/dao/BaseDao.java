/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SqlQuery;

/**
 * Hibernate Dao的基本接口，所有Hibernate Dao都实现该接口。
 */
public interface BaseDao {

	/**
	 * 根据ID获取对象, 支持泛型.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the id
	 * @return the t
	 */
	public abstract <T> T get(Class<T> entityClass, Serializable id);

	/**
	 * 根据Hibernate的锁定模式获取对象，支持泛型
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the id
	 * @param lockMode
	 *            the lock mode
	 * @return the by lock mode
	 */
	public abstract <T> T getByLockMode(Class<T> entityClass, Serializable id, LockMode lockMode);

	/**
	 * 根据ID获取对象, 支持泛型, 如果对象不存在，抛出异常.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the id
	 * @return the t
	 */
	public abstract <T> T load(Class<T> entityClass, Serializable id);

	/**
	 * 根据ID获取对象, 支持泛型,  如果对象不存在，抛出异常.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the id
	 * @param lockMode
	 *            the lock mode
	 * @return the t
	 */
	public abstract <T> T loadByLockMode(Class<T> entityClass, Serializable id, LockMode lockMode);

	/**
	 * 获取全部对象.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @return the all
	 */
	public abstract <T> List<T> getAll(Class<T> entityClass);

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @param orderBy
	 *            the order by
	 * @param isAsc
	 *            the is asc
	 * @return the all
	 */
	public abstract <T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc);

	/**
	 * 保存对象.
	 * 
	 * @param o
	 *            the o
	 * @return the serializable
	 */
	public abstract Serializable save(Object o);

	/**
	 * 删除一系列对象。
	 * 
	 * @param entities
	 *            the entities
	 */
	public abstract void deleteAll(Collection entities);

	/**
	 * 调用hibernate的flush方法
	 */
	public abstract void flush();

	/**
	 * 调用hibernate的Clear方法.
	 */
	public abstract void clear();

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 * 
	 * @param strHQL
	 *            the str hql
	 * @param parameters
	 *            the parameters
	 * @return the list
	 */
	public abstract List findByHQL(String strHQL, Object... parameters);

	/**
	 * 采用HQL查找记录，只返回一条记录。
	 * 
	 * @param <T>
	 *            the generic type
	 * @param strHQL
	 *            the str hql
	 * @param entityClass
	 *            the entity class
	 * @param parameters
	 *            the parameters
	 * @return the t
	 */
	public abstract <T> T findUniqueBy(final String strHQL, final Class<T> entityClass, final Object... parameters);

	/**
	 * 采用HQL查找记录，返回指定范围（大于等于 offset 和 小于offset +  limit）的记录。
	 * 
	 * @param strHQL
	 *            the str hql
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @param parameters
	 *            the parameters
	 * @return the list
	 */
	public abstract List findByHQLLimit(final String strHQL, final int offset, final int limit, final Object... parameters);

	/**
	 * 采用HQL查找记录，返回指定范围（大于等于 offset 和 小于offset +  limit）的唯一记录。
	 * 
	 * @param <T>
	 *            the generic type
	 * @param strHQL
	 *            the str hql
	 * @param entityClass
	 *            the entity class
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @param objects
	 *            the objects
	 * @return the t
	 */
	public abstract <T> T findUniqueByHQLLimit(final String strHQL, final Class<T> entityClass, final int offset, final int limit,
			final Object... objects);

	/**
	 * 原生SQL查询.
	 * 
	 * @param strSQL
	 *            the str sql
	 * @return the list
	 */
	public abstract List findBySQL(final String strSQL);

	/**
	 * 调用Hibernate的merge方法.
	 * 
	 * @param o
	 *            the o
	 * @return the object
	 */
	public abstract Object merge(Object o);

	/**
	 * 支持Sql增删改操作.
	 * 
	 * @param sql
	 *            the sql
	 */
	public abstract void executeBySql(final String sql);

	/**
	 * 调用存储过程.
	 * 
	 * @param procedure
	 *            the procedure
	 * @param ins
	 *            the ins
	 * @return the string
	 */
	public abstract String executeByProcedure(final String procedure, final String... ins);

	/**
	 * 执行HQL update 或 save 动作
	 * 
	 * @param strHQL
	 *            the str hql
	 * @param objects
	 *            the objects
	 * @return the integer
	 */
	public abstract Integer exeByHQL(final String strHQL, final Object... objects);

	/**
	 * 获得hibernate template
	 * 
	 * @return the hibernate template
	 */
	public abstract HibernateTemplate getHibernateTemplate();

	/**
	 *根据HqlQuery查询，支持分页
	 * 
	 * @param hqlQuery
	 *            the hql query
	 * @return the page support
	 */
	public abstract PageSupport find(final HqlQuery hqlQuery);

	/**
	 * 根据SqlQuery查询，支持分页
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the page support
	 */
	public abstract PageSupport find(final SqlQuery sqlQuery);

	/**
	 * 根据CriteriaQuery（QBC）查询，如果isOffset = true 则支持分页
	 * 
	 * @param cq
	 *            the cq
	 * @param isOffset
	 *            the is offset
	 * @return the page support
	 */
	public abstract PageSupport find(final CriteriaQuery cq, final boolean isOffset);

	/**
	 *根据CriteriaQuery（QBC）查询，默认isOffset = true 支持分页
	 * 
	 * @param cq
	 *            the cq
	 * @return the page support
	 */
	public abstract PageSupport find(final CriteriaQuery cq);

	/**
	 * 根据CriteriaQuery（QBC）查询
	 * 
	 * @param cq
	 *            the cq
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @return the list
	 */
	public abstract List findListByCriteria(final CriteriaQuery cq, final int offset, final int limit);

}