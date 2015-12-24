/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.service.timer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.model.SubForm;
import com.legendshop.model.SubQueryForm;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.Sub;

/**
 * 订单服务接口.
 */
public interface SubService {

	/**
	 * 结束超时不付费的订单.
	 */
	public void finishUnPay();

	/**
	 * 结束超时不确认收货的订单.
	 */
	public void finishUnAcklodge();

	/**
	 * 移除已经过期的购物车.
	 */
	public void removeOverTimeBasket();

	/**
	 * 保存订单.
	 * 
	 * @param form
	 *            the form
	 * @return the list
	 */
	public List<Sub> saveSub(SubForm form);

	// basket_check = Y 表示已经下单了，形成了一条订单
	/**
	 * Gets the basket by sub number.
	 * 
	 * @param subNumber
	 *            the sub number
	 * @return the basket by sub number
	 */
	public abstract List<Basket> getBasketBySubNumber(String subNumber);

	/**
	 * Find sub by sub number.
	 * 
	 * @param subNumber
	 *            the sub number
	 * @return the sub
	 */
	public Sub getSubBySubNumber(String subNumber);

	/**
	 * Update sub.
	 * 
	 * @param sub
	 *            the sub
	 */
	public void updateSub(Sub sub);

	/**
	 * Gets the order list.
	 * 
	 * @param cq
	 *            the cq
	 * @return the order list
	 */
	public PageSupport getOrderList(CriteriaQuery cq);

	/**
	 * Gets the order detail.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sub
	 *            the sub
	 * @param userName
	 *            the user name
	 * @param subNumber
	 *            the sub number
	 * @return the order detail
	 */
	public String getOrderDetail(HttpServletRequest request, HttpServletResponse response, Sub sub, String userName,
			String subNumber);

	/**
	 * 得到正在处理中的订单.
	 *
	 * @param userName the user name
	 * @return the total processing order
	 */
	public abstract Long getTotalProcessingOrder(String userName);
	
	/**
	 * 根据ID，获取订单.
	 *
	 * @param subId the sub id
	 * @return the sub by id
	 */
	public abstract Sub getSubById(Long subId);

	/**
	 * 更新订单价格.
	 *
	 * @param sub the sub
	 * @param userName the user name
	 * @param totalPrice the total price
	 * @return true, if successful
	 */
	public abstract boolean updateSubPrice(Sub sub, String userName, Double totalPrice);
	
	/**
	 * 更新订单状态.
	 *
	 * @param sub the sub
	 * @param status the status
	 * @param userName the user name
	 * @return true, if successful
	 */
	public boolean updateSub(Sub sub, Integer status, String userName, String payTypeId);

	/**
	 * 删除订单.
	 *
	 * @param sub the sub
	 * @return true, if successful
	 */
	public boolean deleteSub(Sub sub);
	
	/**
	 * 保存订单历史记录.
	 *
	 * @param sub the sub
	 * @param subAction the sub action
	 */
	public abstract void saveSubHistory(Sub sub, String subAction);

	/**
	 * 前台查找订单信息
	 *
	 */
	public String findOrder(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sub entity,String userName,
			SubQueryForm subQueryForm);
}
