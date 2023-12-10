package com.parcel.reportgenerator;

import com.parcel.reportgenerator.client.PackageClient;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
@Component
public class TestRunner implements CommandLineRunner {
    private final MinioClient reportStorageClient;
    private final static String BUCKET_NAME = "test-bucket";
    private final PackageClient packageClient;

    @Override
    public void run(String... args) throws Exception {
        genPdf();
    }

    private static void genPdf() {
        try (var document = new PDDocument()) {
            var page = new PDPage();
            document.addPage(page);
            try (var content = new PDPageContentStream(document, page)) {
                content.beginText();
                content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD), 12);
                content.newLineAtOffset(100, 700);
                content.showText("test");
                content.endText();
            }
            document.save("/home/mszawerd/test.pdf");
            var stream = new ByteArrayOutputStream();
            document.save(stream);
            log.info("DATA: {}", stream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void minioDemo() throws Exception {
        var res = reportStorageClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
        log.warn("Bucket: {}", res);
        var txt = "test2";
        try (var data = new ByteArrayInputStream(txt.getBytes())) {
            reportStorageClient.putObject(
                    PutObjectArgs.builder().bucket(BUCKET_NAME).object("test-obj").stream(
                                    data, txt.getBytes().length, -1)
                            .contentType("text/plain")
                            .build());
            log.info("Saved");
        }

        try (InputStream stream = reportStorageClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object("test-obj")
                        .build())) {
            var content = new Scanner(stream, StandardCharsets.UTF_8).useDelimiter("\\A");
            log.info("Content: {}", content.next());
        }
    }
}
