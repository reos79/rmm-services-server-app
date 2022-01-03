package com.reos79.rmm.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reos79.rmm.exceptions.ObjectNotFoundException;
import com.reos79.rmm.model.Device;
import com.reos79.rmm.repository.DeviceRepository;
import com.reos79.rmm.services.DeviceService;

/**
 * The device service implementation
 * @author reos79
 *
 */
@Service
public class DeviceServiceImpl implements DeviceService{
	
	/**
	 * The device repository
	 */
	private DeviceRepository deviceRepository;

	/**
     * {@inheritDoc}
     */
	@Override
	public Iterable<Device> findAll(Integer customerId) {
		return deviceRepository.findByCustomerId(customerId);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Device findById(Integer id, Integer customerId) {
		Optional<Device> foundDevice = deviceRepository.findByIdAndCustomerId(id, customerId);
		
		if(foundDevice.isEmpty()) {
			throw new ObjectNotFoundException("Device not found.");
		}
		
		return foundDevice.get();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Device create(Device device) {
		return deviceRepository.save(device);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Device update(Device device) {
		Device tempDevice = findById(device.getId(), device.getCustomerId());				
		if(device.getName() != null) {
			tempDevice.setName(device.getName());
		}
		if(device.getType() != null) {
			tempDevice.setType(device.getType());
		}
		return deviceRepository.save(tempDevice);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void delete(Integer id, Integer customerId) {
		Device tempDevice = findById(id, customerId);
		deviceRepository.delete(tempDevice);
	}

	/**
	 * Sets the device repository
	 * @param deviceRepository The device repository
	 */
	@Autowired
	public void setDeviceRepository(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}
	
}
