/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.business.dao.BasketDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.Product;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.SaveToCartStatusEnum;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.service.StockService;
import com.legendshop.util.AppUtils;

/**
 * 购物车Dao.
 */
public class BasketDaoImpl extends BaseDaoImpl implements BasketDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(BasketDaoImpl.class);

	/** The product dao. */
	private ProductDao productDao;
	
	/** The base jdbc dao. */
	private BaseJdbcDao baseJdbcDao;
	
	private StockService stockService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#deleteBasketById(java.lang
	 * .Long)
	 */
	@Override
	public void deleteBasketById(Long basketId) {
		log.debug("deleteBasketById, basketId = {}", basketId);
		exeByHQL("delete from Basket where basketId=?", basketId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getBasketByuserName(java.lang
	 * .String)
	 */
	@Override
	public List<Basket> getBasketByUserName(String userName) {
		if(AppUtils.isBlank(userName)){
			return null;
		}
		log.debug("getBasketByuserName,userName = {}", userName);
		return findByHQL("from Basket where userName=? and basket_check=? order by basketDate desc", userName,
				Constants.FALSE_INDICATOR);

	}

	// 得到有效订单总数
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getTotalBasketByuserName(java
	 * .lang.String)
	 */
	@Override
	public Long getTotalBasketByUserName(String userName) {
		return findUniqueBy("select count(*) from Basket where userName=? and basket_check=?", Long.class, userName,
				Constants.FALSE_INDICATOR);
	}

	// group by shopName
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getBasketByuserNameGroupByShopName
	 * (java.lang.String)
	 */
	@Override
	public Map<String, List<Basket>> getBasketGroupByName(String userName) {
		List<Basket> list = findByHQL("from Basket where userName=? and basket_check=? order by basketDate desc", userName,
				Constants.FALSE_INDICATOR);
		return convertBasketMap(list);
	}

	
	/* (non-Javadoc)
	 * @see com.legendshop.business.dao.BasketDao#getBasketGroupById(java.lang.Long[])
	 */
	public  Map<String, List<Basket>> getBasketGroupById(Long[] basketIdList){
		List<Basket> list = this.getBasketListById(basketIdList);
		return convertBasketMap(list);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getBasketById(java.lang.Long)
	 */
	@Override
	public Basket getBasketById(Long id) {
		return get(Basket.class, id);

	}
	
	/* (non-Javadoc)
	 * @see com.legendshop.business.dao.BasketDao#getBasketListById(java.lang.Long[])
	 */
	@Override
	public List<Basket> getBasketListById(Long[] basketIdList) {
		StringBuffer whereCause = new StringBuffer("?");
		for (int i = 0; i < basketIdList.length -1; i++) {
			whereCause.append(",?");
			
		}
		whereCause.append(")");
		return findByHQL("from Basket where basketId in( " + whereCause.toString(), basketIdList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getBasketByIdName(java.lang
	 * .Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Basket getBasketByIdName(Long prodId, String userName, String shopName, String attribute) {
		return findUniqueBy("from Basket where prodId=? and userName=? and basket_check=? and shopName=? and attribute = ?",
				Basket.class, prodId, userName, Constants.FALSE_INDICATOR, shopName, attribute);
	}

	// 用户在shopName的订购数
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getBasketByUserName(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public Long getBasketByUserName(String userName, String shopName) {
		return findUniqueBy("select count(*) from Basket where userName=? and basket_check=? and shopName=?", Long.class, userName,
				Constants.FALSE_INDICATOR, shopName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#saveBasket(com.legendshop.
	 * model.entity.Basket)
	 */
	@Override
	public Long saveBasket(Basket basket) {
		return (Long) save(basket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#updateBasket(com.legendshop
	 * .model.entity.Basket)
	 */
	@Override
	public void updateBasket(Basket basket) {
		update(basket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#getBasket(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Basket> getBasket(String prodId, String userName) {
		return findByHQL("from Basket where prodId = ? and userName = ? and basketCheck=?", prodId, userName,
				Constants.FALSE_INDICATOR);
	}

 
	@Override
	public void deleteBasketByUserName(String userName) {
		List<Basket> list = findByHQL("from Basket where userName=? and basketCheck=?", new Object[] { userName,
				Constants.FALSE_INDICATOR });
		if (!AppUtils.isBlank(list)) {
			deleteAll(list);
		}
	}

	/**
	 * 删除订单和其购物车
	 * 还原产品的数量
	 */
	@Override
	public void deleteBasketBySubNumber(String subNumber) {
		List<Basket> list = findByHQL("from Basket where subNumber=?", subNumber);
		
		if (!AppUtils.isBlank(list)) {
			for (Basket basket : list) {	
				stockService.releaseHold(basket.getProdId(), basket.getBasketCount());
			}
			deleteAll(list);
		}
	}

	/**
	 * 加入购物车.在购物车中的产品并不会影响库存量，如果在下单时库存不足，则会造成无法下单。
	 *
	 * @param userName the user name
	 * @param prodId the prod id
	 * @param count the count
	 * @param attribute the attribute
	 * @return true, if successful
	 */
	@Override
	public SaveToCartStatusEnum saveToCart(String userName, Long prodId, Integer count, String attribute ,int points) {
		//获取商品
		Product product = productDao.getProduct(prodId);
		if (product == null) {
			log.error("saveToCart failed for prod does not exist");
			return SaveToCartStatusEnum.ERR;
		}
		if(product.getUserName().equals(userName)){//用户不能订购自己的店铺的产品
			return SaveToCartStatusEnum.OWNER;
		}
		//检查购物车大小
		Long totalBasket = getTotalBasketByUserName(userName);
		if(totalBasket >= Constants.MAX_BASKET_SIZE){
			return SaveToCartStatusEnum.MAX;
		}
		//查找购物车是否有同样的商品
		Basket basket = getBasketByIdName(product.getProdId(), userName, product.getUserName(), attribute);
		if (basket == null) {
			// 没有保存过
			// 检查库存
			if(!stockService.canOrder(product, count)){
				return SaveToCartStatusEnum.LESS;
			}
			Basket b = new Basket();
			b.setProdId(product.getProdId());
			b.setPic(product.getPic());
			b.setUserName(userName);
			b.setBasketCount(count);
			b.setProdName(product.getName());
			b.setCash(product.getCash());
			b.setAttribute(attribute);
			b.setCarriage(product.getCarriage());
			b.setBasketDate(new Date());
			b.setLastUpdateDate(b.getBasketDate());
			b.setBasketCheck(Constants.FALSE_INDICATOR);
			b.setShopName(product.getUserName());
			b.setPoints(points);
			b.setPointsSubtotal(points);
			saveBasket(b);
			CommonServiceUtil.calBasketTotalCount(ThreadLocalContext.getRequest().getSession(), -1);
		} else {
			// 增加订购数量
			if(!stockService.canOrder(product, basket.getBasketCount() + count)){
				return SaveToCartStatusEnum.LESS;
			} else {
				if (count > 0) {
					basket.setBasketCount(basket.getBasketCount() + count);
					basket.setPointsSubtotal( (basket.getBasketCount() + count) * points );
					updateBasket(basket);
				}
			}
		}
		return SaveToCartStatusEnum.OK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BasketDao#setProductDao(com.legendshop
	 * .business.dao.impl.ProductDaoImpl)
	 */
	/**
	 * Sets the product dao.
	 * 
	 * @param productDaoImpl
	 *            the new product dao
	 */
	@Required
	public void setProductDao(ProductDao productDaoImpl) {
		this.productDao = productDaoImpl;
	}

	/**
	 * 删除商城购物车相关的记录
	 */
	@Override
	public void deleteBasket(String userName) {
		baseJdbcDao.update("delete from ls_basket where shop_name = ?", userName);
	}

	/**
	 * Sets the base jdbc dao.
	 *
	 * @param baseJdbcDao the baseJdbcDao to set
	 */
	public void setBaseJdbcDao(BaseJdbcDao baseJdbcDao) {
		this.baseJdbcDao = baseJdbcDao;
	}

	/**
	 * 按商城区分购物车
	 * @param list
	 * @return
	 */
	private Map<String, List<Basket>> convertBasketMap(List<Basket> list) {
		if(AppUtils.isBlank(list)){
			return null;
		}
		Map<String, List<Basket>> map = new HashMap<String, List<Basket>>();
		for (Basket basket : list) {
			List<Basket> baskets = map.get(basket.getShopName());
			if (baskets == null) {
				baskets = new ArrayList<Basket>();
			}
			baskets.add(basket);
			map.put(basket.getShopName(), baskets);
		}
		return map;
	}

	@Override
	public void deleteUserBasket(String userName) {
		// 删除basket
		baseJdbcDao.deleteUserItem("ls_basket", userName);
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}
}
