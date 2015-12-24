/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 目前项目需要在tomcat运行的时候加载新业务处理的功能，实现的思路如下：
 * 
 * 1 tomcat可以支持动态加载，只要把新的class问价考入到相应工程的class文件夹下就能使用
 * 
 * 2 由于业务使用的都是spring，所以需要spring能够在与运行的时候加载新的类：经过一系列的研究，找到了方法：
 * 
 * 需要往BeanFactory加入一个新的bean的定义,方法如下.
 * 
 * @author Administrator
 */
public class SpringBeanManager {
	private static Logger log = LoggerFactory.getLogger(SpringBeanManager.class);

	/**
	 * Adds the data source.
	 * 
	 * @param servletContext
	 *            the servlet context
	 * @param dataSource
	 *            the data source
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static void addDataSource(ServletContext servletContext, DataSource dataSource) throws ClassNotFoundException {
		// 1 get BeanFactory

		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) WebApplicationContextUtils.getWebApplicationContext(
				servletContext).getAutowireCapableBeanFactory();

		// 2 create Bean Definition
		AbstractBeanDefinition beanDef = BeanDefinitionReaderUtils.createBeanDefinition(null, dataSource.getClass().getName(),
				WebApplicationContextUtils.getWebApplicationContext(servletContext).getClassLoader());

		// 3 add Bean Definition to BeanFactory
		factory.registerBeanDefinition("dataSource", beanDef);
	}

	public static void removeBean(ServletContext servletContext, String beanName) {
		log.debug("remove beanName {} from spring contxt ", beanName);
		// 1 get BeanFactory
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) WebApplicationContextUtils.getWebApplicationContext(
				servletContext).getAutowireCapableBeanFactory();

		// 3 add Bean Definition to BeanFactory
		factory.removeBeanDefinition(beanName);
	}

	public void removeBean(ServletContext servletContext, Set<String> keySet) {
		if (keySet == null) {
			return;
		}
		for (String key : keySet) {
			SpringBeanManager.removeBean(servletContext, key);
		}
	}
}
