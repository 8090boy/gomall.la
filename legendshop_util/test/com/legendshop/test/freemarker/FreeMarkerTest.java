package com.legendshop.test.freemarker;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class FreeMarkerTest {
	public static void main(String[] args) {
		FreeMarkerTest test = new FreeMarkerTest();
		test.sayHello("LegendShop");
	}

	public void sayHello(String name) {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setClassForTemplateLoading(this.getClass(), "/");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		Template template;
		Locale.setDefault(Locale.ENGLISH);
		try {
			template = freemarkerCfg.getTemplate("Hello.ftl");
			template.setEncoding("UTF-8");
			HashMap root = new HashMap();
			root.put("user", name);
			StringWriter writer = new StringWriter();
			template.process(root, writer);
			System.out.println(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}