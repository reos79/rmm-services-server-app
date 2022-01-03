package com.reos79.rmm.exceptions;

/**
 * Exception thrown when an object already exists.
 * @author reos79
 *
 */
public class ObjectAlreadyExistException extends RuntimeException{

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -8881870578976717744L;

	/**
	 * Constructor of the class
	 * @param message The exception message.
	 */
	public ObjectAlreadyExistException(String message) {
		super(message);
	}
}
