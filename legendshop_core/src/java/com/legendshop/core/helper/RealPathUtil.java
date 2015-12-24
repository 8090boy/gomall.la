/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import javax.servlet.ServletContext;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class RealPathUtil {

	/**
	 * 取当前系统真实的大图片路径.
	 * 
	 * @return the big pic real path
	 */
	public static String getBigPicRealPath() {
		return PropertiesUtil.getBigFilesAbsolutePath();
	}

	/**
	 * 得到系统的实际安装路径.
	 * 
	 * @param context
	 *            the context
	 * @return the system real path
	 */
	public static String getSystemRealPath(ServletContext context) {
		return context.getRealPath("/");
	}

	/**
	 * 取缩略图当前系统路径.
	 * 
	 * @return the small pic real path
	 */
	public static String getSmallPicRealPath() {
		return PropertiesUtil.getSmallFilesAbsolutePath();
	}

	/**
	 * 取当FCKEditor文件系统路径.
	 * 
	 * @param filePath
	 *            the file path
	 * @return the fCK real path
	 */
	public static String getFCKRealPath(String filePath) {
		String userFilesPath = PropertiesUtil.getPhotoPathPrefix();
		String path = "";
		if ((filePath != null) && (userFilesPath != null)) {
			int pos = filePath.indexOf(userFilesPath);
			if (pos > -1) {
				path = filePath.substring(pos + userFilesPath.length() + 1);
			}

		}
		return PropertiesUtil.getBigFilesAbsolutePath() + "/" + path;
	}

}
