package com.parcel.reportgenerator.web;

import com.parcel.reportgenerator.client.PackageClient;
import com.parcel.reportgenerator.domain.Report;
import com.parcel.reportgenerator.drawer.ReportDrawerService;
import com.parcel.reportgenerator.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;
    private final ReportDrawerService drawerService;
    private final PackageClient packageClient;

    @PostMapping(value = "/report", produces = "application/json")
    @ResponseBody
    public String createReport(@RequestBody Report report) {
        var res = reportService.createReport(report);
        res.ifPresent(s -> log.info("Report created: {}", s));
        return res.orElse("");
    }

    @GetMapping("/report/{name}")
    public ResponseEntity<InputStreamResource> getReport(@PathVariable String name) {
        var content = reportService.getReport(name).orElseThrow();
        return getResponseEntity(content);
    }

    @NotNull
    private ResponseEntity<InputStreamResource> getResponseEntity(byte[] content) {
        var headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "test.pdf");
        var parcels = packageClient.getParcels();
        try (var stream = new ByteArrayOutputStream()) {
            try (var bufferedStream = new BufferedOutputStream(stream)) {
                drawerService.draw(parcels, bufferedStream);
                bufferedStream.flush();
                var bytes = stream.toByteArray();
                log.info("Request: {}", bytes);
                return ResponseEntity.status(HttpStatus.OK)
                        .contentLength(bytes.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers)
                        .body(new InputStreamResource(new ByteArrayInputStream(bytes)));
            }
        } catch (IOException e) {
            log.error("Error when generating report");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

}
