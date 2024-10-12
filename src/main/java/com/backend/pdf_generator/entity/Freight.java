package com.backend.pdf_generator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "freights")
public class Freight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freightId;

    @NotBlank(message = "Seller name cannot be blank")
    @Column(nullable = false)
    private String seller;

    @NotBlank(message = "Seller Gst cannot be blank")
    @Column(nullable = false)
    private String sellerGstin;

    @NotBlank(message = "Seller Address cannot be blank")
    @Column(nullable = false)
    private String sellerAddress;

    @NotBlank(message = "Buyer name cannot be blank")
    @Column(nullable = false)
    private String buyer;

    @NotBlank(message = "Buyer Gst cannot be blank")
    @Column(nullable = false)
    private String buyerGstin;

    @NotBlank(message = "Buyer Address cannot be blank")
    @Column(nullable = false)
    private String buyerAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "freight_id")
    private List<Item> items;
}
