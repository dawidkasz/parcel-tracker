package com.parcel.reportgenerator.drawer;

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
public class ReportDrawerService {

    public void draw(List<Parcel> parcels, OutputStream pdfDocument) {
        try (var document = new PDDocument()) {
            for (var parcel : parcels) {
                var page = new PDPage();
                document.addPage(page);
                try (var content = new PDPageContentStream(document, page)) {
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 12);
                    content.newLineAtOffset(100, 700);
                    content.showText("Origin: %s".formatted(parcel.origin()));
                    content.endText();
                    content.beginText();

                    content.newLineAtOffset(100, 680);
                    content.showText("Destination: %s".formatted(parcel.destination()));
                    content.endText();
                    content.beginText();
                    content.newLineAtOffset(100, 660);
                    content.showText("Weight: %s".formatted(parcel.weight()));
                    content.endText();
                    content.beginText();
                    content.newLineAtOffset(100, 640);
                    content.showText("Status");

                    content.endText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

                    var startY = 620;
                    for (var status : parcel.status()) {
                        content.beginText();
                        content.newLineAtOffset(100, startY);
                        content.showText("Time: %s State: %s".formatted(status.time(), status.text()));
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
