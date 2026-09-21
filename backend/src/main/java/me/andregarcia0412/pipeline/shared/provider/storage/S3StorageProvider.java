package me.andregarcia0412.pipeline.shared.provider.storage;

import jakarta.annotation.PostConstruct;
import me.andregarcia0412.pipeline.shared.provider.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3StorageProvider implements StorageProviderPort{
    private final S3Client s3;
    private final S3Presigner presigner;
    private final String bucket;

    public S3StorageProvider(S3Client s3, S3Presigner presigner, @Value("${storage.bucket}") String bucket) {
        this.s3 = s3;
        this.presigner = presigner;
        this.bucket = bucket;
    }

    @PostConstruct
    void ensureBucket() {
        try {
            s3.headBucket(b -> b.bucket(bucket));
        } catch (NoSuchBucketException exception) {
            s3.createBucket(b -> b.bucket(bucket));
        }
    }

    @Override
    public String upload(MultipartFile file, String prefix) {
        String key = prefix + "/" + UUID.randomUUID() + extension(file.getOriginalFilename());
        try {
            s3.putObject(
                    b -> b
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType()),
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );
            return key;
        } catch (IOException exception) {
            throw new StorageException(exception.getMessage(), exception);
        }

    }

    @Override
    public String getPresignedUrl(String key, Duration ttl) {
        return presigner.presignGetObject(p -> p
                .signatureDuration(ttl)
                .getObjectRequest(g -> g.bucket(bucket).key(key))
        ).url().toString();
    }

    @Override
    public void delete(String key) {
        s3.deleteObject(b -> b
                .bucket(bucket)
                .key(key)
        );
    }

    private String extension(String fileName) {
        if(fileName == null || !fileName.contains("."))
            return "";

        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }
}
