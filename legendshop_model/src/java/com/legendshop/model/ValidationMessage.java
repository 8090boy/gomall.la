/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ValidationMessage.
 */
public class ValidationMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5235165939452646497L;

	/** The error infos. */
	private List<ErrorInfo> errorInfos;

	@Override
	public String toString() {
		if (errorInfos != null) {
			StringBuilder sb = new StringBuilder();
			for (ErrorInfo info : errorInfos) {
				sb.append(info.getDesc()).append("\n");
			}
			return sb.toString();
		}
		return null;
	}

	public void addError(String filed, ErrorType type, String desc) {
		if (errorInfos == null) {
			errorInfos = new ArrayList<ErrorInfo>();
		}
		errorInfos.add(new ErrorInfo(filed, type, desc));
	}

	public boolean isFailed() {
		return errorInfos != null;
	}

	/**
	 * Gets the error infos.
	 * 
	 * @return the errorInfos
	 */
	public List<ErrorInfo> getErrorInfos() {
		return errorInfos;
	}

	/**
	 * Sets the error infos.
	 * 
	 * @param errorInfos
	 *            the errorInfos to set
	 */
	public void setErrorInfos(List<ErrorInfo> errorInfos) {
		this.errorInfos = errorInfos;
	}
}
