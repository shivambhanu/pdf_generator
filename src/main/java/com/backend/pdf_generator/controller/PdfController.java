package com.backend.pdf_generator.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.repository.FreightRepository;
import com.backend.pdf_generator.service.PdfService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;


@RestController
public class PdfController {
    private final PdfService pdfService;

    @Autowired
    private FreightRepository freightRepository;

    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String > generatePdf(@Valid @RequestBody Freight freightObj) {
        try {
            Optional<Freight> existingFreight = freightRepository.findBySellerGstinAndBuyerGstin(
                    freightObj.getSellerGstin(),
                    freightObj.getBuyerGstin()
            );
            if(existingFreight.isPresent()) {
                String response = "PDF already exists! \nPlease use the id: " + existingFreight.get().getFreightId() + " to download it";
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Long id = pdfService.generatePdf("freight", freightObj);
            String response = "New PDF generated! \nPlease use the id: " + id + " download it";
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) throws IOException {
        Freight freight= freightRepository.findById(id).orElseThrow(() -> new IOException("Invalid input id"));

        // Read the file from the given location.
        String filePath = "freights/freight_" + freight.getFreightId() + ".pdf";
        File pdfFile = new File(filePath);
        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "downloaded_freight_" + freight.getFreightId() + ".pdf");
        headers.setContentLength(pdfBytes.length);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

    }


    // Just for debugging
    @GetMapping("/freight")
    public ResponseEntity<List<Freight>> getAllFreights() {
        return new ResponseEntity<>(freightRepository.findAll(), HttpStatus.OK);
    }
}
