package com.parcel.reportgenerator.infrastructure.persistence.redis;

import com.parcel.reportgenerator.domain.ReportDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Report")
public class RedisReport implements Serializable {
    @Id
    @Indexed
    UUID reportId;

    @Indexed
    long contentHash;

    String query;

    @CreatedDate
    Instant creationTime;

    public static RedisReport from(ReportDocument document) {
        return new RedisReport(document.id().uuid(), document.contentHash(), document.description(), document.created());
    }
}
