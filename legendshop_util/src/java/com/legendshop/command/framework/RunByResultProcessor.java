/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class RunByResultProcessor extends BaseProcessor {

	/** The logger. */
	private static Logger logger = Logger.getLogger(RunByResultProcessor.class);

	/** The first activities. */
	private String firstActivities;

	/**
	 * Gets the first activities.
	 * 
	 * @return the first activities
	 */
	public String getFirstActivities() {
		return firstActivities;
	}

	/**
	 * Sets the first activities.
	 * 
	 * @param firstCommand
	 *            the new first activities
	 */
	public void setFirstActivities(String firstCommand) {
		this.firstActivities = firstCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.Processor#supports(com.legendshop.command
	 * .framework.Command)
	 */
	public boolean supports(Command component) {
		return (component instanceof AbstractCommand);
	}

	/**
	 * Listto map.
	 * 
	 * @param activities
	 *            the activities
	 * @return the map
	 */
	private Map listtoMap(List activities) {
		Map resulet = new HashMap();
		for (Iterator it = activities.iterator(); it.hasNext();) {
			Command component = (Command) it.next();
			String name = component.getBeanName();
			resulet.put(name, component);

		}
		return resulet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.Processor#doActivities(com.legendshop
	 * .command.framework.Request, com.legendshop.command.framework.Response)
	 */
	public void doActivities(Request request, Response resp) throws Exception {

		// if (logger.isDebugEnabled())
		logger.info("<-- RunByResultProcessor " + getBeanName());
		Map response = resp.getValues();
		Map params = request.getValues();
		// retrieve injected by Spring
		List activities = getActivities();
		Map activitiesMap = listtoMap(activities);
		String first = getFirstActivities();
		if (activitiesMap.size() != 0) {
			Command component = (Command) activitiesMap.get(first);
			try {
				logger.info("    AbstractCommand " + component.getBeanName());
				component.execute(params, response);
				String next = (String) resp.getValue("next");
				while (next != null && activitiesMap.containsKey(next)) {
					component = (Command) activitiesMap.get(next);
					logger.info("    AbstractCommand " + component.getBeanName());
					component.init("");
					component.execute(params, response);
					component.fini();
					next = (String) resp.getValue("next");
				}
			} catch (Throwable th) {
				ErrorHandler errorHandler = component.getErrorHandler();
				if (errorHandler == null) {
					logger.info("no error handler for AbstractCommand " + component.getBeanName()
							+ ", run processorerrorHandler and abort AbstractCommand ");
					ErrorHandler processorerrorHandler = getErrorHandler();
					if (processorerrorHandler == null) {
						logger.info("no error handler for this processor, run defaultErrorHandler and abort processor ");
						ErrorHandler defaultErrorHandler = ((ErrorHandler) beanFactory.getBean("defaultErrorHandler"));
						if (defaultErrorHandler != null)
							defaultErrorHandler.handleError(resp, th);
						else {
							logger.info("no default errorHandler for this invoke process, abort!!");
						}

					} else {
						logger.info("run processor errorHandler and continue");
						processorerrorHandler.handleError(resp, th);
					}
					// 由ErrorHandler判断需要不需要听下来;
				} else {
					logger.info("run command errorHandler and continue");
					errorHandler.handleError(resp, th);
				}
			}
		}
		logger.info("    RunByResultProcessor end -->");
	}

}