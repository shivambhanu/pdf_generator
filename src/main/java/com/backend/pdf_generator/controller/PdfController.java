package com.backend.pdf_generator.controller;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/freight/pdf")
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody Freight freightData) {
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("seller", freightData.getSeller());
            dataMap.put("sellerGstin", freightData.getSellerGstin());
            dataMap.put("sellerAddress", freightData.getSellerAddress());
            dataMap.put("buyer", freightData.getBuyer());
            dataMap.put("buyerGstin", freightData.getBuyerGstin());
            dataMap.put("buyerAddress", freightData.getBuyerAddress());
            dataMap.put("items", freightData.getItems());

            String outputFilePath = "freights/" + freightData.getSeller() + "_freight.pdf";

            File pdf = pdfService.generatePdf("freight", dataMap, outputFilePath);

            return new ResponseEntity<>("PDF has been saved to: " + outputFilePath, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
