/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.randing;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.AttributeKeys;
import com.legendshop.util.MD5Util;
import com.legendshop.util.converter.ByteConverter;
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
public class ImageServlet extends HttpServlet {

	private boolean useSession = true;

	private final DES2 des = new DES2();

	/**
	 * Instantiates a new image servlet.
	 */
	public ImageServlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	// /* (non-Javadoc)
	// * @see
	// javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	// javax.servlet.http.HttpServletResponse)
	// */
	// @Override
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 输出图象到页面
		String sRand = request.getParameter("rand");
		request.getSession().setAttribute("randStr", sRand);
		try {
			if (sRand != null) {
				String aimRand = new String(des.createDecryptor(des.stringToByte(ByteConverter.decode(sRand))));
				// request.getSession().setAttribute("rand", aimRand);
				ImageIO.write(new Image().creatImage(aimRand), "JPEG", response.getOutputStream());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost_backup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 输出图象到页面
		generate(request, response);

	}

	private void generate(HttpServletRequest request, HttpServletResponse response) {
		String randNumber = RandomStringUtils.random(4, true, true);
		try {
			ImageIO.write(new Image().creatImage(randNumber), "JPEG", response.getOutputStream());
			if (useSession) {
				request.getSession().setAttribute(AttributeKeys.RANDNUMBER, MD5Util.toMD5(randNumber));
			} else {
				// 增加到Cookie
				Cookie cookie = new Cookie(AttributeKeys.RANDNUMBER, MD5Util.toMD5(randNumber));
				cookie.setPath("/");
				cookie.setMaxAge(-1); // -1为永不过期,或者指定过期时间
				response.addCookie(cookie);// 在退出登录操作中删除cookie.
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

	public void setUseSession(boolean useSession) {
		this.useSession = useSession;
	}

}
