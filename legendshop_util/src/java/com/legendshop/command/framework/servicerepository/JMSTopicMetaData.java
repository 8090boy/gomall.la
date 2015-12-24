/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

import java.util.Map;

import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
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
public class JMSTopicMetaData implements IMetaData {

	/** The logger. */
	private static Logger logger = Logger.getLogger(JMSTopicMetaData.class);

	/** The name. */
	private String name;

	/** The jms factory name. */
	private String jmsFactoryName;

	/** The jms topic name. */
	private String jmsTopicName;

	/** The url. */
	private String url;

	/** The factory. */
	private TopicConnectionFactory factory;

	/** The topic. */
	private Topic topic;

	/** The init. */
	private boolean init;

	/**
	 * Instantiates a new jMS topic meta data.
	 * 
	 * @param name
	 *            the name
	 * @param factory
	 *            the factory
	 * @param topic
	 *            the topic
	 * @param url
	 *            the url
	 */
	public JMSTopicMetaData(String name, String factory, String topic, String url) {
		this.name = name;
		this.jmsFactoryName = factory;
		this.jmsTopicName = topic;
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.servicerepository.IMetaData#get(java
	 * .lang.String)
	 */
	public Object get(String urlName) {
		throw new UnsupportedOperationException(
				"[Object JMSTopicMetaData.get(String)] is not supported.This error may be caused by incorrected ServiceConfig.xml settings");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.command.framework.servicerepository.IMetaData#get()
	 */
	public Map get() {
		throw new UnsupportedOperationException(
				"[Map JMSTopicMetaData.get()] is not supported.This error may be caused by incorrected ServiceConfig.xml settings");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.servicerepository.IMetaData#getOne()
	 */
	public Object getOne() {
		throw new UnsupportedOperationException(
				"[Map JMSTopicMetaData.getOne()] is not supported.This error may be caused by incorrected ServiceConfig.xml settings");
	}

	/**
	 * Gets the topic connection factory.
	 * 
	 * @return the topic connection factory
	 * @throws Exception
	 *             the exception
	 */
	public TopicConnectionFactory getTopicConnectionFactory() throws Exception {
		try {
			initializeTopic();
		} catch (Exception e) {
			logger.debug("create topic connection factory fail", e);
			throw e;
		}
		return factory;
	}

	/**
	 * Gets the topic.
	 * 
	 * @return the topic
	 * @throws Exception
	 *             the exception
	 */
	public Topic getTopic() throws Exception {
		try {
			initializeTopic();
		} catch (Exception e) {
			logger.debug("create topic fail", e);
			throw e;
		}
		return topic;
	}

	/**
	 * 初始化TopicConnectionFactory和Topic.
	 * 
	 * @return Object
	 * @throws Exception
	 *             the exception
	 */
	private synchronized void initializeTopic() throws Exception {
		if (init)
			return;
		Context ctx = ServiceLocator.getInstance().getContext(url);
		if (ctx == null)
			throw new Exception("can't find the context by specified url " + url);

		factory = (TopicConnectionFactory) PortableRemoteObject.narrow(ctx.lookup(jmsFactoryName), TopicConnectionFactory.class);
		topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(jmsTopicName), Topic.class);
		init = true;
	}
}
