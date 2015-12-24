/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.exception;

import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.model.UserMessages;

/**
 * Exception基类.
 */
public abstract class BaseException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2848225058035251507L;

	/** The error code. */
	protected String errorCode;

	/** The user messages. */
	protected UserMessages userMessages;

	/**
	 * Instantiates a new base exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public BaseException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
		generateMessage(message);
	}

	/**
	 * Instantiates a new base exception.
	 *
	 * @param cause the cause
	 * @param message the message
	 * @param errorCode the error code
	 */
	public BaseException(Throwable cause, String message, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
		generateMessage(message);
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 * 
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the user messages.
	 * 
	 * @return the user messages
	 */
	public UserMessages getUserMessages() {
		return userMessages;
	}

	/**
	 * Sets the user messages.
	 * 
	 * @param userMessages
	 *            the new user messages
	 */
	public void setUserMessages(UserMessages userMessages) {
		this.userMessages = userMessages;
	}

	/**
	 * Instantiates a new base exception.
	 * 
	 * @param userMessages
	 *            the user messages
	 */
	public BaseException(UserMessages userMessages) {
		super(userMessages.getDesc());
		this.userMessages = userMessages;

	}
	
	/**
	 * 得到错误详细描述.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return this.getUserMessages().getDesc();
	}

	/**
	 * 得到错误标题.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.getUserMessages().getTitle();
	}

	/**
	 * Generate message.
	 *
	 * @param desc the desc
	 */
	private void generateMessage(String desc) {
		UserMessages uem = new UserMessages();
		uem.setCode(errorCode);
		uem.setTitle(ResourceBundleHelper.getString("error.code." + errorCode, "ERROR"));
		uem.setDesc(desc);
		setUserMessages(uem);
	}

}
