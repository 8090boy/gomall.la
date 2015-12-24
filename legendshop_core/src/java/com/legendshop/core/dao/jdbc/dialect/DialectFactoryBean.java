/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.dao.jdbc.dialect;

import org.springframework.beans.factory.FactoryBean;

import com.legendshop.core.exception.ApplicationException;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.util.AppUtils;

/**
 * The Class DialectFactoryBean.
 */
public class DialectFactoryBean implements FactoryBean<Dialect> {

	/** The dialect. */
	private String dialect;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Dialect getObject() throws Exception {
		if (AppUtils.isBlank(dialect)) {
			throw new NotFoundException("dialect can not be empty");
		}
		Dialect result = null;
		if (dialect.equalsIgnoreCase("org.hibernate.dialect.MySQLDialect")) {
			result = new MySQLDialect();
		} else if (dialect.equalsIgnoreCase("org.hibernate.dialect.OracleDialect")) {
			result = new OracleDialect();
		} else if (dialect.equalsIgnoreCase("com.legendshop.util.SqlServerDialect")) {
			result = new MsSQLDialect();
		} else {
			throw new ApplicationException("Can not support this dialect ");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<?> getObjectType() {
		return Dialect.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * Sets the dialect.
	 * 
	 * @param dialect
	 *            the dialect to set
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

}
