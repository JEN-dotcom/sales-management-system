package com.ingryd.sms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "invoice_date")
    private Date date;

    @Column(name = "order_total")
    private Double orderTotal;


    private String invoice;
}