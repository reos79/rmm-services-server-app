package com.reos79.rmm.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reos79.rmm.web.controllers.validators.actions.Create;
import com.reos79.rmm.web.controllers.validators.actions.Update;

/**
 * A system device.
 * @author reos79
 *
 */
@Entity
@Table(name = "devices")
public class Device {

	/**
	 * The device id.
	 */
	@Valid
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Null(groups = {Create.class, Update.class})
	private Integer id;
	/**
	 * The device name.
	 */
	@Valid
	@NotBlank(groups = Create.class)
	@Size(min = 1, max = 128, groups =  {Create.class, Update.class})
	private String name;
	/**
	 * The device type.
	 */
	@Valid
	@Enumerated(EnumType.STRING)
	@NotNull(groups = Create.class)
	private DeviceType type;
	
	/**
	 * The device customer indentifier.
	 */
	@Valid
	@Null(groups = {Create.class, Update.class})
	@JsonIgnore
	private Integer customerId;
	
	/**
	 * Returns the device id.
	 * @return The device id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the device id.
	 * @param id The device id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the device name.
	 * @return The device name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the device name.
	 * @param name The device name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the device type.
	 * @return The device type.
	 */
	public DeviceType getType() {
		return type;
	}

	/**
	 * Sets the device type.
	 * @param type The device type.
	 */
	public void setType(DeviceType type) {
		this.type = type;
	}

	/**
	 * Returns the device customer identifier.
	 * @return The device customer identifier.
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the device customer identifier.
	 * @param customerId The device customer identifier.
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
