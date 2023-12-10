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
            var page = new PDPage();
            document.addPage(page);
            try (var content = new PDPageContentStream(document, page)) {
                for (var parcel : parcels) {
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 12);
                    content.newLineAtOffset(100, 700);
                    content.showText("test");
                    content.endText();
                }
            }
            document.save(pdfDocument);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
