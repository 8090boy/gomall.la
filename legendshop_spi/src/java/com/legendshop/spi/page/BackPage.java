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
 * The Enum BackPage.
 */
public enum BackPage implements PageDefinition {

	/** The variable. */
	VARIABLE(""),

	/** The back error page. */
	BACK_ERROR_PAGE(ERROR_PAGE_PATH),

	/** The orderdetail. */
	ORDERDETAIL("/order/orderDetail"),

	/** The adv list page. */
	ADV_LIST_PAGE("/advertisement/advertisementList"),

	/** The adv edit page. */
	ADV_EDIT_PAGE("/advertisement/advertisement"),

	/** The brand list page. */
	BRAND_LIST_PAGE("/brand/brandList"),

	/** The brand edit page. */
	BRAND_EDIT_PAGE("/brand/brand"),

	/** The link list page. */
	LINK_LIST_PAGE("/externallink/externallinkList"),

	/** The link edit page. */
	LINK_EDIT_PAGE("/externallink/externallink"),

	/** The hot list page. */
	HOT_LIST_PAGE("/hotsearch/hotsearchList"),

	/** The hot edit page. */
	HOT_EDIT_PAGE("/hotsearch/hotsearch"),

	/** The img list page. */
	IMG_LIST_PAGE("/imgFile/imgFileList"),

	/** The img edit page. */
	IMG_EDIT_PAGE("/prod/prod"),

	/** The dash board. */
	DASH_BOARD("/dashboard/index"),

	/** The admin home. */
	ADMIN_HOME("/frame/index"),

	/** The admin top. */
	ADMIN_TOP("/frame/top"),

	/** The ijpg list page. */
	IJPG_LIST_PAGE("/indexjpg/indexjpgList"),

	/** The ijpg edit page. */
	IJPG_EDIT_PAGE("/indexjpg/indexjpg"),

	/** The upgrade page. */
	UPGRADE_PAGE("/dashboard/upgrade"),

	/** The login hist list page. */
	LOGIN_HIST_LIST_PAGE("/loginhistory/loginHistoryList"),

	/** The login hist sum page. */
	LOGIN_HIST_SUM_PAGE("/loginhistory/loginHistorySum"),

	/** The logo list page. */
	LOGO_LIST_PAGE("/logo/logoList"),

	/** The logo edit page. */
	LOGO_EDIT_PAGE("/logo/logo"),

	/** The lucene page. */
	LUCENE_PAGE("/lucene/reindexer"),

	/** The league list page. */
	LEAGUE_LIST_PAGE("/myleague/myleagueList"),

	/** The league edit page. */
	LEAGUE_EDIT_PAGE("/myleague/myleague"),

	/** The news list page. */
	NEWS_LIST_PAGE("/news/newsList"),

	/** The news edit page. */
	NEWS_EDIT_PAGE("/news/news"),

	/** The newscat list page. */
	NEWSCAT_LIST_PAGE("/newsCategory/newsCategoryList"),

	/** The newscat edit page. */
	NEWSCAT_EDIT_PAGE("/newsCategory/newsCategory"),

	/** The nsort list page. */
	NSORT_LIST_PAGE("/nsort/nsortList"),

	/** The nsort edit page. */
	NSORT_EDIT_PAGE("/nsort/nsort"),

	/** The nsort appendbrand page. */
	NSORT_APPENDBRAND_PAGE("/nsort/appendBrand"),

	/** The processing order list page. */
	PROCESSING_ORDER_LIST_PAGE("/order/processingOrder"),

	/** The processed order list page. */
	PROCESSED_ORDER_LIST_PAGE("/order/processedOrder"),

	/** The pay page. */
	PAY_PAGE("/payment/payto"),

	/** The pay type list page. */
	PAY_TYPE_LIST_PAGE("/payType/payTypeList"),

	/** The pay type edit page. */
	PAY_TYPE_EDIT_PAGE("/payType/payType"),

	/** The prod list page. */
	PROD_LIST_PAGE("/prod/prodList"),
	
	/** 产品页面AJAX call返回页面**/
	PROD_CONTENT_LIST_PAGE("/prod/prodContentList"),

	PROD_LIST_PAGE_TEMP("/prod/prodListTemp"),

	/** The prod edit page. */
	PROD_EDIT_PAGE("/prod/prod"),
	
	/** The prod edit new page. */
	PROD_EDIT_NEW_PAGE("/prod/prodNew"),

	PROD_PUBLISH_PAGE("/sort/publish"),
	
	PUBLISH_SORT_QUERY_PAGE("/sort/loadSort"),
	
	PUBLISH_NSORT_QUERY_PAGE("/sort/loadNsort"),
	
	PUBLISH_SUBSORT_QUERY_PAGE("/sort/loadSubSort"),
	
	PUBLISH_BRAND_QUERY_PAGE("/sort/loadBrand"),
	
	PROD_PUBLISH2_PAGE("/sort/publish2"),
	
	PROD_PUBLISH3_PAGE("/sort/publish3"),
	
	PROD_PUBLISH4_PAGE("/sort/publish4"),
	
	PROD_CATEGORY_2_PAGE("/sort/category2"),
	
	PROD_CATEGORY_3_PAGE("/sort/category3"),
	
	/** The prod property page. */
	PROD_EDIT_PROPERTY_PAGE("/prod/productProperty"),
	
	/** The prod details page. */
	PROD_EDIT_DETAILS_PAGE("/prod/productDetails"),
	
	/** The prod saleProperty page. */
	PROD_EDIT_SALE_PROP_PAGE("/prod/saleProperty"),

	/** The append product. */
	APPEND_PRODUCT("/prod/appendProduct"),

