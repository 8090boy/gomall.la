/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.uid;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class UIDGenerator extends AbstractUUIDGenerator implements Configurable {

	/** The last time. */
	private static long lastTime = System.currentTimeMillis();

	/** The last count. */
	private static short lastCount = -32768;

	/** The mutex. */
	private static Object mutex = new Object();

	/** The ON e_ second. */
	private static long ONE_SECOND = 1000L;

	/** The sep. */
	private String sep = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.
	 * SessionImplementor, java.lang.Object)
	 */
	public Serializable generate(SessionImplementor session, Object obj) {
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
		String s = Integer.toString(i, 16) + sep + Long.toString(l, 16) + sep + Integer.toString(word0, 16);
		if (s.length() > 24)
			s = s.substring(s.length() - 24);
		return s;

	}

	/**
	 * Generate_old.
	 * 
	 * @param session
	 *            the session
	 * @param obj
	 *            the obj
	 * @return the serializable
	 */
	public Serializable generate_old(SessionImplementor session, Object obj) {
		String name = obj.getClass().getName();
		return new StringBuffer(64).append(name.substring(name.lastIndexOf('.') + 1)).append(sep).append((short) getIP())
				.append(sep).append(Math.abs((short) getJVM())).append(sep)
				// .append(getHiTime()).append(sep)
				// .append(getLoTime()).append(sep)
				.append(getCount()).toString();
	}

	/**
	 * Gets the host unique num.
	 * 
	 * @return the host unique num
	 */
	private static int getHostUniqueNum() {
		return (new Object()).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.id.Configurable#configure(org.hibernate.type.Type,
	 * java.util.Properties, org.hibernate.dialect.Dialect)
	 */
	public void configure(Type type, Properties params, Dialect d) {
		sep = PropertiesHelper.getString("separator", params, "");
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty("separator", "");
		IdentifierGenerator gen = new UIDGenerator();
		((Configurable) gen).configure(Hibernate.STRING, props, null);
		IdentifierGenerator gen2 = new UIDGenerator();
		((Configurable) gen2).configure(Hibernate.STRING, props, null);

		for (int i = 0; i < 10; i++) {
			String id = (String) gen.generate(null, gen);
			System.out.println(id);
			String id2 = (String) gen2.generate(null, gen2);
			System.out.println(id2);
		}

	}

}
