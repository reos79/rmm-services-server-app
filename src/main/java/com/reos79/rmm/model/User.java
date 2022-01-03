package com.reos79.rmm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A user of the system
 * @author reos79
 *
 */
@Entity
@Table(name = "users")
public class User {

	/**
	 * The username
	 */
	@Id
	private String username;
	/**
	 * The password
	 */
	private String password;
	/**
	 * The customer identifier
	 */
	private Integer customerId;

	/**
	 * Returns the username
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username
	 * @param username The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the password
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password
	 * @param password The password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the customer identifier
	 * @return The customer identifier
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer identifier
	 * @param customerId The customer identifier
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
