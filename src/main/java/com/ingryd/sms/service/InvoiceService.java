package com.ingryd.sms.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Invoice;

public interface InvoiceService {

    public Invoice getInvoiceById(Long id);

    public Invoice createInvoice(Date date, Order order);    

    public List<Invoice> getInvoicesByDate(String dateStringDDMMYYYY) throws ParseException;

    public List<Invoice> getInvoicesByDate(String startDateStringDDMMYYYY, String endDateStringDDMMYYYY) throws ParseException;

    public double getGrandTotalOfInvoices(List<Invoice> invoiceList);
   
    public String getGrandTotalOfInvoices(List<Invoice> invoiceList, double grandTotal);
    
   

    public ResponseEntity<String> deleteInvoicesBeforeDate(String dateStringDDMMYYYY) throws ParseException;
}
