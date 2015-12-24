package com.legendshop.core.helper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.legendshop.core.handler.Handler;

public class SystemInterceptor extends HandlerInterceptorAdapter {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(SystemInterceptor.class);

	private List<Handler> handlers;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (ThreadLocalContext.requestStarted()) {
			log.error("SystemInterceptorgetRequest  = " + ThreadLocalContext.getRequest());
			for (Handler handlerProcessor : handlers) {
				handlerProcessor.handle(request, response);
			}
		}
		return super.preHandle(request, response, handler);
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

}
