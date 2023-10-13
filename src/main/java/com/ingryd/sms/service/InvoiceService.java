package com.ingryd.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {

    public Invoice createInvoice(Date date, Order order);

    public List<Invoice> getAllInvoices();

    public Optional<Invoice> getInvoiceById(Long id);

    public ResponseEntity<String> deleteInvoice(Long id);
}
