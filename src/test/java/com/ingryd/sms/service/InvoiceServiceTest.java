package com.ingryd.sms.service;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.repository.InvoiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InvoiceServiceImpl.class)
public class InvoiceServiceTest {

    @MockBean
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void testCreateInvoice(){

        Date invoiceDate = new Date();
        Order order = new Order();
        Invoice invoice = new Invoice();

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        Invoice createdInvoice = invoiceService.createInvoice(invoiceDate, order);

        verify(invoiceRepository, times(1)).save(any(Invoice.class));
        assertEquals(invoice, createdInvoice);
    }

}
