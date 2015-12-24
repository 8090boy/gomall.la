/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.randing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 提供验证码图片的Servlet
 * 
 */
@SuppressWarnings("serial")
public class JcaptchaServlet extends HttpServlet {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	private ImageCaptchaService captchaService = null;

	@Override
	public void init() throws ServletException {
		WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		if(appCtx != null){
			captchaService = BeanFactoryUtils.beanOfTypeIncludingAncestors(appCtx, ImageCaptchaService.class);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// byte[] captchaChallengeAsJpeg = null;
		// // the output stream to render the captcha image as jpeg into
		// ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		// try {
		// // get the session id that will identify the generated captcha.
		// // the same id must be used to validate the response, the session id
		// // is a good candidate!
		//
		// String captchaId = request.getRequestedSessionId();
		// BufferedImage challenge = captchaService.getImageChallengeForID(
		// captchaId, request.getLocale());
		// // Jimi.putImage("image/jpeg", challenge, jpegOutputStream);
		// ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
		// } catch (IllegalArgumentException e) {
		// response.sendError(HttpServletResponse.SC_NOT_FOUND);
		// return;
		// } catch (CaptchaServiceException e) {
		// response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		// return;
		// }
		// // catch (JimiException e) {
		// // response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		// // return;
		// // }
		//
		// captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		//
		// // flush it in the response
		// response.setHeader("Cache-Control", "no-store");
		// response.setHeader("Pragma", "no-cache");
		// response.setDateHeader("Expires", 0);
		// response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);
		//
		// ServletOutputStream responseOutputStream =
		// response.getOutputStream();
		// responseOutputStream.write(captchaChallengeAsJpeg);
		// responseOutputStream.flush();
		// responseOutputStream.close();

		genernateCaptchaImage(request, response);
	}

	private void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId,
					request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
		} finally {
			out.close();
		}
	}
}
