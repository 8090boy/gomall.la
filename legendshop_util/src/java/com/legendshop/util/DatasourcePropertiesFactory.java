/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for creating DatasourceProperties objects.
 */
public class DatasourcePropertiesFactory {

	/** The log. */
	protected final static Logger log = LoggerFactory.getLogger(DatasourcePropertiesFactory.class);

	/** The Constant PRODUCTION_MODE. */
	private static Boolean PRODUCTION_MODE = true;

	/** The Constant PROP_PASSWORD. */
	private static final String PROP_PASSWORD = "password";

	/** The Constant DEFAULT_SECURE_KEY. */
	private static String DEFAULT_SECURE_KEY = "LegendShop";

	/**
	 * Gets the properties.
	 * 
	 * @param pwd
	 *            the pwd
	 * @param production
	 *            the production
	 * @return the properties
	 * @throws Exception
	 *             the exception
	 */
	public static Properties getProperties(String pwd, Boolean production) throws Exception {
		return getProperties(pwd, production, null);
	}

	public static Properties getProperties(String pwd, Boolean production, String secureKey) throws Exception {
		log.debug("jdbc production {} , secureKey {}", new Object[] { production, secureKey });
		Properties p = new Properties();
		PRODUCTION_MODE = production;
		// production mode
		if (PRODUCTION_MODE) {
			try {
				if (AppUtils.isNotBlank(secureKey)) {
					DEFAULT_SECURE_KEY = secureKey;
				}
				p.setProperty(PROP_PASSWORD, decode(pwd));
			} catch (Exception e) {
				throw e;
			}
		} else {
			p.setProperty(PROP_PASSWORD, pwd);
		}
		return p;
	}

	// 以下两个方法参考于jboss的实现
	/**
	 * Encode.
	 * 
	 * @param secret
	 *            the secret
	 * @return the string
	 * @throws NamingException
	 *             the naming exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 */
	private static String encode(String secret) throws NamingException, NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
		System.out.println("加密前密码是 " + secret);
		if (PRODUCTION_MODE) {
			byte[] kbytes = DEFAULT_SECURE_KEY.getBytes();
			SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

			Cipher cipher;
			cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encoding = cipher.doFinal(secret.getBytes());
			BigInteger n = new BigInteger(encoding);
			return n.toString(16);
		} else {
			return secret;
		}

	}

	/**
	 * Decode.
	 * 
	 * @param secret
	 *            the secret
	 * @return the string
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 */
	public static String decode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		System.out.println("将要解密的密码是 " + secret);
		if (PRODUCTION_MODE) {
			byte[] kbytes = DEFAULT_SECURE_KEY.getBytes();
			SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

			BigInteger n = new BigInteger(secret, 16);
			byte[] encoding = n.toByteArray();
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decode = cipher.doFinal(encoding);
			return new String(decode);
		} else {
			return secret;
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws NamingException
	 *             the naming exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 */
	public static void main(String[] args) throws NamingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
		DatasourcePropertiesFactory.PRODUCTION_MODE = true;
		String secret = "root";
		String encoded = encode(secret);
		System.out.println("加密后密码是 " + encoded);
		System.out.println("还原的明文密码是 " + decode(encoded));
	}
}