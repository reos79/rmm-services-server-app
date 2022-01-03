package com.reos79.rmm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A proforma invoice of the system
 * @author reos79
 *
 */
public class ProformaInvoice {

	/**
	 * The proforma invoice detail
	 */
	private List<ProformaInvoiceLine> detail;
	
	/**
	 * The constructor of the class. 
	 * Initialize the detail.
	 */
	public ProformaInvoice() {
		detail = new ArrayList<>();
	}

	/**
	 * Returns the proforma invoice detail.
	 * @return The proforma invoice detail.
	 */
	public List<ProformaInvoiceLine> getDetail() {
		return detail;
	}

	/**
	 * Returns the proforma invoice total.
	 * @return the proforma invoice total.
	 */
	public Double getTotal() {
		double total = 0;
		for(ProformaInvoiceLine proformaInvoiceLine: detail) {
			total += (proformaInvoiceLine.getTotal());
		}
		return total;
	}
}
