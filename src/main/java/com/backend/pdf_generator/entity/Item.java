package com.backend.pdf_generator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Quantity cannot be blank")
    @Column(nullable = false)
    private String quantity;

    @NotNull(message = "Rate cannot be null")
    @Column(nullable = false)
    private Double rate;

    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private Double amount;

    // freight_id is the foreign key from Item table to Freight Table
    // Is this important? Remove later if not.
//    @ManyToOne
//    @JoinColumn(name = "freight_id")
//    private Freight freight;
}
