/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */

package com.legendshop.command.framework;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public abstract class BaseProcessor implements InitializingBean, BeanNameAware, BeanFactoryAware, Processor {

	/** The bean factory. */
	protected BeanFactory beanFactory;

	/** The bean name. */
	private String beanName;

	/** The activities. */
	private List activities;

	/** The error handler. */
	private ErrorHandler errorHandler;

	/*
	 * Sets name of the spring bean in the application context that this
	 * processor is configured under (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang
	 * .String)
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;

	}

	/*
	 * Sets the spring bean factroy bean that is responsible for this processor.
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org
	 * .springframework.beans.factory.BeanFactory)
	 */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

	/*
	 * Called after the properties have been set, Ensures the list of activities
	 * is not empty and each activity is supported by this Workflow Processor
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {

		if (!(beanFactory instanceof ListableBeanFactory))
			throw new BeanInitializationException(
					"The workflow processor ["
							+ beanName
							+ "] "
							+ "is not managed by a ListableBeanFactory, please re-deploy using some dirivative of ListableBeanFactory such as"
							+ "ClassPathXmlApplicationContext ");

		if (activities == null || activities.isEmpty())
			throw new UnsatisfiedDependencyException(getBeanDesc(), beanName, "activities",
					"No activities were wired for this workflow");

		for (Iterator iter = activities.iterator(); iter.hasNext();) {
			Command activitiy = (Command) iter.next();
			if (!supports(activitiy))
				throw new BeanInitializationException("The workflow processor [" + beanName + "] does "
						+ "not support the activity of type" + activitiy.getClass().getName());
		}

	}

	/**
	 * Returns the bean description if the current bean factory allows it.
	 * 
	 * @return spring bean description configure via the spring description tag
	 */
	protected String getBeanDesc() {
		return (beanFactory instanceof ConfigurableListableBeanFactory) ? ((ConfigurableListableBeanFactory) beanFactory)
				.getBeanDefinition(beanName).getResourceDescription() : "Workflow Processor: " + beanName;

	}

	/**
	 * Sets the collection of Activities to be executed by the Workflow Process.
	 * 
	 * @param activities
	 *            ordered collection (List) of activities to be executed by the
	 *            processor
	 */
	public void setActivities(List activities) {
		this.activities = activities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.Processor#setErrorHandler(com.legendshop
	 * .command.framework.ErrorHandler)
	 */
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/**
	 * Gets the activities.
	 * 
	 * @return the activities
	 */
	public List getActivities() {
		return activities;
	}

	/**
	 * Gets the bean name.
	 * 
	 * @return the bean name
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * Gets the error handler.
	 * 
	 * @return the error handler
	 */
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	/**
	 * Gets the bean factory.
	 * 
	 * @return the bean factory
	 */
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
}