/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.util.AppUtils;

/**
 * 对detachedCriteria的封装.
 */
public class CriteriaQuery extends AbstractQuery implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1464383406564081554L;

	/** The criterion list. */
	private CriterionList criterionList;

	/** The detached criteria. */
	private DetachedCriteria detachedCriteria;

	private List<Order> orderList;

	/**
	 * Instantiates a new criteria query.
	 */
	public CriteriaQuery() {
		super();
	}

	/**
	 * Instantiates a new criteria query.
	 * 
	 * @param c
	 *            the c
	 */
	public CriteriaQuery(Class c) {
		this.criterionList = new CriterionList();
		this.detachedCriteria = DetachedCriteria.forClass(c);
	}

	/**
	 * Instantiates a new criteria query.
	 * 
	 * @param c
	 *            the c
	 * @param curPage
	 *            the cur page
	 * @param myaction
	 *            the myaction
	 */
	public CriteriaQuery(Class c, String curPage, String myaction, PageProviderEnum pareProvider) {
		if (myaction != null) {
			this.myaction = myaction;
		}
		this.setCurPage(curPage);
		this.criterionList = new CriterionList();
		this.detachedCriteria = DetachedCriteria.forClass(c);
		this.pageProvider = pareProvider;

	}

	public CriteriaQuery(Class c, String curPage, String myaction) {
		this(c, curPage, myaction, PageProviderEnum.PAGE_PROVIDER);
	}

	/**
	 * Instantiates a new criteria query.
	 * 
	 * @param c
	 *            the c
	 * @param curPage
	 *            the cur page
	 */
	public CriteriaQuery(Class c, String curPage) {
		this(c, curPage, null, PageProviderEnum.PAGE_PROVIDER);
	}

	public CriteriaQuery(Class c, String curPage, PageProviderEnum pareProvider) {
		this(c, curPage, null, pareProvider);
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
	public Criterion and(CriteriaQuery query, int source, int dest) {
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
	public Criterion and(Criterion c, CriteriaQuery query, int source) {
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
	public Criterion or(CriteriaQuery query, int source, int dest) {
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
	public Criterion or(Criterion c, CriteriaQuery query, int source) {
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
		if (orderList == null) {
			orderList = new ArrayList<Order>();
		}

		// 写入order查询条件
		if ("asc".equals(ordername)) {
			orderList.add(Order.asc(ordervalue));
			// detachedCriteria.addOrder(Order.asc(ordervalue));
		} else {
			orderList.add(Order.desc(ordervalue));
			// detachedCriteria.addOrder(Order.desc(ordervalue));
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.eq(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.or(Restrictions.gt(keyname, keyvalue), Restrictions.lt(keyname, keyvalue)));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.like(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.gt(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.lt(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.le(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.ge(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue)) {
			criterionList.addPara(Restrictions.in(keyname, keyvalue));
		}
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
		if (AppUtils.isNotBlank(keyvalue1) && AppUtils.isNotBlank(keyvalue2)) {
			c = Restrictions.between(keyname, keyvalue1, keyvalue2);
		} else if (AppUtils.isNotBlank(keyvalue1)) {
			c = Restrictions.ge(keyname, keyvalue1);
		} else if (AppUtils.isNotBlank(keyvalue2)) {
			c = Restrictions.le(keyname, keyvalue2);
		}
		criterionList.add(c);
	}

	public List<Order> getOrderList() {
		return orderList;
	}

}
