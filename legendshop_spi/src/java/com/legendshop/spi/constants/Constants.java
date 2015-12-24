package com.legendshop.spi.constants;
import com.legendshop.core.AttributeKeys;

/**
 * LegendShop 常量.
 */
public class Constants implements AttributeKeys {

	/** 购物车. */
	public static final String BASKET_KEY = "BASKET_KEY";

	/** 当前购物车里面商品数量. */
	public static final String BASKET_COUNT = "BASKET_COUNT";

	/** 当前购物车里面总的商品数量. */
	public static final String BASKET_TOTAL_COUNT = "BASKET_TOTAL_COUNT";

	/** 购物车最大数量. */
	public static final int MAX_BASKET_SIZE = 50;

	/** The Constant PRODUCT_LESS. */
	public static final String SAVE_TO_CART_STATUS = "status";

	/** The Constant BASKET_TOTAL_CASH. */
	public static final String BASKET_TOTAL_CASH = "BASKET_TOTAL_CASH";

	/** The Constant BASKET_TOTAL_CASH. */
	public static final String POINTS_TOTAL = "POINTS_TOTAL";

	/** The Constant BASKET_HW_COUNT. */
	public static final String BASKET_HW_COUNT = "BASKET_HW_COUNT";

	/** The Constant BASKET_HW_ATTR. */
	public static final String BASKET_HW_ATTR = "BASKET_HW_ATTR";

	/** The Constant CLUB_COOIKES_NAME. */
	public static final String CLUB_COOIKES_NAME = "jforumUserInfo";

	/** The Constant REG_USER_EXIST. */
	public static final Integer REG_USER_EXIST = 1;

	/** The Constant REG_EMAIL_EXIST. */
	public static final Integer REG_EMAIL_EXIST = 2;

	/** The Constant REG_USER_EMAIL_EXIST. */
	public static final Integer REG_USER_EMAIL_EXIST = 3;

	/** The Constant REG_CHECK_NORMAL. */
	public static final Integer REG_CHECK_NORMAL = 0;

	/** The Constant PRODUCTTYPE_HW. */
	public static final Short PRODUCTTYPE_HW = 1;

	/** The Constant PRODUCTTYPE_NEWS. */
	public static final Short PRODUCTTYPE_NEWS = 2;

	/** The Constant PRODUCTTYPE_SHOP. */
	public static final Short PRODUCTTYPE_SHOP = 3;

	public static final Integer TEMPLATE_ATTRIBUTE = 1;

	/** The Constant ORDER_STATUS. */
	public static final String ORDER_STATUS = "ORDER_STATUS";

	/** The Constant TOKEN. */
	public static final String TOKEN = "SESSION_TOKEN"; // 放在session中的token

	// 950 ad
	/** The Constant USER_REG_ADV_950. */
	public static final String USER_REG_ADV_950 = "USER_REG_ADV_950";

	// 740 ad
	/** The Constant USER_REG_ADV_740. */
	public static final String USER_REG_ADV_740 = "USER_REG_ADV_740";

	/** The Constant LOGONED_COMMENT. */
	public static final String LOGONED_COMMENT = "LOGONED_COMMENT";

	/** The Constant ANONYMOUS_COMMENT. */
	public static final String ANONYMOUS_COMMENT = "ANONYMOUS_COMMENT";

	/** The Constant NO_COMMENT. */
	public static final String NO_COMMENT = "NO_COMMENT";

	/** The Constant BUYED_COMMENT. */
	public static final String BUYED_COMMENT = "BUYED_COMMENT";

	public static final String FAIL = "fail";

	public static final String SUCCESS = "OK";

	// 动态模版，商品动态属性和参数
	public static final String DYNAMIC_TYPE = "DYNAMIC_TYPE";

	// 动态属性和参数的JSON string
	public static final String DYN_TEMP_JSON = "dynTempJson";

	// 菜单
	public static final String MENU_LIST = "MENU_LIST";

}
