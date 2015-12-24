/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.business.service.locator.SubServiceLocator;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.exception.PermissionException;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.SubQueryForm;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.form.BasketForm;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.AdvertisementService;
import com.legendshop.spi.service.BasketService;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.ScoreService;
import com.legendshop.spi.service.UserDetailService;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;

@Controller
@RequestMapping("/p")
public class OrderController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(OrderController.class);

	/** The default value. */
	private final String defaultValue = "0";

	/** The advertisement service. */
	@Autowired
	private AdvertisementService advertisementService;

	/** The order service. */
	@Autowired
	private SubServiceLocator subServiceLocator;

	/** The basket service. */
	@Autowired
	private BasketService basketService;

	/** The user detail service. */
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@Autowired
	private ScoreService scoreService;

	/**
	 * Order.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	@RequestMapping("/order")
	public String order(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sub entity, SubQueryForm subQueryForm) {

		String userName = UserManager.getUserName(request);

		if (userName == null) {
			request.setAttribute(Constants.RETURN_URL, PropertiesUtil.getDomainName() + "/p/order");
			return PathResolver.getPath(request, response, TilesPage.NO_LOGIN);
		}
		if (entity != null && entity.getSubCheck() == null) {
			entity.setSubCheck(Constants.FALSE_INDICATOR);
		}
		SubService subService = subServiceLocator.getConcreteService(request, response, TilesPage.ORDER);
		
		String  result  = subService.findOrder(request, response,curPageNO,  entity, userName, subQueryForm);
		return result;
	}

	/**
	 * Update.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param basket
	 *            the basket
	 * @return the string
	 */
	@RequestMapping("/buy")
	public String update(HttpServletRequest request, HttpServletResponse response, BasketForm basket) {
		Integer count = basket.getCount();
		if (count == null) {
			count = 1;
		}
		advertisementService.getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response),
				Constants.USER_REG_ADV_950);
		return PathResolver.getPath(request, response, TilesPage.BUY);
	}

	/**
	 * 删除购物车
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/clear")
	public String clear(HttpServletRequest request, HttpServletResponse response) {
		String userName = UserManager.getUserName(request);
		if (AppUtils.isBlank(userName)) {
			return PathResolver.getPath(request, response, TilesPage.NO_LOGIN);
		}
		String basketId = request.getParameter("basketId");
		if (basketId == null) {
			basketService.deleteBasketByUserName(userName);
			CommonServiceUtil.setBasketTotalCount(request.getSession(), 0);
		} else {
			try {
				Long id = Long.valueOf(basketId);
				basketService.deleteBasketById(id);
				CommonServiceUtil.calBasketTotalCount(request.getSession(), -1);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return PathResolver.getPath(request, response, TilesPage.BUY);
	}

	/**
	 * Bought.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/bought")
	public String bought(HttpServletRequest request, HttpServletResponse response) {
		List<Basket> baskets = basketService.getBasketByUserName(UserManager.getUserName(request));
		if (!AppUtils.isBlank(baskets)) {
			Double totalcash = CommonServiceUtil.calculateTotalCash(baskets);
			request.setAttribute("baskets", baskets);
			request.setAttribute("totalcash", totalcash);
		}

		return PathResolver.getPath(request, response, FrontPage.BOUGHT);
	}

	/**
	 * 准备保存订单.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/cashsave")
	public String cashsave(HttpServletRequest request, HttpServletResponse response) {
		String total = request.getParameter("total");
		if (total != null) {
			request.setAttribute("total", total);
		} else {
			total = defaultValue;// 没有找到总的cash
		}
		String userName = UserManager.getUserName(request);
		if (AppUtils.isBlank(userName)) {
			return PathResolver.getPath(request, response, TilesPage.NO_LOGIN);
		}

		UserDetail member = userDetailService.getUserDetail(userName);
		if (!AppUtils.isBlank(member)) {
			request.setAttribute("member", member);
		}
		return PathResolver.getPath(request, response, FrontPage.CASH_SAVE);

	}

	/**
	 * Order detail.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param subNumber
	 *            the sub number
	 * @return the string
	 */
	@RequestMapping("/orderDetail/{subNumber}")
	public String orderDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable String subNumber) {
		String userName = UserManager.getUserName(request);
		if (AppUtils.isBlank(userName)) {
			return PathResolver.getPath(request, response, TilesPage.LOGIN);
		}
		
		Sub sub = subServiceLocator.getSubService().getSubBySubNumber(subNumber);
		if (sub == null) {
			throw new NotFoundException("sub not found with userName = " + userName);
		}

		if (!userName.equals(sub.getUserName()) && !userName.equals(sub.getShopName())) {
			if (!CommonServiceUtil.haveViewAllDataFunction(request)) {
				throw new PermissionException("can not modify others order detail");
			}
		}
		return subServiceLocator.getSubService().getOrderDetail(request, response, sub, userName, subNumber);
	}

	/**
	 * 重新确认购物车，如果数量有更新，则更新购物车
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/cash")
	public String cash(HttpServletRequest request, HttpServletResponse response) {
		
		String[] basketIdList = request.getParameterValues("strArray");
		if(AppUtils.isBlank(basketIdList)){
			throw new BusinessException("Basket at least choose one");
		}
		Long[]  basketId = new Long[basketIdList.length];
		for (int i = 0; i < basketId.length; i++) {
			basketId[i] = Long.parseLong(basketIdList[i]);
		}
		List<Basket> basketList = basketService.getBasketListById(basketId);
		if(AppUtils.isNotBlank(basketList)){
			for (int i = 0; i < basketList.size(); i++) {
				Basket basket = basketList.get(i);
				Integer bc = Integer.parseInt(request.getParameter("basketCount" + basket.getBasketId()));
				int pointsSubtotal = Integer.parseInt(request.getParameter("pointsSubtotal" + basket.getBasketId()));
				if(!basket.getBasketCount().equals(bc)){ //更新购物车数量
					basket.setBasketCount(bc);
					basket.setPointsSubtotal(pointsSubtotal);
					basketService.updateBasket(basket);
				}
				
			}
		}
		Double totalcash = CommonServiceUtil.calculateTotalCash(basketList);
		int pointsSubtotalAll = CommonServiceUtil.calculateSubTotalPoints(basketList);
		request.setAttribute("baskets", basketList);
		request.setAttribute("totalcash", totalcash);
		request.setAttribute("pointsSubtotalAll", pointsSubtotalAll);
		// 加入token，生成随机数并保存到token
		request.getSession().setAttribute(Constants.TOKEN, CommonServiceUtil.generateRandom()); 
		return PathResolver.getPath(request, response, TilesPage.PAGE_CASH);
	}
	
	
	/**
	 * 下订单
	 * @deprecated
	 * @param subId
	 * @param shopName
	 * @param payTypeId
	 */
	@RequestMapping("/payToOrder")
	public  void payToOrder(HttpServletRequest request, HttpServletResponse response, Long subId, String shopName, String payTypeId) {
		if (AppUtils.isNotBlank(subId)) {
			Sub sub = subServiceLocator.getSubService().getSubById(subId);
			PayType payType = payTypeService.getPayTypeByIdAndName(shopName, payTypeId);
			if (payType != null) {
				sub.setPayTypeName(payType.getPayTypeName());
				sub.setPayTypeId(payType.getPayTypeId());
				sub.setPayId(payType.getPayId());
				sub.setPayDate(new Date());
				subServiceLocator.getSubService().updateSub(sub);
			}
		}
	}
	/**
	 * 更新订单状态
	 * @param subId
	 * @param status
	 * @param userName
	 * @return
	 */
	@RequestMapping("/updateSubStatus")
	public @ResponseBody String updateSubStatus(HttpServletRequest request, HttpServletResponse response, String subNumber, Integer status,String payTypeId) {
		String userName = UserManager.getUserName(request);
		if (userName == null) {
			return Constants.FAIL;
		}
		Sub sub = subServiceLocator.getSubService().getSubBySubNumber(subNumber);
		if (sub == null) {
			return Constants.FAIL;
		}
		if (!sub.getUserName().equals(userName) && !sub.getShopName().equals(userName)) { // 只有卖家和买家才可以更新订单状态
			if (!CommonServiceUtil.haveViewAllDataFunction(request)) {
				log.warn("updateSub:userName {} or shopName {} not equal userName {}",
						new Object[] { sub.getUserName(), sub.getShopName(), userName });
				return Constants.FAIL;
			}
		}
		boolean result = subServiceLocator.getSubService().updateSub(sub, status, userName,payTypeId);
		if (result) {
			return null;
		} else {
			return Constants.FAIL;
		}

	}
	/**
	 * 删除订单
	 * @param request
	 * @param response
	 * @param subId
	 * @return
	 */
	@RequestMapping("/deleteSub")
	public @ResponseBody String deleteSub(HttpServletRequest request, HttpServletResponse response,Long subId) {
		if (subId == null) {
			return Constants.FAIL;
		}
		Sub sub = subServiceLocator.getSubService().getSubById(subId);
		// 检查权限
		String hasAccessRight = checkPrivilege(request, UserManager.getUserName(request.getSession()), sub.getUserName());
		if (hasAccessRight != null) {
			return Constants.FAIL;
		}
		
		boolean result = subServiceLocator.getSubService().deleteSub(sub);
		if (result) {
			return null;
		} else {
			return Constants.FAIL;
		}

	}
	
	/**
	 * 计算积分
	 * @param score
	 * @return
	 */
	@RequestMapping("/calMoneySocre")
	public @ResponseBody Double calMoneySocre(Long score) {
		return scoreService.calMoney(score);
	}

	/**
	 * 使用积分
	 * @param subId
	 * @param score
	 * @return
	 */
	@RequestMapping("/userScore")
	public @ResponseBody Map<String, Object> userScore(Long subId, Long score) {
		Sub sub = subServiceLocator.getSubService().getSubById(subId);
		if (sub == null) {
			return null;
		}
		return scoreService.useScore(sub, score);
	}


}
