/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.Product;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.SaveToCartStatusEnum;
import com.legendshop.spi.service.BasketService;
import com.legendshop.spi.service.ProductService;
import com.legendshop.spi.service.StockService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.Arith;

@Controller
public class PublicOrderController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(PublicOrderController.class);

	/**
	 * 产品服务
	 */
	@Autowired
	private ProductService productService;

	/**
	 * 购物车服务
	 */
	@Autowired
	private BasketService basketService;

	/**
	 * 库存服务
	 */
	@Autowired
	private StockService stockService;

	// 加入购物车
	@RequestMapping("/addtocart")
	public @ResponseBody
	Map<String, Object> addtocart(HttpServletRequest request, HttpServletResponse response, Long prodId, Integer count, int points, String attribute) {
		String userName = UserManager.getUserName(request);
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		if (log.isDebugEnabled()) {
			log.debug("userName {} order product with id {} in shop {}, quantity is {}", new Object[] { userName, prodId, shopName, count });
		}
		Product product = productService.getProductById(prodId);
		// <key, count>
		Map<String, Object> map = new HashMap<String, Object>();
		if (product == null) {
			return map;
		}
		if (attribute == null) { // default
			attribute = "";
		}
		if (count == null) { // default
			count = 1;
		}
		if (points < 1) { // default
			points = 0;
		}
		// 是否订购成功标志位
		SaveToCartStatusEnum saveToCartStatus = SaveToCartStatusEnum.OK;
		HttpSession session = request.getSession();
		if (AppUtils.isBlank(userName)) {// 没有登录，先放到session中
			// 库存
			if (!stockService.canOrder(product, count)) {
				saveToCartStatus = SaveToCartStatusEnum.LESS;
			} else if (product.getUserName().equals(userName)) { // 能订购自己的产品

				saveToCartStatus = SaveToCartStatusEnum.OWNER;
			}
			// 没有登录, 先放到session
			Map<String, Basket> basketMap = (Map<String, Basket>) session.getAttribute(Constants.BASKET_KEY);
			if (basketMap == null) {
				// 第一次加入购物车
				if (saveToCartStatus.equals(SaveToCartStatusEnum.OK)) {
					basketMap = new HashMap<String, Basket>();
					Basket b = new Basket();
					b.setProdId(prodId);
					b.setBasketCount(count);
					b.setAttribute(attribute);
					b.setCash(product.getCash());
					b.setCarriage(product.getCarriage());
					b.setLastUpdateDate(new Date());
					b.setPoints(points);
					b.setPointsSubtotal(points);
					// 加入购物车
					saveToCartStatus = addProductToCart(basketMap, CommonServiceUtil.getBasketKey(shopName, prodId, attribute), b);
					session.setAttribute(Constants.BASKET_KEY, basketMap);
					map.put(Constants.BASKET_COUNT, 1);
					Double totalCash = Arith.mul(count, product.getCash());
					if (product.getCarriage() != null) {
						totalCash = Arith.add(totalCash, product.getCarriage());
					}
					map.put(Constants.BASKET_TOTAL_CASH, totalCash);
					map.put(Constants.POINTS_TOTAL, points);
				} else {
					map.put(Constants.BASKET_COUNT, 0);
					map.put(Constants.BASKET_TOTAL_CASH, 0d);
				}

			} else {
				// 第二次以上加入购物车
				Basket b = null;
				if (saveToCartStatus.equals(SaveToCartStatusEnum.OK)) {
					String basketKey = CommonServiceUtil.getBasketKey(shopName, prodId, attribute);
					b = basketMap.get(basketKey);
					if (b == null) {// 该商品第一次加入
						b = new Basket();
						b.setProdId(prodId);
						b.setBasketCount(count);
						b.setPoints(product.getPoints());
						b.setPointsSubtotal(product.getPoints());
						b.setAttribute(attribute);
						b.setCash(product.getCash());
						b.setCarriage(product.getCarriage());
						b.setLastUpdateDate(new Date());
						// 加入购物车
						saveToCartStatus = addProductToCart(basketMap, basketKey, b);
						session.setAttribute(Constants.BASKET_KEY, basketMap);

					} else {
						// 该商品第二次或第二多次加入，重新计算数量
						int c = b.getBasketCount() + count;
						if (!stockService.canOrder(product, c)) { // 是否有库存
							// 检查数量
							saveToCartStatus = SaveToCartStatusEnum.LESS;
						} else {
							// 更新数量
							b.setBasketCount(c);
							b.setPointsSubtotal(c * b.getPoints());
							saveToCartStatus = addProductToCart(basketMap, basketKey, b);
							session.setAttribute(Constants.BASKET_KEY, basketMap);
						}
					}
				}

				map.put(Constants.POINTS_TOTAL, CommonServiceUtil.calculateSubTotalPoints(basketMap)); // 积分总计
				map.put(Constants.BASKET_COUNT, basketMap.size());
				map.put(Constants.BASKET_TOTAL_CASH, CommonServiceUtil.calculateTotalCash(basketMap));
			}
		} else {// 已经登录
			// 立即保存到数据库
			saveToCartStatus = basketService.saveToCart(userName, prodId, count, attribute, points);
			// 加载登录用户所有购物车
			List<Basket> baskets = basketService.getBasketByUserName(userName);
			// 计算总金额
			Double totalcash = CommonServiceUtil.calculateTotalCash(baskets);
			// 计算总积分
			int SubTotalPoints = CommonServiceUtil.calculateSubTotalPoints(baskets);
			// 放入结果map
			map.put(Constants.BASKET_COUNT, baskets.size());
			map.put(Constants.POINTS_TOTAL, SubTotalPoints);
			map.put(Constants.BASKET_TOTAL_CASH, totalcash);
		}
		// 计算购物车数量,只在第一次装载从数据库中读取，以后是内存操作
		//CommonServiceUtil.setBasketTotalCount(session, (Integer) map.get(Constants.BASKET_COUNT)); //试着替换这句，下面代替
		session.setAttribute(Constants.BASKET_TOTAL_COUNT, (Integer) map.get(Constants.BASKET_COUNT) );
		map.put(Constants.SAVE_TO_CART_STATUS, saveToCartStatus.value());
		return map;
	}

	private SaveToCartStatusEnum addProductToCart(Map<String, Basket> basketMap, String key, Basket basket) {
		if (basketMap.size() < Constants.MAX_BASKET_SIZE) {
			basketMap.put(key, basket);
			return SaveToCartStatusEnum.OK;
		} else {
			return SaveToCartStatusEnum.MAX;
		}
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

}
