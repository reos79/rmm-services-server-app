package com.reos79.rmm.services;

import java.util.Set;

import com.reos79.rmm.model.Service;

/**
 * The service service
 * @author reos79
 *
 */
public interface ServiceService {
	
	/**
	 * Finds a service by id
	 * @param serviceId The service id
	 * @return The service found
	 */
	public Service findServiceById(Integer serviceId);

	/**
	 * Add a service to a customer
	 * @param customerId The customer id
	 * @param serviceId The service id
	 */
	public void addServiceToCustomer(Integer customerId, Integer serviceId);
	
	/**
	 * Remove a service from a customer
	 * @param customerId The customer id
	 * @param serviceId The service id
	 */
	public void removeServiceFromCustomer(Integer customerId, Integer serviceId);
	
	/**
	 * Finds the services belongings to a customer
	 * @param customerId The customer id
	 * @return The services found
	 */
	public Set<Service> findAllServicesByCustomer(Integer customerId);
}
