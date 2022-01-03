package com.reos79.rmm.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reos79.rmm.model.ProformaInvoice;
import com.reos79.rmm.services.ProformaInvoiceService;

/**
 * The profoma invoice controller
 * @author reos79
 *
 */
@RestController
public class ProformaInvoiceController {

	/**
	 * The proforma invoice service
	 */
	private ProformaInvoiceService proformaInvoiceService;
	
	/** 
	 * Generates the proforma invoice of the customer
	 * @param customerId The customer id
	 * @return The proforma invoice
	 */
	@GetMapping("/customers/{customerId}/proformaInvoice")
	public ProformaInvoice getProformaInvoice(@PathVariable Integer customerId) {
		try {
			return proformaInvoiceService.getProformaInvoice(customerId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Sets the proforma invoice service
	 * @param proformaInvoiceService The proforma invoice service
	 */
	@Autowired
	public void setProformaInvoiceService(ProformaInvoiceService proformaInvoiceService) {
		this.proformaInvoiceService = proformaInvoiceService;
	}
	
}
