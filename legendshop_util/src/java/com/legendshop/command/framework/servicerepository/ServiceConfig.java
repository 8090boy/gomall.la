/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.servicerepository;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class ServiceConfig {

	/** The log. */
	private static Logger log = Logger.getLogger(ServiceConfig.class);

	/** The Constant ROOT. */
	public static final String ROOT = "/ServiceRepository";

	/** The Constant ENVS. */
	public static final String ENVS = "Envs";

	/** The Constant ENV. */
	public static final String ENV = "Env";

	/** The Constant URL. */
	public static final String URL = "URL";

	/** The Constant Factory. */
	public static final String Factory = "Factory";

	/** The Constant USER. */
	public static final String USER = "User";

	/** The Constant PWD. */
	public static final String PWD = "Password";

	/** The Constant EJB_DEF. */
	public static final String EJB_DEF = "EJBDef";

	/** The Constant EJB. */
	public static final String EJB = "EJB";

	/** The Constant HOME_CLASS. */
	public static final String HOME_CLASS = "HomeClass";

	/** The Constant JMS_DEF. */
	public static final String JMS_DEF = "JMSDef";

	/** The Constant JMS. */
	public static final String JMS = "JMS";

	/** The Constant JMS_FACTORY. */
	public static final String JMS_FACTORY = "JMSFactory";

	/** The Constant JMS_TOPIC. */
	public static final String JMS_TOPIC = "JMSTopic";

	/** The Constant Topic. */
	public static final String Topic = "Topic";

	/** The Constant Queue. */
	public static final String Queue = "Queue";

	/** The Constant JMS_QUEUE. */
	public static final String JMS_QUEUE = "JMSQueue";

	/** The Constant OBJECT_DEF. */
	public static final String OBJECT_DEF = "ObjectDef";

	/** The Constant OBJECT. */
	public static final String OBJECT = "Object";

	/** The Constant NAME_ATTR. */
	public static final String NAME_ATTR = "name";

	/** The Constant SPRING_DEF. */
	public static final String SPRING_DEF = "SpringDef";

	/** The Constant SPRING_XML_FILE_DEF. */
	public static final String SPRING_XML_FILE_DEF = "SpringXmlFileDef";

	/** The Constant VALUE. */
	public static final String VALUE = "value";

	/** The Constant PROJECT. */
	public static final String PROJECT = "Project";

	/** The Constant MAIN_ATTR. */
	public static final String MAIN_ATTR = "main";

	/** The document. */
	Document document;

	/**
	 * 构造函数.
	 */
	public ServiceConfig() {
	}

	// 迭代元素Element下面的所有子元素
	/**
	 * Element iterator.
	 * 
	 * @param element
	 *            the element
	 * @return the map
	 */
	public Map ElementIterator(Element element) {
		Map map = new HashMap();
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element e = (Element) i.next();
			map.put(e.getName(), e.getTextTrim());
		}
		return map;
	}

	// 迭代元素Element下面的name子元素
	/**
	 * Element iterator.
	 * 
	 * @param element
	 *            the element
	 * @param name
	 *            the name
	 * @return the map
	 */
	public Map ElementIterator(Element element, String name) {
		Map map = new HashMap();
		for (Iterator i = element.elementIterator(name); i.hasNext();) {
			Element e = (Element) i.next();
			map.put(e.getName(), e.getTextTrim());
		}
		return map;
	}

	/**
	 * Gets the item value list.
	 * 
	 * @param e
	 *            the e
	 * @param name
	 *            the name
	 * @return the item value list
	 */
	public List getItemValueList(Element e, String name) {
		LinkedList linkedlist = new LinkedList();
		for (Iterator i = e.elementIterator(name); i.hasNext();) {
			Element ename = (Element) i.next();
			linkedlist.add(ename.getTextTrim());
		}
		return linkedlist;
	}

	/**
	 * Gets the item value.
	 * 
	 * @param e
	 *            the e
	 * @param name
	 *            the name
	 * @return the item value
	 */
	public String getItemValue(Element e, String name) {
		String value = null;
		for (Iterator i = e.elementIterator(name); i.hasNext();) {
			Element ename = (Element) i.next();
			value = ename.getTextTrim();
		}
		return value;
	}

	/**
	 * 以name为名放在一个MAP，value为一个MAP1，MAP1放的是name下面的元素。包括URL，FACTORY，USER，PWD等.
	 * 
	 * @return Map
	 */
	public Map getEnvs() {
		Map ctxs = new HashMap();
		try {
			Element root = document.getRootElement();
			Element envs = root.element(ENVS);
			String name;
			for (Iterator i = envs.elementIterator(ENV); i.hasNext();) {
				Element env = (Element) i.next();
				name = env.attributeValue(NAME_ATTR);
				Map envcontent = ElementIterator(env);
				ctxs.put(name, envcontent);
			}
		} catch (Exception e) {
			log.debug("read context info fail", e);
			return null;
		}
		return ctxs;
	}

	/**
	 * Gets the eJB meta data.
	 * 
	 * @return the eJB meta data
	 */
	public Map getEJBMetaData() {
		Map ejbMetaDatas = new HashMap();
		try {
			Element root = document.getRootElement();
			Element ejbdef = root.element(EJB_DEF);
			String name;
			for (Iterator i = ejbdef.elementIterator(EJB); i.hasNext();) {
				Element ejb = (Element) i.next();
				name = ejb.attributeValue(NAME_ATTR);
				List ctxs = getItemValueList(ejb, ENV);
				String homeClassName = getItemValue(ejb, HOME_CLASS);
				ejbMetaDatas.put(name, new EJBMetaData(name, homeClassName, ctxs));
			}
		} catch (Exception e) {
			log.debug("read EJB info fail", e);
			return null;
		}
		return ejbMetaDatas;
	}

	/**
	 * Gets the jMS meta data.
	 * 
	 * @return the jMS meta data
	 */
	public Map getJMSMetaData() {
		Map jmsMetaDatas = new HashMap();
		try {
			Element root = document.getRootElement();
			Element jmsDef = root.element(JMS_DEF);
			// read Topic
			Element jmsTopic = jmsDef.element(JMS_TOPIC);
			String topicName;
			for (Iterator i = jmsTopic.elementIterator(JMS); i.hasNext();) {
				Element jms = (Element) i.next();
				topicName = jms.attributeValue(NAME_ATTR);
				Map itemValue = ElementIterator(jms);
				String ctx = (String) itemValue.get(ENV);
				String factory = (String) itemValue.get(JMS_FACTORY);
				String topic = (String) itemValue.get(Topic);
				jmsMetaDatas.put(topicName, new JMSTopicMetaData(topicName, factory, topic, ctx));
			}
			// read Queue
			Element jmsQueue = jmsDef.element(JMS_QUEUE);
			String queueName;
			for (Iterator i = jmsQueue.elementIterator(JMS); i.hasNext();) {
				Element jms = (Element) i.next();
				queueName = jms.attributeValue(NAME_ATTR);
				Map itemValue = ElementIterator(jms);
				String ctx = (String) itemValue.get(ENV);
				String factory = (String) itemValue.get(JMS_FACTORY);
				String queue = (String) itemValue.get(Queue);
				jmsMetaDatas.put(queueName, new JMSQueueMetaData(queueName, factory, queue, ctx));
			}

		} catch (Exception e) {
			log.debug("read context info fail", e);
			return null;
		}
		return jmsMetaDatas;
	}

	/**
	 * Gets the spring application context.
	 * 
	 * @return the spring application context
	 */
	public Map getSpringApplicationContext() {
		Map map = new HashMap();
		try {
			Element root = document.getRootElement();
			Element SpringDef = root.element(SPRING_DEF);
			Element springXmlFileList = SpringDef.element(SPRING_XML_FILE_DEF);
			String name = null;
			String main = null;

			for (Iterator i = springXmlFileList.elementIterator(PROJECT); i.hasNext();) {
				Element project = (Element) i.next();
				name = project.attributeValue(NAME_ATTR);
				main = project.attributeValue(MAIN_ATTR);
				List xmlFile = getItemValueList(project, VALUE);
				map.put(name + "-main", main);
				map.put(name, xmlFile);
			}

		} catch (Exception e) {
			log.debug("read context info fail", e);
			return null;
		}
		return map;
	}

	/**
	 * Gets the obj meta data.
	 * 
	 * @return the obj meta data
	 */
	public Map getObjMetaData() {
		Map objMetaDatas = new HashMap();
		try {
			Element root = document.getRootElement();
			Element envs = root.element(OBJECT_DEF);
			String name;
			for (Iterator i = envs.elementIterator(OBJECT); i.hasNext();) {
				Element obj = (Element) i.next();
				name = obj.attributeValue(NAME_ATTR);
				String ctx = (String) obj.getTextTrim();
				objMetaDatas.put(name, new ObjectMetaData(name, ctx));
			}
		} catch (Exception e) {
			log.debug("read context info fail", e);
			return null;
		}
		return objMetaDatas;
	}

	/**
	 * Config.
	 * 
	 * @param confFilePath
	 *            the conf file path
	 * @return true, if successful
	 */
	public boolean config(String confFilePath) {

		try {
			SAXReader reader = new SAXReader();
			document = reader.read(new File(confFilePath));
			return true;
		} // try
		catch (Exception e) {
			log.debug("parser xml file fail", e);
		}
		return false;
	}

}
