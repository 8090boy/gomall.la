/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.model.StatusKeyValueEntity;
import com.legendshop.model.entity.Basket;
import com.legendshop.model.entity.Sub;
import com.legendshop.spi.constants.Constants;
import com.legendshop.util.AppUtils;
import com.legendshop.util.Arith;
import com.legendshop.util.TimerUtil;
import com.legendshop.util.des.DES2;

/**
 * 公用服务类.
 */
public class CommonServiceUtil extends FoundationUtil {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(CommonServiceUtil.class);

	/**
	 * 得到订单随机数.
	 * 
	 * @param userName
	 *            the user name
	 * @return the sub nember
	 */
	public synchronized static String getSubNember(String userName) {
		String subNumber = "";
		String now = TimerUtil.dateToStr(new Date());
		subNumber = now;
		subNumber = subNumber.replace("-", "");
		subNumber = subNumber.replace(" ", "");
		subNumber = subNumber.replace(":", "");
		Random r = new Random();
		subNumber = subNumber + randomNumeric(r, 8);
		return subNumber;
	}

	/**
	 * Random numeric.
	 * 
	 * @param random
	 *            the random
	 * @param count
	 *            the count
	 * @return the string
	 */
	private static String randomNumeric(Random random, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			int val = random.nextInt(10);
			sb.append(String.valueOf(val));
		}
		return sb.toString();
	}

	/**
	 * Random.
	 * 
	 * @param count
	 *            the count
	 * @return the int
	 */
	public static int random(int count) {
		Random random = new Random();
		return random.nextInt(count);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			int re = random(2);
			System.out.println(re);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("t2 - t1 = " + (t2 - t1));

	}

	/**
	 * 计算每一个购物车的价格.
	 * 设置总数total，不包括运费
	 * totalcash 包括所有商品费用和运费
	 * @param baskets
	 *            the baskets
	 * @return the double
	 */
	public static Double calculateTotalCash(List<Basket> baskets) {
		if (AppUtils.isNotBlank(baskets)) {
			Double totalcash = 0d;
			for (Basket bo : baskets) {
				try {
					double total = Arith.mul(bo.getBasketCount() == null ? 1d : bo.getBasketCount(), bo.getCash());
					bo.setTotal(total);
					if (bo.getCarriage() != null) {
						totalcash = Arith.add(totalcash, bo.getCarriage());
						totalcash = Arith.add(totalcash, bo.getTotal());
					}else{
						totalcash = Arith.add(totalcash, bo.getTotal());
					}
					
				} catch (Exception e) {
					log.error("calculateTotalCash ", e);
				}
			}
			return totalcash;
		} else {
			return 0d;
		}
	}
	
	/**
	 * 计算总积分
	 * @param baskets
	 * @return
	 */
	public static int calculateSubTotalPoints(List<Basket> baskets) {
		if (AppUtils.isNotBlank(baskets)) {
			int totalcash = 0;
			for (Basket bo : baskets) {
				try {
					int total =   bo.getBasketCount() * bo.getPoints(); //小计积分
					bo.setPointsSubtotal(total);
					totalcash = totalcash + total;
					
				} catch (Exception e) {
					log.error("calculateSubTotalPoints ", e);
				}
			}
			return totalcash;
		} else {
			return 0;
		}
	}
	
	public static long calculatePointsAllForSub(List<Sub> subList){
		if (AppUtils.isNotBlank(subList)) {
			long totalcash = 0;
			for (Sub sub : subList) {
				try {
				 
					totalcash = totalcash + sub.getScore();
					
				} catch (Exception e) {
					log.error("calculateSubTotalPoints ", e);
				}
			}
			return totalcash;
		} else {
			return 0;
		}
	}

	/**
	 * Calculate total cash.
	 * 
	 * @param basketMap
	 *            the basket map
	 * @return the double
	 */
	public static Double calculateTotalCash(Map<String, Basket> basketMap) {
		Double totalcash = 0d;
		for (Basket basket : basketMap.values()) {
			try {
				double total = Arith.mul(basket.getBasketCount() == null ? 1d : basket.getBasketCount(), basket.getCash());
				if (basket.getCarriage() != null) {
					total = Arith.add(total, basket.getCarriage());
				}
				basket.setTotal(total);
				totalcash = Arith.add(totalcash, basket.getTotal());
			} catch (Exception e) {
				log.error("convert count", e);
			}
		}
		return totalcash;
	}
	
	
/**
 * 总积分
 * @param basketMap
 * @return
 */
	public static int calculateSubTotalPoints(Map<String, Basket> basketMap) {
		int totalcash = 0;
		for (Basket basket : basketMap.values()) {
			try {
				int total = basket.getBasketCount() * basket.getPoints(); // 小计积分
				basket.setPointsSubtotal(total);
				totalcash = totalcash + total;
			} catch (Exception e) {
				log.error("convert PointsSubtotal", e);
			}
		}
		return totalcash;
	}

	/**
	 * Generate random.
	 * 
	 * @return the integer
	 */
	public static Integer generateRandom() {
		Random r = new Random();
		return new Integer(r.nextInt(100000));
	}

	/**
	 * Gets the dES.
	 * 
	 * @return the dES
	 */
	public static DES2 getDES() {
		return new DES2("!23done!");
	}
	
	//转换为页面可以显示的数据格式
	/**
	 * Convert entity.
	 *
	 * @param list the list
	 * @return the list
	 */
	public static List<StatusKeyValueEntity> convertKeyValueEntity(List<StatusKeyValueEntity> list, String selected){
		List<StatusKeyValueEntity> result = new ArrayList<StatusKeyValueEntity>();
		if(AppUtils.isNotBlank(list)){
			for (StatusKeyValueEntity key : list) {
				StatusKeyValueEntity entity = new StatusKeyValueEntity();
				entity.setKey(key.getKey());
				String value = key.getValue();
				value = ResourceBundleHelper.getString(key.getValue(), key.getValue());
				entity.setValue(value);
				if(key.getKey().equals(selected)){
					entity.setStatus("selected");
				}
				result.add(entity);
			}
		}
		return result;
	}
	
	public static List<StatusKeyValueEntity> convertKeyValueEntity(List<StatusKeyValueEntity> list){
		return convertKeyValueEntity(list, null);
	}

	/**
	 * 设置购物车总数,在内存中运算,避免每次读取数据库
	 * @param request
	 * @param count
	 */
	public static void setBasketTotalCount(HttpSession session, int count) {
		session.setAttribute(Constants.BASKET_TOTAL_COUNT, count );
	}
	
	/**
	 * 计算购物车总数
	 * @param request
	 * @param count
	 */
	public static void calBasketTotalCount(HttpSession session, int count) {
		Integer totalCount = (Integer)session.getAttribute(Constants.BASKET_TOTAL_COUNT);
		Integer result = totalCount + count;
		if(result >= 0){
			session.setAttribute(Constants.BASKET_TOTAL_COUNT, result);
		}
		
	}
	
	/**
	 * 生成购物车ID
	 * @param shopName
	 * @param prodId
	 * @param attribute
	 * @return
	 */
	public static String getBasketKey(String shopName, Long prodId, String attribute) {
		StringBuffer sb = new StringBuffer();
		sb.append(shopName).append(prodId).append(AppUtils.getCRC32(attribute));
		return sb.toString();
	}
}
