package com.backend.pdf_generator.service;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.repository.FreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightService {
    @Autowired
    private FreightRepository freightRepository;


    public List<Freight> getAllFreight() {
        return freightRepository.findAll();
    }


    public Freight getFreightById(Long freightId) {
        return freightRepository.findById(freightId).orElseThrow(() -> new RuntimeException("Freight not found!"));
    }

    public Freight saveFreight(Freight freightObj) {
        return freightRepository.save(freightObj);
    }

//    public void updateFreight(Freight updatedFreightObj) {
//
//    }

    public void deleteFreight(Long freightId) {
        freightRepository.deleteById(freightId);
    }
}
