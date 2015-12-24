package com.legendshop.core.randing;

import com.legendshop.util.ContextServiceLocator;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 按照官方的做法： 一定为单例
 * 
 * @author Administrator
 * 
 */
public class CaptchaServiceSingleton {
	private static ImageCaptchaService instance;

	// private static ImageCaptchaService instance=new
	// DefaultManageableImageCaptchaService();
	public static ImageCaptchaService getInstance() {
		if (instance == null) {

			CaptchaEngine engine = (CaptchaEngine) ContextServiceLocator.getInstance().getBean("imageEngine");
			instance = new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(), engine, 180, 100000, 75000);
		}
		return instance;
	}
}