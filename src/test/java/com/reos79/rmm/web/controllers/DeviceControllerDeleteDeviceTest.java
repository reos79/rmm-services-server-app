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
import com.reos79.rmm.model.Device;
import com.reos79.rmm.model.DeviceType;
import com.reos79.rmm.model.Token;
import com.reos79.rmm.model.User;
import com.reos79.rmm.repository.DeviceRepository;
import com.reos79.rmm.web.filters.JwtAuthorizationFilter;


public class DeviceControllerDeleteDeviceTest extends RmmServicesServerAppApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private Token token;
	
	private Device deviceToDelete;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@BeforeEach
	public void setup() throws Exception {
		User user = new User();
		user.setUsername("user3");
		user.setPassword("pwd3");
		Object urlVariables = null;
		ResponseEntity<Token> entityToken = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		token = entityToken.getBody();
		
		Device device = new Device();
		device.setName("Device to delete");
		device.setType(DeviceType.MAC);
		device.setCustomerId(3);
		deviceToDelete = deviceRepository.save(device);
	}
	
	@Test
	public void deleteDeviceOK() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", JwtAuthorizationFilter.PREFIX + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
		Optional<Device> device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
		
        ResponseEntity<Device> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/3/devices/" + deviceToDelete.getId(), 
        		HttpMethod.DELETE, 
        		request, 
        		Device.class);
        assertEquals(204, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isEmpty());
	}
	
	@Test
	public void deleteDeviceNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		HttpEntity request = new HttpEntity(headers);
		
		Optional<Device> device = deviceRepository.findById(10020304);
		assertTrue(device.isEmpty());
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/3/devices/10020304", 
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(404, entity.getStatusCodeValue());
	}
	
	@Test
	public void deleteDeviceWrongCustomer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices/" + deviceToDelete.getId(),
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        
        device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
	}
	
	@Test
	public void deleteDeviceNoAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Optional<Device> device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/3/devices/" + deviceToDelete.getId(), 
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        
        device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
	}
	
	@Test
	public void deleteDeviceWrongAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer 2837489194792398jkshdkjfsd");
		
		Optional<Device> device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
		
		HttpEntity request = new HttpEntity(headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/3/devices/" + deviceToDelete.getId(),
        		HttpMethod.DELETE, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        
        device = deviceRepository.findById(deviceToDelete.getId());
		assertTrue(device.isPresent());
	}
}
