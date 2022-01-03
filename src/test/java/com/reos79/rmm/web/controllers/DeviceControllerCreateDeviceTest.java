package com.reos79.rmm.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.reos79.rmm.model.Device;
import com.reos79.rmm.model.DeviceType;
import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;
import com.reos79.rmm.repository.DeviceRepository;
import com.reos79.rmm.web.filters.JwtAuthorizationFilter;


public class DeviceControllerCreateDeviceTest extends RmmServicesServerAppApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private Token token;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@BeforeEach
	public void setup() throws Exception {
		User user = new User();
		user.setUsername("user2");
		user.setPassword("pwd2");
		Object urlVariables = null;
		ResponseEntity<Token> entityToken = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		token = entityToken.getBody();
	}
	
	@Test
	public void createDeviceOK() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", JwtAuthorizationFilter.PREFIX + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Device> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Device.class);
        assertEquals(201, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertEquals("Device101", entity.getBody().getName());
		Optional<Device> device = deviceRepository.findById(entity.getBody().getId());
		assertEquals("Device101", device.get().getName());
	}
	
	@Test
	public void createDeviceWithId() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"id\": 45,\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceWithCustomerIdOK() {
		//The customerId value is not taken from the request body.
		//Because of jsonignore
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"customerId\": 1,\r\n"
				+ "    \"name\": \"Device1001\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
		ResponseEntity<Device> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Device.class);
        assertEquals(201, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertNotNull(entity.getBody().getId());
		assertEquals("Device1001", entity.getBody().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, entity.getBody().getType());
		Optional<Device> device = deviceRepository.findById(entity.getBody().getId());
		assertEquals("Device1001", device.get().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, device.get().getType());
	}
	
	@Test
	public void createDeviceWithoutName() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceWithoutType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceWrongName() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101ksjdfhksdhfkaskjfhkjsdahfjkashdfkjhaskjfhkjsahkfhsakjfhksajhfkshfkhsakfjhsakjfhkjsafhkahfjkhsajhkfajkfhkajsdhfkjasdsdertyuiop\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceWrongType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"MAC_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceWrongCustomer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceNoAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
	}
	
	@Test
	public void createDeviceWrongAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer 2837489194792398jkshdkjfsd");
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/2/devices", 
        		HttpMethod.POST, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
	}
}
