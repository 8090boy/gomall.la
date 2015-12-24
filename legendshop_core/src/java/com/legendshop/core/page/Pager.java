/**
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */

package com.legendshop.core.page;

import java.util.Locale;

/**
 * 分页封装类.
 */
public class Pager implements PageProvider {

	/** The app name. */
	private final String appName = "legendshop";

	/** The cur page no. */
	private int curPageNO; // 当前页

	/** The page size. */
	private int pageSize; // 每页显示的记录数

	/** The rows count. */
	private long rowsCount; // 记录行数

	/** The page count. */
	private int pageCount; // 页数

	/** The offset. */
	private int offset;

	/**
	 * Instantiates a new pager.
	 * 
	 * @param allCount
	 *            记录行数
	 * @param offset
	 *            记录开始数目
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public Pager(long allCount, int offset, int pageSize) {
		this.offset = offset;
		this.curPageNO = (offset == 0) ? 1 : (int) Math.ceil((double) offset / pageSize);
		this.pageSize = pageSize;
		this.rowsCount = allCount;
		this.pageCount = (int) Math.ceil((double) allCount / pageSize);
	}

	/**
	 * Inits the.
	 * 
	 * @param allCount
	 *            the all count
	 * @param offset
	 *            the offset
	 * @param pageSize
	 *            the page size
	 */
	public void init(long allCount, int offset, int pageSize) {
		this.offset = offset;
		this.curPageNO = (offset == 0) ? 1 : (int) Math.ceil((double) offset / pageSize);
		this.pageSize = pageSize;
		this.rowsCount = allCount;
		this.pageCount = (int) Math.ceil((double) allCount / pageSize);
	}

	/**
	 * Instantiates a new pager.
	 */
	public Pager() {
	}

	// getCurPage:返回当前的页数
	/**
	 * Gets the cur page no.
	 * 
	 * @return the cur page no
	 */
	public int getCurPageNO() {
		return curPageNO;
	}

	// getPageSize：返回分页大小
	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	// getRowsCount：返回总记录行数
	/**
	 * Gets the rows count.
	 * 
	 * @return the rows count
	 */
	public long getRowsCount() {
		return rowsCount;
	}

	// getPageCount：返回总页数
	/**
	 * Gets the page count.
	 * 
	 * @return the page count
	 */
	public int getPageCount() {
		return pageCount;
	}

	// 第一页
	/**
	 * First.
	 * 
	 * @return the int
	 */
	public int first() {
		return 1;
	}

	// 最后一页
	/**
	 * Last.
	 * 
	 * @return the int
	 */
	public int last() {
		return pageCount;
	}

	// 上一页
	/**
	 * Previous.
	 * 
	 * @return the int
	 */
	public int previous() {
		return (curPageNO - 1 < 1) ? 1 : curPageNO - 1;
	}

	// 下一页
	/**
	 * Next.
	 * 
	 * @return the int
	 */
	public int next() {
		return (curPageNO + 1 > pageCount) ? pageCount : curPageNO + 1;
	}

	// 第一页
	/**
	 * Checks if is first.
	 * 
	 * @return true, if is first
	 */
	public boolean isFirst() {
		return (curPageNO == 1) ? true : false;
	}

	// 第一页
	/**
	 * Checks if is last.
	 * 
	 * @return true, if is last
	 */
	public boolean isLast() {
		return (curPageNO == pageCount) ? true : false;
	}

	/**
	 * Sets the cur page no.
	 * 
	 * @param curPageNO
	 *            the new cur page no
	 */
	public void setCurPageNO(int curPageNO) {
		this.curPageNO = curPageNO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pager的值为 " + " curPageNO = " + curPageNO + " limit = " + pageSize + " rowsCount = " + rowsCount + " pageCount = "
				+ pageCount;
	}

	/**
	 * 获取工具条 不用图片的，用下拉框.
	 * 
	 * @param url
	 *            the url
	 * @return String
	 */
	public String getToolBar(String url) {
		String temp = "";
		if (url.indexOf("?") == -1) {
			temp = "?";
		} else {
			temp = "&";
		}
		StringBuffer str = new StringBuffer();
		if (isFirst()) {
			str.append("首页 上一页&nbsp;");
		} else {
			str.append("<a href='").append(url).append(temp).append("curPageNO=1'>首页</a>&nbsp;").append("<a href='").append(url)
					.append(temp).append("curPageNO=").append(previous()).append("'>上一页</a>&nbsp;");
		}
		if (isLast() || (rowsCount == 0)) {
			str.append("下一页 尾页&nbsp;");
		} else {
			str.append("<a href='").append(url).append(temp).append("curPageNO=").append(next()).append("'>下一页</a>&nbsp;")
					.append("<a href='").append(url).append(temp).append("curPageNO=").append(pageCount).append("'>尾页</a>&nbsp;");
		}
		str.append("&nbsp;共<b>").append(rowsCount).append("</b>条记录&nbsp;")
				.append("&nbsp;转到<select name='page' onChange=\"location='").append(url).append(temp)
				.append("curPageNO='+this.options[this.selectedIndex].value\">");
		int begin = (curPageNO > 10) ? curPageNO - 10 : 1;
		int end = (pageCount - curPageNO > 10) ? curPageNO + 10 : pageCount;
		for (int i = begin; i <= end; i++) {
			if (i == curPageNO) {
				str.append("<option value='").append(i).append("' selected>第").append(i).append("页</option>");
			} else {
				str.append("<option value='").append(i).append("'>第").append(i).append("页</option>");
			}
		}
		str.append("</select>");
		return str.toString();
	}

