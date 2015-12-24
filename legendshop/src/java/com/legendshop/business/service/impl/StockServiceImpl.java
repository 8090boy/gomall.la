/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import org.hibernate.LockMode;

import com.legendshop.model.entity.Product;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.service.StockService;

/**
 * 库存管理服务
 */
public class StockServiceImpl implements StockService {

	private ProductDao productDao;

	/**
	 * hold住库存，客户下单时调用，使得后面的人不能继续下单 如果库存不足， 则不能hold成功
	 * 
	 * @param product
	 * @param count
	 */
	@Override
	public boolean addHold(Long prodId, Integer basketCount) {
		Product product = productDao.getByLockMode(Product.class, prodId, LockMode.UPGRADE_NOWAIT);
		if (product != null) {
			// 产品库存
			Integer stocks = product.getStocks();
			if (stocks == null) {
				stocks = 0;
			}
			if (stocks - basketCount < 0) {
				return false;
			} else {
				product.setStocks(stocks - basketCount);
				productDao.updateOnly(product);
				return true;
			}

		}
		return false;
	}

	/**
	 * 释放holding，还没有出库前订单取消，还原订单数量
	 * 
	 * @param product
	 * @param count
	 */
	@Override
	public void releaseHold(Long prodId, Integer count) {
		Product product = productDao.getByLockMode(Product.class, prodId, LockMode.UPGRADE_NOWAIT);
		if (product != null) {
			// 产品库存
			Integer stocks = product.getStocks();
			if (stocks == null) {
				stocks = 0;
			}
			product.setStocks(stocks + count);
			productDao.updateOnly(product);
		}
	}

	/**
	 * 入库, 增加库存
	 * 
	 * @param product
	 * @param count
	 */
	@Override
	public void addStock(Long prodId, Integer count) {
		Product product = productDao.getByLockMode(Product.class, prodId, LockMode.UPGRADE_NOWAIT);
		if (product != null) {
			// 产品库存
			Integer stocks = product.getStocks();
			if (stocks == null) {
				stocks = 0;
			}
			Integer actualStocks = product.getActualStocks();
			if (actualStocks == null) {
				actualStocks = 0;
			}
			product.setStocks(stocks + count);
			product.setActualStocks(actualStocks + count);
			productDao.updateOnly(product);
		}

	}

	/**
	 * 出库，减少库存
	 * 
	 * @param product
	 * @param count
	 */
	@Override
	public void reduceStock(Long prodId, Integer count) {
		Product product = productDao.getByLockMode(Product.class, prodId, LockMode.UPGRADE_NOWAIT);
		if (product != null) {
			// 产品实际库存
			Integer actualStocks = product.getActualStocks();
			if (actualStocks == null) {
				actualStocks = product.getStocks();
			}
			product.setActualStocks(actualStocks - count);
			product.setBuys(product.getBuys() + count);
			productDao.updateOnly(product);
		}

	}

	/**
	 * 检查库存，是否可以订购
	 * 
	 * @param product
	 *            the product
	 * @return true, if successful
	 */
	@Override
	public boolean canOrder(Product product, Integer count) {
		// 数量检查
		if (product == null || (product.getStocks() != null && product.getStocks() < count)) {
			return false;
		} else {
			return true;
		}
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * 增加购买数
	 */
	@Override
	public void addBuys(Long prodId, Integer basketCount) {
		Product product = productDao.getByLockMode(Product.class, prodId, LockMode.UPGRADE_NOWAIT);
		if (product != null) {
			// 产品库存
			Integer buys = product.getBuys();
			if (buys == null) {
				buys = 0;
			}
			product.setBuys(buys + basketCount);
			productDao.updateOnly(product);
		}
		
	}

}
