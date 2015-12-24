package com.legendshop.core;

import com.legendshop.BaseAttributeKeys;
import com.legendshop.core.helper.PropertiesUtil;

 
public interface AttributeKeys extends BaseAttributeKeys {

	/** The Constant BACKUP_IMAGE. */
	public static final String BACKUP_IMAGE = "backupImage";

	/** The Constant SMALL_IMAGE. */
	public static final String SMALL_IMAGE = "smallImage";

	/** The Constant DOWNLOAD_PATH. */
	public static final String DOWNLOAD_PATH = "WEB-INF/download";

	public static final String HTML_COMMON = "html/";

	/** The Constant LEGENDSHOP_DOMAIN_NAME. */
	public static final String LEGENDSHOP_DOMAIN_NAME = "http://www.kingbloc.com";

	/** The Constant BIG_IMAGE. */
	public static final String BIG_IMAGE = "bigImage";

	// assign at installing
	/** The Constant DOMAIN_NAME. */
	public static final String DOMAIN_NAME = "DOMAIN_NAME";

	/** The Constant LUCENE_PATH. */
	public static final String LUCENE_PATH = "WEB-INF/luceneIndex";

	/** The Constant SIMPLE_PAGE_PROVIDER. */
	public static final String SIMPLE_PAGE_PROVIDER = "SimplePageProvider";

	/** The Constant LOCALE_FILE. */
	public static final String LOCALE_FILE = "i18n/ApplicationResources";

	/** The Constant LEGENSHOP_LICENSE. */
	public static final String LEGENSHOP_LICENSE = "LEGENSHOP_LICENSE";

	/** The Constant UN_AUTH_MSG. */
	public static final String UN_AUTH_MSG = "UN_AUTH_MSG";

	/** The Constant AUTH_NORMAL. */
	public static final String AUTH_NORMAL = "NORMAL";

	/** The Constant USER_NAME. */
	public static final String USER_NAME = "SPRING_SECURITY_LAST_USERNAME";

	/** The Constant SPRING_SECURITY_CONTEXT. */
	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	/** The Constant AUTH_SERVICE_NAME. */
	public static final String AUTH_SERVICE_NAME = "authService";

	/** The Constant RETURN_URL. */
	public static final String RETURN_URL = "returnUrl";

	/** The Constant RUNTIME_MODE. */
	public static final String RUNTIME_MODE = "RUNTIME_MODE";

	/** The Constant BUSINESS_MODE. */
	public static final String BUSINESS_MODE = "BUSINESS_MODE";

	/** The Constant LANGUAGE_MODE. */
	public static final String LANGUAGE_MODE = "LANGUAGE_MODE";

	/** The Constant ORDER_INDICATOR. */
	public static final String ORDER_INDICATOR = "orderIndicator";

	/** The Constant SHOP_NAME. */
	public static final String SHOP_NAME = "shopName";

	/** The Constant SHOP_DETAIL. */
	public static final String SHOP_DETAIL = "shopDetail";

	public static final String LOCALE_KEY = "LOCALE_KEY";

	public static final String EDITOR_PIC_PATH = "/editor";

	public static final String RANDNUMBER = "LEGENDSHOP_RANDNUM";

	public static final String LOCALE_RESOLVER = "localeResolver";

	public static final String LOCALE_MESSAGE_PREFIX = "message:";

	public static final String DEFAULT_PAGE = "default";

	/** The Constant VISIT_HISTORY. */
	public static final String VISIT_HISTORY = "VisitHistory";

	public static final String IMAGES_PATH_PREFIX = "IMAGES_PATH_PREFIX";

	public static final String PHOTO_PATH_PREFIX = "connector.userFilesPath";

	/**
	 * 语言
	 */
	public static final String SESSION_LANGSTYLE = "LegendShopLanguage";

	/**
	 * 登录重复次数
	 */
	public static final String TRY_LOGIN_COUNT = "TRY_LOGIN_COUNT";

}
