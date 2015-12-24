/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

import com.legendshop.command.framework.ErrorCode;
import com.legendshop.command.framework.JCFException;
import com.legendshop.command.framework.Response;
import com.legendshop.command.framework.State;
import com.legendshop.util.AppUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class DelegateUtil {

	/** The logger. */
	private static Logger logger = Logger.getLogger(DelegateUtil.class);

	/**
	 * Instantiates a new delegate util.
	 */
	public DelegateUtil() {
	}

	/**
	 * Checks if is null param.
	 * 
	 * @param paramObject
	 *            the param object
	 * @param paramName
	 *            the param name
	 * @param state
	 *            the state
	 * @return true, if is null param
	 */
	public static boolean isNullParam(Object paramObject, String paramName, State state) {
		if (paramObject == null) {
			logger.error("Parameter " + paramName + " is null;");
			state.setErrCode(ErrorCode.PARAMETER_ERROR);
			return true;
		}
		return false;
	}

	/**
	 * Checks if is null or empty string.
	 * 
	 * @param str
	 *            the str
	 * @param paramName
	 *            the param name
	 * @param state
	 *            the state
	 * @return true, if is null or empty string
	 */
	public static boolean isNullOrEmptyString(String str, String paramName, State state) {
		if (str == null || str.length() == 0) {
			logger.error("String Parameter " + paramName + " is null or empty;");
			state.setErrCode(ErrorCode.PARAMETER_ERROR);
			return true;
		}
		return false;
	}

	/**
	 * Gets the next command.
	 * 
	 * @param nextCommand
	 *            the next command
	 * @param commandName
	 *            the command name
	 * @return the next command
	 */
	public int getNextCommand(String nextCommand, String[] commandName) {
		int j = -1;
		for (int i = 0; i < commandName.length; i++) {
			if (nextCommand.equals(commandName[i])) {
				j = i;
				break;
			}
		}
		if (!checkRange(j, commandName))
			j = -1;
		return j;
	}

	/**
	 * Check range.
	 * 
	 * @param nextCommand
	 *            the next command
	 * @param commandName
	 *            the command name
	 * @return true, if successful
	 */
	public boolean checkRange(int nextCommand, String[] commandName) {
		return (nextCommand <= (commandName.length) && nextCommand >= 0);
	}

	/**
	 * Check null.
	 * 
	 * @param o
	 *            the o
	 * @param msg
	 *            the msg
	 * @throws JCFException
	 *             the jCF exception
	 */
	public static void checkNull(Object o, String msg) throws JCFException {
		if (AppUtils.isBlank(o)) {
			logger.error(msg);
			throw new JCFException(ErrorCode.PARAMETER_ERROR, msg);
		}
	}

	/**
	 * Check null.
	 * 
	 * @param o
	 *            the o
	 * @param msg
	 *            the msg
	 * @throws JCFException
	 *             the jCF exception
	 */
	public static void checkNull(Object[] o, String msg) throws JCFException {
		if (!AppUtils.isBlank(o)) {
			for (int i = 0; i < o.length; i++) {
				if (AppUtils.isBlank(o[i])) {
					logger.error(o[i] + "    " + msg);
					throw new JCFException(ErrorCode.PARAMETER_ERROR, msg);
				}
			}
		} else {
			logger.error(o + "    " + msg);
			throw new JCFException(ErrorCode.PARAMETER_ERROR, msg);
		}
	}

	/**
	 * Handle exception.
	 * 
	 * @param e
	 *            the e
	 * @param methodName
	 *            the method name
	 * @param state
	 *            the state
	 */
	public static void handleException(Exception e, String methodName, State state) {
		String ErrorMsg;
		if (e instanceof RemoteException) {
			ErrorMsg = "RemoteException happened when " + methodName + " delegate called:";
			logger.error(ErrorMsg, e);
			if (state != null) {
				state.setThrowable(e);
				state.setErrCode(ErrorCode.REMOTE_ERROR);
			}
		} else if (e instanceof CreateException) {
			ErrorMsg = "CreateException(create ejb fail) happened when " + methodName + " delegate called:";
			logger.error(ErrorMsg, e);
			if (state != null) {
				state.setThrowable(e);
				state.setErrCode(ErrorCode.CALL_EJB_ERROR);
			}
		} else {
			ErrorMsg = "Exception happened when " + methodName + " delegate called:";
			logger.error(ErrorMsg, e);
			if (state != null) {
				state.setThrowable(e);
				state.setErrCode(ErrorCode.SYSTEM_ERROR);
			}
		}
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            the state
	 * @param resp
	 *            the resp
	 */
	public static void setState(State state, Response resp) {
		state.setErrCode(resp.getState().getErrCode());
		state.setThrowable(resp.getState().getThrowable());
	}

}
