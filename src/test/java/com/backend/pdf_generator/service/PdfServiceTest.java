package com.backend.pdf_generator.service;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.repository.FreightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

    @Mock
    private FreightRepository freightRepository;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private PdfService pdfService;

    private Freight freightObj;

    @BeforeEach
    void setUp() {
        freightObj = new Freight(1L, "XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
                "Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
                Collections.emptyList());
    }

    @Test
    void testGeneratePdf_FileDoesNotExist() throws Exception {
        // Mock repository save
        when(freightRepository.save(freightObj)).thenReturn(freightObj);

        Long res = pdfService.generatePdf("template", freightObj);

        assertEquals(freightObj.getFreightId(), res);
        verify(freightRepository, times(1)).save(freightObj);
    }
}
