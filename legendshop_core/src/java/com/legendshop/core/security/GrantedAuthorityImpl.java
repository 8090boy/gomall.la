package com.legendshop.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

public final class GrantedAuthorityImpl implements GrantedAuthority, Comparable<GrantedAuthority> {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String role;

	public GrantedAuthorityImpl(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	public String getAuthority() {
		return role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GrantedAuthorityImpl) {
			return role.equals(((GrantedAuthorityImpl) obj).role);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.role.hashCode();
	}

	@Override
	public String toString() {
		return this.role;
	}

	public int compareTo(GrantedAuthority authority) {
		return authority.getAuthority().compareTo(this.role);
	}
}
