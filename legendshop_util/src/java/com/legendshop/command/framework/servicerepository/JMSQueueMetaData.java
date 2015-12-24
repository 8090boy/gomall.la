/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

import java.util.Map;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
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
public class JMSQueueMetaData implements IMetaData {

	/** The logger. */
	private static Logger logger = Logger.getLogger(JMSQueueMetaData.class);

	/** The name. */
	private String name;

	/** The jms factory name. */
	private String jmsFactoryName;

	/** The jms queue name. */
	private String jmsQueueName;

	/** The url. */
	private String url;

	/** The factory. */
	private QueueConnectionFactory factory;

	/** The queue. */
	private Queue queue;

	/** The init. */
	private boolean init;

	/**
	 * Instantiates a new jMS queue meta data.
	 * 
	 * @param name
	 *            the name
	 * @param factory
	 *            the factory
	 * @param queue
	 *            the queue
	 * @param url
	 *            the url
	 */
	public JMSQueueMetaData(String name, String factory, String queue, String url) {
		this.name = name;
		this.jmsFactoryName = factory;
		this.jmsQueueName = queue;
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
	 * Gets the queue connection factory.
	 * 
	 * @return the queue connection factory
	 * @throws Exception
	 *             the exception
	 */
	public QueueConnectionFactory getQueueConnectionFactory() throws Exception {
		try {
			initializeQueue();
		} catch (Exception e) {
			logger.debug("create Queue connection factory fail", e);
			throw e;
		}
		return factory;
	}

	/**
	 * Gets the queue.
	 * 
	 * @return the queue
	 * @throws Exception
	 *             the exception
	 */
	public Queue getQueue() throws Exception {
		try {
			initializeQueue();
		} catch (Exception e) {
			logger.debug("create Queue fail", e);
			throw e;
		}
		return queue;
	}

	/**
	 * 初始化QueueConnectionFactory和Queue.
	 * 
	 * @return Object
	 * @throws Exception
	 *             the exception
	 */
	private synchronized void initializeQueue() throws Exception {
		if (init)
			return;
		Context ctx = ServiceLocator.getInstance().getContext(url);
		if (ctx == null)
			throw new Exception("can't find the context by specified url " + url);

		factory = (QueueConnectionFactory) PortableRemoteObject.narrow(ctx.lookup(jmsFactoryName), QueueConnectionFactory.class);
		queue = (Queue) PortableRemoteObject.narrow(ctx.lookup(jmsQueueName), Queue.class);
		init = true;
	}
}
