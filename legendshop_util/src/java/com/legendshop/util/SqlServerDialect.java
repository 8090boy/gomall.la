package com.legendshop.util;

import java.sql.Types;

import org.hibernate.Hibernate;

public class SqlServerDialect extends org.hibernate.dialect.SQLServerDialect {

	public SqlServerDialect() {
		super();
		//Types.NVARCHAR for jdk 1.6
		registerHibernateType(Types.VARCHAR, Hibernate.STRING.getName());
	}
}