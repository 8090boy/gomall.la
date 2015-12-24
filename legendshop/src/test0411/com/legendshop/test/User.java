/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.test;

/**
 * The Class User.
 */
public class User {
	
	/** The id. */
	private long id;
	
	/** The username. */
	private String username;
	
	/** The realname. */
	private String realname;
	
	/** The password. */
	private String password;
	
	public User(){
		
	}
	
	@Override
	public String toString() {
		return " id= " + id + ", name = " + username + ", username = " + username + ", realname = " + realname + ", password = " + password;
	}
	
	public User(long id, String username, String realname, String password, String memo) {
		super();
		this.id = id;
		this.username = username;
		this.realname = realname;
		this.password = password;
		this.memo = memo;
	}

	/** The memo. */
	private String memo;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the realname.
	 *
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}
	
	/**
	 * Sets the realname.
	 *
	 * @param realname the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	
	/**
	 * Sets the memo.
	 *
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
