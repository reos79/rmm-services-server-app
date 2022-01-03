package com.reos79.rmm.services.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.reos79.rmm.model.Device;
import com.reos79.rmm.model.DeviceType;
import com.reos79.rmm.model.ProformaInvoice;
import com.reos79.rmm.model.ProformaInvoiceLine;
import com.reos79.rmm.model.Service;
import com.reos79.rmm.model.ServicePrice;
import com.reos79.rmm.services.DeviceService;
import com.reos79.rmm.services.ProformaInvoiceService;
import com.reos79.rmm.services.ServiceService;

/**
 * The proforma invoice service implementation
 * @author reos79
 *
 */
@org.springframework.stereotype.Service
public class ProformaInvoiceServiceImpl implements ProformaInvoiceService{

	/**
	 * Constant
	 */
	private static final String DEVICES_ELEMENT_NAME = "Devices";
	/**
	 * Constant
	 */
	private static final String COST = "cost";
	
	/**
	 * The device service
	 */
	private DeviceService deviceService;
	/**
	 * The service service
	 */
	private ServiceService serviceService;
	/**
	 * The device price
	 */
	private Double devicePrice;
	
	/**
     * {@inheritDoc}
     */
	@Override
	public ProformaInvoice getProformaInvoice(Integer customerId) {		
		Iterable<Device> devices = deviceService.findAll(customerId);
		Set<Service> services = serviceService.findAllServicesByCustomer(customerId);
		
		ProformaInvoice proformaInvoice = new ProformaInvoice();
		List<ProformaInvoiceLine> detail = proformaInvoice.getDetail();
				
		Map<DeviceType, Integer> quantities = new HashMap<>();	
		int totalQuantity = 0;
		for(Device device: devices) {
			Integer quantity = quantities.getOrDefault(device.getType(), 0);
			quantities.put(device.getType(), ++quantity);
			totalQuantity++;
		}
		
		if(totalQuantity > 0) {
			ProformaInvoiceLine proformaInvoiceLineDevices = new ProformaInvoiceLine();
			proformaInvoiceLineDevices.setElementName(new StringBuilder(DEVICES_ELEMENT_NAME).append(' ').append(COST).toString());
			proformaInvoiceLineDevices.setTotal(devicePrice*totalQuantity);
			detail.add(proformaInvoiceLineDevices);
		}
		
		for(Service service: services) {
			Collection<ServicePrice> prices = service.getPrices();
			double total = 0;
			for(ServicePrice price: prices) {
				Integer quantity = quantities.getOrDefault(price.getDeviceType(), 0);
				if(quantity > 0) {
					total += (quantity * price.getPrice());
				}
			}
			
			if(total > 0) {
				ProformaInvoiceLine proformaInvoiceLine = new ProformaInvoiceLine();
				proformaInvoiceLine.setElementName(new StringBuilder(service.getName()).append(' ').append(COST).toString() );
				proformaInvoiceLine.setTotal(total);
				detail.add(proformaInvoiceLine);
			}
		}
		return proformaInvoice;
	}

	/**
	 * Sets the device service
	 * @param deviceService The device service
	 */
	@Autowired
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	/**
	 * Sets the service service
	 * @param serviceService The service service
	 */
	@Autowired
	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	/**
	 * The device price
	 * @param devicePrice
	 */
	@Value("${device.price}")
	public void setDevicePrice(Double devicePrice) {
		this.devicePrice = devicePrice;
	}
}
