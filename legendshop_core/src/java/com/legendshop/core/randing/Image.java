/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.randing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class Image {
	// 字符的位数
	/** The STRIN g_ length. */
	public final int STRING_LENGTH = 4;

	// public String sRand="";

	/**
	 * Gets the rand color.
	 * 
	 * @param fc
	 *            the fc
	 * @param bc
	 *            the bc
	 * @return the rand color
	 */
	public Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * Creat image.
	 * 
	 * @param sRand
	 *            the s rand
	 * @return the buffered image
	 */
	public BufferedImage creatImage(String sRand) {
		// 在内存中创建图象
		int width = 70, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(new Color(255, 244, 175));// (getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Arial", Font.ITALIC, 20));

		// 随机产生200条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 200; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(15);
			int yl = random.nextInt(15);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 产生随机数，包含大小写字母和数字
		// 修改此处可以支持数字，字母，或者字母数字组合
		// sRand = RandomStringUtils.randomNumeric(this.STRING_LENGTH);
		for (int i = 0; i < sRand.length(); i++) {
			String rand = String.valueOf(sRand.charAt(i));
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 15 * i + 6, 16);
		}
		// 图象生效
		g.dispose();
		return image;
	}
}
