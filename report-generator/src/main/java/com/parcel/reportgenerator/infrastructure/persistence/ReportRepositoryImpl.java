package com.parcel.reportgenerator.infrastructure.persistence;

import com.parcel.reportgenerator.domain.ReportDocument;
import com.parcel.reportgenerator.domain.ReportId;
import com.parcel.reportgenerator.domain.ReportRepository;
import com.parcel.reportgenerator.infrastructure.persistence.minio.MinioReportRepository;
import com.parcel.reportgenerator.infrastructure.persistence.redis.RedisReport;
import com.parcel.reportgenerator.infrastructure.persistence.redis.RedisReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    private final RedisReportRepository redisReportRepository;
    private final MinioReportRepository minioReportRepository;

    @Override
    public void save(ReportDocument document) {
        minioReportRepository.saveFile(document)
                .ifPresent(value -> {
                    redisReportRepository.save(RedisReport.from(document));
                });
    }

    @Override
    public Optional<ReportId> findIdByContentHash(long contentHash) {
        return redisReportRepository.findByContentHash(contentHash)
                .map(report -> new ReportId(report.getReportId()));
    }

    @Override
    public Optional<ReportDocument> findById(ReportId reportId) {
        var content = minioReportRepository.getFile(reportId.uuid().toString());
        var metadata = redisReportRepository.findById(reportId.uuid());
        return content.flatMap(
                data -> metadata.map(
                        metaData -> new ReportDocument(reportId, metaData.getQuery(), data, metaData.getContentHash(), metaData.getCreationTime())
                )
        );
    }
}
