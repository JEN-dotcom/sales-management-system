package com.ingryd.sms.service;

import java.util.Date;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;

public interface InvoiceService {

    public Invoice createInvoice(Date date, Order order);
}
