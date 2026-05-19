package com.example.resumescreening.util;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfUtil {

    public static String extractText(String filePath)
            throws IOException {

        File file = new File(filePath);

        PDDocument document = PDDocument.load(file);

        PDFTextStripper pdfStripper =
                new PDFTextStripper();

        String text = pdfStripper.getText(document);

        document.close();

        return text;
    }
}