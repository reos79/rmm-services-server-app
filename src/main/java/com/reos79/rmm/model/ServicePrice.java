package com.reos79.rmm.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A service price by device type
 * @author reos79
 *
 */
@Entity
@Table(name = "services_price")
public class ServicePrice {

	/**
	 * The price identifier
	 */
	@Id
	private Integer id;
	/**
	 * The device type
	 */
	@Enumerated(EnumType.STRING)
	private DeviceType deviceType;
	/**
	 * The price value
	 */
	private Double price;

	/**
	 * Returns the price identifier
	 * @return The price identifier
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the price identifier
	 * @param id The price identifier
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the device type
	 * @return The device type
	 */
	public DeviceType getDeviceType() {
		return deviceType;
	}

	/**
	 * Sets the device type
	 * @param deviceType The device type
	 */
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * Returns the price value
	 * @return The price value
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price value
	 * @param price The price value
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

}
