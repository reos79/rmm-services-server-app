package com.reos79.rmm.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.reos79.rmm.RmmServicesServerAppApplicationTests;
import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;


public class AuthenticationControllerTest extends RmmServicesServerAppApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void loginOk() throws Exception {
		User user = new User();
		user.setUsername("user1");
		user.setPassword("pwd1");
		Object urlVariables = null;
		ResponseEntity<Token> token = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		assertEquals(200, token.getStatusCodeValue());
		assertNotNull(token.getBody().getToken());
	}
	
	@Test
	public void loginFailedInvalidUser() throws Exception {
		User user = new User();
		user.setUsername("user");
		user.setPassword("pwd1");
		Object urlVariables = null;
		ResponseEntity<Token> token = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		assertEquals(401, token.getStatusCodeValue());
		assertNull(token.getBody());
	}
	
	@Test
	public void loginFailedInvalidPassword() throws Exception {
		User user = new User();
		user.setUsername("user1");
		user.setPassword("pwd");
		Object urlVariables = null;
		ResponseEntity<Token> token = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		assertEquals(401, token.getStatusCodeValue());
		assertNull(token.getBody());
	}
	
	@Test
	public void loginFailedInvalidUser2() throws Exception {
		User user = new User();
		user.setPassword("pwd1");
		Object urlVariables = null;
		ResponseEntity<Token> token = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		assertEquals(401, token.getStatusCodeValue());
		assertNull(token.getBody());
	}
	
	@Test
	public void loginFailedInvalidPassword2() throws Exception {
		User user = new User();
		user.setUsername("user1");
		Object urlVariables = null;
		ResponseEntity<Token> token = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		assertEquals(401, token.getStatusCodeValue());
		assertNull(token.getBody());
	}
}
