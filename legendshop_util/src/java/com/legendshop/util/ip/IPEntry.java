/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.ip;

/**
 * 
 * 一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP *
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class IPEntry {

	/** The begin ip. */
	public String beginIp;

	/** The end ip. */
	public String endIp;

	/** The country. */
	public String country;

	/** The area. */
	public String area;

	/**
	 * Instantiates a new iP entry.
	 */
	public IPEntry() {
		beginIp = endIp = country = area = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.area + "  " + this.country + "IP范围:" + this.beginIp + "-" + this.endIp;
	}
}