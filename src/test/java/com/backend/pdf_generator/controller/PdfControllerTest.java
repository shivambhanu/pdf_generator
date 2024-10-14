package com.backend.pdf_generator.controller;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.service.PdfService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PdfController.class)
public class PdfControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PdfService pdfService;

    private Freight freightData;

    @Test
    public void testGeneratePdf() throws Exception {
        Mockito.when(pdfService.generatePdf(Mockito.anyString(), Mockito.anyMap(), Mockito.anyString())).thenReturn(new File("freights/XYZ Pvt. Ltd._freight.pdf"));

        ResultActions result = mockMvc.perform(post("/freight/pdf/create").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                        "seller": "XYZ Pvt. Ltd.",
                        "sellerGstin": "29AABBCCDD121ZD",
                        "sellerAddress": "New Delhi, India",
                        "buyer": "Vedant Computers",
                        "buyerGstin": "29AABBCCDD131ZD",
                        "buyerAddress": "New Delhi, India",
                        "items": [
                            {
                                "name": "Product 1",
                                "quantity": "12 Nos",
                                "rate": 123.00,
                                "amount": 1476.00
                            }
                        ]
                }
            """));

        result.andExpect(status().isOk()).andExpect(content().string(""));
    }


    @Test
    public void testDownloadPdf() throws Exception {

    }
}

