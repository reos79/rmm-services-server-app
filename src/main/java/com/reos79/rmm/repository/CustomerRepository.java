package com.reos79.rmm.repository;

import org.springframework.data.repository.CrudRepository;

import com.reos79.rmm.model.Customer;

/**
 * The customer data access.
 * @author reos79
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}