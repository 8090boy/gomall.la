/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.page;

import java.io.Serializable;
import java.util.Set;

/**
 * The Class PageDefination.
 */
public class TemplatePage implements Serializable, Comparable<TemplatePage> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8014143912442929809L;

	/** The template. */
	private String template;

	/** The priority. */
	private int priority = 1;

	/** The page name. */
	private Set<String> pageNames;

	/**
	 * Gets the template.
	 * 
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * Sets the template.
	 * 
	 * @param template
	 *            the new template
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * Gets the page names.
	 * 
	 * @return the page names
	 */
	public Set<String> getPageNames() {
		return pageNames;
	}

	/**
	 * Sets the page names.
	 * 
	 * @param pageNames
	 *            the new page names
	 */
	public void setPageNames(Set<String> pageNames) {
		this.pageNames = pageNames;
	}

	/**
	 * Gets the priority.
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 * 
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(TemplatePage tp) {
		if (tp == null) {
			return -1;
		}
		if (tp.getPriority() > this.getPriority()) {
			return 1;
		} else if (tp.getPriority() == this.getPriority()) {
			return 0;
		} else {
			return -1;
		}
	}

}
