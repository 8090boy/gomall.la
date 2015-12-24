/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.spi.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.constant.PagePathCalculator;

/**
 * The Enum FowardPage.
 */
public enum FowardPage implements PageDefinition {
	/** The VARIABLE. 可变路径 */
	VARIABLE(""),
	/** The INDE x_ query. */
	INDEX_QUERY("/index"),
	
	HOME_QUERY("/home"),

	/** The Product View. */
	VIEWS("/views"),

	/** The AD v_ lis t_ query. */
	ADV_LIST_QUERY("/admin/advertisement/query"),

	/** The BRAN d_ lis t_ query. */
	BRAND_LIST_QUERY("/admin/brand/query"),

	/** The LIN k_ lis t_ query. */
	LINK_LIST_QUERY("/admin/externallink/query"),

	/** The HO t_ lis t_ query. */
	HOT_LIST_QUERY("/admin/hotsearch/query"),

	/** The IM g_ lis t_ query. */
	IMG_LIST_QUERY("/admin/imgFile/query"),

	/** The IJP g_ lis t_ query. */
	IJPG_LIST_QUERY("/admin/indexjpg/query"),

	/** The LOG o_ lis t_ query. */
	LOGO_LIST_QUERY("/admin/logo/query"),

	/** The LEAGU e_ lis t_ query. */
	LEAGUE_LIST_QUERY("/admin/myleague/query"),

	/** The NEW s_ lis t_ query. */
	NEWS_LIST_QUERY("/admin/news/query"),

	/** The NEWSCA t_ lis t_ query. */
	NEWSCAT_LIST_QUERY("/admin/newsCategory/query"),

	/** The NSOR t_ lis t_ query. */
	NSORT_LIST_QUERY("/admin/nsort/query"),

	/** The PA y_ typ e_ lis t_ query. */
	PAY_TYPE_LIST_QUERY("/admin/paytype/query"),

	/** The PRO d_ lis t_ query. */
	PROD_LIST_QUERY("/admin/product/query"),

	/** The SHO p_ detai l_ lis t_ query. */
	SHOP_DETAIL_LIST_QUERY("/admin/shopDetail/query"),
	
	SHOP_DETAIL_LOAD("/admin/shopDetail/load/"),

	/** The SOR t_ lis t_ query. */
	SORT_LIST_QUERY("/admin/sort/query"),

	GSORT_LIST_QUERY("/admin/gsort/query"),

	PARAM_LIST_QUERY("/admin/system/systemParameter/query"),

	PROD_COMM_LIST_QUERY("/admin/productcomment/query"),

	/** The USE r_ com m_ lis t_ query. */
	USER_COMM_LIST_QUERY("/admin/userComment/query"),

	VLOG_LIST_QUERY("/admin/visitLog/query"),

	USER_DETAIL_LIST_QUERY("/admin/userDetail/query"),

	PUB_LIST_QUERY("/admin/pub/query"),

	DYNAMIC_QUERY("/dynamic/query"),

	DELIVERYCORP_LIST_QUERY("/admin/deliveryCorp/query"),

	DELIVERYTYPE_LIST_QUERY("/admin/deliveryType/query"),

	/** The partner list query. */
	PARTNER_LIST_QUERY("/admin/partner/query"),

	/** The tag query. */
	TAG_QUERY("/admin/tag/query"),

	/** The event query. */
	EVENT_QUERY("/admin/event/query"),

	/** The prod spec list query. */
	PROD_SPEC_LIST_QUERY("/admin/prodspec/query"),

	PROD_CONSULT_LIST_QUERY("/admin/productConsult/query"),
	
	PRODUCTPROPERTY_LIST_QUERY("/admin/productProperty/query"), 
	
	
	NAVIGATION_LIST_QUERY("/admin/system/navigation/query"), 
	
	SENSITIVEWORD_LIST_QUERY("/admin/system/sensitiveWord/query"), 
	
	DISTRICT_LIST_PAGE("/admin/system/district/query");

	/** The value. */
	private final String value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.constant.PageDefinition#getValue(javax.servlet.http
	 * .HttpServletRequest)
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response) {
		return getValue(request, response, value, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.constant.PageDefinition#getValue(javax.servlet.http
	 * .HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String, com.legendshop.core.constant.PageDefinition)
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateActionPath("forward:", path);
	}

	/**
	 * Instantiates a new tiles page.
	 * 
	 * @param value
	 *            the value
	 */
	private FowardPage(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.constant.PageDefinition#getNativeValue()
	 */
	public String getNativeValue() {
		return value;
	}

}
