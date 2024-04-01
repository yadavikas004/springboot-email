package com.springboot.email.service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements PdfService{

  @Override
  public ByteArrayInputStream createPdf() {

    var title = "Welcome to Pdf Generation";
    var content = "This is the example of pdf generation where I am generating pdf from learning.";

    var out = new ByteArrayOutputStream();

    var document = new Document();

    PdfWriter.getInstance(document,out);

    var footer = new HeaderFooter(true,new Phrase(" - Vikas_Learning"));
    footer.setAlignment(Element.ALIGN_CENTER);
    footer.setBorderWidthBottom(0);
    document.setFooter(footer);

    document.open();

    //PdfTable

    var titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,20, Color.BLACK);
    var titlePara = new Paragraph(title,titleFont);
    titlePara.setAlignment(Element.ALIGN_CENTER);
    document.add(titlePara);

    var paraFont = FontFactory.getFont(FontFactory.HELVETICA,18,Color.BLACK);
    var paragraph = new Paragraph(content);
    document.add(new Chunk("We added This text after learning tutorials"));
    document.add(paragraph);

    document.close();

    return new ByteArrayInputStream(out.toByteArray());
  }
}
