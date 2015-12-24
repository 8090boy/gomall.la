package com.legendshop.command.framework;

public interface State extends java.io.Serializable {

	/** The OK. */
	public static String OK = "0";

	/**
	 * 返回业务方法运行时出错代码.
	 * 
	 * @return String
	 */
	public String getErrCode();

	/**
	 * 设置错误代码.
	 * 
	 * @param errCode
	 *            the new err code
	 */
	public void setErrCode(String errCode);

	/**
	 * 返回业务方法调用时抛出的异常.
	 * 
	 * @return Throwable
	 */
	public Throwable getThrowable();

	/**
	 * Sets the throwable.
	 * 
	 * @param throwable
	 *            the new throwable
	 */
	public void setThrowable(Throwable throwable);

	/**
	 * Checks if is oK.
	 * 
	 * @return boolean
	 */
	public boolean isOK();

}
