package com.reos79.rmm.utils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The utilitarian for JWT Token
 * @author reos79
 *
 */
@Component
public class JwtTokenUtil {
	
	/**
	 * Constant
	 */
	private static final String ROLE_USER = "ROLE_USER";
	/**
	 * Constant
	 */
	private static final String SOFT_TOKEN_JWT = "softtekJWT";
	/**
	 * Constant
	 */
	public static final String AUTHORITIES = "authorities";
	/**
	 * Constant
	 */
	public static final String CUSTOMER = "customer";
	
	/**
	 * The secret key
	 */
	private String secretKey;	
	/**
	 * The expiration time in millis
	 */
	private long expirationTimeMillis;

	/**
	 * Generate a JWT token
	 * @param username The username
	 * @param customerId The customer id
	 * @return The JWT token generated
	 */
	public String generateJwtToken(String username, Integer customerId) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(ROLE_USER);
		
		String token = Jwts
				.builder()
				.setId(SOFT_TOKEN_JWT)
				.setSubject(username)
				.claim(AUTHORITIES,
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.claim(CUSTOMER, customerId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return token;
	}
	
	/**
	 * Validates a JWT Token
	 * @param jwtToken The JWT token to validate
	 * @return The claims of the token if it's valid otherwise an exception
	 */
	public Claims validateJwtToken(String jwtToken) {
		return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Sets the secret key
	 * @param secretKey The secret key
	 */
	@Value("${jwt.secret}")
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Sets the expiration time in millis
	 * @param expirationTimeMillis The expiration time in millis
	 */
	@Value("${jwt.expiration}")
	public void setExpirationTimeMillis(long expirationTimeMillis) {
		this.expirationTimeMillis = expirationTimeMillis;
	}
	
}
