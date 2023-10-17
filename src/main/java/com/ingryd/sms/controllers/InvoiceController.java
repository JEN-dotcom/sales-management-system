package com.ingryd.sms.controllers;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.repository.InvoiceRepository;
import com.ingryd.sms.repository.OrderRepository;
import com.ingryd.sms.service.InvoiceServiceImpl;
import com.ingryd.sms.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public ResponseEntity<List<Invoice>> getInvoicesByDateRange(
            @RequestParam(name = "date", required = false) String dateStringDDMMYYYY,
            @RequestParam(name = "startDate", required = false) String startDateStringDDMMYYYY,
            @RequestParam(name = "endDate", required = false) String endDateStringDDMMYYYY) throws ParseException {

        if (dateStringDDMMYYYY != null) {
            List<Invoice> invoiceList = invoiceService.getInvoicesByDate(dateStringDDMMYYYY);
            if (!invoiceList.isEmpty()) {
                return ResponseEntity.ok(invoiceList);
            }
        } else if (startDateStringDDMMYYYY != null && endDateStringDDMMYYYY != null) {
            List<Invoice> invoiceList = invoiceService.getInvoicesByDate(startDateStringDDMMYYYY, endDateStringDDMMYYYY);
            if (!invoiceList.isEmpty()) {
                return ResponseEntity.ok(invoiceList);
            }
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice != null)
            return ResponseEntity.ok(invoice);
        return ResponseEntity.notFound().build();

    }
    @GetMapping("/invoice/GrandTotal")
    public ResponseEntity<Double> getInvoiceGrandTotal(@RequestParam List<Invoice> invoiceList) {
        Double grandTotal = calculateInvoiceGrandTotal(invoiceList);
        return ResponseEntity.ok(grandTotal);
    }
    private Double calculateInvoiceGrandTotal(List<Invoice> invoiceList) {
        return invoiceList.stream()
                .mapToDouble(Invoice::getOrderTotal)
                .sum();
    }

    @GetMapping("/invoices/grandTotal")
    public ResponseEntity<String> getGrandTotal(@RequestParam List<Invoice> invoiceList, @RequestParam double grandTotal) {
        double calculatedGrandTotal = calculateGrandTotal(invoiceList);

        if (calculatedGrandTotal == grandTotal) {
            String response = "Grand Total: " + grandTotal;
            return ResponseEntity.ok(response);
        } else {
            String response = "Grand Total does not match the provided value. Calculated: " + calculatedGrandTotal;
            return ResponseEntity.badRequest().body(response);
        }
    }
    private double calculateGrandTotal(List<Invoice> invoiceList) {
        return invoiceList.stream()
                .mapToDouble(Invoice::getOrderTotal)
                .sum();
    }
    @DeleteMapping("/invoice/date")
    public ResponseEntity<String> deleteInvoicesBeforeDate(@RequestParam String dateStringDDMMYYYY) throws ParseException{

        try {invoiceService.deleteInvoicesBeforeDate(dateStringDDMMYYYY);
            return ResponseEntity.ok("All entries before " + dateStringDDMMYYYY + " successfully deleted.");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting entries.");
        }
    }
    @GetMapping("/generateInvoice")
    public String generateInvoice(@RequestParam double orderTotal) {
        Order order = fetchOrder();

        if (order != null) {
            String invoiceSummary = invoiceTotal(order, orderTotal);
            return invoiceSummary;
        } else {
            return "Order not found.";
        }
    }
    private Order fetchOrder() {

        return null;
    }

    private String invoiceTotal(Order order, double orderTotal) {
        StringBuilder result = new StringBuilder();

        result.append("Order ID: ").append(order.getOrderId()).append("\n");
        result.append("Order Date: ").append(order.getDate()).append("\n");
        result.append("Items:\n");

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            result.append("- Product: ").append(item.getProduct().getName()).append("\n");
            result.append("  Price per unit: ").append(item.getProduct().getPrice()).append("\n");
            result.append("  Quantity: ").append(item.getQuantity()).append("\n");
            result.append("  Discount Percent: ").append(item.getProduct().getDiscount()).append("%\n");
            result.append("  Total Price: ").append(calculateItemPrice(item)).append("\n");
        }

        result.append("Order Total: ").append(orderTotal).append("\n\n");

        return result.toString();
    }

    private double calculateItemPrice(OrderItem item) {
        double price = item.getProduct().getPrice();
        double discountPercent = item.getProduct().getDiscount();
        int quantity = item.getQuantity();
        double discountedPrice = price * (1.0 - (discountPercent / 100.0));

        DecimalFormat df = new DecimalFormat("0.00");
        String formattedValue = df.format(discountedPrice * quantity);
        return Double.parseDouble(formattedValue);
    }

}
