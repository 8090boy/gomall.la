/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.legendshop.plugins.Plugin;
import com.legendshop.plugins.PluginConfig;
import com.legendshop.plugins.PluginStatusEnum;
import com.legendshop.util.AppUtils;

/**
 * The Class NamespaceHandler.
 */
public class NamespaceHandler extends NamespaceHandlerSupport {
	/** The log. */
	private final Logger logger = LoggerFactory.getLogger(NamespaceHandler.class);

	/** The Constant SCOPE_ATTRIBUTE. */
	private static final String SCOPE_ATTRIBUTE = "scope";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {
		registerBeanDefinitionParser("import", new ImportDefinitionParser());
		registerBeanDefinitionParser("plugin", new PluginDefinitionParser());
		registerBeanDefinitionParser("map", new MapBeanDefinitionParser());
		registerBeanDefinitionParser("list", new ListBeanDefinitionParser());
	}

	/**
	 * Load spring context.
	 * 
	 * @param element
	 *            the element
	 * @param parserContext
	 *            the parser context
	 * @param location
	 *            the location
	 */
	private void loadSpringContext(final Element element, final ParserContext parserContext, String location) {
		XmlReaderContext readerContext = parserContext.getReaderContext();
		if (!StringUtils.hasText(location)) {
			// readerContext.error("Resource location must not be empty",
			// element);
			return;
		}
		// location = SystemPropertyUtils.resolvePlaceholders(location);

		if (ResourcePatternUtils.isUrl(location)) {
			try {
				Set<Resource> actualResources = new LinkedHashSet<Resource>(4);
				int importCount = readerContext.getReader().loadBeanDefinitions(location, actualResources);
				if (logger.isDebugEnabled()) {
					logger.debug("Imported " + importCount + " bean definitions from URL location [" + location + "]");
				}

				Resource[] actResArray = actualResources.toArray(new Resource[actualResources.size()]);
				readerContext.fireImportProcessed(location, actResArray, readerContext.extractSource(element));
			} catch (BeanDefinitionStoreException ex) {
				readerContext.error("Failed to import bean definitions from URL location [" + location + "]", element, ex);
			}
		} else {
			// No URL -> considering resource location as relative to the
			// current file.
			try {
				Resource relativeResource = readerContext.getResource().createRelative(location);
				int importCount = readerContext.getReader().loadBeanDefinitions(relativeResource);
				if (logger.isDebugEnabled()) {
					logger.debug("Imported " + importCount + " bean definitions from relative location [" + location + "]");
				}
				readerContext.fireImportProcessed(location, new Resource[] { relativeResource },
						readerContext.extractSource(element));
			} catch (IOException ex) {
				readerContext.error("Invalid relative resource location [" + location + "] to import bean definitions from",
						element, ex);
			} catch (BeanDefinitionStoreException ex) {
				readerContext.error("Failed to import bean definitions from relative location [" + location + "]", element, ex);
			}
		}
	}

	/**
	 * The Class ImportDefinitionParser.
	 */
	private class ImportDefinitionParser implements BeanDefinitionParser {

		/** * For conditional import **. */
		public static final String RESOURCE_ATTRIBUTE = "resource";

		/** The Constant MATCHER_CLASS. */
		public static final String MATCHER_CLASS = "matcher-class";

		/** The Constant REG_EXP. */
		public static final String TYPE_ATTR = "type";

