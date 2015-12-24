package com.legendshop.util.converter;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import com.legendshop.util.TimerUtil;
import com.legendshop.util.des.DES2;
public class ByteConverter {

	/**
	 * String to hex string.
	 * 
	 * @param strPart
	 *            the str part
	 * @return the string
	 */
	public static String stringToHexString(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			hexString = hexString + strHex;
		}
		return hexString;
	}

	/** The HE x_ string. */
	private static String HEX_STRING = "0123456789ABCDEF";

	/**
	 * Encode.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(HEX_STRING.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(HEX_STRING.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * Decode.
	 * 
	 * @param bytes
	 *            the bytes
	 * @return the string
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((HEX_STRING.indexOf(bytes.charAt(i)) << 4 | HEX_STRING.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * Encode.
	 * 
	 * @param str
	 *            the str
	 * @param pos
	 *            the pos
	 * @return the string
	 */
	public static String encode(String str, int pos) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(HEX_STRING.charAt((bytes[i] & 0xf0) >> pos));
			sb.append(HEX_STRING.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * Decode.
	 * 
	 * @param bytes
	 *            the bytes
	 * @param pos
	 *            the pos
	 * @return the string
	 */
	public static String decode(String bytes, int pos) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((HEX_STRING.indexOf(bytes.charAt(i)) << pos | HEX_STRING.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * Unite bytes.
	 * 
	 * @param src0
	 *            the src0
	 * @param src1
	 *            the src1
	 * @return the byte
	 */
	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}

	/**
	 * Hex string2 bytes.
	 * 
	 * @param src
	 *            the src
	 * @return the byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[6];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 6; ++i) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * convert the hex char string to byte array.
	 * 
	 * @param hexString
	 *            the hex string
	 * @return the byte[]
	 * @throws IllegalArgumentException
	 *             if the hex string contains none hex char.
	 */
	public static byte[] hexStringToBytes(String hexString) throws IllegalArgumentException {
		int len = hexString.length();
		if (len % 2 > 0) {
			hexString = "0" + hexString;
			len++;
		}

		byte[] bytes = new byte[len / 2];

		char[] chars = hexString.toUpperCase().toCharArray();
		for (int i = 0, j = 0; i < bytes.length; i++) {
			int indexHigh = HEX_STRING.indexOf(chars[j++]);
			if (indexHigh < 0) {
				throw new IllegalArgumentException(chars[j - 1] + " is not a hex char");
			}
			int indexLow = HEX_STRING.indexOf(chars[j++]);
			if (indexLow < 0) {
				throw new IllegalArgumentException(chars[j - 1] + " is not a hex char");
			}
			bytes[i] = (byte) ((indexHigh << 4) | indexLow);
		}

		return bytes;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		String s = "413779696E723974526D3944524A585A613348736165596331796D7A2B377967";
		DES2 des = new DES2();
		String s1 = new String(des.createDecryptor(des.stringToByte(ByteConverter.decode(s))));
		Date date = TimerUtil.strToDate(s1);
		System.out.println("date = " + date);
		System.out.println("s1 = " + s1);
		// System.out.println(decode(s));
		System.out.println(encode("AB"));
		System.out.println(decode(s));
		byte[] b = hexStringToBytes("4142");
		System.out.println("length = " + b.length);
		for (int i = 0; i < b.length; i++) {
			byte c = b[i];
			System.out.println(c);
		}

		String text = "1234";
		// String edcodeText = encode(encode(text,3),5);
		// System.out.println(encode(edcodeText));
		// System.out.println(decode(decode(edcodeText,5),3));

		String edword = encode(encode(text, 2), 6);
		System.out.println(edword);
		System.out.println(decode(decode(edword, 2), 6));
		/*
		 * String src = "srcHewq234234dsfsfxcvxcfgdfg234bngh vxchgjhkhkjkl";
		 * byte[] chBuf = src.getBytes(); // 以16进制输出文件内容, 每16个数换行一次 for(int i =
		 * 0; i < src.length(); i++) { if(i % 16 == 0) System.out.println();
		 * String strHex = new String(); strHex =
		 * Integer.toHexString(chBuf[i]).toUpperCase(); if(strHex.length() > 3)
		 * System.out.print(strHex.substring(6)); else if(strHex.length() < 2)
		 * System.out.print("0" + strHex); else System.out.print(strHex);
		 * 
		 * System.out.print(" "); }
		 */
	}
}
