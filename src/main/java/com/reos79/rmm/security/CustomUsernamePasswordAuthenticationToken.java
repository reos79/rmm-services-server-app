package com.reos79.rmm.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * User used in the auth context
 * @author reos79
 *
 */
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = 1L;
	
	/**
	 * The customer identifier
	 */
	private Integer customerId;

	/**
	 * The constructor of the class 
	 * @param principal The principal
	 * @param credentials The credentials
	 * @param authorities The authorities
	 */
	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	/**
	 * Returns the customer identifier
	 * @return The customer identifier
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer identifier
	 * @param customerId The customer identifier
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
}
