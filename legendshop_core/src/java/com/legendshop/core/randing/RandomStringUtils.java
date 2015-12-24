/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.randing;

import java.util.Random;

import com.legendshop.util.des.DES2;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class RandomStringUtils {

	/** The Constant RANDOM. */
	private static final Random RANDOM = new Random();

	/**
	 * Instantiates a new random string utils.
	 */
	public RandomStringUtils() {
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String random(int count) {
		return random(count, false, false);
	}

	/**
	 * Random ascii.
	 * 
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomAscii(int count) {
		return random(count, 32, 127, false, false);
	}

	/**
	 * Random alphabetic.
	 * 
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomAlphabetic(int count) {
		return random(count, true, false);
	}

	/**
	 * Random alphanumeric.
	 * 
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomAlphanumeric(int count) {
		return random(count, true, true);
	}

	/**
	 * 通过session的传递会将前面的0给去掉，因此加上前缀.
	 * 
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomNumeric(int count) {
		return randomNumeric(count, 4);
	}

	/**
	 * Random numeric.
	 * 
	 * @param count
	 *            the count
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String randomNumeric(int count, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int val = RANDOM.nextInt(10);
			sb.append(String.valueOf(val));
		}
		return sb.toString();
	}

	/**
	 * Random letter.
	 * 
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomLetter(int count) {
		DES2 des = new DES2();
		String encryptorString = random(count, false, true);
		String desStr = "1234";
		try {
			desStr = des.byteToString(des.createEncryptor(encryptorString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desStr;
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @param letters
	 *            the letters
	 * @param numbers
	 *            the numbers
	 * @return the string
	 */
	public static String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param letters
	 *            the letters
	 * @param numbers
	 *            the numbers
	 * @return the string
	 */
	public static String random(int count, int start, int end, boolean letters, boolean numbers) {
		return random(count, start, end, letters, numbers, null);
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param letters
	 *            the letters
	 * @param numbers
	 *            the numbers
	 * @param set
	 *            the set
	 * @return the string
	 */
	public static String random(int count, int start, int end, boolean letters, boolean numbers, char set[]) {
		if (start == 0 && end == 0) {
			end = 122;
			start = 32;
			if (!letters && !numbers) {
				start = 0;
				end = 0x7fffffff;
			}
		}
		StringBuffer buffer = new StringBuffer();
		int gap = end - start;
		while (count-- != 0) {
			char ch;
			if (set == null) {
				ch = (char) (RANDOM.nextInt(gap) + start);
			} else {
				ch = set[RANDOM.nextInt(gap) + start];
			}
			if (letters && numbers && Character.isLetterOrDigit(ch) || letters && Character.isLetter(ch) || numbers
					&& Character.isDigit(ch) || !letters && !numbers) {
				buffer.append(ch);
			} else {
				count++;
			}
		}
		return buffer.toString();
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @param set
	 *            the set
	 * @return the string
	 */
	public static String random(int count, String set) {
		return random(count, set.toCharArray());
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @param set
	 *            the set
	 * @return the string
	 */
	public static String random(int count, char set[]) {
		return random(count, 0, set.length - 1, false, false, set);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(randomNumeric(3, 6));
	}
}
