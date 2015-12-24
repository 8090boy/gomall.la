/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.legendshop.util.AppUtils;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
@SuppressWarnings("serial")
public class SimpleQbc implements Serializable {

	// 初始化list
	/** The cur page. */
	private String curPage = null;

	/** The page size. */
	private int pageSize = 15;

	/** The criterion list. */
	private CriterionList criterionList;

	/** The detached criteria. */
	private DetachedCriteria detachedCriteria;

	/**
	 * Instantiates a new simple qbc.
	 */
	public SimpleQbc() {
		super();
	}

	/**
	 * Instantiates a new simple qbc.
	 * 
	 * @param c
	 *            the c
	 */
	public SimpleQbc(Class c) {
		this.criterionList = new CriterionList();
		this.detachedCriteria = DetachedCriteria.forClass(c);
	}

	/**
	 * Instantiates a new simple qbc.
	 * 
	 * @param c
	 *            the c
	 * @param curPage
	 *            the cur page
	 */
	public SimpleQbc(Class c, String curPage) {
		this.curPage = curPage;
		this.criterionList = new CriterionList();
		this.detachedCriteria = DetachedCriteria.forClass(c);
	}

	/**
	 * Gets the criterion list.
	 * 
	 * @return the criterion list
	 */
	public CriterionList getCriterionList() {
		return criterionList;
	}

	/**
	 * Sets the criterion list.
	 * 
	 * @param criterionList
	 *            the new criterion list
	 */
	public void setCriterionList(CriterionList criterionList) {
		this.criterionList = criterionList;
	}

	/**
	 * Gets the cur page.
	 * 
	 * @return the cur page
	 */
	public String getCurPage() {
		return curPage;
	}

	/**
	 * Sets the cur page.
	 * 
	 * @param curPage
	 *            the new cur page
	 */
	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	/**
	 * Gets the detached criteria.
	 * 
	 * @return the detached criteria
	 */
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	/**
	 * Sets the detached criteria.
	 * 
	 * @param detachedCriteria
	 *            the new detached criteria
	 */
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}

	/**
	 * Adds the.
	 * 
	 * @param c
	 *            the c
	 */
	public void add(Criterion c) {
		detachedCriteria.add(c);
	}

	/**
	 * Adds the.
	 */
	public void add() {
		for (int i = 0; i < getCriterionList().size(); i++) {
			add(getCriterionList().getParas(i));
		}
	}

	/**
	 * Creates the alias.
	 * 
	 * @param name
	 *            the name
	 */
	public void createAlias(String name) {
		detachedCriteria.createCriteria(name);
	}

	/**
	 * Creates the alias.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void createAlias(String name, String value) {
		detachedCriteria.createCriteria(name, value);
	}

	/**
	 * And.
	 * 
	 * @param query
	 *            the query
	 * @param source
	 *            the source
	 * @param dest
	 *            the dest
	 * @return the criterion
	 */
	public Criterion and(SimpleQbc query, int source, int dest) {
		return Restrictions.and(query.getCriterionList().getParas(source), query.getCriterionList().getParas(dest));
	}

	/**
	 * And.
	 * 
	 * @param c
	 *            the c
	 * @param query
	 *            the query
	 * @param source
	 *            the source
	 * @return the criterion
	 */
	public Criterion and(Criterion c, SimpleQbc query, int source) {
		return Restrictions.and(c, query.getCriterionList().getParas(source));
	}

	/**
	 * And.
	 * 
	 * @param c1
	 *            the c1
	 * @param c2
	 *            the c2
	 * @return the criterion
	 */
	public Criterion and(Criterion c1, Criterion c2) {
		return Restrictions.and(c1, c2);
	}

	/**
	 * Or.
	 * 
	 * @param query
	 *            the query
	 * @param source
	 *            the source
	 * @param dest
	 *            the dest
	 * @return the criterion
	 */
	public Criterion or(SimpleQbc query, int source, int dest) {
		return Restrictions.or(query.getCriterionList().getParas(source), query.getCriterionList().getParas(dest));
	}

	/**
	 * Or.
	 * 
	 * @param c
	 *            the c
	 * @param query
	 *            the query
	 * @param source
	 *            the source
	 * @return the criterion
	 */
	public Criterion or(Criterion c, SimpleQbc query, int source) {
		return Restrictions.or(c, query.getCriterionList().getParas(source));
	}

	/**
	 * Or.
	 * 
	 * @param c1
	 *            the c1
	 * @param c2
	 *            the c2
	 */
	public void or(Criterion c1, Criterion c2) {
		this.detachedCriteria.add(Restrictions.or(c1, c2));
	}

	/**
	 * Adds the order.
	 * 
	 * @param ordername
	 *            the ordername
	 * @param ordervalue
	 *            the ordervalue
	 */
	public void addOrder(String ordername, String ordervalue) {
		// 写入order查询条件
		if ("asc".equals(ordername)) {
			detachedCriteria.addOrder(Order.asc(ordervalue));
		} else {
			detachedCriteria.addOrder(Order.desc(ordervalue));
		}
	}

	/**
	 * Eq.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void eq(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.eq(keyname, keyvalue));
	}

	/**
	 * Isnull.
	 * 
	 * @param propertyName
	 *            the property name
	 */
	public void isnull(String propertyName) {
		criterionList.addPara(Restrictions.isNull(propertyName));
	}

	/**
	 * Checks if is not null.
	 * 
	 * @param propertyName
	 *            the property name
	 */
	public void isNotNull(String propertyName) {
		criterionList.addPara(Restrictions.isNotNull(propertyName));
	}

	/**
	 * Not.
	 * 
	 * @param expression
	 *            the expression
	 */
	public void not(Criterion expression) {
		criterionList.addPara(Restrictions.not(expression));
	}

	/**
	 * Not eq.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void notEq(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.or(Restrictions.gt(keyname, keyvalue), Restrictions.lt(keyname, keyvalue)));
	}

	/**
	 * Like.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void like(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.like(keyname, keyvalue));
	}

	/**
	 * Gt.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void gt(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.gt(keyname, keyvalue));
	}

	/**
	 * Lt.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void lt(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.lt(keyname, keyvalue));
	}

	/**
	 * Le.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void le(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.le(keyname, keyvalue));
	}

	/**
	 * Ge.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void ge(String keyname, Object keyvalue) {
		criterionList.addPara(Restrictions.ge(keyname, keyvalue));
	}

	/**
	 * Ilike.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue
	 *            the keyvalue
	 */
	public void ilike(String keyname, Object[] keyvalue) {
		criterionList.addPara(Restrictions.in(keyname, keyvalue));
	}

	/**
	 * Between.
	 * 
	 * @param keyname
	 *            the keyname
	 * @param keyvalue1
	 *            the keyvalue1
	 * @param keyvalue2
	 *            the keyvalue2
	 */
	public void between(String keyname, Object keyvalue1, Object keyvalue2) {
		Criterion c = null;// 写入between查询条件
		if (!AppUtils.isBlank(keyvalue1) && !AppUtils.isBlank(keyvalue2)) {
			c = Restrictions.between(keyname, keyvalue1, keyvalue2);
		} else if (!AppUtils.isBlank(keyvalue1)) {
			c = Restrictions.ge(keyname, keyvalue1);
		} else if (!AppUtils.isBlank(keyvalue2)) {
			c = Restrictions.le(keyname, keyvalue2);
		}
		criterionList.add(c);
	}

	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the page size.
	 * 
	 * @param pageSize
	 *            the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
