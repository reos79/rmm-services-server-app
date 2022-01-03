package com.reos79.rmm.services;

import com.reos79.rmm.model.Customer;

/**
 * The customer service
 * @author reos79
 *
 */
public interface CustomerService {

	/**
	 * Finds a customer by id
	 * @param customerId The customer id
	 * @return The customer found
	 */
	public Customer findCustomerById(Integer customerId);
	
	/**
	 * Updte a customer
	 * @param customer The customer to update
	 * @return The updated customer
	 */
	public Customer updateCustomer(Customer customer);
}
