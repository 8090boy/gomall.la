/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.tag;

import javax.servlet.jsp.tagext.TagSupport;

import com.legendshop.core.helper.FoundationUtil;

/**
 * The Class UserValidationImageTag.
 */
public class UserValidationImageTag extends TagSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8943927608529578818L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() {
		Boolean result = FoundationUtil.needToValidation(pageContext.getSession());
		if (result) {
			return TagSupport.EVAL_BODY_INCLUDE;
		} else {
			return TagSupport.SKIP_BODY;
		}
	}

}
