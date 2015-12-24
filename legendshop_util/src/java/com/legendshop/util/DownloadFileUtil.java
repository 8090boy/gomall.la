/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 * 
 */
public class DownloadFileUtil {

	/** The instance. */
	private static DownloadFileUtil instance;

	/** The logger. */
	private static Logger logger = Logger.getLogger(DownloadFileUtil.class.getName());

	/**
	 * Gets the single instance of DownloadFileUtil.
	 * 
	 * @return single instance of DownloadFileUtil
	 */
	public static DownloadFileUtil getInstance() {
		if (instance == null) {
			instance = new DownloadFileUtil();

		}
		return instance;
	}

	/**
	 * Instantiates a new download file util.
	 */
	protected DownloadFileUtil() {

	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * 
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					ex.printStackTrace();
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Gets the encoding file name.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the encoding file name
	 */
	public static String getEncodingFileName(String fileName) {

		logger.debug("EncodingFileName: " + fileName);

		int index = fileName.lastIndexOf(".");
		String name = fileName;
		String ext = "";
		int length = 56;

		if (index > -1) {
			name = fileName.substring(0, index);
			ext = fileName.substring(index);

			length = length - ext.length() - 4;
		}

		String dot = "";

		try {
			int showBlength = name.getBytes("GBK").length;

			if (showBlength > length) {
				showBlength = length;
				dot = "....";
			}

			String tmpStr = new String((name).getBytes("GBK"), 0, showBlength, "GBK");
			if (("").equals(tmpStr)) {
				tmpStr = new String((name).getBytes("GBK"), 0, showBlength + 1, "GBK");

			}

			logger.debug("Encode File Name: " + (tmpStr + dot + ext));

			return tmpStr + dot + ext;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Download file.
	 * 
	 * @param response
	 *            the response
	 * @param filePath
	 *            the file path
	 * @param fileName
	 *            the file name
	 * @param isDownload
	 *            the is download
	 */
	public void downloadFile(HttpServletResponse response, String filePath, String fileName, boolean isDownload) {
		try {
			File file = new File(filePath);

			if (!file.exists() || !file.isFile()) {
				logger.debug("File: " + (filePath) + " Not Exists");

				response.setHeader("Content-Type", "text/html; charset=GBK");
				ServletOutputStream os = response.getOutputStream();
				os.println("文件不存在！联系管理员！");
			} else {

				if (isDownload) {
					response.setHeader("Content-Type", "application/octet-stream; charset=GBK");

					response.setHeader("Content-Disposition", "attachment; filename=" + toUtf8String(fileName));
				} else {

					String contentType = "application/pdf; charset=GBK";

					if (fileName != null && fileName.endsWith(".doc")) {
						contentType = "application/msword; charset=GBK";

						response.setHeader("Content-Type", contentType);
					} else if (fileName != null && fileName.endsWith(".pdf")) {
						contentType = "application/pdf; charset=GBK";

						response.setHeader("Content-Type", contentType);
					} else {
						contentType = "application/force-download";

						response.setHeader("Content-Type", contentType);
					}

					response.setHeader("Content-Disposition", "filename=" + toUtf8String(fileName));
				}

				/*
				 * String contentType= "application/pdf; charset=GBK" ;
				 */

				FileInputStream fis = new FileInputStream(filePath);
				byte data[] = new byte[8192];
				ServletOutputStream os = response.getOutputStream();
				int i;
				while ((i = fis.read(data, 0, 8192)) != -1) {
					os.write(data, 0, i);
				}

				os.flush();
				fis.close();
				os.close();

				logger.debug("Download File: " + filePath + " Finished");
			}
		} catch (Exception e) {
			logger.error("DownloadFile: " + filePath + " Error", e);
		}
	}
}