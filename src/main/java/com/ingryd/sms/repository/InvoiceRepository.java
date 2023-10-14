package com.ingryd.sms.repository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ingryd.sms.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByDateBetween(Date startOfDay, Date endOfDay);

    void deleteByDateBefore(Date cutoffDate);
}
