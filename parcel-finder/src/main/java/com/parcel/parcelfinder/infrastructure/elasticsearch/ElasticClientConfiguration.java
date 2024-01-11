package com.parcel.parcelfinder.infrastructure.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.lang.NonNullApi;

@Configuration
public class ElasticClientConfiguration extends ElasticsearchConfiguration {
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String elasticUrl;
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticUrl)
                .build();
    }
}
