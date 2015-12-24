/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.plugins;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * The Class PluginClassLoader.
 */
public class PluginClassLoader extends URLClassLoader {

	/**
	 * Construtor.
	 * 
	 * @param urls
	 *            Array of urls with own libraries and all exported libraries of
	 *            plugins that are required to this plugin
	 * @param parent
	 *            the parent
	 */
	public PluginClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

}
