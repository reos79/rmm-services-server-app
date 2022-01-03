package com.reos79.rmm.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;
import com.reos79.rmm.services.AuthenticationService;

/**
 * The authentication controller
 * @author reos79
 *
 */
@RestController
public class AuthenticationController {
	
	/**
	 * The authentication service
	 */
	private AuthenticationService authenticationService;

	/**
	 * Authenticates a user
	 * @param user The credentials
	 * @return An authorization token
	 */
	@PostMapping("/authenticate")
	public Token authenticate(@RequestBody User user) {
		try {
			return authenticationService.authenticateUser(user);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Sets the authentication service
	 * @param authenticationService The authentication service
	 */
	@Autowired
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}