	/**
	 * Gets the tool bar_bak.
	 * 
	 * @param url
	 *            the url
	 * @return the tool bar_bak
	 */
	@Deprecated
	public String getToolBar_bak(String url) {
		String temp = "";
		if (url.indexOf("?") == -1) {
			temp = "?";
		} else {
			temp = "&";
		}
		String str = "";
		// str+="";
		if (isFirst()) {
			str += "首页 上一页&nbsp;";
		} else {
			str += "<a href='" + url + temp + "curPageNO=1'>首页</a>&nbsp;";
			str += "<a href='" + url + temp + "curPageNO=" + previous() + "'>上一页</a>&nbsp;";
		}
		if (isLast() || (rowsCount == 0)) {
			str += "下一页 尾页&nbsp;";
		} else {
			str += "<a href='" + url + temp + "curPageNO=" + next() + "'>下一页</a>&nbsp;";
			str += "<a href='" + url + temp + "curPageNO=" + pageCount + "'>尾页</a>&nbsp;";
		}
		str += "&nbsp;共<b>" + rowsCount + "</b>条记录&nbsp;";
		str += "&nbsp;转到<select name='page' onChange=\"location='" + url + temp
				+ "curPageNO='+this.options[this.selectedIndex].value\">";
		int begin = (curPageNO > 10) ? curPageNO - 10 : 1;
		int end = (pageCount - curPageNO > 10) ? curPageNO + 10 : pageCount;
		for (int i = begin; i <= end; i++) {
			if (i == curPageNO) {
				str += "<option value='" + i + "' selected>第" + i + "页</option>";
			} else {
				str += "<option value='" + i + "'>第" + i + "页</option>";
			}
		}
		str += "</select>";
		return str;
	}

	// 生成工具条，采用数字形式和POST方式
	/**
	 * Gets the tool bar english.
	 * 
	 * @param url
	 *            the url
	 * @return the tool bar english
	 */
	public String getToolBarEnglish(String url) {
		String str = "";
		if (isFirst()) {
			str += "First Previous&nbsp;";
		} else {
			str += "<a href='" + url + "(1)'>First</a>&nbsp;";
			str += "<a href='" + url + "(" + previous() + ")'>Previous</a>&nbsp;";
		}
		if (isLast() || (rowsCount == 0)) {
			str += "Next Last&nbsp;";
		} else {
			str += "<a href='" + url + "(" + next() + ")'>Next</a>&nbsp;";
			str += "<a href='" + url + "(" + pageCount + ")'>Last</a>&nbsp;";
		}
		str += "&nbsp;Total &nbsp;<b>" + rowsCount + "</b>&nbsp;Items&nbsp;";
		str += "&nbsp;To&nbsp;<select name='page' onChange=\"" + url + "(this.options[this.selectedIndex].value)\">";
		int begin = (curPageNO > 10) ? curPageNO - 10 : 1;
		int end = (pageCount - curPageNO > 10) ? curPageNO + 10 : pageCount;
		for (int i = begin; i <= end; i++) {
			if (i == curPageNO) {
				str += "<option value='" + i + "' selected>" + i + "</option>";
			} else {
				str += "<option value='" + i + "'>" + i + "</option>";
			}
		}
		str += "</select>";
		return str;
	}

	// 生成工具条，采用数字形式和POST方式
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.page.PageProvider#getBar(java.lang.String)
	 */
	public String getBar(String url) {
		String path = PagerUtil.getPath();
		StringBuilder str = new StringBuilder();
		if (isFirst()) {
			str.append("<img src=\"").append(path).append("/common/toobar/firstPageDisabled.gif\">  <img src=\"").append(path)
					.append("/common/toobar/prevPageDisabled.gif\">&nbsp;");
		} else {
			str.append("<a href='").append(url).append("(1)'><img src=\"").append(path)
					.append("/common/toobar/firstPage.gif\"></a>&nbsp;").append("<a href='").append(url).append("(")
					.append(previous()).append(")'><img src=\"").append(path).append("/common/toobar/prevPage.gif\"></a>&nbsp;");
		}
		if (isLast() || (rowsCount == 0)) {
			str.append("<img src=\"").append(path).append("/common/toobar/nextPageDisabled.gif\"> <img src=\"").append(path)
					.append("/common/toobar/lastPageDisabled.gif\">&nbsp;");
		} else {
			str.append("<a href='").append(url).append("(").append(next()).append(")'><img src=\"").append(path)
					.append("/common/toobar/nextPage.gif\"></a>&nbsp;").append("<a href='").append(url).append("(")
					.append(pageCount).append(")'><img src=\"").append(path).append("/common/toobar/lastPage.gif\"></a>&nbsp;");
		}
		str.append("&nbsp;Total &nbsp;<b>").append(rowsCount).append("</b>&nbsp;Items&nbsp;").append("&nbsp;<img src=\"")
				.append(path).append("/common/toobar/gotoPage.gif\">&nbsp;<select name='page' onChange=\"").append(url)
				.append("(this.options[this.selectedIndex].value)\">");
		int begin = (curPageNO >= 10) ? curPageNO - 10 : 1;
		int end = (pageCount - curPageNO > 10) ? curPageNO + 10 : pageCount;
		for (int i = begin; i <= end; i++) {
			if (i == curPageNO) {
				str.append("<option value='").append(i).append("' selected>").append(i).append("</option>");
			} else {
				str.append("<option value='").append(i).append("'>").append(i).append("</option>");
			}

		}
		str.append("</select>");
		return str.toString();
	}