		/** The Constant PROPERTY_KEY. */
		public static final String VALUE_ATTR = "value";

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.xml.BeanDefinitionParser#parse(
		 * org.w3c.dom.Element,
		 * org.springframework.beans.factory.xml.ParserContext)
		 */
		public BeanDefinition parse(final Element element, final ParserContext parserContext) {
			
			String resource = element.getAttribute(RESOURCE_ATTRIBUTE);
			String type = element.getAttribute(TYPE_ATTR);
			String value = element.getAttribute(VALUE_ATTR);

			String matcherClassName = element.getAttribute(MATCHER_CLASS);
			AbstractPluginMatcher conditionalImpMatcher = null;
			try {
				if (AppUtils.isNotBlank(matcherClassName)) {
					conditionalImpMatcher = (AbstractPluginMatcher) ClassUtils.forName(matcherClassName).newInstance();
				} else {
					conditionalImpMatcher = new ImportMatcher();
				}

				conditionalImpMatcher.setResource(resource);
				conditionalImpMatcher.setType(type);
				conditionalImpMatcher.setValue(value);
//				DefaultListableBeanFactory registry = (DefaultListableBeanFactory) parserContext.getRegistry();
//				BeanFactory beanFactory = registry.getParentBeanFactory();
//				if (beanFactory != null) {
//					conditionalImpMatcher.setBeanFactory(beanFactory);
//				} else {
//					conditionalImpMatcher.setBeanFactory(registry);
//				}
			} catch (Exception ex) {
				throw new IllegalStateException("Unable to load class [" + matcherClassName + "]", ex);
			}

			if (!conditionalImpMatcher.isMatch()) {
				return null;
			}

			String location = conditionalImpMatcher.getParsedResource();
			loadSpringContext(element, parserContext, location);
			return null;
		}

	}

	/**
	 * The Class PluginDefinitionParser.
	 */
	private class PluginDefinitionParser implements BeanDefinitionParser {

		/** The Constant PULGINID_ATTR. */
		public static final String PULGINID_ATTR = "pulginId";

		/** The Constant PULGINVERSION_ATTR. */
		public static final String PULGINVERSION_ATTR = "pulginVersion";

		/** The Constant STATUS_ATTR. */
		public static final String STATUS_ATTR = "status";

		/** The Constant REQUIRED_ATTR. */
		public static final String REQUIRED_ATTR = "required";

		/** The Constant SPRING_CONFIGURATION_ATTR. */
		public static final String SPRING_CONFIGURATION_ATTR = "springConfiguration";

		/** The Constant I18N_ATTR. */
		public static final String I18N_ATTR = "i18n";

		/** The Constant CLASS_ATTR. */
		public static final String CLASS_ATTR = "class";

		/** The Constant DESCRIPTION_ATTR. */
		public static final String DESCRIPTION_ATTR = "description";

		/** The Constant PROVIDER. */
		public static final String PROVIDER = "provider";

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.xml.BeanDefinitionParser#parse(
		 * org.w3c.dom.Element,
		 * org.springframework.beans.factory.xml.ParserContext)
		 */
		public BeanDefinition parse(Element element, ParserContext parserContext) {

			String pulginId = element.getAttribute(PULGINID_ATTR);
			String pulginVersion = element.getAttribute(PULGINVERSION_ATTR);
			String status = element.getAttribute(STATUS_ATTR);
			Boolean isRequired = false;
			if (element.getAttribute(REQUIRED_ATTR) != null) {
				try {
					isRequired = Boolean.valueOf(element.getAttribute(REQUIRED_ATTR));
				} catch (Exception e) {
				}
			}
			String springConfiguration = element.getAttribute(SPRING_CONFIGURATION_ATTR);
			String i18n = element.getAttribute(I18N_ATTR);
			String className = element.getAttribute(CLASS_ATTR);
			String description = element.getAttribute(DESCRIPTION_ATTR);
			String provider = element.getAttribute(PROVIDER);

			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setDescription(description);
			pluginConfig.setPulginId(pulginId);
			pluginConfig.setPulginVersion(pulginVersion);
			pluginConfig.setProvider(provider);
			pluginConfig.setRequired(isRequired);
			pluginConfig.setSpringConfiguration(springConfiguration);
			try {
				PluginStatusEnum pluginStatus = PluginStatusEnum.valueOf(status);
				pluginConfig.setStatus(pluginStatus);
			} catch (Exception e) {
				pluginConfig.setStatus(PluginStatusEnum.N);
			}

			pluginConfig.setI18n(i18n);

			// fail before spring db initialized
			Plugin plugin = null;
			PluginImportServiceMatcher pluginConditionalImportMatcher = new PluginImportServiceMatcher();
			 try {
				 plugin =(Plugin) ClassUtils.forName(className).newInstance();
				 plugin.setPluginConfig(pluginConfig);
			} catch (Exception e) {
				logger.error("can not instantiate class {}", className);
			}
			pluginConditionalImportMatcher.setPlugin(plugin);
			pluginConditionalImportMatcher.setValue(pulginId);
//			DefaultListableBeanFactory registry = (DefaultListableBeanFactory) parserContext.getRegistry();
//			BeanFactory beanFactory = registry.getParentBeanFactory();
//			if (beanFactory != null) {
//				pluginConditionalImportMatcher.setBeanFactory(beanFactory);
//			} else {
//				pluginConditionalImportMatcher.setBeanFactory(registry);
//			}

//			if (!(PluginStatusEnum.N.equals(pluginConfig.getStatus()))) {
//				registerPlugin(element, parserContext, className, pluginConfig);
//			}
			
			if (!pluginConditionalImportMatcher.isMatch()) {
				return null;
			}

			if (PluginStatusEnum.Y.equals(pluginConfig.getStatus())) {
				// load plugin spring config
				loadSpringContext(element, parserContext, pluginConfig.getSpringConfiguration());
			}
			return null;
		}

		/**
		 * Register plugin.
		 * 
		 * @param element
		 *            the element
		 * @param parserContext
		 *            the parser context
		 * @param className
		 *            the class name
		 * @param pluginConfig
		 *            the plugin config
		 */
		private void registerPlugin(Element element, ParserContext parserContext, String className, PluginConfig pluginConfig) {
			String beanName = className + "_" + pluginConfig.getPulginId();
			if (!parserContext.getRegistry().containsBeanDefinition(beanName)) {
				RootBeanDefinition def = new RootBeanDefinition();
				def.setBeanClassName(className);
				parsePluginProperty(element, def, pluginConfig);
				parserContext.registerBeanComponent(new BeanComponentDefinition(def, beanName));
			}
		}

		/**
		 * Parses the plugin property.
		 * 
		 * @param element
		 *            the element
		 * @param def
		 *            the def
		 * @param pluginConfig
		 *            the plugin config
		 */
		private void parsePluginProperty(Element element, BeanDefinition def, PluginConfig pluginConfig) {
			// def.getPropertyValues().add("pluginConfig", new
			// RuntimeBeanReference("beanId"));
			def.getPropertyValues().add("pluginConfig", pluginConfig);
		}

	}

	/**
	 * The Class MapBeanDefinitionParser.
	 */
	private static class MapBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

		/** The constant default key. */
		public static final String DEFAULT_KEY = "default-key";

		/** The constant map class name. */
		public static final String MAP_CLASS_NAME = "com.legendshop.util.handler.DefaultKeyMap";

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
		 * #getBeanClass(org.w3c.dom.Element)
		 */
		@Override
		protected Class<DefaultMapFactoryBean> getBeanClass(final Element element) {
			return DefaultMapFactoryBean.class;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
		 * #doParse(org.w3c.dom.Element,
		 * org.springframework.beans.factory.xml.ParserContext,
		 * org.springframework.beans.factory.support.BeanDefinitionBuilder)
		 */
		@Override
		protected void doParse(final Element element, final ParserContext parserContext, final BeanDefinitionBuilder builder) {
			Map<?, ?> parsedMap = parserContext.getDelegate().parseMapElement(element, builder.getRawBeanDefinition());

			String id = element.getAttribute(AbstractBeanDefinitionParser.ID_ATTRIBUTE);
			String defaultKey = element.getAttribute(DEFAULT_KEY);
			String originalDefaultKey = null;
			// Merge the entries from another map which has the same bean id
			// Retrieve the previous default key if provided
			if (parserContext.getRegistry().isBeanNameInUse(id)) {
				PropertyValue sourceMapPv = parserContext.getRegistry().getBeanDefinition(id).getPropertyValues()
						.getPropertyValue("sourceMap");
				PropertyValue defaultKeyPv = parserContext.getRegistry().getBeanDefinition(id).getPropertyValues()
						.getPropertyValue("defaultKey");

				ManagedMap oldMap = (ManagedMap) sourceMapPv.getValue();
				originalDefaultKey = (String) defaultKeyPv.getValue();

				parsedMap.putAll(oldMap);
			}

			builder.addPropertyValue("sourceMap", parsedMap);
			builder.addPropertyValue("targetMapClass", MAP_CLASS_NAME);

			// 1) throw exception when different default key were provided
			// 2) the first converted bean has no default key, but next one does
			// 3) no default key provided and same default key provided
			if (StringUtils.hasText(defaultKey)) {
				if (StringUtils.hasText(originalDefaultKey) && !originalDefaultKey.equals(defaultKey)) {
					throw new RuntimeException("Default key must be unique in one map!");
				}
			} else if (StringUtils.hasText(originalDefaultKey)) {
				defaultKey = originalDefaultKey;
			}
			builder.addPropertyValue("defaultKey", defaultKey);

			String scope = element.getAttribute(SCOPE_ATTRIBUTE);
			if (StringUtils.hasLength(scope)) {
				builder.setScope(scope);
			}
		}
	}

	/**
	 * The Class ListBeanDefinitionParser.
	 */
	private static class ListBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
		 * #getBeanClass(org.w3c.dom.Element)
		 */
		@Override
		protected Class<ListFactoryBean> getBeanClass(final Element element) {
			return ListFactoryBean.class;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
		 * #doParse(org.w3c.dom.Element,
		 * org.springframework.beans.factory.xml.ParserContext,
		 * org.springframework.beans.factory.support.BeanDefinitionBuilder)
		 */
		@Override
		protected void doParse(final Element element, final ParserContext parserContext, final BeanDefinitionBuilder builder) {
			String listClass = element.getAttribute("list-class");
			List<?> currentParsedList = parserContext.getDelegate().parseListElement(element, builder.getRawBeanDefinition());

			String id = element.getAttribute(AbstractBeanDefinitionParser.ID_ATTRIBUTE);
			if (parserContext.getRegistry().isBeanNameInUse(id)) {
				PropertyValue propValue = parserContext.getRegistry().getBeanDefinition(id).getPropertyValues()
						.getPropertyValue("sourceList");
				ManagedList oldList = (ManagedList) propValue.getValue();

				currentParsedList.addAll(oldList);
			}

			builder.addPropertyValue("sourceList", currentParsedList);
			if (StringUtils.hasText(listClass)) {
				builder.addPropertyValue("targetListClass", listClass);
			}
			String scope = element.getAttribute(NamespaceHandler.SCOPE_ATTRIBUTE);
			if (StringUtils.hasLength(scope)) {
				builder.setScope(scope);
			}
		}
	}

}
