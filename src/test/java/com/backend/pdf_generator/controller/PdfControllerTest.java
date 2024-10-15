package com.backend.pdf_generator.controller;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.repository.FreightRepository;
import com.backend.pdf_generator.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PdfController.class)
class PdfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FreightRepository freightRepository;

    @MockBean
    private PdfService pdfService;

    private Freight freightObj;

    @BeforeEach
    void setup() {
        freightObj = new Freight();
        freightObj.setFreightId(1L);
        freightObj.setSeller("Test Seller");
        freightObj.setSellerGstin("29AABBCCDD121ZD");
        freightObj.setSellerAddress("New Delhi, India");
        freightObj.setBuyer("Vedant Computers");
        freightObj.setBuyerGstin("29AABBCCDD131ZD");
        freightObj.setBuyerAddress("New Delhi, India");
    }

    @Test
    void testGeneratePdf_WhenNewPdfIsGenerated() throws Exception {
        when(freightRepository.findBySellerGstinAndBuyerGstin(freightObj.getSellerGstin(), freightObj.getBuyerGstin()))
                .thenReturn(Optional.empty());
        when(pdfService.generatePdf(anyString(), any(Freight.class))).thenReturn(1L);

        mockMvc.perform(post("/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"seller\":\"XYZ Pvt. Ltd.\", \"sellerGstin\":\"29AABBCCDD121ZD\", \"sellerAddress\":\"New Delhi, India\", \"buyer\":\"Vedant Computers\", \"buyerGstin\":\"29AABBCCDD131ZD\", \"buyerAddress\":\"New Delhi, India\", \"items\":[{\"name\":\"Product 1\", \"quantity\":\"12 Nos\", \"rate\":123.00, \"amount\":1476.00}]}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("New PDF generated! \nPlease use the id: 1 download it"));


        verify(freightRepository, times(1)).findBySellerGstinAndBuyerGstin(anyString(), anyString());
        verify(pdfService, times(1)).generatePdf(anyString(), any(Freight.class));
    }

    @Test
    void testGeneratePdf_WhenPdfAlreadyExists() throws Exception {
        when(freightRepository.findBySellerGstinAndBuyerGstin(freightObj.getSellerGstin(), freightObj.getBuyerGstin()))
                .thenReturn(Optional.of(freightObj));

        mockMvc.perform(post("/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"seller\":\"XYZ Pvt. Ltd.\", \"sellerGstin\":\"29AABBCCDD121ZD\", \"sellerAddress\":\"New Delhi, India\", \"buyer\":\"Vedant Computers\", \"buyerGstin\":\"29AABBCCDD131ZD\", \"buyerAddress\":\"New Delhi, India\", \"items\":[{\"name\":\"Product 1\", \"quantity\":\"12 Nos\", \"rate\":123.00, \"amount\":1476.00}]}"))
                .andExpect(status().isOk())
                .andExpect(content().string("PDF already exists! \nPlease use the id: 1 to download it"));

        verify(freightRepository, times(1)).findBySellerGstinAndBuyerGstin(anyString(), anyString());
        verify(pdfService, times(0)).generatePdf(anyString(), any(Freight.class));
    }

    @Test
    void testGetAllFreights() throws Exception {
        mockMvc.perform(get("/freight"))
                .andExpect(status().isOk());

        verify(freightRepository, times(1)).findAll();
    }
}
