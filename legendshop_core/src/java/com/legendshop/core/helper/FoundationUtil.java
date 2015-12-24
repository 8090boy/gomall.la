/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.legendshop.core.AttributeKeys;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.FunctionEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.AbstractQuery;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.SqlQuery;
import com.legendshop.util.AppUtils;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class FoundationUtil {

	/**
	 * Have view all data function.
	 * 
	 * @param session
	 *            the session
	 * @return true, if successful
	 */
	public static boolean haveViewAllDataFunction(HttpServletRequest request) {
		return UserManager.hasFunction(request, FunctionEnum.FUNCTION_VIEW_ALL_DATA.value());
	}

	/**
	 * 是否外部排序.
	 * 
	 * @param cq
	 *            the cq
	 * @param request
	 *            the request
	 * @return true, if is data sort by external
	 */
	public static boolean isDataSortByExternal(CriteriaQuery cq, HttpServletRequest request) {
		// String sortColumn = request.getParameter((new
		// ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		String sortName = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		String sortOrder = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
		if (AppUtils.isNotBlank(sortName)) {
			if ("1".equals(sortOrder)) {
				cq.addOrder("desc", sortName);
			} else {
				cq.addOrder("asc", sortName);
			}
			return true;
		}
		return false;
	}

	/**
	 * 是否外部排序.
	 * 
	 * @param cq
	 *            the cq
	 * @param request
	 *            the request
	 * @param map
	 *            the map
	 * @return true, if is data sort by external
	 */
	public static boolean isDataSortByExternal(SqlQuery cq, HttpServletRequest request, Map<String, Object> map) {
		String sortName = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		String sortOrder = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
		if (AppUtils.isNotBlank(sortName)) {
			if ("1".equals(sortOrder)) {
				map.put(AttributeKeys.ORDER_INDICATOR, "order by " + sortName + " desc");
			} else {
				map.put(AttributeKeys.ORDER_INDICATOR, "order by " + sortName + " asc");
			}
			return true;
		}
		return false;
	}

	/**
	 * 是否外部排序.
	 * 
	 * @param hql
	 *            the hql
	 * @param request
	 *            the request
	 * @param map
	 *            the map
	 * @return true, if is data sort by external
	 */
	public static boolean isDataSortByExternal(HqlQuery hql, HttpServletRequest request, Map<String, Object> map) {
		// String sortColumn = request.getParameter((new
		// ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		String sortName = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		String sortOrder = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
		if (AppUtils.isNotBlank(sortName)) {
			if ("1".equals(sortOrder)) {
				map.put(AttributeKeys.ORDER_INDICATOR, "order by " + sortName + " desc");
			} else {
				map.put(AttributeKeys.ORDER_INDICATOR, "order by " + sortName + " asc");
			}
			return true;
		}
		return false;
	}

	/**
	 * true: export, false:normal.
	 * 
	 * @param cq
	 *            the cq
	 * @param request
	 *            the request
	 * @return true, if is data for export
	 */
	public static boolean isDataForExport(AbstractQuery cq, HttpServletRequest request) {
		String exportValue = request.getParameter(TableTagParameters.PARAMETER_EXPORTING);
		if (AppUtils.isNotBlank(exportValue)) {// 导出
			cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.EXPORT_SIZE, Integer.class));
			return true;
		}
		return false;
	}

	/**
	 * true: export, false:normal.
	 * 
	 * @param query
	 *            the query
	 * @param request
	 *            the request
	 * @return true, if is data for export
	 */
	public static boolean isDataForExport(SqlQuery query, HttpServletRequest request) {
		String exportValue = request.getParameter(TableTagParameters.PARAMETER_EXPORTING);
		if (AppUtils.isNotBlank(exportValue)) {// 导出
			query.setPageSize(PropertiesUtil.getObject(SysParameterEnum.EXPORT_SIZE, Integer.class));
			return true;
		}
		return false;
	}

	/**
	 * true: export, false:normal.
	 * 
	 * @param hql
	 *            the hql
	 * @param request
	 *            the request
	 * @return true, if is data for export
	 */
	public static boolean isDataForExport(HqlQuery hql, HttpServletRequest request) {
		String exportValue = request.getParameter(TableTagParameters.PARAMETER_EXPORTING);
		if (AppUtils.isNotBlank(exportValue)) {// 导出
			hql.setPageSize(PropertiesUtil.getObject(SysParameterEnum.EXPORT_SIZE, Integer.class));
			return true;
		}
		return false;
	}

	// 是否需要验证码
	public static boolean needToValidation(HttpSession session) {
		if (session == null) {
			return false;
		}
		Integer count = (Integer) session.getAttribute(AttributeKeys.TRY_LOGIN_COUNT);

		Boolean needToValidation = true;
		if (count == null || count < 3) {
			needToValidation = false;
		}
		Boolean result = PropertiesUtil.getBooleanObject(SysParameterEnum.VALIDATION_IMAGE.name());
		return (result != null && result) && needToValidation;
	}
}