	/** The createp roduct step. */
	CREATEP_RODUCT_STEP("/prod/createProductStep"),

	/** The prod comm list page. */
	PROD_COMM_LIST_PAGE("/prodComment/prodCommentList"),

	/** The prod comm edit page. */
	PROD_COMM_EDIT_PAGE("/prodComment/prodComment"),

	/** The pub list page. */
	PUB_LIST_PAGE("/pub/pubList"),

	/** The pub edit page. */
	PUB_EDIT_PAGE("/pub/pub"),

	/** The shop detail list page. */
	SHOP_DETAIL_LIST_PAGE("/shopDetail/shopDetailList"),

	/** The shop detail edit page. */
	SHOP_DETAIL_EDIT_PAGE("/shopDetail/shopDetail"),

	/** The sort list page. */
	SORT_LIST_PAGE("/sort/sortList"),

	/** The sort edit page. */
	SORT_EDIT_PAGE("/sort/sort"),

	/** The param list page. */
	PARAM_LIST_PAGE("/systemParameter/systemParameterList"),

	/** The cache list page. */
	CACHE_LIST_PAGE("/cache/cacheList"),

	/** The html list page. */
	HTML_LIST_PAGE("/cache/htmlList"),

	/** The cache list page. */
	PLUGIN_LIST_PAGE("/plugin/pluginList"),

	/** The param edit page. */
	PARAM_EDIT_PAGE("/systemParameter/systemParameter"),

	/** The user comm list page. */
	USER_COMM_LIST_PAGE("/userComment/userCommentList"),

	/** The user comm edit page. */
	USER_COMM_EDIT_PAGE("/userComment/userComment"),

	/** The user detail list page. */
	USER_DETAIL_LIST_PAGE("/userDetail/userDetailList"),

	/** The vlog list page. */
	VLOG_LIST_PAGE("/visitLog/visitLogList"),

	/** The vlog edit page. */
	VLOG_EDIT_PAGE("/visitLog/visitLog"),

	/** The show dynamic attribute. */
	SHOW_DYNAMIC_ATTRIBUTE("/xml/showDynamicAttribute"),

	/** The show dynamic. */
	SHOW_DYNAMIC("/xml/showdynamic"),

	/** The dynamic attribute. */
	DYNAMIC_ATTRIBUTE("/xml/dynamicAttribute"),

	/** The modifyprice. */
	MODIFYPRICE("/order/modifyPrice"),

	/** The deliverycorp list page. */
	DELIVERYCORP_LIST_PAGE("/deliveryCorp/deliveryCorpList"),

	/** The deliverycorp edit page. */
	DELIVERYCORP_EDIT_PAGE("/deliveryCorp/deliveryCorp"),

	/** The deliverytype list page. */
	DELIVERYTYPE_LIST_PAGE("/deliveryType/deliveryTypeList"),

	/** The deliverytype edit page. */
	DELIVERYTYPE_EDIT_PAGE("/deliveryType/deliveryType"),

	/** The partner list page. */
	PARTNER_LIST_PAGE("/partner/partnerList"),

	/** The partner edit page. */
	PARTNER_EDIT_PAGE("/partner/partner"),

	/** The partner change password page. */
	PARTNER_CHANGE_PASSWORD_PAGE("/partner/partnerChangePassword"),

	/** The tag list. */
	TAG_LIST("/tag/tagList"),

	/** The tag. */
	TAG("/tag/tag"),

	EVENT_LIST_PAGE("/event/eventList"),

	EVENT_EDIT_PAGE("/event/event"),

	PRODUCTSPEC_LIST_PAGE("/productspec/prodSpecList"),

	PRODUCTSPEC_EDIT_PAGE("/productspec/prodSpec"),

	FILE_EDIT_PAGE("/file/edit"),

	PROD_CONSULT_LIST_PAGE("/prodConsult/productConsultList"),

	PROD_CONSULT_EDIT_PAGE("/prodConsult/productConsult"), 
	
	PRODUCTPROPERTY_LIST_PAGE("/productProperty/productPropertyList"),
	
	PRODUCTPROPERTY_EDIT_PAGE("/productProperty/productProperty") ,
	
	//后台菜单
	MENU("/frame/menu"), 
	
	NAVIGATION_LIST_PAGE("/navigation/navigationList"), 
	
	
	NAVIGATION_EDIT_PAGE("/navigation/navigation"),
	
	NAVIGATIONITEM_EDIT_PAGE("/navigationItem/navigationItem"),
	
	SENSITIVEWORD_LIST_PAGE("/sensitiveWord/sensitiveWordList"), 
	
	SENSITIVEWORD_EDIT_PAGE("/sensitiveWord/sensitiveWord"), 
	
	DISTRICT_LIST_PAGE("/district/districtList"),
	
	RPOVINCE_PAGE("/district/province"),
	
	CITY_PAGE("/district/city"),
	
	AREA_PAGE("/district/area"),
	
	PROVINCE_CONTENT_LIST_PAGE("/district/provinceContent"),
	
	CITY_CONTENT_LIST_PAGE("/district/cityContent"),
	
	AREA_CONTENT_LIST_PAGE("/district/areaContent")

	;

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new back page.
	 * 
	 * @param value
	 *            the value
	 * @param template
	 *            the template
	 */
	private BackPage(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.constant.PageDefinition#getValue(javax.servlet.http
	 * .HttpServletRequest)
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response) {
		return getValue(request, response, value, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.constant.PageDefinition#getValue(javax.servlet.http
	 * .HttpServletRequest, java.lang.String)
	 */
	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateBackendPath(request, response, path, pageDefinition);
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
