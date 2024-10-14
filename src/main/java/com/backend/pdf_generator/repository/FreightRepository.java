package com.backend.pdf_generator.repository;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FreightRepository extends JpaRepository<Freight, Long> {
    Optional<Freight> findBySellerGstinAndBuyerGstin(String sellerGstin, String buyerGstin);
}
