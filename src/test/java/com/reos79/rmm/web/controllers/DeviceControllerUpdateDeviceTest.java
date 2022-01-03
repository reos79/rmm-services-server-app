package com.reos79.rmm.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


public class DeviceControllerUpdateDeviceTest extends RmmServicesServerAppApplicationTests{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private Token token;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	private Device deviceToUpdate;
	
	@BeforeEach
	public void setup() throws Exception {
		User user = new User();
		user.setUsername("user4");
		user.setPassword("pwd4");
		Object urlVariables = null;
		ResponseEntity<Token> entityToken = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", user, Token.class, urlVariables );
		token = entityToken.getBody();
		
		Device device = new Device();
		device.setName("Device to update");
		device.setType(DeviceType.MAC);
		device.setCustomerId(4);
		deviceToUpdate = deviceRepository.save(device);
	}
	
	@Test
	public void updateDeviceOK() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", JwtAuthorizationFilter.PREFIX + token.getToken());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device10001\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
        ResponseEntity<Device> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(), 
        		HttpMethod.PUT, 
        		request, 
        		Device.class);
        assertEquals(200, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertNotNull(entity.getBody().getId());
		assertEquals("Device10001", entity.getBody().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, entity.getBody().getType());
		device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device10001", device.get().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, device.get().getType());
	}
	
	@Test
	public void updateDeviceNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(10020304);
		assertTrue(device.isEmpty());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device10001\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/10020304", 
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(404, entity.getStatusCodeValue());
	}
	
	@Test
	public void updateDeviceWithId() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"id\": 45,\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(), 
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
	
	@Test
	public void updateDeviceWithCustomerIdOK() {
		//The customerId value is not taken from the request body.
		//Because of jsonignore
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"customerId\": 1,\r\n"
				+ "    \"name\": \"Device10001\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
		ResponseEntity<Device> entity = restTemplate.exchange(
				"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Device.class);
		assertEquals(200, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertNotNull(entity.getBody().getId());
		assertEquals("Device10001", entity.getBody().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, entity.getBody().getType());
		device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device10001", device.get().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, device.get().getType());
	}
	
	@Test
	public void updateDeviceWithoutName() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
		ResponseEntity<Device> entity = restTemplate.exchange(
				"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Device.class);
        assertEquals(200, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertNotNull(entity.getBody().getId());
		assertEquals("Device to update", entity.getBody().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, entity.getBody().getType());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.WINDOWS_SERVER, device.get().getType());
	}
	
	@Test
	public void updateDeviceWithoutType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device10001\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
		ResponseEntity<Device> entity = restTemplate.exchange(
				"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Device.class);
        assertEquals(200, entity.getStatusCodeValue());
		assertNotNull(entity.getBody());
		assertNotNull(entity.getBody().getId());
		assertEquals("Device10001", entity.getBody().getName());
		assertEquals(DeviceType.MAC, entity.getBody().getType());
		device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device10001", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
	
	@Test
	public void updateDeviceWrongName() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101ksjdfhksdhfkaskjfhkjsdahfjkashdfkjhaskjfhkjsahkfhsakjfhksajhfkshfkhsakfjhsakjfhkjsafhkahfjkhsajhkfajkfhkajsdhfkjasdsdertyuiop\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(), 
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
	
	@Test
	public void updateDeviceWrongType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"MAC_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(400, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
	
	@Test
	public void updateDeviceWrongCustomer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token.getToken());
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/1/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
	
	@Test
	public void updateDeviceNoAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
	
	@Test
	public void updateDeviceWrongAuthorizationHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer 2837489194792398jkshdkjfsd");
		
		Optional<Device> device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Device101\",\r\n"
				+ "    \"type\": \"WINDOWS_SERVER\"\r\n"
				+ "}";
		
		HttpEntity request = new HttpEntity(requestBody, headers);
		
        ResponseEntity<Object> entity = restTemplate.exchange(
        		"http://localhost:" + port + "/customers/4/devices/" + deviceToUpdate.getId(),
        		HttpMethod.PUT, 
        		request, 
        		Object.class);
        assertEquals(403, entity.getStatusCodeValue());
        device = deviceRepository.findById(deviceToUpdate.getId());
		assertEquals("Device to update", device.get().getName());
		assertEquals(DeviceType.MAC, device.get().getType());
	}
}
