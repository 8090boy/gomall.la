/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.jdbc.dialect;

/**
 * The Class OracleDialect.
 */
public class OracleDialect implements Dialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.dialect.Dialect#gePageSQL(java.lang.String,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String getLimitString(String querySelect, int offset, int limit) {
		StringBuilder sb = new StringBuilder(querySelect.length() + 100);
		sb.append("SELECT * FROM (SELECT ROW_.*, ROWNUM RN FROM (");
		sb.append(querySelect);
		sb.append(") ROW_ WHERE ROWNUM <=");
		sb.append(offset + limit);
		sb.append(") WHERE RN>=");
		sb.append(offset);
		return sb.toString();
	}

	public String getLimitString(String sql, boolean hasOffset) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (hasOffset) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
		} else {
			pagingSelect.append(" ) where rownum <= ?");
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
