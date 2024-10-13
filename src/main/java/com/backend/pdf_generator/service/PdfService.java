package com.backend.pdf_generator.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

@Service
public class PdfService {
    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public File createPdf(String templateName, Map<String, Object> data, String outputFilePath) throws Exception {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);


        File outputFile = new File(outputFilePath);
        try(OutputStream os = new FileOutputStream(outputFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
        }

        return outputFile;
    }
}
