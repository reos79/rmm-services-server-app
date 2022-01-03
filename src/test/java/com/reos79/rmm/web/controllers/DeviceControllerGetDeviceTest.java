package com.reos79.rmm.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.reos79.rmm.RmmServicesServerAppApplicationTests;
import com.reos79.rmm.model.Device;
import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;
import com.reos79.rmm.web.filters.JwtAuthorizationFilter;


public class DeviceControllerGetDeviceTest extends RmmServicesServerAppApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private Token token;
	
	@BeforeEach
	public void setup() throws Exception {
		User user = new User();
		user.setUsername("user1");
		user.setPassword("pwd1");
		Object urlVariables = null;
		ResponseEntity<Token> entityToken = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		token = entityToken.getBody();
	}
	
	@Test
	public void getDeviceOK() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", JwtAuthorizationFilter.PREFIX + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Device> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices/1", 
        		HttpMethod.GET, 
        		request, 
        		Device.class);
        assertEquals(200, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertEquals("Device1", entity.getBody().getName());
	}
	
	@Test
	public void getDeviceNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices/10", 
        		HttpMethod.GET, 
        		request, 
        		Object.class);
        assertEquals(404, entity.getStatusCodeValue());
	}
	
	@Test
	public void getDeviceWrongCustomer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices/1", 
        		HttpMethod.GET, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
	}
	
	@Test
	public void getDeviceNoAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices/1", 
        		HttpMethod.GET, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
	}
	
	@Test
	public void getDeviceWrongAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer 2837489194792398jkshdkjfsd");
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices/1", 
        		HttpMethod.GET, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
	}
}
