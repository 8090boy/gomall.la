/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.AbstractDataSource;

/**
 * RefreshableDataSource。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class RefreshableDataSource extends AbstractDataSource {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(RefreshableDataSource.class);

	/** The data source. */
	private DataSource dataSource;

	/**
	 * Sets the data source.
	 * 
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection()
	 */

	public Connection getConnection() throws SQLException {
		Connection con = dataSource.getConnection();
//		if(log.isDebugEnabled()){
//			log.debug("------> Get Connection {}", con);
//		}
		return con;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection(java.lang.String,
	 * java.lang.String)
	 */

	public Connection getConnection(String username, String password) throws SQLException {
	//	log.debug("getConnection(String username, String password) calling");
		return dataSource.getConnection(username, password);
	}

}
