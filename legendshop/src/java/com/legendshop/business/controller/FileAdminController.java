/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.spi.page.BackPage;
import com.legendshop.util.AppUtils;

/**
 * The Class EventController.
 */
@Controller
public class FileAdminController extends BaseController {

	/**
	 * 编辑注册协议.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/admin/system/file/register")
	public String register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parentFilePath = "/register";
		String fileName = "regItem.html";
		File file = new File(PropertiesUtil.getDownloadFilePath() + parentFilePath + "/" + fileName);
		String content = FileProcessor.readFile(file, true);
		request.setAttribute("content", content);
		request.setAttribute("parentFilePath", parentFilePath);
		request.setAttribute("fileName", fileName);
		request.setAttribute("title", "编辑注册协议");
		return PathResolver.getPath(request, response, BackPage.FILE_EDIT_PAGE);
	}

	/**
	 * 重置密码邮件内容.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	@RequestMapping("/admin/system/file/resetpassmail")
	public String resetpassmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parentFilePath = "/mail";
		String fileName = "resetpassmail.jsp";
		File file = new File(PropertiesUtil.getDownloadFilePath() + parentFilePath + "/" + fileName);
		String content = FileProcessor.readFile(file, true);
		request.setAttribute("content", content);
		request.setAttribute("parentFilePath", parentFilePath);
		request.setAttribute("fileName", fileName);
		request.setAttribute("title", "编辑重置密码");
		return PathResolver.getPath(request, response, BackPage.FILE_EDIT_PAGE);
	}

	/**
	 * Registersuccess. 注册成功邮件内容
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/admin/system/file/registersuccess")
	public String registersuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parentFilePath = "/mail";
		String fileName = "registersuccess.jsp";
		File file = new File(PropertiesUtil.getDownloadFilePath() + parentFilePath + "/" + fileName);
		String content = FileProcessor.readFile(file, true);
		request.setAttribute("content", content);
		request.setAttribute("parentFilePath", parentFilePath);
		request.setAttribute("fileName", fileName);
		request.setAttribute("title", "编辑注册成功");
		return PathResolver.getPath(request, response, BackPage.FILE_EDIT_PAGE);
	}

	// ajax call
	/**
	 * Save.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param content
	 *            the content
	 * @param parentFilePath
	 *            the parent file path
	 * @param fileName
	 *            the file name
	 * @return the integer
	 */
	@RequestMapping("/admin/system/file/save")
	public @ResponseBody
	Integer save(HttpServletRequest request, HttpServletResponse response, String content, String parentFilePath, String fileName) {
		if (AppUtils.isBlank(content)) {
			return -1;
		}
		try {
			FileProcessor.writeFile(content, PropertiesUtil.getDownloadFilePath() + parentFilePath, fileName);
		} catch (Exception e) {
			return -2;
		}
		return 0;
	}

	/**
	 * Read reg item.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/regItem")
	public @ResponseBody
	String readRegItem(HttpServletRequest request, HttpServletResponse response) {
		String content = null;
		try {
			File file = new File(PropertiesUtil.getDownloadFilePath() + "/register/regItem.html");
			content = FileProcessor.readFile(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

}
