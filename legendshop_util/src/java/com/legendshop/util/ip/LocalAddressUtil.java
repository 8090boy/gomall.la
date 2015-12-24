/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * The Class LocalAddressUtil.
 */
public class LocalAddressUtil {
	// 获取本地IP和域名
	/**
	 * Gets the local address.
	 * 
	 * @return the local address
	 */
	public static LocalAddress getLocalAddress() {
		InetAddress ip = null;
		LocalAddress ipAddr = null;
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				// System.out.println(netInterface.getName());
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = addresses.nextElement();
					if (ip != null && !ip.isLoopbackAddress() && ip instanceof Inet4Address) {
						ipAddr = new LocalAddress(ip.getHostAddress(), ip.getHostName());
					}
				}
			}
		} catch (Exception e) {

		}
		return ipAddr;

	}

	// 是否是本地IP地址
	/**
	 * Checks if is inner ip.
	 * 
	 * @param ipAddress
	 *            the ip address
	 * @return true, if is inner ip
	 */
	public static boolean isInnerIP(String ipAddress) {
		boolean isInnerIp = false;
		long ipNum = getIpNum(ipAddress);
		/**
		 * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
		 * 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
		 */
		long aBegin = getIpNum("10.0.0.0");
		long aEnd = getIpNum("10.255.255.255");
		long bBegin = getIpNum("172.16.0.0");
		long bEnd = getIpNum("172.31.255.255");
		long cBegin = getIpNum("192.168.0.0");
		long cEnd = getIpNum("192.168.255.255");
		isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
				|| ipAddress.equals("127.0.0.1");
		return isInnerIp;
	}

	/**
	 * Gets the ip num.
	 * 
	 * @param ipAddress
	 *            the ip address
	 * @return the ip num
	 */
	private static long getIpNum(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);

		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}

	/**
	 * Checks if is inner.
	 * 
	 * @param userIp
	 *            the user ip
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @return true, if is inner
	 */
	private static boolean isInner(long userIp, long begin, long end) {
		return (userIp >= begin) && (userIp <= end);
	}
}
