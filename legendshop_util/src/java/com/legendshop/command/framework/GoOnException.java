/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * The Class GoOnException.
 */
public class GoOnException extends Exception {
	/*
	 * keep the last exception information in this variable
	 */
	/** The exception cause. */
	private Throwable exceptionCause = null;

	/** The error code. */
	private String errorCode;

	/** The Constant defaultCode. */
	public final static String defaultCode = "1";

	/** The Constant sameAsLocal. */
	public final static String sameAsLocal = "-1";

	/**
	 * Instantiates a new go on exception.
	 */
	public GoOnException() {
		super();
		this.errorCode = defaultCode;
	}

	/**
	 * Instantiates a new go on exception.
	 * 
	 * @param errorCode
	 *            the error code
	 * @param pExceptionMsg
	 *            the exception msg
	 */
	public GoOnException(String errorCode, String pExceptionMsg) {
		super(pExceptionMsg);
		this.errorCode = errorCode;
		if (errorCode == null) {
			this.errorCode = defaultCode;
		}
	}

	/**
	 * Instantiates a new go on exception.
	 * 
	 * @param errorCode
	 *            the error code
	 * @param pExceptionMsg
	 *            the exception msg
	 * @param pException
	 *            the exception
	 */
	public GoOnException(String errorCode, String pExceptionMsg, Throwable pException) {
		super(pExceptionMsg);
		this.exceptionCause = pException;
		this.errorCode = errorCode;
		if (errorCode == null) {
			this.errorCode = defaultCode;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace() {
		super.printStackTrace();
		if (exceptionCause != null) {
			System.err.println("An exception has been caused by: ");
			exceptionCause.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		if (exceptionCause != null) {
			s.println("An exception has been caused by: ");
			exceptionCause.printStackTrace(s);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
		if (exceptionCause != null) {
			s.println("An exception has been caused by: ");
			exceptionCause.printStackTrace(s);
		}
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}
}
