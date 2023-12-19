package com.linmo.oj.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置类
 * @author ljl
 * @since 2023-12-04 15:30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    // minio 文件访问地址
    private String url;
    // minio 服务地址
    private String endpoint;
    // minio 服务的accessKey
    private String accessKey;
    // minio 服务的secretKey
    private String secretKey;
    // 桶名称
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }


}

