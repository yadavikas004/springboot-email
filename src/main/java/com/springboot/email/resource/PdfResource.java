package com.springboot.email.resource;

import com.springboot.email.service.PdfService;
import java.io.ByteArrayInputStream;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
@AllArgsConstructor
public class PdfResource {

  @Autowired
  private PdfService pdfService;

  @GetMapping("/create")
  public ResponseEntity<?> createPdf(){  //InputStreamResource
    ByteArrayInputStream pdf = pdfService.createPdf();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-Disposition","inline;file=vikas.pdf");

    return ResponseEntity
        .ok()
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(pdf));
  }
}
