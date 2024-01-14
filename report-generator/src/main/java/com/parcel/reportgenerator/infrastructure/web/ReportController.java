package com.parcel.reportgenerator.infrastructure.web;

import com.parcel.reportgenerator.domain.ReportRequest;
import com.parcel.reportgenerator.domain.ReportService;
import com.parcel.reportgenerator.infrastructure.persistence.minio.MinioReportRepository;
import com.parcel.reportgenerator.infrastructure.persistence.redis.RedisReport;
import com.parcel.reportgenerator.infrastructure.persistence.redis.RedisReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;
    private final MinioReportRepository minioReportRepository;
    private final RedisReportRepository redisReportRepository;

    @PostMapping(value = "/report", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> createReport(@RequestBody ReportRequest reportRequest) {
        var res = reportService.createReport(reportRequest);
        res.ifPresent(s -> log.info("Report created: {}", s));
        return res
                .map(reportName -> ResponseEntity.ok().body("/report/%s".formatted(reportName.uuid())))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/report/{name}")
    public ResponseEntity<InputStreamResource> getReport(@PathVariable("name") String name) {
        Optional<byte[]> report = minioReportRepository.getFile(name);
        return report
                .map(rep -> getResponseEntity(name, rep))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/reports")
    public List<ReportResponse> getReports(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        return redisReportRepository.findAll(pageable).stream()
                .map(ReportResponse::from)
                .toList();
    }

    private record ReportResponse(UUID id, String query, Instant creationTime) {
        public static ReportResponse from(RedisReport report) {
            return new ReportResponse(report.getReportId(), report.getQuery(), report.getCreationTime());
        }
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
