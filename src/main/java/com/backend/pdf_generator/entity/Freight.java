package com.backend.pdf_generator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Map;

@Entity
public class Freight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freightId;


    private String seller;

    private String sellerGstin;

    private String sellerAddress;

    private String buyer;

    private String buyerGstin;

    private String buyerAddress;

    private List<ItemDetail> items;
}
