# Dynamic PDF Generation using Spring Boot

## Overview
This project is a **Spring Boot** application that provides a REST API for generating PDFs dynamically using a Java template engine (**Thymeleaf**). The API accepts structured input data in JSON format, generates a PDF based on this data, allows for the download of the PDF, and stores the PDF on local storage for future retrieval.

## Features
- REST API to accept input data and generate PDF dynamically.
- **PDF Download**: The API provides an option to download the generated PDF.
- **Local Storage**: Re-downloads previously generated PDFs when the same data is provided.
- Uses **Thymeleaf** as the template engine for generating PDFs.
- Can be tested using **Postman**.
- Supports **Test-Driven Development (TDD)** with proper test cases.

## API Endpoints
http://localhost:8080/freight
http://localhost:8080/generate
http://localhost:8080/download/1


### Request Body Example
```json
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
