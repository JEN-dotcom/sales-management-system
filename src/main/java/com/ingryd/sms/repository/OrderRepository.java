package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDateBetween(Date startOfDay, Date endOfDay, Pageable pageable);

    // @Modifying
    // @Transactional
    // @Query("DELETE FROM Order o WHERE o.date < :targetDate")
    // void deleteOrdersBeforeDate(Date targetDate);
}
