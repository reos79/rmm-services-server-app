package com.reos79.rmm.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * A system customer.
 * @author reos79
 *
 */
@Entity
@Table(name = "customers")
public class Customer {

	/**
	 * The customer id.
	 */
	@Id
	private Integer id;
	/**
	 * The customer name.
	 */
	private String name;
	/**
	 * The customer services
	 */
	@ManyToMany
	@JoinTable(
			  name = "customers_services", 
			  joinColumns = @JoinColumn(name = "customer_id"), 
			  inverseJoinColumns = @JoinColumn(name = "service_id"))
	private Set<Service> services;

	/**
	 * Returns the customer id.
	 * @return The customer id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the customer id.
	 * @param id The customer id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the customer name.
	 * @return The customer name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the customer name.
	 * @param name The customer name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Reurns the customer services.
	 * @return The customer services.
	 */
	public Set<Service> getServices() {
		return services;
	}

	/**
	 * Sets the customer services.
	 * @param services The customer services.
	 */
	public void setServices(Set<Service> services) {
		this.services = services;
	}
}
