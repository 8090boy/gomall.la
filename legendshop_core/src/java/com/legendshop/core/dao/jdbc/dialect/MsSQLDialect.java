/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.jdbc.dialect;

/**
 * The Class MsSQLDialect.
 */
public class MsSQLDialect implements Dialect {

	/** The Constant SELECT. */
	private static final String SELECT = "select";

	/** The Constant FROM. */
	private static final String FROM = "from";

	/** The Constant DISTINCT. */
	private static final String DISTINCT = "distinct";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.dialect.Dialect#gePageSQL(java.lang.String,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public String getLimitString(String querySelect, int offset, int limit) {
		if (offset > 0) {
			throw new UnsupportedOperationException("query result offset is not supported");
		}
		return new StringBuilder(querySelect.length() + 8).append(querySelect)
				.insert(getAfterSelectInsertPoint(querySelect), " top " + limit).toString();
	}

	/**
	 * Gets the after select insert point.
	 * 
	 * @param sql
	 *            the sql
	 * @return the after select insert point
	 */
	private int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.core.dao.jdbc.dialect.Dialect#getLimitString(java.lang
	 * .String, boolean)
	 */
	public String getLimitString(String querySqlString, boolean hasOffset) {
		StringBuilder sb = new StringBuilder(querySqlString.trim().toLowerCase());

		int orderByIndex = sb.indexOf("order by");
		CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length()) : "ORDER BY CURRENT_TIMESTAMP";

		// Delete the order by clause at the end of the query
		if (orderByIndex > 0) {
			sb.delete(orderByIndex, orderByIndex + orderby.length());
		}

		// HHH-5715 bug fix
		replaceDistinctWithGroupBy(sb);

		insertRowNumberFunction(sb, orderby);

		// Wrap the query within a with statement:
		sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
		if(hasOffset){
			sb.append("WHERE __hibernate_row_nr__ BETWEEN ? AND ?");
		}else{
			sb.append("WHERE __hibernate_row_nr__  <= ?");
		}

		return sb.toString();
	}

	/**
	 * Replace distinct with group by.
	 * 
	 * @param sql
	 *            the sql
	 */
	protected static void replaceDistinctWithGroupBy(StringBuilder sql) {
		int distinctIndex = sql.indexOf(DISTINCT);
		if (distinctIndex > 0) {
			sql.delete(distinctIndex, distinctIndex + DISTINCT.length() + 1);
			sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
		}
	}

	/**
	 * Insert row number function.
	 * 
	 * @param sql
	 *            the sql
	 * @param orderby
	 *            the orderby
	 */
	protected static void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
		// Find the end of the select statement
		int selectEndIndex = sql.indexOf(SELECT) + SELECT.length();

		// Insert after the select statement the row_number() function:
		sql.insert(selectEndIndex, " ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__,");
	}

	/**
	 * Gets the select fields without aliases.
	 * 
	 * @param sql
	 *            the sql
	 * @return the select fields without aliases
	 */
	protected static CharSequence getSelectFieldsWithoutAliases(StringBuilder sql) {
		String select = sql.substring(sql.indexOf(SELECT) + SELECT.length(), sql.indexOf(FROM));

		// Strip the as clauses
		return stripAliases(select);
	}

	/**
	 * Strip aliases.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 */
	protected static String stripAliases(String str) {
		return str.replaceAll("\\sas[^,]+(,?)", "$1");
	}
}
