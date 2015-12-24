/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.BasketDao;
import com.legendshop.model.entity.Basket;
import com.legendshop.spi.constants.SaveToCartStatusEnum;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.service.BasketService;

/**
 * Basket服务.
 */
public class BasketServiceImpl implements BasketService {

	/** The basket dao. */
	private BasketDao basketDao;

	/** The product dao. */
	private ProductDao productDao;

	/**
	 * 加入购物车.在购物车中的产品并不会影响库存量，如果在下单时库存不足，则会造成无法下单。
	 */
	@Override
	public void saveToCart(Long prodId, String shopName, String prodattr, String userName, Integer count,int point) {
		String attribute = prodattr == null ? "" : prodattr;
		Basket basket = basketDao.getBasketByIdName(prodId, userName, shopName, attribute);
		if (basket == null) {
			basketDao.saveToCart(userName, prodId, count, attribute, point);
		}
	}

	/**
	 * Sets the product dao.
	 * 
	 * @param productDao
	 *            the new product dao
	 */
	@Required
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * Sets the basket dao.
	 * 
	 * @param basketDao
	 *            the new basket dao
	 */
	@Required
	public void setBasketDao(BasketDao basketDao) {
		this.basketDao = basketDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.BasketService#deleteBasketByUserName(
	 * java.lang.String)
	 */
	@Override
	public void deleteBasketByUserName(String userName) {
		basketDao.deleteBasketByUserName(userName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.BasketService#deleteBasketById(java.lang
	 * .Long)
	 */
	@Override
	public void deleteBasketById(Long id) {
		basketDao.deleteBasketById(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.BasketService#getBasketByuserName(java
	 * .lang.String)
	 */
	@Override
	public List<Basket> getBasketByUserName(String userName) {
		return basketDao.getBasketByUserName(userName);
	}

	@Override
	public Long getTotalBasketByUserName(String userName) {
		return basketDao.getTotalBasketByUserName(userName);
	}

	/**
	 * 加入购物车.在购物车中的产品并不会影响库存量，如果在下单时库存不足，则会造成无法下单。
	 */
	@Override
	public SaveToCartStatusEnum saveToCart(String userName, Long prodId, Integer count, String attribute, int points) {
		return basketDao.saveToCart(userName, prodId, count, attribute, points);
	}

	@Override
	public List<Basket> getBasketListById(Long[] basketIdList) {
		return basketDao.getBasketListById(basketIdList);
	}

	@Override
	public void updateBasket(Basket basket) {
		 basketDao.updateBasket(basket);
	}

}
