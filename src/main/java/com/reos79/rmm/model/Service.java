package com.reos79.rmm.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A service of the system
 * @author reos79
 *
 */
@Entity
@Table(name = "services")
public class Service {

	/**
	 * The service id
	 */
	@Id
	private Integer id;
	/**
	 * The service name
	 */
	private String name;
	/**
	 * The service prices
	 */
	@JsonIgnore
	@OneToMany
	@JoinColumn(name="service_id")
	private Collection<ServicePrice> prices;

	/**
	 * Returns the service id
	 * @return The service id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the service id
	 * @param id The service id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the service name
	 * @return The service name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the service name
	 * @param name The service name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the service prices
	 * @return The service prices
	 */
	public Collection<ServicePrice> getPrices() {
		return prices;
	}

	/**
	 * Sets the service prices
	 * @param prices The service prices
	 */
	public void setPrices(Collection<ServicePrice> prices) {
		this.prices = prices;
	}
}
