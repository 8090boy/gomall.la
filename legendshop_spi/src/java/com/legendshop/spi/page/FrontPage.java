package com.legendshop.spi.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legendshop.core.constant.PageDefinition;
import com.legendshop.core.constant.PagePathCalculator;

/**
 * The Enum FrontPage.
 */
public enum FrontPage implements PageDefinition {
	/** The VARIABLE. 可变路径 */
	VARIABLE(""),
	/** The ERRO r_ page. */
	ERROR_PAGE(ERROR_PAGE_PATH),

	/** The INSTALL. */
	INSTALL("/install/index"),
 

	/** The AL l_ page. */
	ALL_PAGE("/all"),

	/** The FAIL. */
	FAIL("/fail"),

	/** The TOPALL. */
	TOPALL("/topAll"),

	/** The TOP. */
	TOP("/top"),

	/** The TOP. */
	TOP_USER_INFO("/userinfo"),

	/** The TOP_SORT. */
	TOP_SORT("/topsort"),

	/** The TOP_SORT_NEWS. */
	TOP_SORT_NEWS("/topsortnews"),

	/** The TOP_NEWS. */
	TOP_NEWS("/topnews"),

	/** The COPY Page. */
	COPY("/copy"),

	/** The HOT_ON. */
	HOT_ON("/hoton"),

	/** The HOT_SALE. */
	HOT_SALE("/hotsale"),

	/** The FRIEND_LINK. */
	FRIEND_LINK("/friendlink"),

	/** The HOT_VIEW. */
	HOT_VIEW("/hotview"),

	HOT_COMMENT("/hotcomment"),

	HOT_RECOMMEND("/hotrecommend"),

	/** The ALL. */
	ALL("/all"),

	/** The PRO d_ pi c_ gallery. */
	PROD_PIC_GALLERY("/gallery"),

	/** The IPSEARCH. */
	IPSEARCH("/ipsearch"),

	/** The BOUGHT. */
	BOUGHT("/bought"),

	/** The CAS h_ save. */
	CASH_SAVE("/cashsave"),

	/** The RESETPASSWORD. */
	RESETPASSWORD("/resetpassword"),

	/** the top page. */
	HOME_TOP("/home/top"),

	/** the common bottom page. */
	BOTTOM("/bottom"),

	// 产品查看历史
	/** The visited prod. */
	VISITED_PROD("/visitedprod"),

	// 商铺产看历史
	/** The visited shop. */
	VISITED_SHOP("/visitedshop"),

	// The user's order information in user center module
	ORDER("/order"),

	AJAX_SEARCH("/ajaxsearch"),

	PRODUCT_SORT_LIST("/prodsortlist"),

	SORT_LIST("/sortlist"),

	PRODUCT_COMMENTS("/prodcomments"),

	PRODUCT_COMMENT_LIST("/prodcommentlist"),

	PRODUCT_CONSULTS("/prodconsults"),

	PRODUCT_CONSULTS_LIST("/prodconsultlist"), ;

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new front page.
	 * 
	 * @param value
	 *            the value
	 * @param template
	 *            the template
	 */
	private FrontPage(String value, String... template) {
		this.value = value;
	}

	public String getValue(HttpServletRequest request, HttpServletResponse response) {
		return getValue(request, response, value, this);
	}

	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateFronendPath(request, response, path, pageDefinition);
	}

	public String getNativeValue() {
		return value;
	}

}
