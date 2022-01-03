package com.reos79.rmm.repository;

import org.springframework.data.repository.CrudRepository;

import com.reos79.rmm.model.Service;
/**
 * The service data access
 * @author reos79
 *
 */
public interface ServiceRepository extends CrudRepository<Service, Integer>{

}
