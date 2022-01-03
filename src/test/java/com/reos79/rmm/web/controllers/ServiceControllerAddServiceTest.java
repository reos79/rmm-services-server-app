package com.reos79.rmm.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Optional;

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
import com.reos79.rmm.model.Service;
import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;
import com.reos79.rmm.repository.CustomerRepository;
import com.reos79.rmm.repository.ServiceRepository;
import com.reos79.rmm.web.filters.JwtAuthorizationFilter;


public class ServiceControllerAddServiceTest extends RmmServicesServerAppApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private Token token;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@BeforeEach
	public void setup() throws Exception {
		User user = new User();
		user.setUsername("user5");
		user.setPassword("pwd5");
		Object urlVariables = null;
		ResponseEntity<Token> entityToken = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		token = entityToken.getBody();
	}
	
	
	@Test
	public void addServiceOK() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", JwtAuthorizationFilter.PREFIX + token.getToken());
						
		HttpEntity request = new HttpEntity(headers);
				
		int servicesNumber = getServicesNumber();
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services/1", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(204, entity.getStatusCodeValue());
		assertEquals(servicesNumber+1, getServicesNumber());
	}
	
	@Test
	public void addServiceAlreadyAdded() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", JwtAuthorizationFilter.PREFIX + token.getToken());
						
		HttpEntity request = new HttpEntity(headers);
				
		int servicesNumber = getServicesNumber();
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services/2", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(204, entity.getStatusCodeValue());
		assertEquals(servicesNumber+1, getServicesNumber());
		
		entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services/2", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
		assertEquals(servicesNumber+1, getServicesNumber());
	}
	
	@Test
	public void addServiceNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
		int servicesNumber = getServicesNumber();
		
		Optional<Service> service = serviceRepository.findById(10020304);
		assertTrue(service.isEmpty());
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services/10020304", 
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(404, entity.getStatusCodeValue());
        assertEquals(servicesNumber, getServicesNumber());
	}
	
	@Test
	public void addServiceWrongCustomer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		int servicesNumber = getServicesNumber();
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/services/1",
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        assertEquals(servicesNumber, getServicesNumber());
	}
	
	@Test
	public void addServiceNoAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		int servicesNumber = getServicesNumber();
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services/1", 
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        assertEquals(servicesNumber, getServicesNumber());
	}
	
	@Test
	public void addServiceWrongAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer 2837489194792398jkshdkjfsd");
		
		int servicesNumber = getServicesNumber();
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services/1",
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        assertEquals(servicesNumber, getServicesNumber());
	}
	
	
	private int getServicesNumber() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Service[]> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/5/services", 
        		HttpMethod.GET, 
        		request, 
        		Service[].class);
        return entity.getBody().length;
	}
}
