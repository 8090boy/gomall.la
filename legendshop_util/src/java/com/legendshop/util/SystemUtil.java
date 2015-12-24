package com.legendshop.util;




public class SystemUtil {
	// 系统的安装实际路径
	/** The system real path. */
	private static String systemRealPath;
	

	/**
	 * Gets the system real path.
	 * 
	 * @return the system real path
	 */
	public static String getSystemRealPath() {
		return systemRealPath;
	}

	/**
	 * Sets the system real path.
	 * 
	 * @param systemRealPath
	 *            the new system real path
	 */
	public static void setSystemRealPath(String systemRealPath) {
		SystemUtil.systemRealPath = systemRealPath;
	}


}
