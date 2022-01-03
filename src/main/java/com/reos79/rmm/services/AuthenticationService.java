package com.reos79.rmm.services;

import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;

/**
 * The authentication service
 * @author reos79
 *
 */
public interface AuthenticationService {

	/**
	 * Authenticate a user of the system
	 * @param user The user credentials
	 * @return An authorization token
	 */
	public Token authenticateUser(User user);
}
