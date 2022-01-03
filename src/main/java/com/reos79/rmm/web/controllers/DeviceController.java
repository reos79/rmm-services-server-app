package com.reos79.rmm.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reos79.rmm.exceptions.ObjectNotFoundException;
import com.reos79.rmm.model.Device;
import com.reos79.rmm.services.DeviceService;
import com.reos79.rmm.web.controllers.validators.actions.Create;
import com.reos79.rmm.web.controllers.validators.actions.Update;

/**
 * The device controller
 * @author reos79
 *
 */
@RestController
public class DeviceController {
	
	/**
	 * The device service
	 */
	private DeviceService deviceService;

	/**
	 * Finds all devices by customer id
	 * @param customerId The customer id
	 * @return The devices found
	 */
	@GetMapping("/customers/{customerId}/devices")
	public Iterable<Device> findAllDevices(@PathVariable Integer customerId){
		try {
			return deviceService.findAll(customerId);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Finds a device by id
	 * @param customerId The customer id
	 * @param id The device id
	 * @return The device found
	 */
	@GetMapping("/customers/{customerId}/devices/{id}")
	public Device findDevice(@PathVariable Integer customerId,
			@PathVariable Integer id){
		try {
			return deviceService.findById(id, customerId);
		}catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Creates a new device
	 * @param customerId The customer id
	 * @param device The device data
	 * @return The device created
	 */
	@PostMapping("/customers/{customerId}/devices")
	@ResponseStatus(HttpStatus.CREATED)
	public Device createDevice(@PathVariable Integer customerId,
			@RequestBody @Validated(Create.class) Device device){
		try {
			device.setId(null);
			device.setCustomerId(customerId);
			return deviceService.create(device);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Updates a device
	 * @param customerId The customer id
	 * @param id The device id
	 * @param device The device data
	 * @return The device updated
	 */
	@PutMapping("/customers/{customerId}/devices/{id}")
	public Device updateDevice(@PathVariable Integer customerId,
			@PathVariable Integer id, @RequestBody @Validated(Update.class) Device device){
		try {
			device.setId(id);
			device.setCustomerId(customerId);
			return deviceService.update(device);
		}catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Deletes a device
	 * @param customerId The customer id
	 * @param id The device id
	 */
	@DeleteMapping("/customers/{customerId}/devices/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDevice(@PathVariable Integer customerId,
			@PathVariable Integer id){
		try {
			deviceService.delete(id, customerId);			
		}catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Sets the device service
	 * @param deviceService The device service
	 */
	@Autowired
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
}
