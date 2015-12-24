/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

import java.util.List;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public interface Processor {

	/**
	 * To be implemented by subclasses, ensures each Command configured in this
	 * process is supported. This method is called by the Processor for each
	 * Command configured. An implementing subclass should ensure the Command
	 * type passed in is supported. Implementors could possibly support multiple
	 * types of activities.
	 * 
	 * @param component
	 *            the component
	 * @return true if the activity is supported
	 */
	public boolean supports(Command component);

	/**
	 * Abstract method used to kickoff the processing of work flow activities.
	 * Each processor implementation should implement doActivities as a means of
	 * carrying out the activities wired to the workflow process. This version
	 * of doActivities is designed to be called from some external entity, e.g.
	 * listening a JMS queue. That external entitiy would proved the seed data.
	 * 
	 * @param request
	 *            the request
	 * @param resp
	 *            the resp
	 * @throws Exception
	 *             the exception
	 */
	public void doActivities(Request request, Response resp) throws Exception;

	/**
	 * Sets the collection of Activities to be executed by the Workflow Process.
	 * 
	 * @param activities
	 *            ordered collection (List) of activities to be executed by the
	 *            processor
	 */
	public void setActivities(List activities);

	/**
	 * Sets the error handler.
	 * 
	 * @param errorHandler
	 *            the new error handler
	 */
	public void setErrorHandler(ErrorHandler errorHandler);
}