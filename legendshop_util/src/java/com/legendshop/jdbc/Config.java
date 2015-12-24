/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.jdbc;

import com.legendshop.util.DatasourcePropertiesFactory;
import com.legendshop.util.EnvironmentConfig;
import com.legendshop.util.FileConfig;

/**
 * The Class Config.
 */
public class Config {
	
	/** The _instance. */
	private static Config _instance = null;
	
	/** The db username. */
	private String dbUsername;
	
	/** The db passwd. */
	private String dbPasswd;
	
	/** The db connect string. */
	private String dbConnectString;
	
	/** The db driver. */
	private String dbDriver;
	
	/** The protected password. */
	private String protectedPassword;

	/**
	 * Instantiates a new config.
	 */
	private Config() {
		loadFromFile(FileConfig.JdbcFile);
	}

	/**
	 * Gets the single instance of Config.
	 *
	 * @return single instance of Config
	 */
	public static Config getInstance() {
		if (_instance == null) {
			_instance = new Config();
		}
		return _instance;
	}


	/**
	 * Gets the db connect string.
	 *
	 * @return the db connect string
	 */
	public String getDbConnectString() {
		return dbConnectString;
	}

	/**
	 * Gets the db passwd.
	 *
	 * @return the db passwd
	 */
	public String getDbPasswd() {
		return dbPasswd;
	}

	/**
	 * Gets the db username.
	 *
	 * @return the db username
	 */
	public String getDbUsername() {
		return dbUsername;
	}

	/**
	 * Sets the db username.
	 *
	 * @param dbUsername the new db username
	 */
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	/**
	 * Sets the db passwd.
	 *
	 * @param dbPasswd the new db passwd
	 */
	public void setDbPasswd(String dbPasswd) {
		this.dbPasswd = dbPasswd;
	}

	/**
	 * Sets the db connect string.
	 *
	 * @param dbConnectString the new db connect string
	 */
	public void setDbConnectString(String dbConnectString) {
		this.dbConnectString = dbConnectString;
	}


	/**
	 * Load from file.
	 *
	 * @param file the file
	 */
	public void loadFromFile(String file) {
		dbUsername = EnvironmentConfig.getInstance().getPropertyValue(file,"jdbc.username");
		
		dbConnectString =  EnvironmentConfig.getInstance().getPropertyValue(file,"jdbc.url");
		dbDriver = EnvironmentConfig.getInstance().getPropertyValue(file,"jdbc.driverClassName");
		protectedPassword = EnvironmentConfig.getInstance().getPropertyValue(file,"password.protected");
		if(getProtectedPassword()){
			try {
				dbPasswd = 	DatasourcePropertiesFactory.decode(  EnvironmentConfig.getInstance().getPropertyValue(file,"jdbc.password"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			dbPasswd =  EnvironmentConfig.getInstance().getPropertyValue(file,"jdbc.password");
		}
	}


	/**
	 * Gets the db driver.
	 *
	 * @return the dbDriver
	 */
	public String getDbDriver() {
		return dbDriver;
	}

	/**
	 * Sets the db driver.
	 *
	 * @param dbDriver the dbDriver to set
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	/**
	 * Gets the protected password.
	 *
	 * @return the protectedPassword
	 */
	public boolean getProtectedPassword() {
		if(protectedPassword == null){
			return false;
		}
		return "true".equalsIgnoreCase(protectedPassword.trim());
	}

	/**
	 * Sets the protected password.
	 *
	 * @param protectedPassword the protectedPassword to set
	 */
	public void setProtectedPassword(String protectedPassword) {
		this.protectedPassword = protectedPassword;
	}

}