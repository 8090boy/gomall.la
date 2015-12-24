/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.ip;

/**
 * 
 * 本级IP地址
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class LocalAddress {

	/** The ip. */
	public String ip;

	/** The host name. */
	public String hostName;

	/**
	 * Instantiates a new local address.
	 * 
	 * @param ip
	 *            the ip
	 * @param hostName
	 *            the host name
	 */
	public LocalAddress(String ip, String hostName) {
		this.ip = ip;
		this.hostName = hostName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer().append("ip:").append(this.ip).append(", hostname:").append(this.hostName).toString();
	}

	/**
	 * Gets the ip.
	 * 
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 * 
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the host name.
	 * 
	 * @return the host name
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Sets the host name.
	 * 
	 * @param hostName
	 *            the new host name
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}