/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.uid;

import java.util.Random;

/**
 * 从20个数字（1-20）中选出6位不重复的数字，空间和时间的要求最好
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class RandomNumber {
	// 一共有几个位数
	/** The total. */
	private final int total;

	// 挑选出其中几个数据
	/** The number. */
	private final int number;

	/** The datas. */
	private final int[] datas;

	/** The random. */
	Random random = new Random();

	/**
	 * Instantiates a new random number.
	 * 
	 * @param total
	 *            the total
	 * @param number
	 *            the number
	 */
	public RandomNumber(int total, int number) {
		this.total = total;
		this.number = number;
		datas = new int[this.total];
		for (int i = 0; i < total; i++) {
			datas[i] = i;
		}
	}

	/**
	 * Gets the ramdom number.
	 * 
	 * @return the ramdom number
	 */
	public int[] getRamdomNumber() {
		int totalNumber = total - 1;
		for (int i = 0; i < number; i++) {
			int pos = random.nextInt(totalNumber);
			swap(pos, totalNumber);
			totalNumber--;
		}
		int[] result = new int[number];
		for (int i = 0; i < result.length; i++) {
			result[i] = datas[total - 1 - i];
		}
		return result;
	}

	/**
	 * 将i和j位置的值交换.
	 * 
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 */
	public void swap(int i, int j) {
		int temp = datas[i];
		datas[i] = datas[j];
		datas[j] = temp;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		// Random r =new Random();
		// for (int i = 0; i < 20; i++) {
		// System.out.println(r.nextInt(1));
		// }
		RandomNumber rn = new RandomNumber(20, 6);
		for (int i = 0; i < 10; i++) {
			int[] result = rn.getRamdomNumber();
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < result.length; j++) {
				sb.append(result[j]).append(",");
			}
			System.out.println(sb.toString());
		}
	}

}
