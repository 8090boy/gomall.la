/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class EJBMetaData implements IMetaData {

	/** The log. */
	private static Logger log = Logger.getLogger(EJBMetaData.class);

	/** The name. */
	private String name;

	/** The home class name. */
	private String homeClassName;

	/** key--url, value--ejb home object. */
	private Map containers;

	/**
	 * Instantiates a new eJB meta data.
	 * 
	 * @param name
	 *            String
	 * @param homeClassName
	 *            String
	 * @param urlList
	 *            List
	 */
	public EJBMetaData(String name, String homeClassName, List urlList) {
		this.name = name;
		this.homeClassName = homeClassName;
		containers = new HashMap();
		if (urlList != null) {
			Iterator itor = urlList.iterator();
			while (itor.hasNext()) {
				containers.put(itor.next(), null);
			} // while
		} // if
	}

	/**
	 * Gets the.
	 * 
	 * @param urlName
	 *            the url name
	 * @return Object
	 */
	public Object get(String urlName) {
		Object obj = containers.get(urlName);
		if (obj != null) {
			return obj;
		}

		if (!containers.containsKey(urlName)) {
			log.info("no specified url " + urlName);
			return null;
		}
		// 得到EJB，并根据地址urlName缓存在containers
		obj = newEJBHome(urlName);
		if (obj != null) {
			containers.put(urlName, obj);
		} else {
			log.info("create ejb home with jndi name[" + name + "] url name[" + urlName + "] fail");
		}
		return obj;
	}

	/**
	 * 一个EJB只有一个地址，一个Map.Entry里面有<key,value>,like <urlName,obj>
	 * 
	 * @return Object(返回最后一个EJB)
	 */
	public Object getOne() {
		Object obj = null;
		Iterator itor = containers.entrySet().iterator();
		if (itor.hasNext()) {
			Map.Entry entry = (Map.Entry) itor.next();
			if (entry.getValue() == null) {
				String urlName = (String) entry.getKey();
				obj = newEJBHome(urlName);
				if (obj != null) {
					entry.setValue(obj);
				} else {
					log.info("create ejb home with jndi name[" + name + "] url name[" + urlName + "] fail");
				}
			} else {
				obj = entry.getValue();
			}
		} // if
		return obj;
	}

	/**
	 * Gets the.
	 * 
	 * @return Map，包括所有的EJB
	 */
	public Map get() {
		Iterator itor = containers.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry entry = (Map.Entry) itor.next();
			if (entry.getValue() == null) {
				String urlName = (String) entry.getKey();
				Object obj = newEJBHome(urlName);
				if (obj != null) {
					entry.setValue(obj);
				} else {
					log.info("create ejb home with jndi name[" + name + "] url name[" + urlName + "] fail");
				}
			}
		} // while
		return containers;
	}

	/**
	 * 得到urlName下的EJB.
	 * 
	 * @param urlName
	 *            String
	 * @return Object
	 */
	private Object newEJBHome(String urlName) {
		try {
			Context ctx = ServiceLocator.getInstance().getContext(urlName);
			if (ctx == null) {
				log.info("can't find the context by specified url " + urlName);
				return null;
			}
			Object jndiRef = ctx.lookup(name);
			Class cl = Class.forName(homeClassName);
			Object homeObj = PortableRemoteObject.narrow(jndiRef, cl);
			return homeObj;
		} catch (Exception e) {
			log.debug("create home obj fail", e);
		}
		return null;
	}
}
