package com.ingryd.sms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.User;

import jakarta.transaction.Transactional;

@DataJpaTest
public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        user1 = entityManager.persistAndFlush(user1);
    }

    @Test
    public void saveInvoice() {
        Date date = new Date();
        Order order = new Order();
        order.setUser(user1);

        Invoice invoice = new Invoice();
        invoice.setDate(date);
        order = entityManager.persistAndFlush(order);
        invoice.setOrder(order);

        invoiceRepository.save(invoice);
    }

    @Test
    public void findInvoiceByDate() throws ParseException {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        Date startofDay = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endofDay = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        entityManager.persist(createTestInvoice());
        entityManager.persist(createTestInvoice());
        entityManager.persist(createTestInvoice());
        entityManager.persist(createTestInvoice());

        List<Invoice> invoices = invoiceRepository.findByDateBetween(startofDay, endofDay);
        assertEquals(4, invoices.size());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = dateFormat.parse("01-01-2050");
        Date endDate = dateFormat.parse("01-01-2051");

        List<Invoice> noInvoices = invoiceRepository.findByDateBetween(startDate, endDate);
        assertEquals(0, noInvoices.size());
    }

    @Test
    public void findById() {
        Invoice invoice1 = createTestInvoice();
        entityManager.persist(invoice1);
        Invoice found = invoiceRepository.findById(invoice1.getId()).get();
        assertEquals(invoice1.getId(), found.getId());
    }

    @Test
    @Transactional
    public void testDeleteByDateBefore() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date cutoffDate = sdf.parse("01-01-2023 00:00:00");
        Date beforeCutoff = sdf.parse("31-12-2022 10:00:00");
        Date afterCutoff = sdf.parse("01-01-2023 10:00:00");

        Invoice invoice1 = createTestInvoice(beforeCutoff);
        Invoice invoice2 = createTestInvoice(afterCutoff);

        entityManager.persist(invoice1);
        entityManager.persist(invoice2);

        invoiceRepository.deleteByDateBefore(cutoffDate);

        Optional<Invoice> deletedInvoice = invoiceRepository.findById(invoice1.getId());
        assertTrue(deletedInvoice.isEmpty());
    }

    public Invoice createTestInvoice() {
        Date date = new Date();
        Order order = new Order();
        order.setUser(user1);

        Invoice invoice = new Invoice();
        invoice.setDate(date);
        order = entityManager.persistAndFlush(order);
        invoice.setOrder(order);

        return invoice;
    }

    public Invoice createTestInvoice(Date date) {
        Invoice invoice = createTestInvoice();
        invoice.setDate(date);

        return invoice;
    }
}
