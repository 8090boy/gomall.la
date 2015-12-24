/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.StringUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

import com.legendshop.core.UserManager;
import com.legendshop.core.security.GrantedFunction;
import com.legendshop.core.security.GrantedFunctionImpl;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
@SuppressWarnings("serial")
public class AuthorizeActionTag extends TagSupport {

	/** The if all granted. */
	private String ifAllGranted = "";

	/** The if any granted. */
	private String ifAnyGranted = "";

	/** The if not granted. */
	private String ifNotGranted = "";

	public AuthorizeActionTag() {

	}

	/**
	 * Sets the if all granted.
	 * 
	 * @param ifAllGranted
	 *            the new if all granted
	 * @throws JspException
	 *             the jsp exception
	 */
	public void setIfAllGranted(String ifAllGranted) throws JspException {
		this.ifAllGranted = ifAllGranted;
	}

	/**
	 * Gets the if all granted.
	 * 
	 * @return the if all granted
	 */
	public String getIfAllGranted() {
		return ifAllGranted;
	}

	/**
	 * Sets the if any granted.
	 * 
	 * @param ifAnyGranted
	 *            the new if any granted
	 * @throws JspException
	 *             the jsp exception
	 */
	public void setIfAnyGranted(String ifAnyGranted) throws JspException {
		this.ifAnyGranted = ifAnyGranted;
	}

	/**
	 * Gets the if any granted.
	 * 
	 * @return the if any granted
	 */
	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	/**
	 * Sets the if not granted.
	 * 
	 * @param ifNotGranted
	 *            the new if not granted
	 * @throws JspException
	 *             the jsp exception
	 */
	public void setIfNotGranted(String ifNotGranted) throws JspException {
		this.ifNotGranted = ifNotGranted;
	}

	/**
	 * Gets the if not granted.
	 * 
	 * @return the if not granted
	 */
	public String getIfNotGranted() {
		return ifNotGranted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		if (((null == ifAllGranted) || "".equals(ifAllGranted)) && ((null == ifAnyGranted) || "".equals(ifAnyGranted))
				&& ((null == ifNotGranted) || "".equals(ifNotGranted))) {
			return Tag.SKIP_BODY;
		}

		String evaledIfNotGranted = ExpressionEvaluationUtils.evaluateString("ifNotGranted", ifNotGranted, pageContext);
		Collection<GrantedFunction> granted = UserManager.getPrincipalFunctionByAuthorities(pageContext.getSession());
		if ((null != evaledIfNotGranted) && !"".equals(evaledIfNotGranted)) {
			Set grantedCopy = retainAll(granted, parseSecurityString(evaledIfNotGranted));
			if (!grantedCopy.isEmpty()) {
				return Tag.SKIP_BODY;
			}
		}

		final String evaledIfAllGranted = ExpressionEvaluationUtils.evaluateString("ifAllGranted", ifAllGranted, pageContext);

		if ((null != evaledIfAllGranted) && !"".equals(evaledIfAllGranted)) {
			if (!granted.containsAll(parseSecurityString(evaledIfAllGranted))) {
				return Tag.SKIP_BODY;
			}
		}

		final String evaledIfAnyGranted = ExpressionEvaluationUtils.evaluateString("ifAnyGranted", ifAnyGranted, pageContext);

		if ((null != evaledIfAnyGranted) && !"".equals(evaledIfAnyGranted)) {
			Set grantedCopy = retainAll(granted, parseSecurityString(evaledIfAnyGranted));

			if (grantedCopy.isEmpty()) {
				return Tag.SKIP_BODY;
			}
		}

		return Tag.EVAL_BODY_INCLUDE;
	}

	/**
	 * 得到用户功能（Function）的集合，并且验证是否合法，而且可以过滤重复项.
	 * 
	 * @param c
	 *            Collection 类型
	 * @return Set类型
	 */
	private Set SecurityObjectToFunctions(Collection c) {
		Set target = new HashSet();

		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			GrantedFunction function = (GrantedFunction) iterator.next();

			if (null == function.getFunction()) {
				throw new IllegalArgumentException(
						"Cannot process GrantedFunction objects which return null from getFunction() - attempting to process "
								+ function.toString());
			}

			target.add(function.getFunction());
		}

		return target;
	}

	/**
	 * 处理页面标志属性 ，用' ，'区分,返回Set型数据过滤重复项.
	 * 
	 * @param functionsString
	 *            the functions string
	 * @return the sets the
	 */
	private Set parseSecurityString(String functionsString) {
		final Set requiredFunctions = new HashSet();
		final String[] functions = StringUtils.commaDelimitedListToStringArray(functionsString);
		for (String authority : functions) {
			// Remove the role's whitespace characters without depending on JDK
			// 1.4+
			// Includes space, tab, new line, carriage return and form feed.
			String function = StringUtils.replace(authority, " ", "");
			function = StringUtils.replace(function, "\t", "");
			function = StringUtils.replace(function, "\r", "");
			function = StringUtils.replace(function, "\n", "");
			function = StringUtils.replace(function, "\f", "");

			requiredFunctions.add(new GrantedFunctionImpl(function));
		}

		return requiredFunctions;
	}

	/**
	 * 获得用户所拥有的Function 和 要求的 Function 的交集.
	 * 
	 * @param granted
	 *            用户已经获得的Function
	 * @param required
	 *            所需要的Function
	 * @return the sets the
	 */

	private Set retainAll(final Collection granted, final Set required) {
		Set grantedFunction = SecurityObjectToFunctions(granted);
		Set requiredFunction = SecurityObjectToFunctions(required);
		// retailAll() 获得 grantedFunction 和 requiredFunction 的交集
		// 即删除 grantedFunction 中 除了 requiredFunction 的项
		grantedFunction.retainAll(requiredFunction);

		return rolesToAuthorities(grantedFunction, granted);
	}

	/**
	 * Roles to authorities.
	 * 
	 * @param grantedFunctions
	 *            已经被过滤过的Function
	 * @param granted
	 *            未被过滤过的，即用户所拥有的Function
	 * @return the sets the
	 */
	private Set rolesToAuthorities(Set grantedFunctions, Collection granted) {
		Set target = new HashSet();

		for (Iterator iterator = grantedFunctions.iterator(); iterator.hasNext();) {
			String function = (String) iterator.next();

			for (Iterator grantedIterator = granted.iterator(); grantedIterator.hasNext();) {
				GrantedFunction grantedFunction = (GrantedFunction) grantedIterator.next();

				if (grantedFunction.getFunction().equals(function)) {
					target.add(grantedFunction);

					break;
				}
			}
		}

		return target;
	}
}
