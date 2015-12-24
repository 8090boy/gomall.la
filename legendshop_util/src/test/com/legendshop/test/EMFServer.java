package com.legendshop.test;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.InputStreamResource;

public class EMFServer implements Runnable {
	private static GenericApplicationContext appCtx;

	private static boolean running = false;

	public EMFServer() {
		Object o = this.getClass().getClassLoader();
		System.out.println("ClassLoader: " + o.getClass());
	}

	public void run() {
		appCtx = new GenericApplicationContext();
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(appCtx);
		xmlReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_DTD);
		xmlReader.loadBeanDefinitions(new InputStreamResource(this.getClass().getClassLoader()
				.getResourceAsStream("componentContext.xml")));
		appCtx.refresh();

		running = true;

		while (running) {
			// Do something...
		}
	}
}