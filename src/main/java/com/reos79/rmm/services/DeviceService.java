package com.reos79.rmm.services;

import com.reos79.rmm.model.Device;

/**
 * The device service
 * @author reos79
 *
 */
public interface DeviceService {

	/**
	 * Finds all devices by customer id
	 * @param customerId The customer id
	 * @return The devices found
	 */
	public Iterable<Device> findAll(Integer customerId);
	
	/**
	 * Find a device by id and customer id
	 * @param id The id
	 * @param customerId The customer id
	 * @return The device found
	 */
	public Device findById(Integer id, Integer customerId);
	
	/**
	 * Creates a device
	 * @param device The device data
	 * @return The device created
	 */
	public Device create(Device device);
	
	/**
	 * Updates a device
	 * @param device The device to update
	 * @return The device updated
	 */
	public Device update(Device device);
	
	/**
	 * Deletes a device
	 * @param id The device id
	 * @param customerId The customer id
	 */
	public void delete(Integer id, Integer customerId);
}
