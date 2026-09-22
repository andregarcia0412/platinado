package me.andregarcia0412.platinado.shared.provider.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class StorageConfig {
    @Value("${storage.endpoint}")
    String endpoint;

    @Value("${storage.access-key}")
    String accessKey;

    @Value("${storage.secret-key}")
    String secretKey;

    private StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider
                .create(
                        AwsBasicCredentials.create(
                                accessKey,
                                secretKey
                        )
                );
    }

    @Bean
    protected S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider())
                .forcePathStyle(true)
                .build();
    }

    @Bean
    protected S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .credentialsProvider(credentialsProvider())
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build()
                )
                .build();
    }
}
