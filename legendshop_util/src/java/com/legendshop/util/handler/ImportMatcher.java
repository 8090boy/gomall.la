/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.legendshop.util.AppUtils;
import com.legendshop.util.EnvironmentConfig;
import com.legendshop.util.FileConfig;

/**
 * for legendshop:import.
 */
public class ImportMatcher extends AbstractPluginMatcher {

	/** The Constant logger. */
	protected final static Log logger = LogFactory.getLog(ImportMatcher.class);

	/** The Constant PLACEHOLDER_PREFIX. */
	public static final String PLACEHOLDER_PREFIX = "${";

	/** The Constant PLACEHOLDER_SUFFIX. */
	public static final String PLACEHOLDER_SUFFIX = "}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.util.conditional.AbstractPluginMatcher#getParsedResource()
	 */
	public String getParsedResource() {
		return resolvePath(this.resource);
	}

	/**
	 * Resolve path.
	 * 
	 * @param path
	 *            the path
	 * @return the string
	 */
	protected String resolvePath(String path) {
		StringBuilder buf = new StringBuilder(path);

		int startIndex = path.indexOf(PLACEHOLDER_PREFIX);
		while (startIndex != -1) {
			int endIndex = buf.toString().indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
				String propVal = System.getProperty(placeholder);
				if (propVal == null) {
					propVal = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.GlobalFile, placeholder);
					if(AppUtils.isBlank(propVal)){
						propVal = EnvironmentConfig.getInstance().getPropertyValue(FileConfig.ConfigFile, placeholder);
					}
				}
				if (propVal != null) {
					buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
					startIndex = buf.toString().indexOf(PLACEHOLDER_PREFIX, startIndex + propVal.length());
				} else {
					logger.warn("Could not resolve placeholder '" + placeholder + "' in resource path [" + path
							+ "] as system property");
					startIndex = buf.toString().indexOf(PLACEHOLDER_PREFIX, endIndex + PLACEHOLDER_SUFFIX.length());
				}
			} else {
				startIndex = -1;
			}
		}
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.util.conditional.AbstractPluginMatcher#isMatch()
	 */
	@Override
	public boolean isMatch() {
		return true;
	}

}