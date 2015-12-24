/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * NavigationItem排序器
 */
public 	class NavigationItemComparator implements Comparator<NavigationItem>,Serializable{

	private static final long serialVersionUID = 4230413111485447011L;

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(NavigationItem o1, NavigationItem o2) {
		if (o1 == null || o2 == null || o1.getSeq() == null || o2.getSeq() == null) {
			return -1;
		} else if (o1.getSeq() < o2.getSeq()) {
			return -1;
		} else {
			return 1;
		}
	}
}