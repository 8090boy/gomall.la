/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.legendshop.core.security.GrantedFunction;

/**
 * 扩展的Acegi用户 LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 * 
 */
public class UserDetail extends User {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5898134878028341628L;

	/** The functions. */
	private final Collection<GrantedFunction> functions;

	/** The user id. */
	private final String userId;

	private String ipAddress;

	public boolean equals(Object object) {
		if (object instanceof UserDetail) {
			if (this.userId.equals(((UserDetail) object).getUserId()))
				return true;
		}
		return false;
	}

	public int hashCode() {
		return this.userId.hashCode();
	}

	/**
	 * Instantiates a new user detail.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param enabled
	 *            the enabled
	 * @param accountNonExpired
	 *            the account non expired
	 * @param credentialsNonExpired
	 *            the credentials non expired
	 * @param accountNonLocked
	 *            the account non locked
	 * @param authorities
	 *            the authorities
	 * @param functions
	 *            the functions
	 * @param userId
	 *            the user id
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<GrantedAuthority> authorities, Collection<GrantedFunction> functions, String userId)
			throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.functions = functions;
		this.userId = userId;
	}

	/**
	 * Gets the functions.
	 * 
	 * @return the functions
	 */
	public Collection<GrantedFunction> getFunctions() {
		return functions;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
