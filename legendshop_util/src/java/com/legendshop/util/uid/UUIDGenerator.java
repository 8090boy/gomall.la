/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.uid;

import java.net.InetAddress;

/**
 * 生成UUID字符串
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class UUIDGenerator {

	/** The Constant IP. */
	private static final int IP;
	static {
		int ipadd;
		try {
			byte[] ip = InetAddress.getLocalHost().getAddress();
			ipadd = ((((int) ip[0]) << 24) & 0xFF000000) | ((((int) ip[1]) << 16) & 0x00FF0000)
					| ((((int) ip[2]) << 8) & 0x0000FF00) | (((int) ip[3]) & 0x000000FF);
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	/** The counter. */
	private static short counter = (short) 0;

	/** The Constant JVM. */
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	/**
	 * Gets the hi time.
	 * 
	 * @return the hi time
	 */
	protected static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	/**
	 * Gets the lo time.
	 * 
	 * @return the lo time
	 */
	protected static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	/**
	 * Format.
	 * 
	 * @param intval
	 *            the intval
	 * @return the string
	 */
	protected static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	/**
	 * Format.
	 * 
	 * @param shortval
	 *            the shortval
	 * @return the string
	 */
	protected static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/**
	 * 产生一个32个字符长的UUID.
	 * 
	 * @return the string
	 */
	public static synchronized String generate() {
		return new StringBuffer(20).append(format(IP)).append(format(JVM)).append(format(getHiTime())).append(format(getLoTime()))
				.append(format(counter++)).toString();

	}

	/**
	 * Generate8 hex.
	 * 
	 * @return the string
	 */
	public static synchronized String generate8Hex() {
		return format(getLoTime());
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(UUIDGenerator.generate());
		System.out.println(UUIDGenerator.generate8Hex());
	}
}
