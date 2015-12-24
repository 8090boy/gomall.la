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
 * The Enum TilesPage.
 */
public enum TilesPage implements PageDefinition {
	/** The VARIABLE. 可变路径 */
	VARIABLE(""),

	/** The INDEX page. */
	INDEX_PAGE("index."),

	/** The N o_ login. */
	NO_LOGIN("loginhint."),

	/** The AFTE r_ operation. */
	AFTER_OPERATION("afteroperation."),

	/** The NSORT. */
	NSORT("nsort."),

	/** The LOGIN. */
	LOGIN(LOGIN_PATH),

	/** The LEAVEWORD. */
	LEAVEWORD("leaveword."),

	/** The NEWS. */
	NEWS("news."),
	
	/** 公告 */
	PUB("pub."),

	/** The AL l_ news. */
	ALL_NEWS("allnews."),

	/** The PAG e_ cash. */
	PAGE_CASH("cash."),

	/** The REG. */
	REG("reg."),

	/** The LEAGUE. */
	LEAGUE("league."),

	/** The PRODUCTSORT. */
	PRODUCT_SORT("productsort."),

	/** The SEARCHALL. */
	SEARCHALL("searchall."),

	/** The PAG e_ sub. */
	PAGE_SUB("savesub."),

	/** The MYACCOUNT. */
	MYACCOUNT("myaccount."),

	/** The SHOPCONTACT. */
	SHOPCONTACT("shopcontact."),

	/** The BUY. */
	BUY("buy."),

	/** The ORDER. */
	ORDER("order."),

	/** The OPENSHOP. */
	OPEN_SHOP("openshop."),

	/** The VIEWS. */
	VIEWS("views."),

	PRODUCT_COMMENT("productcomment."),

	/**
	 * C2C home page
	 */
	HOME("home."),

	/**
	 * All products sort page
	 */
	ALL_SORTS("allsorts."),

	/** The all prods. */
	ALL_PRODS("allprods."),

	/**
	 * All brands page
	 */
	ALL_BRAND("allbrands."),

	/**
	 * Customer leave message page
	 */
	LEAVE_MSG("leaveMsg."),

	/**
	 * The shop search page
	 */
	SHOP_SEARCH("shopSearch."),

	/**
	 * The warning message page
	 */
	WARNING("warning.");

	/** The value. */
	private final String value;

	private TilesPage(String value) {
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

	public String getValue(HttpServletRequest request, HttpServletResponse response, String path, PageDefinition pageDefinition) {
		return PagePathCalculator.calculateTilesPath(request, response, path, pageDefinition);
	}

	public String getNativeValue() {
		return value;
	}

}
