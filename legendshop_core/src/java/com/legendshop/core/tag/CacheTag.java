/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.legendshop.core.cache.LegendCacheManager;
import com.legendshop.util.ContextServiceLocator;

/**
 * The Class CacheTag.
 */
public class CacheTag extends BodyTagSupport implements TryCatchFinally {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -262317675765477229L;

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CacheTag.class);

	/** The legend cache manager. */
	private LegendCacheManager legendCacheManager;

	/** if cache key is null, the request URI is used. */
	private String key = null;

	/** The cache name. */
	private String cacheName = null;

	/**
	 * Instantiates a new cache tag.
	 */
	public CacheTag() {
		if (legendCacheManager == null) {
			legendCacheManager = (LegendCacheManager) ContextServiceLocator.getInstance().getBean("cacheManager");
		}
	}

	/**
	 * After the cache body, either update the cache, serve new cached content
	 * or indicate an error.
	 * 
	 * @return The standard BodyTag return.
	 * @throws JspTagException
	 *             The standard exception thrown.
	 */
	@Override
	public int doAfterBody() throws JspTagException {
		String body = null;
		try {
			// if we have a body, and we have not been told to use the cached
			// version
			if ((bodyContent != null) && ((body = bodyContent.getString()) != null)) {
				Cache cache = legendCacheManager.getCache(cacheName);
				cache.put(generateKey(), body);
				bodyContent.clearBody();
				bodyContent.write(body);
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			}
		} catch (java.io.IOException e) {
			throw new JspTagException("IO Error: " + e.getMessage());
		}

		return SKIP_BODY;
	}

	private String generateKey() {
		return ((HttpServletRequest) pageContext.getRequest()).getRequestURI() + key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.jsp.tagext.TryCatchFinally#doCatch(java.lang.Throwable)
	 */
	public void doCatch(Throwable throwable) throws Throwable {
		throw throwable;
	}

	/**
	 * The end tag - clean up variables used.
	 * 
	 * @return The standard BodyTag return.
	 * @throws JspTagException
	 *             The standard exception thrown.
	 */
	@Override
	public int doEndTag() throws JspTagException {
		return EVAL_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TryCatchFinally#doFinally()
	 */
	public void doFinally() {
		key = null;
	}

	/**
	 * The start of the tag.
	 * <p>
	 * Grabs the administrator, the cache, the specific cache entry, then
	 * decides whether to refresh.
	 * <p>
	 * If no refresh is needed, this serves the cached content directly.
	 * 
	 * @return The standard doStartTag() return.
	 * @throws JspTagException
	 *             The standard exception thrown.
	 */
	@Override
	public int doStartTag() throws JspTagException {
		// We can only skip the body if the cache has the data
		int returnCode = EVAL_BODY_BUFFERED;
		String actualKey = generateKey();
		try {

			Cache cache = legendCacheManager.getCache(cacheName);
			SimpleValueWrapper valueWrapper = (SimpleValueWrapper) cache.get(actualKey);
			if (log.isDebugEnabled()) {
				log.debug("<cache>: Using cached entry : " + actualKey);
			}

			// Ensure that the cache returns the data correctly. Else
			// re-evaluate the body
			if ((valueWrapper != null)) {
				pageContext.getOut().write((String) valueWrapper.get());
				returnCode = SKIP_BODY;
			}
		} catch (IOException e) {
			throw new JspTagException("IO Exception: " + e.getMessage());
		}

		if (returnCode == EVAL_BODY_BUFFERED) {
			if (log.isDebugEnabled()) {
				log.debug("<cache>: Cached content not used: New cache entry, cache stale or scope flushed : " + actualKey);
			}
		}

		return returnCode;
	}

	/**
	 * Sets the key.
	 * 
	 * @param key
	 *            the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Sets the cache name.
	 * 
	 * @param cacheName
	 *            the new cache name
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
}
