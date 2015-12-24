/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

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
public class SequenceProcessor extends BaseProcessor {

	/** The logger. */
	private static Logger logger = Logger.getLogger(SequenceProcessor.class);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.Processor#doActivities(com.legendshop
	 * .command.framework.Request, com.legendshop.command.framework.Response)
	 */
	public void doActivities(Request request, Response resp) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("<-- SequenceProcessor start " + getBeanName());
		}

		Map response = resp.getValues();
		Map params = request.getValues();
		// retrieve injected by Spring
		List activities = getActivities();

		for (Iterator it = activities.iterator(); it.hasNext();) {
			Command component = (Command) it.next();
			if (logger.isDebugEnabled()) {
				logger.debug("    Command " + component.getBeanName());
			}
			try {
				component.init("");
				component.execute(params, response);
				component.fini();
			} catch (Throwable th) {
				ErrorHandler errorHandler = component.getErrorHandler();
				if (errorHandler == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("no Errorhandler for AbstractCommand " + component.getBeanName()
								+ ", run processor Errorhandler and abort AbstractCommand ");
					}
					ErrorHandler processorerrorHandler = getErrorHandler();
					if (processorerrorHandler == null) {
						if (logger.isDebugEnabled()) {
							logger.debug("no error handler for this processor, run defaultErrorHandler and abort processor ");
						}
						// 执行全局的default ErrorHandler;
						// ((ErrorHandler)ContextServiceLocator.getInstance().getBean("defaultErrorHandler")).handleError(resp,
						// th);
						ErrorHandler defaultErrorHandler = ((ErrorHandler) beanFactory.getBean("defaultErrorHandler"));
						if (defaultErrorHandler != null)
							defaultErrorHandler.handleError(resp, th);
						else {
							if (logger.isDebugEnabled()) {
								logger.debug("no default errorHandler for this invoke process, abort!!");
							}
						}
					} else {
						// 执行processor级的ErrorHandler;
						if (logger.isDebugEnabled()) {
							logger.debug("run processor errorHandler and continue");
						}
						processorerrorHandler.handleError(resp, th);
					}

				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("run AbstractCommand Errorhandler and continue");
					}
					// 执行Component级的ErrorHandler;
					errorHandler.handleError(resp, th);
				}
			}
			/*
			 * ensure its ok to continue the process
			 * 由于每次不ok都会抛出异常，都会中断当前流程，所以不用该代码。 if(!resp.getState().isOK())
			 * break;
			 */
		}
		if (logger.isDebugEnabled()) {
			logger.debug("    SequenceProcessor end -->");
		}
	}

}