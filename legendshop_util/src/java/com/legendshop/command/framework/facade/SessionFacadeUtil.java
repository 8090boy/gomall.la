/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import javax.naming.NamingException;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class SessionFacadeUtil {
	/**
	 * Cached remote home (EJBHome). Uses lazy loading to obtain its value
	 * (loaded by getHome() methods).
	 */
	private static BizFacadeHome cachedRemoteHome = null;

	/**
	 * Lookup home.
	 * 
	 * @param environment
	 *            the environment
	 * @param jndiName
	 *            the jndi name
	 * @param narrowTo
	 *            the narrow to
	 * @return the object
	 * @throws NamingException
	 *             the naming exception
	 */
	private static Object lookupHome(java.util.Hashtable environment, String jndiName, Class narrowTo)
			throws javax.naming.NamingException {
		// Obtain initial context
		javax.naming.InitialContext initialContext = new javax.naming.InitialContext(environment);
		try {
			Object objRef = initialContext.lookup(jndiName);
			// only narrow if necessary
			if (java.rmi.Remote.class.isAssignableFrom(narrowTo))
				return javax.rmi.PortableRemoteObject.narrow(objRef, narrowTo);
			else
				return objRef;
		} finally {
			initialContext.close();
		}
	}

	// Home interface lookup methods

	/**
	 * Obtain remote home interface from default initial context.
	 * 
	 * @return Home interface for BizFacade. Lookup using COMP_NAME
	 * @throws NamingException
	 *             the naming exception
	 */
	public static BizFacadeHome getHome() throws javax.naming.NamingException {
		if (cachedRemoteHome == null) {
			cachedRemoteHome = (BizFacadeHome) lookupHome(null, BizFacadeHome.COMP_NAME, BizFacadeHome.class);
		}
		return cachedRemoteHome;
	}

	/**
	 * Obtain remote home interface from parameterised initial context.
	 * 
	 * @param environment
	 *            Parameters to use for creating initial context
	 * @return Home interface for BizFacade. Lookup using COMP_NAME
	 * @throws NamingException
	 *             the naming exception
	 */
	public static BizFacadeHome getHome(java.util.Hashtable environment) throws javax.naming.NamingException {
		return (BizFacadeHome) lookupHome(environment, BizFacadeHome.COMP_NAME, BizFacadeHome.class);
	}

	/** Cached per JVM server IP. */
	private static String hexServerIP = null;

	// initialise the secure random instance
	/** The Constant seeder. */
	private static final java.security.SecureRandom seeder = new java.security.SecureRandom();

	/**
	 * A 32 byte GUID generator (Globally Unique ID). These artificial keys
	 * SHOULD <strong>NOT </strong> be seen by the user, not even touched by the
	 * DBA but with very rare exceptions, just manipulated by the database and
	 * the programs.
	 * 
	 * Usage: Add an id field (type java.lang.String) to your EJB, and add
	 * setId(XXXUtil.generateGUID(this)); to the ejbCreate method.
	 * 
	 * @param o
	 *            the o
	 * @return the string
	 */
	public static final String generateGUID(Object o) {
		StringBuffer tmpBuffer = new StringBuffer(16);
		if (hexServerIP == null) {
			java.net.InetAddress localInetAddress = null;
			try {
				// get the inet address

				localInetAddress = java.net.InetAddress.getLocalHost();
			} catch (java.net.UnknownHostException uhe) {
				System.err.println("SessionFacadeUtil: Could not get the local IP address using InetAddress.getLocalHost()!");
				// todo: find better way to get around this...
				uhe.printStackTrace();
				return null;
			}
			byte serverIP[] = localInetAddress.getAddress();
			hexServerIP = hexFormat(getInt(serverIP), 8);
		}

		String hashcode = hexFormat(System.identityHashCode(o), 8);
		tmpBuffer.append(hexServerIP);
		tmpBuffer.append(hashcode);

		long timeNow = System.currentTimeMillis();
		int timeLow = (int) timeNow & 0xFFFFFFFF;
		int node = seeder.nextInt();

		StringBuffer guid = new StringBuffer(32);
		guid.append(hexFormat(timeLow, 8));
		guid.append(tmpBuffer.toString());
		guid.append(hexFormat(node, 8));
		return guid.toString();
	}

	/**
	 * Gets the int.
	 * 
	 * @param bytes
	 *            the bytes
	 * @return the int
	 */
	private static int getInt(byte bytes[]) {
		int i = 0;
		int j = 24;
		for (int k = 0; j >= 0; k++) {
			int l = bytes[k] & 0xff;
			i += l << j;
			j -= 8;
		}
		return i;
	}

	/**
	 * Hex format.
	 * 
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the string
	 */
	private static String hexFormat(int i, int j) {
		String s = Integer.toHexString(i);
		return padHex(s, j) + s;
	}

	/**
	 * Pad hex.
	 * 
	 * @param s
	 *            the s
	 * @param i
	 *            the i
	 * @return the string
	 */
	private static String padHex(String s, int i) {
		StringBuffer tmpBuffer = new StringBuffer();
		if (s.length() < i) {
			for (int j = 0; j < i - s.length(); j++) {
				tmpBuffer.append('0');
			}
		}
		return tmpBuffer.toString();
	}

}
