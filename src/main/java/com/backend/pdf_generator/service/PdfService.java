package com.backend.pdf_generator.service;

import com.backend.pdf_generator.entity.Freight;
import com.backend.pdf_generator.repository.FreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfService {
    @Autowired
    private FreightRepository freightRepository;

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public Long generatePdf(String templateName, Freight freightObj) throws Exception {
        Freight savedFreight = freightRepository.save(freightObj);

        String filePath = "freights/freight_" + savedFreight.getFreightId() + ".pdf";
        File outputFile = new File(filePath);
        if (outputFile.exists()) return savedFreight.getFreightId();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("seller", savedFreight.getSeller());
        dataMap.put("sellerGstin", savedFreight.getSellerGstin());
        dataMap.put("sellerAddress", savedFreight.getSellerAddress());
        dataMap.put("buyer", savedFreight.getBuyer());
        dataMap.put("buyerGstin", savedFreight.getBuyerGstin());
        dataMap.put("buyerAddress", savedFreight.getBuyerAddress());
        dataMap.put("items", savedFreight.getItems());

        Context context = new Context();
        context.setVariables(dataMap);
        String htmlContent = templateEngine.process(templateName, context);

        try(OutputStream os = new FileOutputStream(outputFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
        }

        return savedFreight.getFreightId();
    }
}
