package com.legendshop.core.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.SystemConfig;
import com.legendshop.core.constant.BusinessModeEnum;
import com.legendshop.core.constant.ConfigPropertiesEnum;
import com.legendshop.core.constant.LanguageEnum;
import com.legendshop.core.constant.RuntimeModeEnum;
import com.legendshop.core.event.SysDestoryEvent;
import com.legendshop.core.event.SysInitEvent;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.RealPathUtil;
import com.legendshop.core.helper.SpringBeanManager;
import com.legendshop.core.page.PagerUtil;
import com.legendshop.event.BaseEventListener;
import com.legendshop.event.EventHome;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;
import com.legendshop.util.FileConfig;
import com.legendshop.util.sql.ConfigCode;

public class InitSysListener implements ServletContextListener {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(InitSysListener.class);

	/** The systemInited. */
	private static boolean systemInited = false;

	private final static Object systemInitedMonitor = new Object();

	/**
	 * 系统初始化.
	 * 
	 * @param event
	 *            the event
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log.debug("system destory start");
		EventHome.publishEvent(new SysDestoryEvent(event));
	}

	public void contextInitialized(ServletContextEvent event) {
		synchronized (systemInitedMonitor) {
			log.info("********* Initializing  gomall.la System now *************");
			initSystem(event);
			if (!systemInited && PropertiesUtil.isSystemInstalled()) {
				systemInited = true;
				// 2.Spring
				initspring(event);

				// 3.ConfigCode
				ConfigCode sQlCode = ConfigCode.getInstance("classpath*:DAL.cfg.xml");
				boolean debugMode = PropertiesUtil.isSystemInDebugMode();
				log.debug("System in DEBUG MODE is {}", debugMode);
				sQlCode.setDebug(debugMode);

				// ShopSystemId，必须先更改再reload配置文件
				if (AppUtils.isBlank(PropertiesUtil.getLegendShopSystemId())) {
					PropertiesUtil.changeLegendShopSystemId();
				}

				// 初始化事件系统
				Map<String, BaseEventListener> eventListeners = EventHome.initBaseEventListener();
				SpringBeanManager springBeanManager = (SpringBeanManager) ContextServiceLocator.getInstance().getBean("springBeanManager");
				springBeanManager.removeBean(event.getServletContext(), eventListeners.keySet());

				SystemConfig systemConfig = (SystemConfig) ContextServiceLocator.getInstance().getBean("systemConfig");
				event.getServletContext().setAttribute(ConfigPropertiesEnum.SYSTEM_CONFIG.name(), systemConfig);

				// 发送系统启动事件
				EventHome.publishEvent(new SysInitEvent(event));

				printBeanFactory(ContextServiceLocator.getInstance().getContext());
			}
			log.info("********* gomall.la System Initialized successful **********");
		}
	}

	private void initSystem(ServletContextEvent event) {

		// 1. 初始化系统真实路径
		String realPath = RealPathUtil.getSystemRealPath(event.getServletContext());
		if (realPath != null && !realPath.endsWith("/")) {
			realPath = realPath + "/";
		}
		PropertiesUtil.setSystemRealPath(realPath);

		ServletContext servletContext = event.getServletContext();

		// 相对路径
		PagerUtil.setPath(servletContext.getContextPath());

		// 货币
		servletContext.setAttribute(ConfigPropertiesEnum.CURRENCY_PATTERN.name(), PropertiesUtil.getCurrencyPattern());

		// 域名
		servletContext.setAttribute(AttributeKeys.DOMAIN_NAME, PropertiesUtil.getDomainName());

		// 业务模式
		String businessMode = PropertiesUtil.getProperties(FileConfig.GlobalFile, ConfigPropertiesEnum.BUSINESS_MODE.name());
		if (BusinessModeEnum.C2C.name().equals(businessMode)) {
			servletContext.setAttribute(AttributeKeys.BUSINESS_MODE, BusinessModeEnum.C2C.name());
		} else {
			servletContext.setAttribute(AttributeKeys.BUSINESS_MODE, BusinessModeEnum.B2C.name());
		}

		// 语言模式
		String languageMode = PropertiesUtil.getProperties(FileConfig.GlobalFile, ConfigPropertiesEnum.LANGUAGE_MODE.name());
		if (LanguageEnum.ENGLISH.equals(languageMode)) {
			servletContext.setAttribute(AttributeKeys.LANGUAGE_MODE, LanguageEnum.USERCHOICE);
		} else if (LanguageEnum.CHINESE.equals(languageMode)) {
			servletContext.setAttribute(AttributeKeys.LANGUAGE_MODE, LanguageEnum.CHINESE);
		} else {
			servletContext.setAttribute(AttributeKeys.LANGUAGE_MODE, LanguageEnum.USERCHOICE);
		}

		servletContext.setAttribute(AttributeKeys.RUNTIME_MODE, RuntimeModeEnum.PRODUCTION);

		servletContext.setAttribute(ConfigPropertiesEnum.LEGENDSHOP_VERSION.name(),
				PropertiesUtil.getProperties(FileConfig.GlobalFile, ConfigPropertiesEnum.LEGENDSHOP_VERSION.name()));

	}

	/*
	 * 从Web上下文得到Spring
	 */
	/**
	 * Initspring.
	 * 
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	private void initspring(ServletContextEvent event) {
		// ApplicationContext ctx =
		// WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext(event.getServletContext().getInitParameter("contextConfigLocation"));
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		ContextServiceLocator.getInstance().setContext(ctx);
	}

	private void printBeanFactory(ApplicationContext ctx) {
		if (log.isDebugEnabled()) {
			int i = 0;
			String[] beans = ctx.getBeanDefinitionNames();
			StringBuffer sb = new StringBuffer("系统配置的Spring Bean [ \n");
			if (!AppUtils.isBlank(beans)) {
				for (String bean : beans) {
					sb.append(++i).append(" ").append(bean).append("\n");
				}
				sb.append(" ]");
				log.debug(sb.toString());
			}
		}

	}

}
