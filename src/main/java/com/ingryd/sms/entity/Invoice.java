package com.ingryd.sms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "invoice_date")
    private Date date;

    @Column(name = "order_total")
    private Double orderTotal;

    @Column(columnDefinition = "Text", length = 1000000)
    private String invoice;
}