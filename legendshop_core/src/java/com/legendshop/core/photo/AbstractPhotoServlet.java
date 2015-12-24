/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.photo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.helper.PropertiesUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 显示缩略图，如果缩略图不在，则先去找到对应的大图，再生成缩略图并显示
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * ----- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ------------
 * ---------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public abstract class AbstractPhotoServlet extends HttpServlet {

	private static final long serialVersionUID = -2064158852379937245L;

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(AbstractPhotoServlet.class);

	/** The N o_ fil e_ replacement. */
	protected static String NO_FILE_REPLACEMENT = "images/blank.png";

	/**
	 * Out logo.
	 * 
	 * @param file
	 *            the file
	 * @param url
	 *            the url
	 * @param out
	 *            the out
	 * @param dwidth
	 *            the dwidth
	 * @param dheight
	 *            the dheight
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public synchronized void outLogo(File file, String url, OutputStream out, int dwidth, int dheight) throws IOException {
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file), 4096);
		Image src = javax.imageio.ImageIO.read(stream);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		int towidth, toheight;
		if (width > dwidth || height > dheight) {
			if (((float) width / dwidth) >= ((float) height / dheight)) {
				towidth = dwidth;
				//toheight = (height * dwidth) / width;
				toheight =dheight;
			} else {
				toheight = dheight;
				//towidth = (width * dheight) / height;
				towidth = dwidth;
			}
		} else {
			towidth = width;
			toheight = height;
		}
		BufferedImage tag = new BufferedImage(towidth, toheight, BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(src, 0, 0, towidth, toheight, null);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

		encoder.encode(tag);
		stream.close();
		out.flush();
		out.close();

		if (url != null) {
			log.debug("generate small image url  {}", url);
			File parentPath = new File(url.substring(0, url.lastIndexOf("/")));
			if (!parentPath.exists()) {
				parentPath.mkdirs();
			}
			FileOutputStream newimage = null;
			try {
				newimage = new FileOutputStream(url); // 输出到文件流
				JPEGImageEncoder encoder1 = JPEGCodec.createJPEGEncoder(newimage);
				encoder1.encode(tag);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (newimage != null)
					newimage.close();
			}

		}

		src = null;
	}

	/**
	 * Output file.
	 * 
	 * @param response
	 *            the response
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void outputFile(HttpServletResponse response, File file) throws IOException {
		response.setContentType("image/gif");
		// Date date = new Date();
		// response.setDateHeader("Expires", date.getTime() + 86400 * 1000L);
		response.setContentLength((int) file.length());
		javax.servlet.ServletOutputStream servletoutputstream = response.getOutputStream();
		try {
			dumpFile(file, servletoutputstream);
		} catch (Exception e) {
			log.error("outputFile: {}", e.getLocalizedMessage());
		} finally {
			servletoutputstream.close();
		}
	}

	/**
	 * Dump file.
	 * 
	 * @param file
	 *            the file
	 * @param outputstream
	 *            the outputstream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void dumpFile(File file, OutputStream outputstream) throws IOException {
		byte[] image = new byte[4096];
		BufferedInputStream bufferedinputstream = null;
		try {
			bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
			int i;
			while ((i = bufferedinputstream.read(image, 0, 4096)) != -1) {
				outputstream.write(image, 0, i);
			}
		} catch (Exception e) {
			log.error("dumpFile: {}", e.getLocalizedMessage());
		} finally {
			if (outputstream != null) {
				outputstream.close();
			}
			if (bufferedinputstream != null) {
				bufferedinputstream.close();
			}
		}
	}

	/**
	 * No file error.
	 * 
	 * @param response
	 *            the response
	 * @param pathName
	 *            the path name
	 */
	protected void noFileError(HttpServletResponse response, String pathName) {
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
			log.error("noFileError", e);
		} finally {
			if (printwriter != null) {
				printwriter.flush();
				printwriter.close();
			}
		}
		log.error("there is no image file named {}", pathName);
	}

}
