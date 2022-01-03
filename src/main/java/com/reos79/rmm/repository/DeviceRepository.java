package com.reos79.rmm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.reos79.rmm.model.Device;

/**
 * The device data access.
 * @author reos79
 *
 */
public interface DeviceRepository extends CrudRepository<Device, Integer> {
	
	/**
	 * Finds the devices by the customer identifier
	 * @param customerId The customer identifier
	 * @return the devices found
	 */
	public Iterable<Device> findByCustomerId(Integer customerId);
	
	/**
	 * Find a device by its identifier and the customer identifier
	 * @param id Its identifier
	 * @param customerId The customer identifier
	 * @return the device found
	 */
	public Optional<Device> findByIdAndCustomerId(Integer id, Integer customerId);
	
}