/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.support;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
@SuppressWarnings("serial")
public class CriterionList extends ArrayList<Criterion> {

	/**
	 * Gets the paras.
	 * 
	 * @param index
	 *            the index
	 * @return the paras
	 */
	public final Criterion getParas(final int index) {
		return (Criterion) super.get(index);
	}

	/**
	 * Adds the para.
	 * 
	 * @param index
	 *            the index
	 * @param p
	 *            the p
	 */
	public final void addPara(final int index, final Criterion p) {
		super.add(index, p);
	}

	/**
	 * Adds the para.
	 * 
	 * @param p
	 *            the p
	 */
	public final void addPara(final Criterion p) {
		super.add(p);
	}

	/**
	 * Indexof para.
	 * 
	 * @param p
	 *            the p
	 * @return the int
	 */
	public final int indexofPara(final Criterion p) {
		return super.indexOf(p);
	}

	/**
	 * Removes the para.
	 * 
	 * @param index
	 *            the index
	 */
	public final void removePara(final int index) {
		super.remove(index);
	}
}