package com.backend.pdf_generator.repository;

import com.backend.pdf_generator.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreightRepository extends JpaRepository<Freight, Long> {
}
