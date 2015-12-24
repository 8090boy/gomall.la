/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.listener;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.plugins.PluginClassLoader;
import com.legendshop.util.AppUtils;

/**
 * The listener interface for receiving pluginContext events. The class that is
 * interested in processing a pluginContext event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addPluginContextListener<code> method. When
 * the pluginContext event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see PluginContextEvent
 */
public class PluginContextListener extends ContextLoaderListener {

	/** The log. */
	protected final Logger log = LoggerFactory.getLogger(PluginContextListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.context.ContextLoaderListener#contextInitialized
	 * (javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		if (PropertiesUtil.isSystemInstalled()) {
			try {
				ArrayList<URL> arrayList = new ArrayList<URL>();

				// add plugin classpath
				String pluginPath = event.getServletContext().getRealPath("/") + "/WEB-INF/plugins/";
				parsePluginFolder(arrayList, new File(pluginPath));

				URL[] urls = arrayList.toArray(new URL[arrayList.size()]);
				PluginClassLoader loader = new PluginClassLoader(urls, Thread.currentThread().getContextClassLoader());
				Thread.currentThread().setContextClassLoader(loader);
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.contextInitialized(event);
		}
	}

	/**
	 * Parses the plugin folder.
	 * 
	 * @param arrayList
	 *            the array list
	 * @param directory
	 *            the directory
	 */
	public void parsePluginFolder(ArrayList<URL> arrayList, File directory) {
		log.info("Plugins: looking in: " + directory.getAbsolutePath());
		for (File oneSubFolder : directory.listFiles()) {
			if (oneSubFolder.isDirectory()) {
				try {
					addPluginClassPath(arrayList, oneSubFolder.getAbsolutePath());
				} catch (MalformedURLException e) {
					log.warn(e.toString());
				}
			}
		}
	}

	/**
	 * Adds the plugin class path.
	 * 
	 * @param arrayList
	 *            the array list
	 * @param pluginPath
	 *            the plugin path
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	private void addPluginClassPath(ArrayList<URL> arrayList, String pluginPath) throws MalformedURLException {
		File libs = new File(pluginPath + "/lib");
		File[] listing = libs.listFiles(getFileFilter());
		if (AppUtils.isNotBlank(listing)) {
			for (File lib : listing) {
				arrayList.add(lib.toURI().toURL());
			}
		}

		File classesPath = new File(pluginPath + "/classes");
		if (classesPath != null) {
			arrayList.add(classesPath.toURI().toURL());
		}

	}

	/**
	 * Gets the file filter.
	 * 
	 * @return the file filter
	 */
	private FileFilter getFileFilter() {
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				String p = file.getAbsolutePath();
				return (p.toLowerCase().endsWith(".jar") || p.toLowerCase().endsWith(".zip"));
			}
		};
		return filter;
	}
}
