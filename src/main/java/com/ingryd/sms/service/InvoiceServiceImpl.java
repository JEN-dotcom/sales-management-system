package com.ingryd.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)){
            invoiceRepository.deleteById(id);
            return ResponseEntity.ok("invoice successfully deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
