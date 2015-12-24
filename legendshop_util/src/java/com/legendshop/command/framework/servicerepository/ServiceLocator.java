/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
public class ServiceLocator {

	/** The singleton. */
	private static ServiceLocator singleton = null;

	/** The log. */
	private static Logger log = Logger.getLogger(ServiceLocator.class);

	/** The ctx container. */
	private Map ctxContainer = new HashMap();

	/** The env container. */
	private Map envContainer = null;

	/** The meta data container. */
	private Map metaDataContainer = new HashMap();

	/** The spring xml file container. */
	private Map springXmlFileContainer = new HashMap();

	/** The state. */
	private boolean state = false;

	/**
	 * Instantiates a new service locator.
	 */
	private ServiceLocator() {
	}

	/**
	 * Gets the single instance of ServiceLocator.
	 * 
	 * @return single instance of ServiceLocator
	 */
	public static synchronized ServiceLocator getInstance() {
		if (singleton == null) {
			singleton = new ServiceLocator();
		}
		return singleton;
	}

	/**
	 * 将envContainer中的参数初始化为Context，并放在ctxContainer.
	 * 
	 * @param envName
	 *            the env name
	 * @return the context
	 */
	protected synchronized Context getContext(String envName) {
		Context ctx = (Context) getInstance().ctxContainer.get(envName);
		if (ctx != null) {
			return ctx;
		}
		// conf 里面包括URL，Factory，USER，PWD
		Map conf = (Map) getInstance().envContainer.get(envName);
		if (conf == null) {
			log.debug("can't find specified env");
			return null;
		}

		Hashtable env = new Hashtable();
		env.put(Context.PROVIDER_URL, conf.get(ServiceConfig.URL));
		env.put(Context.INITIAL_CONTEXT_FACTORY, conf.get(ServiceConfig.Factory));
		String user = (String) conf.get(ServiceConfig.USER);
		String pwd = (String) conf.get(ServiceConfig.PWD);
		if ((user != null) && (pwd != null)) {
			env.put(Context.SECURITY_PRINCIPAL, user);
			env.put(Context.SECURITY_CREDENTIALS, pwd);
		}

		try {
			ctx = new InitialContext(env);
			getInstance().ctxContainer.put(envName, ctx);
			return ctx;
		} catch (NamingException e) {
			log.debug("init context fail:" + envName, e);
			return null;
		}
	}

	/**
	 * Inits the.
	 * 
	 * @param conf
	 *            the conf
	 * @return true, if successful
	 */
	public synchronized boolean init(String conf) {
		if (state) {
			return state;
		}
		// 读配置文件
		ServiceConfig config = new ServiceConfig();
		state = config.config(conf);
		if (!state) {
			return false;
		}

		envContainer = config.getEnvs();
		if (envContainer == null || envContainer.size() <= 0) {
			log.error("no env specified in the config file");
			state = false;
			return false;
		}

		Map map = config.getEJBMetaData();
		if (map != null) {
			metaDataContainer.putAll(map);
		}

		map = config.getJMSMetaData();
		if (map != null) {
			metaDataContainer.putAll(map);
		}

		map = config.getObjMetaData();
		if (map != null) {
			metaDataContainer.putAll(map);
		}
		map = config.getSpringApplicationContext();
		if (map != null) {
			springXmlFileContainer.putAll(map);
		}
		state = true;
		return true;
	}

	/**
	 * 获取一个IMetaData对象。.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @return the meta data
	 */
	public IMetaData getMetaData(String jndiName) {
		IMetaData metaData = (IMetaData) metaDataContainer.get(jndiName);
		return metaData;
	}

	/**
	 * 获取一个List spring配置文件对象。.
	 * 
	 * @param projectName
	 *            the project name
	 * @return the spring application context
	 */
	public List getSpringApplicationContext(String projectName) {
		return (List) springXmlFileContainer.get(projectName);

	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public boolean getState() {
		return state;
	}

	// 得到urlName下的一个EJB
	/**
	 * Gets the.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @param urlName
	 *            the url name
	 * @return the object
	 */
	public Object get(String jndiName, String urlName) {
		IMetaData metaData = (IMetaData) metaDataContainer.get(jndiName);
		if (metaData == null) {
			log.error("can't find the object with name " + jndiName);
		}
		return metaData.get(urlName);
	}

	// 得到一个EJB，在配置文件的最后一个
	/**
	 * Gets the one.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @return the one
	 */
	public Object getOne(String jndiName) {
		IMetaData metaData = (IMetaData) metaDataContainer.get(jndiName);
		if (metaData == null) {
			log.error("can't find the object with name " + jndiName);
		}
		return metaData.getOne();
	}

	// 得到以jndiNameJNDI的EJB，不同的地址有不同的EJB
	/**
	 * Gets the.
	 * 
	 * @param jndiName
	 *            the jndi name
	 * @return the map
	 */
	public Map get(String jndiName) {
		IMetaData metaData = (IMetaData) metaDataContainer.get(jndiName);
		if (metaData == null) {
			log.error("can't find the object with name " + jndiName);
		}
		return metaData.get();
	}

}
