package com.reos79.rmm.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;
import com.reos79.rmm.repository.UserRepository;
import com.reos79.rmm.services.AuthenticationService;
import com.reos79.rmm.utils.JwtTokenUtil;

/**
 * The authentication service implementation
 * @author reos79
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	/**
	 * The user repository
	 */
	private UserRepository userRepository;
	/**
	 * The token util
	 */
	private JwtTokenUtil jwtTokenUtil;
	/**
	 * The password encoder
	 */
	private PasswordEncoder passwordEncoder;

	/**
     * {@inheritDoc}
     */
	@Override
	public Token authenticateUser(User user) {
		Optional<User> foundUser = userRepository.findById(user.getUsername());
		
		if(foundUser.isEmpty()
				|| !passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
			throw new AuthenticationCredentialsNotFoundException("Authentication error.");
		}
		
		Token token = new Token();
		token.setToken(jwtTokenUtil.generateJwtToken(foundUser.get().getUsername(), foundUser.get().getCustomerId()));
		return token;
	}

	/**
	 * Sets the user repository
	 * @param userRepository The user repository
	 */
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Sets The token util 
	 * @param jwtTokenUtil the token util
	 */
	@Autowired
	public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	/**
	 * Sets the password encoder
	 * @param passwordEncoder The password encoder
	 */
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
}
