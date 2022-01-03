package com.reos79.rmm.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reos79.rmm.exceptions.ObjectNotFoundException;
import com.reos79.rmm.model.Customer;
import com.reos79.rmm.repository.CustomerRepository;
import com.reos79.rmm.services.CustomerService;

/**
 * The customer service implementations
 * @author reos79
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{
	
	/**
	 * The customer repository
	 */
	private CustomerRepository customerRepository;

	/**
     * {@inheritDoc}
     */
	@Override
	public Customer findCustomerById(Integer id) {
		Optional<Customer> tempCustomer = customerRepository.findById(id);
		if(tempCustomer.isEmpty()) {
			throw new ObjectNotFoundException("Customer not found");
		}
		return tempCustomer.get();
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public Customer updateCustomer(Customer customer) {
		Customer tempCustomer = findCustomerById(customer.getId());
		if(customer.getName() != null) {
			tempCustomer.setName(customer.getName());
		}
		if(customer.getServices() != null) {
			tempCustomer.setServices(customer.getServices());
		}
		return customerRepository.save(tempCustomer);
	}

	/**
	 * Sets the customer repository 
	 * @param customerRepository The customer repository
	 */
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	
}
