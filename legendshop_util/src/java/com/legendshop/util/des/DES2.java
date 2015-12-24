/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.des;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.legendshop.util.converter.ByteConverter;

/**
 * DES加密的，文件中共有两个方法,加密、解密.
 */
public class DES2 {

	/** The Algorithm. */
	private final String Algorithm = "DES";

	/** The keygen. */
	private KeyGenerator keygen;

	/** The deskey. */
	private SecretKey deskey;

	/** The cipher. */
	private Cipher cipher;

	/** The encryptor data. */
	private byte[] encryptorData;

	/** The decryptor data. */
	private byte[] decryptorData;

	/** The key string. */
	private String keyString = "12345678";

	/**
	 * 初始化 DES 实例.
	 * 
	 * @param keyString
	 *            the key string
	 */
	public DES2(String keyString) {
		if (keyString != null) {
			init(keyString);
		} else {
			init(keyString);
		}
	}

	/**
	 * Instantiates a new dE s2.
	 */
	public DES2() {
		init(keyString);
	}

	/**
	 * DES加密的，文件中共有两个方法,加密、解密.
	 * 
	 * @param keyString
	 *            the key string
	 */
	public void init(String keyString) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			keygen = KeyGenerator.getInstance(Algorithm);
			byte key[] = keyString.getBytes();
			deskey = new SecretKeySpec(key, "DES");
			// deskey = keygen.generateKey();
			// for(int i=0;i<deskey.getEncoded().length;i++){
			// System.out.println(deskey.getEncoded()[i]);
			// }
			cipher = Cipher.getInstance(Algorithm);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 对 byte[] 进行加密.
	 * 
	 * @param datasource
	 *            要加密的数据
	 * @return 返回加密后的 byte 数组
	 */
	public byte[] createEncryptor(byte[] datasource) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
			encryptorData = cipher.doFinal(datasource);
		} catch (java.security.InvalidKeyException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.BadPaddingException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException ex) {
			ex.printStackTrace();
		}
		return encryptorData;
	}

	/**
	 * 将字符串加密.
	 * 
	 * @param datasource
	 *            the datasource
	 * @return the byte[]
	 */
	public byte[] createEncryptor(String datasource) {
		return createEncryptor(datasource.getBytes());
	}

	/**
	 * 对 datasource 数组进行解密.
	 * 
	 * @param datasource
	 *            要解密的数据
	 * @return 返回加密后的 byte[]
	 */
	public byte[] createDecryptor(byte[] datasource) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			decryptorData = cipher.doFinal(datasource);
		} catch (java.security.InvalidKeyException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.BadPaddingException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException ex) {
			ex.printStackTrace();
		}
		return decryptorData;
	}

	/**
	 * 将 DES 加密过的 byte数组转换为字符串.
	 * 
	 * @param dataByte
	 *            the data byte
	 * @return the string
	 */
	public String byteToString(byte[] dataByte) {
		String returnStr = null;
		BASE64Encoder be = new BASE64Encoder();
		returnStr = be.encode(dataByte);
		return returnStr;
	}

	/**
	 * 将字符串转换为DES算法可以解密的byte数组.
	 * 
	 * @param datasource
	 *            the datasource
	 * @return the byte[]
	 */
	public byte[] stringToByte(String datasource) {
		BASE64Decoder bd = new BASE64Decoder();
		byte[] sorData = null;
		try {
			sorData = bd.decodeBuffer(datasource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sorData;
	}

	/**
	 * 输出 byte数组.
	 * 
	 * @param data
	 *            the data
	 */
	public void printByte(byte[] data) {
		System.out.println("*********开始输出字节流**********");
		System.out.println("字节流: " + data.toString());
		for (int i = 0; i < data.length; i++) {
			System.out.println("第 " + i + "字节为：" + data[i]);
		}
		System.out.println("*********结束输出字节流**********");
	}

	/*
	 * public static void main(String args[])throws Exception { DES2 des = new
	 * DES2(); //加密源数据 String encryptorString = "ABCd";
	 * System.out.println("加密前的数据："+encryptorString);
	 * 
	 * //加密获得的byte数组 byte[] encryptorByte =
	 * des.createEncryptor(encryptorString);
	 * 
	 * //加密后的byte[] 转换来的字符串 String byteToString =
	 * des.byteToString(encryptorByte);
	 * 
	 * // System.out.println("加密后的byte[]"); // des.printByte(encryptorByte);
	 * System.out.println("加密后的数据:"+byteToString); /*
	 * 
	 * 
	 * String decryptorString = null;
	 * 
	 * //将byteToString转换为原来的byte[] byte[] stringToByte =
	 * des.stringToByte(byteToString); //将stringToByte解密后的byte[]
	 * byte[]decryptorByte = des.createDecryptor(stringToByte);
	 * System.out.println("解密后"+des.byteToString(decryptorByte));
	 * //解密后的byte[]转换为原来的字符串 decryptorString = new String(decryptorByte);
	 * 
	 * System.out.println("解密前的数据："+byteToString);
	 * System.out.println("转换来的解密的byte[]"); des.printByte(stringToByte);
	 * System.out.println("解密后的数据："+decryptorString); }
	 */
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		DES2 des = new DES2("ABCDEFGH");
		des.test(des);
	}

	/**
	 * Test.
	 * 
	 * @param des
	 *            the des
	 * @throws Exception
	 *             the exception
	 */
	public void test(DES2 des) throws Exception {
		// 加密源数据
		String encryptorString = "gdfndjfjgdfjgoiu3i4ou234uisifsoipfhdf好地方";
		System.out.println("加密前的数据：" + encryptorString);

		// 加密后的byte[] 转换来的字符串
		String byteToString = des.byteToString(des.createEncryptor(encryptorString));
		System.out.println("加密后的数据:" + byteToString);

		String aimStr = ByteConverter.encode(byteToString);
		System.out.println("加密后的16进制编码 :" + aimStr);
		String decodeAim = ByteConverter.decode(aimStr);
		System.out.println("解密后的16进制编码 :" + decodeAim);
		String decryptorString = new String(des.createDecryptor(des.stringToByte(decodeAim)));

		System.out.println("解密后的数据：" + decryptorString);
	}

	/**
	 * Gets the key string.
	 * 
	 * @return the key string
	 */
	public String getKeyString() {
		return keyString;
	}

	/**
	 * Sets the key string.
	 * 
	 * @param keyString
	 *            the new key string
	 */
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

}
