package com.ingryd.sms.controllers;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.repository.InvoiceRepository;
import com.ingryd.sms.repository.OrderRepository;
import com.ingryd.sms.service.InvoiceServiceImpl;
import com.ingryd.sms.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderService;


    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@PathVariable Date date, @RequestBody Order order){
        Invoice invoice = invoiceService.createInvoice(date, order);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices(){
        List<Invoice> invoiceList = invoiceService.getAllInvoices();
        if (!invoiceList.isEmpty())
            return ResponseEntity.ok(invoiceList);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Optional<Invoice>> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent())
            return ResponseEntity.ok(invoice);
        return ResponseEntity.notFound().build();

    }
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<String> deleteInvoice(Long id){
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("invoice successfully deleted");
    }

}
