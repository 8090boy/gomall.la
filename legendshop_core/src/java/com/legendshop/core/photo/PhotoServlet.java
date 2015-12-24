/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.photo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.PropertiesUtil;

/**
 * 显示大图
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * ------ 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------
 * -------------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public class PhotoServlet extends AbstractPhotoServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7666260687183404653L;

	/** The log. */
	Logger log = LoggerFactory.getLogger(PhotoServlet.class);

	/** The real path. */
	private String realPath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		realPath = PropertiesUtil.getBigFilesAbsolutePath();
	}

	// Process the HTTP Get request
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		// 设置缓存有效时间为：60秒
		response.setDateHeader("expires", System.currentTimeMillis() + 1000 * 60);
		response.addHeader("Cache-Control", "max-age=60");

		String pathInfo = request.getPathInfo();
		String fullPath = realPath + pathInfo;
		try {
			File file = new File(fullPath);
			if (!file.isFile()) {
				file = new File(PropertiesUtil.getSystemRealPath() + NO_FILE_REPLACEMENT);
			}
			if (file.isFile()) {
				javax.servlet.ServletOutputStream servletoutputstream = null;
				try {
					response.setContentType("image/gif");
					// Date date = new Date();
					// response.setDateHeader("Expires", date.getTime() + 86400
					// * 1000L);
					response.setContentLength((int) file.length());
					servletoutputstream = response.getOutputStream();
					dumpFile(file, servletoutputstream);
				} catch (Exception e) {
					log.error("doGet: {}", e.getLocalizedMessage());
				} finally {
					if (servletoutputstream != null) {
						try {
							servletoutputstream.close();
						} catch (Exception e) {
							log.error("clos servletoutputstream: {}", e.getLocalizedMessage());
						}

					}
				}

			} else {
				PrintWriter printwriter = null;
				try {
					response.setContentType("text/html");
					printwriter = response.getWriter();
					printwriter.println("<html>");
					printwriter.println("<br><br>Could not get file name ");
					printwriter.println("<br><br>&copy; <a href=\"mailto:"
							+ PropertiesUtil.getObject(SysParameterEnum.SUPPORT_MAIL_LIST, String.class) + "\">LegendShop</a>");
					printwriter.println("</html>");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (printwriter != null) {
						printwriter.flush();
						printwriter.close();
					}
				}
				log.warn("there is no image file named {}", fullPath);
			}
		} catch (Exception e) {
			log.error("PhotoServlet doGet: {}", e.getLocalizedMessage());
		}

	}

	// Clean up resources
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		System.out.println("PicServlet destroying");
	}
}