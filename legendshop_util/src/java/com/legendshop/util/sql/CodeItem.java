/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.sql;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.legendshop.util.xml.ConfigException;
import com.legendshop.util.xml.Configure;

/**
 * 子配置文件解析类
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class CodeItem {

	/** The logger. */
	private static Logger logger = Logger.getLogger(CodeItem.class);

	/** The code file. */
	private File codeFile = null;

	/** The last modified time. */
	private long lastModifiedTime;

	/**
	 * 利用配置文件来进行初始化工作.
	 * 
	 * @return 初始化是否成功
	 */
	public synchronized Map<String, String> init(String confName) {
		Configure configure = new Configure();
		codeFile = new File(confName);
		lastModifiedTime = codeFile.lastModified();
		try {
			configure.parse(confName);
			return parseXML(configure);
		} // try
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 检查属性文件是否被修改过.
	 * 
	 * @return true, if is modified
	 */
	public boolean isModified() {
		if (this.codeFile == null) {
			return false;
		}
		return this.codeFile.lastModified() > this.lastModifiedTime;
	}

	public Map<String, String> init(InputStream inputStream) {
		Configure configure = new Configure();
		try {
			configure.parse(inputStream);
			return parseXML(configure);
		} // try
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Map<String, String> parseXML(Configure configure) throws ConfigException {
		Map<String, String> codes = new HashMap<String, String>();
		// get Object List
		int objectCount = configure.getItemCount("/DataAccessLayer/BusinessObjects");

		// 根据objectName遍历各个Object
		for (int index = 1; index <= objectCount; index++) {
			// get ObjectName
			String objectName = configure.getItemProp("/DataAccessLayer/BusinessObjects/Object[" + index + "]", "objectName");
			int methodCount = configure.getItemCount("/DataAccessLayer/BusinessObjects/Object[" + index + "]");

			// 遍历每一个方法
			for (int i = 0; i < methodCount; i++) {
				String path3 = "/DataAccessLayer/BusinessObjects/Object[" + index + "]";
				String methodName = configure.getItemProp(path3 + "/Method[" + (i + 1) + "]", "name");
				String sql = configure.getItemValue(path3, "/Method[" + (i + 1) + "]");

				// 生成signature
				String iMethodName = ObjectSignature.toSignature(objectName, methodName);
				if (codes.containsKey(iMethodName)) {
					logger.warn(" unique constraint violated ,key = " + iMethodName);
				} else {
					codes.put(iMethodName, sql == null ? "" : sql.trim());
				}

			} // for Method

		} // while object
		return codes;
	}

}
