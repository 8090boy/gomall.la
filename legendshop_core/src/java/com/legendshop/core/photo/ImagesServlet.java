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
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.util.ContextServiceLocator;

/**
 * 显示缩略图，如果缩略图不在，则先去找到对应的大图，再生成缩略图并显示
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * ----------------------------------------------------------------------------
 * ----- 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ------------
 * ----------------------------------------------------------------------
 * 
 * 官方网站：http://www.legendesign.net
 */
public class ImagesServlet extends AbstractPhotoServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -358253459242958398L;

	/** The log. */
	Logger log = LoggerFactory.getLogger(ImagesServlet.class);

	/** The real path. */
	private String realPath;// 缩略图位置

	/** The big path. */
	private String bigPath;// 大图位置

	/** The width. */
	//private int width = 256;

	/** The height. */
	//private int height = 188;
	
	private Map<String, List<Integer>> scaleList = null;
	
	//找到图片尺寸ID
	private int scaleStartPosition;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		//init scaleList
		scaleList =  (Map<String, List<Integer>>)ContextServiceLocator.getInstance().getBean("scaleList");
		if (realPath == null) {
			realPath = PropertiesUtil.getSmallFilesAbsolutePath();// 缩略图仓库
		}
		if (bigPath == null) {
			bigPath = PropertiesUtil.getBigFilesAbsolutePath();// 图片仓库
		}
		scaleStartPosition = PropertiesUtil.getSmallImagePathPrefix().length();
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		response.setDateHeader("expires", System.currentTimeMillis() + 1000 * 60);
		response.addHeader("Cache-Control", "max-age=60");
		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.length() < 1) {
			return;
		}

		String fullPath = realPath + pathInfo;
		File file = new File(fullPath);
		if (!file.isFile()) { // 文件不存在逻辑处理
			file = new File(bigPath + pathInfo.substring(2));
			if (file.isFile()) {
				// 1 展现，并生成缩略图 //2 写入缩略图文件系统
				List<Integer> scales = scaleList.get(parseScale(pathInfo));
				if(scales != null){
					//第一个是width， 第二个参数是height
					outLogo(file, fullPath, response.getOutputStream(), scales.get(0), scales.get(1));
				}else{
					noFileError(response, fullPath);
				}
			} else {
				noFileError(response, fullPath);
			}
		} else {// 显示缩略图逻辑
			outputFile(response, file);
		}

	}
	
	private String parseScale(String pathInfo){
		return pathInfo.substring(1, 2);
	}

	// Clean up resources
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {

	}


}