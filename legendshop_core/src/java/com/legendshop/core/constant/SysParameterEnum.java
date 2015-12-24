/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.constant;

import java.util.List;

/**
 * 系统配置参数
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * --------------------------------------------
 * ---------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ------------------
 * -----------------------------------------------------------------.
 */
public enum SysParameterEnum {

	/** The default shop. */
	DEFAULT_SHOP(String.class),

	/** The export size. */
	EXPORT_SIZE(Integer.class),

	/** The front page size. */
	FRONT_PAGE_SIZE(Integer.class),

	/** The lucene indexer content length. */
	LUCENE_INDEXER_CONTENT_LENGTH(Integer.class),

	/** The page size. */
	PAGE_SIZE(Integer.class),

	/** The search entity per page. */
	SEARCH_ENTITY_PER_PAGE(Integer.class),

	/** The search indexing enabled. */
	SEARCH_INDEXING_ENABLED(Boolean.class),

	/** The send mail. */
	SEND_MAIL(Boolean.class),

	/** The support mail list. */
	SUPPORT_MAIL_LIST(String.class),

	/** The lucene indexer db fetch count. */
	LUCENE_INDEXER_DB_FETCH_COUNT(Long.class),

	/** The max file size. */
	MAX_FILE_SIZE(Long.class),

	/** The max index jpg. */
	MAX_INDEX_JPG(Integer.class),

	/** The allowed upload file tpye. */
	ALLOWED_UPLOAD_FILE_TPYE(List.class),

	/** The open shop. */
	OPEN_SHOP(Boolean.class),

	/** The validation on open shop. */
	VALIDATION_ON_OPEN_SHOP(Boolean.class),

	/** The mail name. */
	MAIL_NAME(String.class),

	/** The mail host. */
	MAIL_HOST(String.class),

	/** The mail port. */
	MAIL_PORT(Integer.class),

	/** The mail password. */
	MAIL_PASSWORD(String.class),

	/** The mail stmp auth. */
	MAIL_STMP_AUTH(Boolean.class),

	/** The mail stmp timeout. */
	MAIL_STMP_TIMEOUT(String.class),

	/** The mail properties changed. */
	MAIL_PROPERTIES_CHANGED(Boolean.class),

	/** The validation from mail. */
	VALIDATION_FROM_MAIL(Boolean.class),

	/** The comment level. */
	COMMENT_LEVEL(String.class),

	/** The visit log enable. */
	VISIT_LOG_ENABLE(Boolean.class),

	/** The visit log index enable. */
	VISIT_LOG_INDEX_ENABLE(Boolean.class),

	/** The visit hw log enable. */
	VISIT_HW_LOG_ENABLE(Boolean.class),

	/** The login log enable. */
	LOGIN_LOG_ENABLE(Boolean.class),

	/** The lucene currently indexing. */
	LUCENE_CURRENTLY_INDEXING(String.class),

	/** The use score. */
	USE_SCORE(Boolean.class),

	/** 图片验证码. */
	VALIDATION_IMAGE(Boolean.class),

	/** 是否使用独立域名 *. */
	INDEPEND_DOMAIN(Boolean.class),

	/** 是否支持静态页面 *. */
	STATIC_PAGE_SUPPORT(Boolean.class);

	/** The clazz. */
	private final Class clazz;

	/**
	 * Instantiates a new parameter enum.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	private SysParameterEnum(Class clazz) {
		this.clazz = clazz;
	}

	/**
	 * Gets the clazz.
	 * 
	 * @return the clazz
	 */
	public Class getClazz() {
		return this.clazz;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println(SysParameterEnum.VISIT_LOG_INDEX_ENABLE.name() + " "
				+ SysParameterEnum.VISIT_LOG_INDEX_ENABLE.getClazz());
	}
}
