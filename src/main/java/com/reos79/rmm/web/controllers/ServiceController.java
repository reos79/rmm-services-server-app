package com.reos79.rmm.web.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reos79.rmm.exceptions.ObjectAlreadyExistException;
import com.reos79.rmm.exceptions.ObjectNotFoundException;
import com.reos79.rmm.model.Service;
import com.reos79.rmm.services.ServiceService;

/**
 * The service controller
 * @author reos79
 *
 */
@RestController
public class ServiceController {

	/**
	 * The service service
	 */
	private ServiceService serviceService;
	
	/**
	 * Finds all customer services
	 * @param customerId The customer id
	 * @return The services found
	 */
	@GetMapping("/customers/{customerId}/services")
	public Set<Service> findServicesByCustomer(@PathVariable Integer customerId){
		try {
			return serviceService.findAllServicesByCustomer(customerId);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Adds a service to a customer
	 * @param customerId The customer id
	 * @param id The service id
	 */
	@PostMapping("/customers/{customerId}/services/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addService(@PathVariable Integer customerId, @PathVariable Integer id) {
		try {
			serviceService.addServiceToCustomer(customerId, id);
		}catch (ObjectAlreadyExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Removes a service from a customer
	 * @param customerId The customer id
	 * @param id The service id
	 */
	@DeleteMapping("/customers/{customerId}/services/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeService(@PathVariable Integer customerId, @PathVariable Integer id) {
		try {
			serviceService.removeServiceFromCustomer(customerId, id);
		}catch (ObjectNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Sets the service service
	 * @param serviceService The service service
	 */
	@Autowired
	public void setServiceService(ServiceService serviceService) {		
		this.serviceService = serviceService;		
	}	
	
}
