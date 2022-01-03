package com.reos79.rmm.model;

/**
 * A line in the detail of the proforma invoice
 * @author reos79
 *
 */
public class ProformaInvoiceLine {

	/**
	 * The name of the line element
	 */
	private String elementName;
	/**
	 * The total price of the line element
	 */
	private Double total;

	/**
	 * Returns the name of the line element
	 * @return The name of the line element
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * Sets the name of the line element
	 * @param elementName The name of the line element
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * Returns the total price of the line element
	 * @return The total price of the line element
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * Sets the total price of the line element
	 * @param total The total price of the line element
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
}
