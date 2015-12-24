/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

import java.util.Map;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ObjectMetaData implements IMetaData {

	/** The name. */
	private String name;

	/** The url. */
	private String url;

	/**
	 * Instantiates a new object meta data.
	 * 
	 * @param name
	 *            the name
	 * @param url
	 *            the url
	 */
	public ObjectMetaData(String name, String url) {
		this.name = name;
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.servicerepository.IMetaData#get(java
	 * .lang.String)
	 */
	public Object get(String name) {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.command.framework.servicerepository.IMetaData#get()
	 */
	public Map get() {
		return null;
	}

	/**
	 * Gets the one.
	 * 
	 * @return Object
	 */
	public Object getOne() {
		return url;
	}

}
