/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.model.UserMessages;
import com.legendshop.model.SubForm;
import com.legendshop.model.entity.Sub;
import com.legendshop.model.entity.User;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.UserDetailService;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;

/**
 * 购物车控制器。.
 */
@Controller
@RequestMapping("/sub")
public class SubController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SubController.class);

	/** The sub service. */
	@Autowired
	private SubService subService;

	/** The user detail service. */
	@Autowired
	private UserDetailService userDetailService;

	/**
	 * 用户下订单.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sub
	 *            the sub
	 * @return the string
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, SubForm sub) {
		
		String userName = UserManager.getUserName(request);
		String result = checkLogin(request, response, userName);
		if (result != null) {
			return result;
		}
		log.debug("{} save sub ", userName);
		HttpSession session =  request.getSession();
		Integer i = (Integer) session.getAttribute(Constants.TOKEN); // 取session中保存的token
		Integer i2 = Integer.parseInt(request.getParameter(Constants.TOKEN)); // 取用户提交过来的token进行对比
		if (i.equals(i2)) {// 这个“暗号”用过后约定用新的暗号, 在cash.do中加入
			session.setAttribute(Constants.TOKEN, CommonServiceUtil.generateRandom());
			parseBasketCount(request,sub);
			List<Sub> subList = subService.saveSub(sub);
			//减少购物车数量
			CommonServiceUtil.calBasketTotalCount(session, -sub.getBasketId().length);
			request.setAttribute("member", sub);
			request.setAttribute("subList", subList);
			//用户积分//在订单中取出
			request.setAttribute("availableScore",CommonServiceUtil.calculatePointsAllForSub(subList) );
		} else {
			// 如果暗号不对的话（用户重复提交）提示“不能重复提交”
			UserMessages uem = new UserMessages();
			uem.setTitle(ResourceBundleHelper.getString("webpage.timeout"));
			uem.setCode(ErrorCodes.INVALID_TOKEN);
			uem.addCallBackList(ResourceBundleHelper.getString("myorder"), ResourceBundleHelper.getString("Query.Order.Status"),"p/order");
			request.setAttribute(UserMessages.MESSAGE_KEY, uem);
			return PathResolver.getPath(request, response, FrontPage.ERROR_PAGE);
		}
		return PathResolver.getPath(request, response, TilesPage.PAGE_SUB);
	}
	
	/**
	 * 计算购物车数量
	 * @param request
	 * @param sub
	 */
	private void parseBasketCount(HttpServletRequest request,SubForm subForm){
		String[] basketIdList = request.getParameterValues("strArray");
		if(AppUtils.isBlank(basketIdList)){
			throw new BusinessException("Basket at least choose one");
		}
		Long[]  basketId = new Long[basketIdList.length];
		Integer[]  basketCount = new Integer[basketIdList.length];
		for (int i = 0; i < basketId.length; i++) {
			basketId[i] = Long.parseLong(basketIdList[i]);
			basketCount[i] =  Integer.parseInt(request.getParameter("basketCount" + basketId[i]));
		}
		subForm.setBasketId(basketId);
	}

}
