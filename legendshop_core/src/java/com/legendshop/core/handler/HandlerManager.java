/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class HandlerManager.
 */
public class HandlerManager {

	/** The handlers. */
	private List<Handler> handlers;

	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		for (Handler h : handlers) {
			h.handle(request, response);
		}
	}

	/**
	 * Gets the handlers.
	 * 
	 * @return the handlers
	 */
	public List<Handler> getHandlers() {
		return handlers;
	}

	/**
	 * Sets the handlers.
	 * 
	 * @param handlers
	 *            the new handlers
	 */
	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}
}
