package com.ingryd.sms.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Date date, Order order) {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceDate(date);

        return invoiceRepository.save(invoice);
    };
}
