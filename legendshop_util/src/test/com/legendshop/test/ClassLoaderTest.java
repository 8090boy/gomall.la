package com.legendshop.test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import com.legendshop.plugins.PluginClassLoader;
import com.legendshop.plugins.PluginConfig;
import com.legendshop.plugins.PluginManagerImpl;
import com.legendshop.plugins.PluginStatusEnum;
import com.legendshop.plugins.SimplePlugin;

public class ClassLoaderTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			IOException {
		ArrayList<URL> arrayList = new ArrayList<URL>();
		URL[] urls = arrayList.toArray(new URL[arrayList.size()]);
		PluginClassLoader loader = new PluginClassLoader(urls, ClassLoaderTest.class.getClassLoader());
		Class extensionClazz = loader.loadClass("com.legendshop.plugins.SimplePlugin");
		SimplePlugin plugin = (SimplePlugin) extensionClazz.newInstance();
		PluginConfig config = new PluginConfig();
		config.setStatus(PluginStatusEnum.Y);
		config.setPulginId("pulginId");
		plugin.setPluginConfig(config);
		System.out.println(loader + " load plugin " + plugin);

		URL[] urls1 = arrayList.toArray(new URL[arrayList.size()]);
		PluginClassLoader loader1 = new PluginClassLoader(urls1, ClassLoaderTest.class.getClassLoader());
		Class extensionClazz1 = loader1.loadClass("com.legendshop.plugins.PluginManagerImpl");
		PluginManagerImpl manager = (PluginManagerImpl) extensionClazz1.newInstance();
		System.out.println(ClassLoaderTest.class.getClassLoader());
		System.out.println(loader1 + " load manager " + manager);

		manager.registerPlugins(plugin);
		SimplePlugin plugin1 = new SimplePlugin();
		config.setPulginId("pulginId1");
		plugin1.setPluginConfig(config);

		System.out.println("manager get plugin = " + manager.getPlugins());

		PluginManagerImpl manager1 = new PluginManagerImpl();
		manager1.registerPlugins(plugin1);
		System.out.println("manager111111 get plugin = " + manager1.getPlugins());

		ClassLoader loader3 = Thread.currentThread().getContextClassLoader();

		Enumeration resources = loader3.getResources("");
		while (resources.hasMoreElements()) {

			URL url = (URL) resources.nextElement();

			System.out.println(url);

		}
	}

}
