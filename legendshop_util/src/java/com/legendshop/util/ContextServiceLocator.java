/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 * 
 */
public final class ContextServiceLocator implements ServiceLocatorIF {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ContextServiceLocator.class);

	/** The instance. */
	private static ServiceLocatorIF instance = null;

	/** The context. */
	private ApplicationContext context;

	/** The is refreshed. */
	private boolean isRefreshed = false;

	/**
	 * Gets the single instance of ContextServiceLocator.
	 * 
	 * @return ServiceLocatorIF Instance
	 */
	public static ServiceLocatorIF getInstance() {
		if (instance == null) {
			instance = new ContextServiceLocator();
		}
		return instance;
	}

	/**
	 * Gets the single instance of ContextServiceLocator.
	 * 
	 * @param fileName
	 *            the file name
	 * @return single instance of ContextServiceLocator
	 */
	public static ServiceLocatorIF getInstance(String fileName) {
		if (instance == null) {
			instance = new ContextServiceLocator();
			if (instance.getContext() == null) {
				instance.setContext(new ClassPathXmlApplicationContext(fileName));
			}

		}
		return instance;
	}

	/**
	 * Create a new ServiceLocatorIF Instance.
	 */
	private ContextServiceLocator() {
	}

	/**
	 * Create a new ServiceLocatorIF Instance.
	 * 
	 * @param context
	 *            the context
	 */
	public ContextServiceLocator(ApplicationContext context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#getContext()
	 */

	public ApplicationContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#getBean(java.lang.String)
	 */

	public Object getBean(String beanName) {
		if(context == null){
			return null;
		}
		return context.getBean(beanName);
	}

	/**
	 * Autowire service.
	 * 
	 * @param bean
	 *            the bean
	 */
	public void autowireService(Object bean) {
		((AbstractApplicationContext) context).getBeanFactory().autowireBeanProperties(bean,
				AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.util.ServiceLocatorIF#setContext(org.springframework.beans
	 * .factory.BeanFactory)
	 */

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#getBean(java.lang.Class,
	 * java.lang.String)
	 */

	public <T> T getBean(Class<T> type, String bean) {
		return context.getBean(bean, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#getBean(java.lang.Class)
	 */

	public <T> T getBean(Class<T> type) {
		return getBean(type, type.getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#containsBean(java.lang.String)
	 */

	public boolean containsBean(String bean) {
		return context.containsBean(bean);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.util.ServiceLocatorIF#refresh(javax.servlet.ServletContext
	 * )
	 */

	public void refresh(ServletContext servletContext) {
		log.info("spring context refreshing");
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ConfigurableApplicationContext arac = (ConfigurableApplicationContext) wac;
		arac.refresh();
		System.out.println("ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = "
				+ servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, arac);
		context = arac;
		isRefreshed = true;
		log.info("spring context refresh successful");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#isContextRefreshed()
	 */

	public boolean isContextRefreshed() {
		return isRefreshed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.ServiceLocatorIF#setContextRefreshed(boolean)
	 */

	public void setContextRefreshed(boolean isRefreshed) {
		this.isRefreshed = isRefreshed;
	}
}
