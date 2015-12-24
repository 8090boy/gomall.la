/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * The Interface AuthService.
 */
public interface AuthService extends UserDetailsService {

	/**
	 * Gets the functions by roles.
	 * 
	 * @param roles
	 *            the roles
	 * @return the functions by roles
	 */
	public Collection<GrantedFunction> getFunctionsByRoles(Collection<? extends GrantedAuthority> roles);
}