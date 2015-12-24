package com.legendshop.core.randing;

import java.awt.Color;
import java.awt.Font;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class MyImageCaptchaEngine extends ListImageCaptchaEngine {
	protected void buildInitialFactories() {
		// 随机生成的字符
		WordGenerator wgen = new RandomWordGenerator("abcdefghijklmnopqrstuvwxyz123456789");
		RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(new int[] { 0, 100 }, new int[] { 0, 100 }, new int[] { 0,
				100 });
		// 文字显示的个数
		TextPaster textPaster = new RandomTextPaster(new Integer(4), new Integer(4), cgen, true);
		// 图片的大小
		// BackgroundGenerator backgroundGenerator = new
		// FunkyBackgroundGenerator(new Integer(80), new Integer(25));
		BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(new Integer(80), new Integer(25), Color.gray,
				Color.white);
		// 字体格式
		Font[] fontsList = new Font[] { new Font("Arial", 0, 10), new Font("Tahoma", 0, 10), new Font("Verdana", 0, 10), };
		// 文字的大小
		FontGenerator fontGenerator = new RandomFontGenerator(new Integer(15), new Integer(16), fontsList);

		WordToImage wordToImage = new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);

		this.addFactory(new GimpyFactory(wgen, wordToImage));
	}
}
