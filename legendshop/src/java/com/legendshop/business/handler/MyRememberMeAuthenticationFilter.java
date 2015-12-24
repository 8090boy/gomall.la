package com.legendshop.business.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.legendshop.business.dao.BasketDao;
import com.legendshop.model.entity.Basket;
import com.legendshop.spi.constants.Constants;

/**
 * The Class MyRememberMeAuthenticationFilter.
 */
public class MyRememberMeAuthenticationFilter extends RememberMeAuthenticationFilter {

	/** The basket dao. */
	private BasketDao basketDao;

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
		request.getSession().setAttribute(ValidateCodeUsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
				TextEscapeUtils.escapeEntities(authResult.getName()));

		// 处理在session中的购物车
		Map<String, Basket> basketMap = (Map<String, Basket>) request.getSession().getAttribute(Constants.BASKET_KEY);
		if (basketMap != null) {
			// 保存进去数据库
			for (Basket basket : basketMap.values()) {
				basketDao.saveToCart(authResult.getName(), basket.getProdId(), basket.getBasketCount(), basket.getAttribute(), basket.getPoints());
			}
			request.getSession().removeAttribute(Constants.BASKET_KEY);
		}
	}

	/**
	 * Sets the basket dao.
	 * 
	 * @param basketDaoImpl
	 *            the new basket dao
	 */
	@Required
	public void setBasketDao(BasketDao basketDao) {
		this.basketDao = basketDao;
	}

}
