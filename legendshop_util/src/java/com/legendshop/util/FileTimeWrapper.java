/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.io.File;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class FileTimeWrapper implements Comparable<FileTimeWrapper> {

	/** File. */
	private File file;

	/** The is file. */
	private Boolean isFile;

	/** The file name. */
	private String fileName;

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Sets the file.
	 * 
	 * @param file
	 *            the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Instantiates a new file time wrapper.
	 * 
	 * @param file
	 *            the file
	 */
	public FileTimeWrapper(File file) {
		this.file = file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(FileTimeWrapper obj) {
		if (this.file.lastModified() - obj.getFile().lastModified() > 0) {
			return -1;
		} else if (this.file.lastModified() - obj.getFile().lastModified() < 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * Gets the checks if is file.
	 * 
	 * @return the checks if is file
	 */
	public Boolean getIsFile() {
		return isFile;
	}

	/**
	 * Sets the checks if is file.
	 * 
	 * @param isFile
	 *            the new checks if is file
	 */
	public void setIsFile(Boolean isFile) {
		this.isFile = isFile;
	}

}