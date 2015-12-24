package com.legendshop.core.sso;

import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

/**
 * 密码由第三方提供，本系统不提供密码，直接通过验证。
 * 
 * @author Administrator
 * 
 */
public class SSOPasswordEncoder extends PlaintextPasswordEncoder {
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return true;
	}
}
