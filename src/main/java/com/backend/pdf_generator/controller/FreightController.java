package com.backend.pdf_generator.controller;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.service.FreightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/freight")
public class FreightController {
    @Autowired
    private FreightService freightService;

    @GetMapping
    public ResponseEntity<List<Freight>> getAllFreights(){
        return new ResponseEntity<>(freightService.getAllFreight(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Freight> getFreightById(@PathVariable Long id) {
        return new ResponseEntity<>(freightService.getFreightById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Freight> createFreight(@Valid @RequestBody Freight freight) {
        return new ResponseEntity<>(freightService.saveFreight(freight), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFreightById(@PathVariable Long id) {
        freightService.deleteFreight(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
