package com.ingryd.sms.service;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.repository.InvoiceRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Test
    public void createInvoice() {
        Date date = new Date();

        Order order = new Order();
        order.setOrderId(1L);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        Product product1 = new Product();
        product1.setPrice(10.0);
        product1.setDiscount(5);
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(2);

        OrderItem orderItem2 = new OrderItem();
        Product product2 = new Product();
        product2.setPrice(20.0);
        product2.setDiscount(10);
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(3);

        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        order.setOrderItems(orderItems);

        double expectedOrderTotal = orderItems.stream()
                .mapToDouble(item -> invoiceService.calculateItemPrice(item))
                .sum();

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setOrder(order);
        expectedInvoice.setDate(date);
        expectedInvoice.setOrderTotal(expectedOrderTotal);
        expectedInvoice.setInvoice(invoiceService.invoiceTotal(order, expectedOrderTotal));

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(expectedInvoice);

        Invoice result = invoiceService.createInvoice(date, order);

        assertEquals(expectedInvoice, result);

        verify(invoiceRepository, times(1)).save(expectedInvoice);
    }

     @Test
    public void getInvoicesByDate() throws ParseException {
        // Create sample data
        String dateStringDDMMYYYY = "01-01-2023";

        // Create a list of invoices to be returned by the repository
        List<Invoice> expectedInvoices = new ArrayList<>();

        // Mock the repository to return the expected invoices
        when(invoiceRepository.findByDateBetween(any(), any())).thenReturn(expectedInvoices);

        // Call the getInvoicesByDate method
        List<Invoice> result = invoiceService.getInvoicesByDate(dateStringDDMMYYYY);

        // Verify the result
        assertEquals(expectedInvoices, result);

        // Verify that the repository method was called with the correct parameters
        verify(invoiceRepository, times(1)).findByDateBetween(any(), any());
    }
}
