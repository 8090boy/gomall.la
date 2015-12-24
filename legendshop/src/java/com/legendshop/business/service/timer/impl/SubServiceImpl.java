/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.timer.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.business.dao.BasketDao;
import com.legendshop.business.dao.SubDao;
import com.legendshop.business.dao.UserDetailDao;
import com.legendshop.business.service.impl.BaseServiceImpl;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.QueryMap;
import com.legendshop.core.dao.support.SimpleSqlQuery;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.event.EventHome;
import com.legendshop.model.SubForm;
import com.legendshop.model.SubListForm;
import com.legendshop.model.SubQueryForm;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.PayType;
import com.legendshop.model.entity.Sub;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.OrderStatusEnum;
import com.legendshop.spi.constants.PayTypeEnum;
import com.legendshop.spi.constants.SubStatusEnum;
import com.legendshop.spi.event.OrderSaveEvent;
import com.legendshop.spi.form.MemberForm;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.PayTypeService;
import com.legendshop.spi.service.StockService;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.sql.ConfigCode;

/**
 * 订单服务实现.
 */
public class SubServiceImpl extends BaseServiceImpl implements SubService {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(SubServiceImpl.class);

	/** The sub dao. */
	private SubDao subDao;

	/** The basket dao. */
	private BasketDao basketDao;

	/** The pay type service. */
	private PayTypeService payTypeService;

	/** The user detail dao. */
	private UserDetailDao userDetailDao;

	/** The commit inteval. */
	private Integer commitInteval = 100;

	/** The expire date. */
	private Integer expireDate = -30;
	
	private StockService stockService;

	/**
	 * 结束超时不确认收货的订单.
	 */
	@Override
	public void finishUnAcklodge() {
		Date date = getDate(expireDate);
		log.debug("finishUnAcklodge,date = {}", date);
		boolean haveValue = true;
		while (haveValue) {
			List<Sub> list = subDao.getUnAcklodgeSub(commitInteval, date);
			log.debug("finishUnAcklodge,list = {}", list);
			if (AppUtils.isBlank(list)) {
				haveValue = false;
			} else {
				for (Sub sub : list) {
					subDao.saveSubHistory(sub, SubStatusEnum.ORDER_OVER_TIME.value());
					sub.setStatus(OrderStatusEnum.SUCCESS.value());
					sub.setSubCheck(Constants.TRUE_INDICATOR);
					sub.setUpdateDate(new Date());
					subDao.updateSub(sub);
				}
				subDao.flush();
			}

		}

	}

	/**
	 * 结束超时不付费的订单.
	 */
	@Override
	public void finishUnPay() {
		Date date = getDate(expireDate);
		log.debug("finishUnPay,date = {}", date);
		boolean haveValue = true;
		while (haveValue) {
			List<Sub> list = subDao.getFinishUnPay(commitInteval, date);
			log.debug("finishUnPay,list = {}", list);
			if (AppUtils.isBlank(list)) {
				haveValue = false;
			} else {
				for (Sub sub : list) {
					subDao.saveSubHistory(sub, SubStatusEnum.ORDER_OVER_TIME.value());
					sub.setStatus(OrderStatusEnum.CLOSE.value());
					sub.setSubCheck(Constants.TRUE_INDICATOR);
					sub.setUpdateDate(new Date());
					subDao.updateSub(sub);
				}
				subDao.flush();
			}

		}
	}

	/**
	 * 移除已经过期的购物车，保留30天.
	 */
	@Override
	public void removeOverTimeBasket() {
		Date date = getDate(expireDate);
		subDao.deleteOverTimeBasket(date);

	}

