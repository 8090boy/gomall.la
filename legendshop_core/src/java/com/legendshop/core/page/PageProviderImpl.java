/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.page;

import java.util.Locale;

import com.legendshop.core.helper.ResourceBundleHelper;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class PageProviderImpl extends Pager implements PageProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.page.Pager#getBar(java.util.Locale,
	 * java.lang.String)
	 */
	public String getBar(Locale locale, String url) {
		String path = PagerUtil.getPath();
		StringBuilder str = new StringBuilder();
		if (isFirst()) {
			str.append("<img src=\"").append(path).append("/common/toobar/firstPageDisabled.gif\"><img src=\"").append(path)
					.append("/common/toobar/prevPageDisabled.gif\">&nbsp;");
		} else {
			str.append("<a href='").append(url).append("(1)'><img src=\"").append(path)
					.append("/common/toobar/firstPage.gif\"></a>&nbsp;").append("<a href='").append(url).append("(")
					.append(previous()).append(")'><img src=\"").append(path).append("/common/toobar/prevPage.gif\"></a>&nbsp;");
		}
		if (isLast() || (getRowsCount() == 0)) {
			str.append("<img src=\"").append(path).append("/common/toobar/nextPageDisabled.gif\"> <img src=\"").append(path)
					.append("/common/toobar/lastPageDisabled.gif\">&nbsp;");
		} else {
			str.append("<a href='").append(url).append("(").append(next()).append(")'><img src=\"").append(path)
					.append("/common/toobar/nextPage.gif\"></a>&nbsp;").append("<a href='").append(url).append("(")
					.append(getPageCount()).append(")'><img src=\"").append(path)
					.append("/common/toobar/lastPage.gif\"></a>&nbsp;");
		}
		str.append(ResourceBundleHelper.getString(locale, "pager.from", "  From ")).append("<b>").append(getOffset() + 1)
				.append("</b>").append(ResourceBundleHelper.getString(locale, "pager.to", " To ")).append("<b>")
				.append(getPageDactSize()).append("</b>");
		str.append(ResourceBundleHelper.getString(locale, "pager.total", ", Total ")).append("<b>").append(getRowsCount())
				.append("</b>").append(ResourceBundleHelper.getString(locale, "pager.items", " Items "));
		str.append("&nbsp;<img src=\"").append(path).append("/common/toobar/gotoPage.gif\">&nbsp;<select name='page' onChange=\"")
				.append(url).append("(this.options[this.selectedIndex].value)\">");
		int begin = (getCurPageNO() > 10) ? getCurPageNO() - 10 : 1;
		int end = (getPageCount() - getCurPageNO() > 10) ? getCurPageNO() + 10 : getPageCount();
		for (int i = begin; i <= end; i++) {
			if (i == getCurPageNO()) {
				str.append("<option value='").append(i).append("' selected>").append(i).append("</option>");
			} else {
				str.append("<option value='").append(i).append("'>").append(i).append("</option>");
			}

		}
		str.append("</select>");
		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.page.Pager#getBar(java.lang.String)
	 */
	@Override
	public String getBar(String url) {
		return getBar(null, url);
	}

}
