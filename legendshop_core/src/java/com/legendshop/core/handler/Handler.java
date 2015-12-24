package com.legendshop.core.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {

	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	/**
	 * This implementation is empty.
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception;

}