	/**
	 * 得到跟现在若干天时间，如果为负数则向前推.
	 * 
	 * @param days
	 *            the days
	 * @return the date
	 */
	private Date getDate(int days) {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * Sets the sub dao.
	 * 
	 * @param subDao
	 *            the new sub dao
	 */
	@Required
	public void setSubDao(SubDao subDao) {
		this.subDao = subDao;
	}

	/**
	 * Sets the commit inteval.
	 * 
	 * @param commitInteval
	 *            the new commit inteval
	 */
	public void setCommitInteval(Integer commitInteval) {
		this.commitInteval = commitInteval;
	}

	/**
	 * Sets the expire date.
	 * 
	 * @param expireDate
	 *            the new expire date
	 */
	public void setExpireDate(Integer expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * 保存订单 返回订单列表.
	 * 
	 * @param form
	 *            the form
	 * @return the list
	 */
	@Override
	public List<Sub> saveSub(SubForm form) {
		List<Sub> subList = new ArrayList<Sub>();

		// 得到用户购物车里所有的商品，每一个商城生成一个订单
		Map<String, List<Basket>> basketMap = basketDao.getBasketGroupById(form.getBasketId());
		if (AppUtils.isNotBlank(basketMap)) {
			for (List<Basket> baskets : basketMap.values()) {
				String subNember = CommonServiceUtil.getSubNember(form.getUserName());
				String prodName = "";
				// baskets 是在同一个商城下，每一个商城生成一个订单
				List<Basket> validateBasketList = new ArrayList<Basket> ();
				for (Basket backet : baskets) {
					prodName = prodName + backet.getProdName() + ",";
					Basket basket = basketDao.getBasketById(backet.getBasketId());
					if (basket != null) {
						//hold 住库存
						if(stockService.addHold(basket.getProdId(), basket.getBasketCount())){
							validateBasketList.add(backet);
							// 更新购物车
							basket.setSubNumber(subNember);
							basket.setBasketCheck(Constants.TRUE_INDICATOR);
							basket.setLastUpdateDate(new Date());
							basketDao.updateBasket(basket);
						}
					}
				}

				if(validateBasketList.size() > 0){
					// 保存订单
					Sub bo = makeSub(form);
					bo.setSubNumber(subNember);
					bo.setTotal(CommonServiceUtil.calculateTotalCash(validateBasketList));
					bo.setActualTotal(bo.getTotal());
					bo.setShopName(validateBasketList.get(0).getShopName()); // 在同一组的商品中都是同一个商城的，所以取第一个商品所属的商城即可。
					bo.setStatus(OrderStatusEnum.UNPAY.value());// 还没有开始付款
					List<PayType> payTypeList = payTypeService.getPayTypeList(baskets.get(0).getShopName());
					if (payTypeList != null && payTypeList.size() == 1) {// 当该卖家只是支持货到付款则直接设置付款方式
						PayType payType = payTypeList.get(0);
						if (payType.getPayTypeId().equals(PayTypeEnum.PAY_AT_GOODS_ARRIVED.value())) {
							bo.setPayId(payType.getPayId());
							bo.setPayDate(new Date());
							bo.setPayTypeId(payType.getPayTypeId());
							bo.setPayTypeName(payType.getPayTypeName());
						}
					}
					
					bo.setProdName(parseProdName(prodName));
					subDao.saveSub(bo);
					bo.setBasket(baskets);
					bo.setPayType(payTypeList);
					subList.add(bo);
					// 触发下订单事件
					EventHome.publishEvent(new OrderSaveEvent(bo));
				}
			}
		}

		return subList;

	}
	
	private String parseProdName(String prodName){
		String result = null;
		if (AppUtils.isNotBlank(prodName)) {
			if (prodName.length() < 1000) {
				result = prodName;
			} else {
				result = prodName.substring(0, 996) + "...";
			}

		}
		return result;
	}

	// basket_check = Y 表示已经下单了，形成了一条订单
	/**
	 * Gets the basket by sub number.
	 * 
	 * @param subNumber
	 *            the sub number
	 * @return the basket by sub number
	 */
	@Override
	public List<Basket> getBasketBySubNumber(String subNumber) {
		return subDao.getBasketBySubNumber(subNumber);
	}

	// 初始化订单
	/**
	 * Make sub.
	 * 
	 * @param form
	 *            the form
	 * @return the sub
	 */
	private Sub makeSub(SubForm form) {
		Sub sub = new Sub();
		sub.setUserName(form.getUserName());
		sub.setSubDate(new Date());
		sub.setScore(form.getPointsAll());
		sub.setSubTel(form.getUserTel());
		sub.setSubPost(form.getUserPostcode());
		sub.setSubMail(form.getUserMail());
		sub.setSubAdds(form.getUserAdds());
		sub.setPayId(form.getPayType());
		sub.setOther(form.getOther());
		sub.setSubCheck(Constants.FALSE_INDICATOR);
		sub.setOrderName(form.getOrderName());
		return sub;
	}

	/**
	 * Sets the basket dao.
	 * 
	 * @param basketDaoImpl
	 *            the new basket dao
	 */
	@Required
	public void setBasketDao(BasketDao basketDaoImpl) {
		this.basketDao = basketDaoImpl;
	}

	/**
	 * Sets the pay type service.
	 * 
	 * @param payTypeService
	 *            the new pay type service
	 */
	@Required
	public void setPayTypeService(PayTypeService payTypeService) {
		this.payTypeService = payTypeService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.timer.SubService#getSubBySubNumber(java
	 * .lang.String)
	 */
	@Override
	public Sub getSubBySubNumber(String subNumber) {
		return subDao.getSubBySubNumber(subNumber);
	}

	/**
	 * 订单详细信息
	 * 
	 */
	@Override
	public String getOrderDetail(HttpServletRequest request, HttpServletResponse response, Sub sub, String userName,
			String subNumber) {
		List<Sub> subList = new ArrayList<Sub>();

		MemberForm form = new MemberForm();
		form.setUserAdds(sub.getSubAdds());
		form.setUserPostcode(sub.getSubPost());
		form.setOrderName(sub.getUserName());
		form.setOther(sub.getOther());
		form.setUserTel(sub.getSubTel());
		form.setPayTypeName(sub.getPayTypeName());
		request.setAttribute("member", form);

		List<Basket> baskets = subDao.getBasketBySubNumber(subNumber);
		if (!AppUtils.isBlank(baskets)) {// 每一个订单最少应该有一个商品
			sub.setBasket(baskets);
			sub.setPayType(payTypeService.getPayTypeList(sub.getShopName()));
			subList.add(sub);
			// request.setAttribute("baskets", baskets);
			request.setAttribute("subList", subList);
		}
		if (OrderStatusEnum.UNPAY.value().equals(sub.getStatus())) {
			UserDetail userdetail = userDetailDao.getUserDetail(userName);
			if (userdetail != null) {
				 
				request.setAttribute("availableScore", userdetail.getScore());
			}
		} else {
			request.setAttribute("availableScore", 0l);
		}
		return PathResolver.getPath(request, response, TilesPage.PAGE_SUB);
	}

	@Override
	public String findOrder(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sub entity,
			String userName, SubQueryForm subQueryForm) {
		getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response),
				Constants.USER_REG_ADV_950);
		String subNumber = entity.getSubNumber();
		if (AppUtils.isNotBlank(subNumber)) {
			subNumber = subNumber.trim();
		}
		log.debug("find order userName {}, subNumber {}", userName, subNumber);

		// Jdbc查找方式
		QueryMap map = new QueryMap();
		map.put("subCheck", entity.getSubCheck());
		map.put("status", entity.getStatus());
		map.like("subNumber", entity.getSubNumber());
		map.put("userName", userName);
		
		String queryAllSQL = ConfigCode.getInstance().getCode("biz.queryCountOrderList", map);
		String querySQL = ConfigCode.getInstance().getCode("biz.queryOrderList", map);
		SimpleSqlQuery query = new SimpleSqlQuery(SubListForm.class, querySQL, queryAllSQL, map.toArray());
		query.setCurPage(curPageNO);
		query.setPageProvider(PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		PageSupport ps = subDao.getOrder(query);
		request.setAttribute("offset", ps.getOffset() + 1);
		request.setAttribute("curPageNO", curPageNO);
		request.setAttribute("toolBar", ps.getToolBar());
		List<SubListForm> subListFormList = convertSubBasketToSub(ps.getResultList());
		Map<String, List<SubListForm>> subListFormMap = convertBasketMap(subListFormList);
		request.setAttribute("map", subListFormMap);
		request.setAttribute("subForm", entity);

		return PathResolver.getPath(request, response, TilesPage.ORDER);
	}
	
	
	/**
	 * 先按订单号组织,找出一个订单下所有产品
	 * @param list
	 * @return
	 */
	protected List<SubListForm> convertSubBasketToSub (List<SubListForm> list) {
		if(AppUtils.isBlank(list)){
			return null;
		}
		Map<String, SubListForm> map = new HashMap<String, SubListForm>();
		
		for (SubListForm subListForm : list) {
			SubListForm sub = map.get(subListForm.getSubNumber());
			if (sub == null) {
				sub = subListForm.clone();
			}
			sub.addBasket(new Basket(subListForm.getBasketId(), subListForm.getCash(), subListForm.getCarriage(), subListForm.getProdId(), subListForm.getPic(), 
					subListForm.getBasketCount(), subListForm.getProdName(), subListForm.getAttribute(),subListForm.getPoints() ,subListForm.getPointsSubtotal() ));
			map.put(sub.getSubNumber(), sub);
		}
		return new ArrayList<SubListForm>(map.values()) ;
	}
	
	/**
	 * 再按商城区分订单
	 * @param list
	 * @return
	 */
	protected Map<String, List<SubListForm>> convertBasketMap(List<SubListForm> list) {
		if(AppUtils.isBlank(list)){
			return null;
		}
		Map<String, List<SubListForm>> map = new HashMap<String, List<SubListForm>>();
		for (SubListForm sub : list) {
			List<SubListForm> subList = map.get(sub.getShopName());
			if (subList == null) {
				subList = new ArrayList<SubListForm>();
			}
			subList.add(sub);
			map.put(sub.getShopName(), subList);
		}
		return map;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.timer.SubService#updateSub(com.legendshop
	 * .model.entity.Sub)
	 */
	@Override
	public void updateSub(Sub sub) {
		subDao.updateSub(sub);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.timer.SubService#getOrderList(com.legendshop
	 * .core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getOrderList(CriteriaQuery cq) {
		return subDao.getOrder(cq);
	}

	/**
	 * Sets the user detail dao.
	 * 
	 * @param userDetailDao
	 *            the new user detail dao
	 */
	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.timer.SubService#getTotalProcessingOrder
	 * (java.lang.String)
	 */
	@Override
	public Long getTotalProcessingOrder(String userName) {
		return subDao.getTotalProcessingOrder(userName);
	}

	@Override
	public Sub getSubById(Long subId) {
		return subDao.getSubById(subId);
	}

	@Override
	public boolean updateSubPrice(Sub sub, String userName, Double totalPrice) {
		return subDao.updateSubPrice(sub, userName, totalPrice);
	}

	/**
	 * 更新订单状态
	 */
	@Override
	public boolean updateSub(Sub sub, Integer status, String userName, String payTypeId) {
		return subDao.updateSub(sub, status, userName, payTypeId);
	}

	/**
	 * 删除订单
	 */
	@Override
	public boolean deleteSub(Sub sub) {
		return subDao.deleteSub(sub);
	}

	@Override
	public void saveSubHistory(Sub sub, String subAction) {
		subDao.saveSubHistory(sub, subAction);

	}

	private CriteriaQuery constructCriteriaQuery(HttpServletRequest request, String curPageNO, Sub entity,
			String userName, String subNumber, SubQueryForm subQueryForm) {
		if (log.isDebugEnabled()) {
			log.debug("orderType:{}, orderActiveStatus:{}, kwType:{}, kwText:{}, subNumber:{}", new Object[] {
					subQueryForm.getOrderType(), subQueryForm.getOrderActiveStatus(), subQueryForm.getKwType(),
					subQueryForm.getKwText(), subNumber });
		}

		CriteriaQuery cq = new CriteriaQuery(Sub.class, curPageNO, PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class));
		cq.eq("userName", userName);

		if (AppUtils.isNotBlank(subNumber)) {
			cq.like("subNumber", subNumber + "%");
		}

		cq.eq("status", entity.getStatus());
		cq.eq("subCheck", entity.getSubCheck());
		cq.addOrder("desc", "subDate");
		return cq;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

}
