/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Simple response wrapper that utilizes a local CharArrayWriter for output.
 */
public class CharResponseWrapper extends HttpServletResponseWrapper {

	/** The char writer. */
	protected CharArrayWriter charWriter;

	/** The writer. */
	protected PrintWriter writer;

	/** The get output stream called. */
	protected boolean getOutputStreamCalled;

	/** The get writer called. */
	protected boolean getWriterCalled;

	/**
	 * Instantiates a new char response wrapper.
	 * 
	 * @param response
	 *            the response
	 */
	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		// Create the writer
		charWriter = new CharArrayWriter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	public ServletOutputStream getOutputStream() throws IOException {
		// Can't call getOutputStream if getWriter
		// has already been called
		if (getWriterCalled) {
			throw new IllegalStateException("getWriter already called");
		}
		getOutputStreamCalled = true;
		return super.getOutputStream();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return writer;
		}
		// Can't call getWriter if getOutputStream
		// has already been called
		if (getOutputStreamCalled) {
			throw new IllegalStateException("getOutputStream already called");
		}
		getWriterCalled = true;
		writer = new PrintWriter(charWriter);
		return writer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = null;
		// Only return a String if the writer was
		// used.
		if (writer != null) {
			s = charWriter.toString();
		}
		return s;
	}

	/**
	 * To char array.
	 * 
	 * @return the char[]
	 */
	public char[] toCharArray() {// 将响应数据以字符数组返回
		return (charWriter.toCharArray());
	}

}