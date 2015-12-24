/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;

/**
 * The Class PlatFormViewPreparer.
 */
public class PlatFormViewPreparer implements ViewPreparer {

	/**
	 * 功能：.
	 * 
	 * @param tilesContext
	 *            the tiles context
	 * @param attributeContext
	 *            the attribute context
	 */
	public void execute(TilesRequestContext tilesContext, AttributeContext attributeContext) {
		String platformIdString = null;
		if (platformIdString == null) {
			platformIdString = "";
		}
		attributeContext.setTemplateAttribute(new Attribute("/WEB-INF/jsp/common/" + platformIdString + "header.jsp"));

	}

}
