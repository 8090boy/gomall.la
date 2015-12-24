/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.uid;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class UID {

	/** The last time. */
	private static long lastTime = System.currentTimeMillis();

	/** The last count. */
	private static short lastCount = -32768;

	/** The mutex. */
	private static Object mutex = new Object();

	/** The ON e_ second. */
	private static long ONE_SECOND = 1000L;

	/**
	 * Instantiates a new uID.
	 */
	public UID() {
	}

	/**
	 * Gets the uID.
	 * 
	 * @return the uID
	 */
	public static String getUID() {
		long l = 0L;
		short word0 = 0;
		int i = 0;
		synchronized (mutex) {
			if (lastCount == 32767) {
				for (boolean flag = false; !flag;) {
					l = System.currentTimeMillis();
					if (l < lastTime + ONE_SECOND) {
						try {
							Thread.currentThread();
							Thread.sleep(ONE_SECOND);
						} catch (InterruptedException interruptedexception) {
						}
					} else {
						lastTime = l;
						lastCount = -32768;
						flag = true;
					}
				}

			} else {
				l = lastTime;
			}
			word0 = lastCount++;
			i = getHostUniqueNum();
		}
		String s = Integer.toString(i, 16) + "`" + Long.toString(l, 16) + "`" + Integer.toString(word0, 16);
		if (s.length() > 24)
			s = s.substring(s.length() - 24);
		return s;
	}

	/**
	 * Gets the host unique num.
	 * 
	 * @return the host unique num
	 */
	private static int getHostUniqueNum() {
		return (new Object()).hashCode();
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 100000000; i++) {
			String uid = getUID();
			System.out.println(i + "=" + uid);
		}
	}

}
