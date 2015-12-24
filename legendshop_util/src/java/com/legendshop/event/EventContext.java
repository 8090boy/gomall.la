/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class EventContext.
 */
public class EventContext extends HashMap<String, Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5320624271576847671L;

	/** The request id. */
	private final String requestId = "reqeust";

	/** The response id. */
	private final String responseId = "response";

	/** The http request. */
	private HttpServletRequest httpRequest;

	/**
	 * Instantiates a new event context.
	 */
	public EventContext() {
		//
	}

	/**
	 * Instantiates a new event context.
	 * 
	 * @param httpRequest
	 *            the http request
	 */
	public EventContext(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	/**
	 * Gets the request.
	 * 
	 * @return the request
	 */
	public Object getRequest() {
		return this.get(requestId);
	}

	/**
	 * Sets the request.
	 * 
	 * @param request
	 *            the new request
	 */
	public void setRequest(Object request) {
		this.put(requestId, request);
	}

	/**
	 * Gets the response id.
	 * 
	 * @return the response id
	 */
	public Object getResponse() {
		return this.get(responseId);
	}

	/**
	 * Gets the boolean response.
	 * 
	 * @return the boolean response
	 */
	public Boolean getBooleanResponse() {
		Boolean result = (Boolean) this.get(responseId);
		if (result == null) {
			result = true;
		}
		return result;
	}

	public Boolean getBooleanResponse(boolean defaultValue) {
		Boolean result = (Boolean) this.get(responseId);
		if (result == null) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * Sets the response.
	 * 
	 * @param response
	 *            the new response
	 */
	public void setResponse(Object response) {
		this.put(responseId, response);
	}

	/**
	 * Gets the http request.
	 * 
	 * @return the http request
	 */
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	/**
	 * Sets the http request.
	 * 
	 * @param httpRequest
	 *            the new http request
	 */
	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

}
