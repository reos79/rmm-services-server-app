package com.reos79.rmm.services;

import com.reos79.rmm.model.ProformaInvoice;

/**
 * The proforma invoice service
 * @author reos79
 *
 */
public interface ProformaInvoiceService {

	/**
	 * Generates a proforma invoice with the monthly cost by customer 
	 * @param customerId The customer id
	 * @return A proforma invoice with the monthly cost by customer
	 */
	public ProformaInvoice getProformaInvoice(Integer customerId);
}
