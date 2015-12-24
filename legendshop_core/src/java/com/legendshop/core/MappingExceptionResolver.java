/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core;

import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.legendshop.core.exception.BaseException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.model.UserMessages;
import com.legendshop.util.AppUtils;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class MappingExceptionResolver extends SimpleMappingExceptionResolver {

	/** The exception code mappings. */
	private Map<String, String> exceptionCodeMappings = new HashMap<String, String>();

	/**
	 * Actually resolve the given exception that got thrown during on handler
	 * execution, returning a ModelAndView that represents a specific error page
	 * if appropriate.
	 * <p>
	 * May be overridden in subclasses, in order to apply specific exception
	 * checks. Note that this template method will be invoked <i>after</i>
	 * checking whether this resolved applies ("mappedHandlers" etc), so an
	 * implementation may simply proceed with its actual exception handling.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @param handler
	 *            the executed handler, or <code>null</code> if none chosen at
	 *            the time of the exception (for example, if multipart
	 *            resolution failed)
	 * @param ex
	 *            the exception that got thrown during handler execution
	 * @return a corresponding ModelAndView to forward to, or <code>null</code>
	 *         for default processing
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			logger.error("Error : ", ex);
			request.setAttribute("ERROR_MESSAGE", printErrorMessage(ex));
			//异常mapping
			parseErrorMessages(request, ex);

			return getModelAndView(viewName, ex, request);
		} else {
			return null;
		}
	}

	private String printErrorMessage(Exception ex) {
		Throwable throwable = ex;
		StringBuffer sb = new StringBuffer(throwable.toString()).append("\n");
		printErrorMessage(ex, sb);
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
			sb.append(throwable.toString()).append("\n");
			printErrorMessage(throwable, sb);
		}
		return sb.toString();
	}

	/**
	 * @param ex
	 * @param sb
	 */
	private void printErrorMessage(Throwable ex, StringBuffer sb) {
		StackTraceElement[] traceElements = ex.getStackTrace();
		if (AppUtils.isNotBlank(traceElements)) {
			for (StackTraceElement stackTraceElement : traceElements) {
				sb.append(stackTraceElement.toString()).append("\n");
			}
		}
	}

	/**
	 * Parses the error messages.
	 * 
	 * @param request
	 *            the request
	 * @param ex
	 *            the ex
	 */
	private void parseErrorMessages(HttpServletRequest request, Exception ex) {
		UserMessages uem = new UserMessages();
		if (ex instanceof BaseException) {
			BaseException be = (BaseException) ex;
			uem.setDesc(be.getDesc());
			uem.setTitle(be.getTitle());
			uem.setCode(be.getErrorCode());
		} else if (ex != null && exceptionCodeMappings.get(ex.getClass().getName()) != null) {
			String errorCode = exceptionCodeMappings.get(ex.getClass().getName());
			getExceptionMessage(uem, ex,errorCode);
		}
		
		request.setAttribute(UserMessages.MESSAGE_KEY, uem);
	}

	/**
	 * Gets the exception message.
	 * 
	 * @param e
	 *            the e
	 * @return the exception message
	 */
	public void getExceptionMessage(UserMessages uem, Exception e,String errorCode ) {
		Throwable throwable = e;
		if (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}
		String title = ResourceBundleHelper.getString("error.code." + errorCode);
		if(AppUtils.isNotBlank(title)){
			uem.setTitle(title);
		}else{
			String errCode = null;
			if ((throwable instanceof ConstraintViolationException) || (throwable instanceof BatchUpdateException) || (throwable instanceof DataIntegrityViolationException)) {
				errCode = ErrorCodes.RESOURCE_CONFLICT;
			}else if (throwable instanceof NullPointerException) {
				errCode = ErrorCodes.ENTITY_NO_FOUND;
			}
			uem.setTitle(ResourceBundleHelper.getString("error.code." + errCode));
		}

		uem.setDesc(throwable.getMessage());
	}

	/**
	 * Sets the exception code mappings.
	 * 
	 * @param exceptionCodeMappings
	 *            the exception code mappings
	 */
	public void setExceptionCodeMappings(Map<String, String> exceptionCodeMappings) {
		this.exceptionCodeMappings = exceptionCodeMappings;
	}

}
