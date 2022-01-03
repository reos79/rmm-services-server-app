package com.reos79.rmm.services.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.reos79.rmm.exceptions.ObjectAlreadyExistException;
import com.reos79.rmm.exceptions.ObjectNotFoundException;
import com.reos79.rmm.model.Customer;
import com.reos79.rmm.model.Service;
import com.reos79.rmm.repository.ServiceRepository;
import com.reos79.rmm.services.CustomerService;
import com.reos79.rmm.services.ServiceService;

/**
 * The service service implementation
 * @author reos79
 *
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{
	
	/**
	 * The customer service
	 */
	private CustomerService customerService;
	/**
	 * The service repository
	 */
	private ServiceRepository serviceRepository;
	
	/**
     * {@inheritDoc}
     */
	@Override
	public Service findServiceById(Integer serviceId) {
		Optional<Service> tempService = serviceRepository.findById(serviceId);
		if(tempService.isEmpty()) {
			throw new ObjectNotFoundException("Service not found");
		}
		return tempService.get();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void addServiceToCustomer(Integer customerId, Integer serviceId) {
		Customer customer = customerService.findCustomerById(customerId);
		Service service = findServiceById(serviceId);
		
		if(customer.getServices().contains(service)) {
			throw new ObjectAlreadyExistException("Service already added");
		}
		
		customer.getServices().add(service);
		customerService.updateCustomer(customer);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void removeServiceFromCustomer(Integer customerId, Integer serviceId) {
		Customer customer = customerService.findCustomerById(customerId);
		Service service = findServiceById(serviceId);
		
		if(!customer.getServices().contains(service)) {
			throw new ObjectNotFoundException("Service not added");
		}
		
		customer.getServices().remove(service);
		customerService.updateCustomer(customer);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Set<Service> findAllServicesByCustomer(Integer customerId) {		
		Customer customer = customerService.findCustomerById(customerId);
		return customer.getServices();
	}

	/**
	 * Sets the customer service
	 * @param customerService The customer service
	 */
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Sets the service repository
	 * @param serviceRepository The service repository
	 */
	@Autowired
	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}
}
