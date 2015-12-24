package com.legendshop.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractHandler implements Handler {

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {

	}

}
