package com.parcel.reportgenerator.web;

import com.parcel.reportgenerator.domain.Report;
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

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;

    @PostMapping(value = "/report", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> createReport(@RequestBody Report report) {
        var res = reportService.createReport(report);
        res.ifPresent(s -> log.info("Report created: {}", s));
        return res
                .map(reportName -> ResponseEntity.ok().body("/report/%s".formatted(reportName)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/report/{name}")
    public ResponseEntity<InputStreamResource> getReport(@PathVariable String name) {
        var report = reportService.getReport(name);
        return report
                .map(rep -> getResponseEntity(name, rep))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @NotNull
    private ResponseEntity<InputStreamResource> getResponseEntity(String name, byte[] pdfReport) {
        var headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", name + ".pdf");
        return ResponseEntity.status(HttpStatus.OK)
                .contentLength(pdfReport.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(new InputStreamResource(new ByteArrayInputStream(pdfReport)));
    }

}
