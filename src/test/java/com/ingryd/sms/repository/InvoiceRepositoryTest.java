package com.ingryd.sms.repository;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;

@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Test
    public void saveInvoice() {
        Date date = new Date();
        Order order = new Order();
        order.setUser(userRepository.findById(1L).orElseThrow());

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(date);
        order.setInvoice(invoice);
        invoice.setOrder(order);

        invoiceRepository.save(invoice);

       
    }

}
