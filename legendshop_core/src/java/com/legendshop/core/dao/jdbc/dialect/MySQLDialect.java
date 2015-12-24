package com.legendshop.core.dao.jdbc.dialect;

/**
 * MySQLDialect.
 */
public class MySQLDialect implements Dialect {

	public String getLimitString(String querySelect, int offset, int limit) {
		StringBuilder buf = new StringBuilder(querySelect.length() + 20).append(querySelect);
		if (offset > 0) {
			buf.append(" limit ").append(offset).append(", ").append(limit);
		} else {
			buf.append(" limit ").append(limit);
		}
		return buf.toString();
	}

	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuilder(sql.length() + 20).append(sql).append(hasOffset ? " limit ?, ?" : " limit ?").toString();
	}

}
