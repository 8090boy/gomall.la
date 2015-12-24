/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.core.base.BaseController;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.LegendFilter;
import com.legendshop.core.helper.RealPathUtil;

/**
 * 系统缓存控制器.
 */
@Controller
@RequestMapping("/admin/file")
public class HtmlFileController extends BaseController {

	@RequestMapping("/deleteHtmlFile")
	public @ResponseBody Integer deleteHtmlFile(String filePath) {
		String realPath = LegendFilter.HTML_PATH;
		FileProcessor.deleteDirectory(new File(realPath + filePath));
		return 0;
	}
	
	@RequestMapping("/deleteFile")
	public @ResponseBody Integer deleteFile(String filePath) {
		String realPath = RealPathUtil.getFCKRealPath(filePath);
		int result = FileProcessor.deleteFile(realPath);
		return result;
	}
}