	/**
	 * 获取工具条.
	 * 
	 * @param myaction
	 *            the myaction
	 * @param myform
	 *            the myform
	 * @return String
	 */
	public String getToolBar(String myaction, String myform) {
		String str = "";
		str += "<script language='javascript'>" + "\n";
		str += "function commonSubmit(val){" + "\n";
		// 校验是否全由数字组成
		str += "var patrn=/^[0-9]{1,20}$/;" + "\n";
		str += "if (!patrn.exec(val)){" + "\n";
		str += " alert(\"请输入有效页号！\");" + "\n";
		str += " return false ;" + "\n";
		str += " }else{" + "\n";
		str += "    document." + myform + ".action='" + myaction + "curPageNO='+val;" + "\n";
		str += "    document." + myform + ".submit();" + "\n";
		str += "    return true ;" + "\n";
		str += "} " + "\n";
		str += " }" + "\n";
		str += "</script>" + "\n";
		str += "&nbsp;共<b>" + rowsCount + "</b>条&nbsp;共" + pageCount + "页&nbsp;当前第" + curPageNO + "页&nbsp;&nbsp;&nbsp;";
		if ((curPageNO == 1) || (curPageNO == 0)) {
			str += "首页|前页|";
		} else {
			str += "<a onMouseMove=\"style.cursor='hand'\" onclick=\"commonSubmit(1)\"><b>首页</b></a>|";
			str += "<a onMouseMove=\"style.cursor='hand'\" onclick=\"commonSubmit(" + (curPageNO - 1) + ")\"><b>前页</b></a>|";
		}
		if ((curPageNO - pageCount == 0) || (pageCount == 0) || (pageCount == 1)) {
			str += "后页|尾页&nbsp;";
		} else {
			str += "<a onMouseMove=\"style.cursor='hand'\" onclick=\"commonSubmit(" + (curPageNO + 1) + ")\"><b>后页</b></a>|";
			str += "<a onMouseMove=\"style.cursor='hand'\" onclick=\"commonSubmit(" + pageCount + ")\"><b>尾页</b></a>";
		}

		if ((pageCount == 1) || (pageCount == 0)) {
			str += " &nbsp;转到:<input type=text maxLength=5  name=\"pageroffset\" size=3  onKeyPress=\"if (event.keyCode == 13) return commonSubmit(document.all.pageroffset.value)\" disabled> 页&nbsp;";
			// str+="<input name=goRun type=submit value=\"GO\" disabled>";
			str += "<INPUT type=image src='/" + appName
					+ "/images/pageGo.gif' onclick='return commonSubmit()' width=34 height=17 border=0 disabled='disabled'>";
		} else {
			str += " &nbsp;转到:<input type=text maxLength=5  name=\"pageroffsetll\" size=3  onKeyPress=\"if (event.keyCode == 13) return commonSubmit(document.all.pageroffsetll.value)\" > 页&nbsp;";
			// str+="<input name=goRun type=submit value=\"GO\" >";
			str += "<INPUT type=image src='/" + appName
					+ "/images/pageGo.gif' onclick='commonSubmit(document.all.pageroffsetll.value)' width=34 height=17 border=0 >";
		}
		return str;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Pager pager = new Pager();
		System.out.println(pager.getToolBar("search.do", "formName"));
	}

	/**
	 * Sets the page size.
	 * 
	 * @param pageSize
	 *            the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Sets the rows count.
	 * 
	 * @param rowsCount
	 *            the new rows count
	 */
	public void setRowsCount(long rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * Sets the page count.
	 * 
	 * @param pageCount
	 *            the new page count
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * Gets the offset.
	 * 
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Gets the page dact size.
	 * 
	 * @return the page dact size
	 */
	public long getPageDactSize() {
		long nextOrder = offset + pageSize;
		return nextOrder > rowsCount ? rowsCount : nextOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.page.PageProvider#getBar(java.util.Locale,
	 * java.lang.String)
	 */
	public String getBar(Locale locale, String url) {
		return getBar(url);
	}
}
