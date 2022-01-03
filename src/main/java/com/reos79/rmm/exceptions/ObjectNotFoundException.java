package com.reos79.rmm.exceptions;

/**
 * Exception thrown when an object not found.
 * @author reos79
 *
 */
public class ObjectNotFoundException extends RuntimeException{

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -1029254006653896735L;

	/**
	 * The construcor of the class
	 * @param message The exception message.
	 */
	public ObjectNotFoundException(String message) {
		super(message);
	}

}
