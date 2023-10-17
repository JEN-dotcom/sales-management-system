package com.ingryd.sms.service;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.error.ObjectNotFoundException;
import com.ingryd.sms.repository.InvoiceRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(Date date, Order order) {
        Invoice invoice = new Invoice();
        double orderTotal = invoiceTotal(order.getOrderItems());

        invoice.setOrder(order);
        invoice.setDate(date);
        invoice.setOrderTotal(orderTotal);
        invoice.setInvoice(invoiceTotal(order, orderTotal));

        return invoiceRepository.save(invoice);

    };

    @Override
    public List<Invoice> getInvoicesByDate(String dateStringDDMMYYYY) throws ParseException {

        Date specificDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
        LocalDateTime dateTime = specificDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime endDate = dateTime.with(LocalTime.MAX);
        Date endofDay = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        return invoiceRepository.findByDateBetween(specificDate, endofDay);
    }

    @Override
    public List<Invoice> getInvoicesByDate(String startDateStringDDMMYYYY, String endDateStringDDMMYYYY)
            throws ParseException {
        Date startOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);
        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);

        LocalDateTime endOfDate = endOfDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                .with(LocalTime.MAX);
        endOfDay = Date.from(endOfDate.atZone(ZoneId.systemDefault()).toInstant());

        return invoiceRepository.findByDateBetween(startOfDay, endOfDay);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Invoice does not exist"));

        return invoice;
    }

    @Override
    public double getGrandTotalOfInvoices(List<Invoice> invoiceList) {
        double total = invoiceList.stream()
                .mapToDouble(item -> item.getOrderTotal())
                .sum();
        return total;
    }

    @Override
    public String getGrandTotalOfInvoices(List<Invoice> invoiceList, double grandTotal) {
        StringBuilder result = new StringBuilder();

        for (Invoice invoice : invoiceList) {
            result.append(invoice.getInvoice());
        }

        result.append("Grand Total: ").append(grandTotal);
        return result.toString();
    }

    @Override
    public ResponseEntity<String> deleteInvoicesBeforeDate(String dateStringDDMMYYYY) throws ParseException {
        Date cutoffDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
        try {
            invoiceRepository.deleteByDateBefore(cutoffDate);
            return ResponseEntity.ok("All entries before " + dateStringDDMMYYYY + " successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting entries.");
        }
    }

    public String invoiceTotal(Order order, double orderTotal) {
        StringBuilder result = new StringBuilder();

        result.append("Order ID: ").append(order.getOrderId()).append("\n");
        result.append("Order Date: ").append(order.getDate()).append("\n");
        result.append("Items:\n");

        List<OrderItem> orderItems = order.getOrderItems();

//        result.append("Order ID: ").append(order.getOrderId()).append("\n");
//        result.append("Order Date: ").append(order.getDate()).append("\n");
//        result.append("Items:\n");
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

   public double calculateItemPrice(OrderItem item) {
        double price = item.getProduct().getPrice();
        double discountPercent = item.getProduct().getDiscount();
        int quantity = item.getQuantity();
        double discountedPrice = price * (1.0 - (discountPercent / 100.0));

        DecimalFormat df = new DecimalFormat("0.00"); // Format for two decimal places
        String formattedValue = df.format(discountedPrice * quantity);
        return Double.parseDouble(formattedValue); // Convert it back to double
    }

   public double invoiceTotal(List<OrderItem> orderItems) {
        double orderTotal = orderItems.stream()
                .mapToDouble(item -> calculateItemPrice(item))
                .sum();
        return orderTotal;
    }

}
