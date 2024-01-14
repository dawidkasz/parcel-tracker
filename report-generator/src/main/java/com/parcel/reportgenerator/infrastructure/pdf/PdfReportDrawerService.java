package com.parcel.reportgenerator.infrastructure.pdf;

import com.parcel.reportgenerator.domain.Parcel;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PdfReportDrawerService {

    public void draw(List<Parcel> parcels, OutputStream pdfDocument) {
        try (var document = new PDDocument()) {
            for (var parcel : parcels) {
                var page = new PDPage();
                document.addPage(page);
                try (var content = new PDPageContentStream(document, page)) {
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 12);
                    content.newLineAtOffset(100, 700);
                    content.showText("Id: %s".formatted(parcel.id()));
                    content.endText();
                    content.beginText();

                    content.newLineAtOffset(100, 680);
                    content.showText("Carrier: %s".formatted(parcel.carrier()));
                    content.endText();
                    content.beginText();
                    content.newLineAtOffset(100, 660);
                    content.showText("Status");

                    content.endText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

                    var startY = 640;
                    for (var status : parcel.history()) {
                        content.beginText();
                        content.newLineAtOffset(100, startY);
                        content.showText("Time: %s Description: %s State: %s".formatted(status.timestamp(), status.description(), status.status()));
                        content.endText();
                        startY -= 20;
                    }
                }
            }
            document.save(pdfDocument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
