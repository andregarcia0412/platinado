package me.andregarcia0412.pipeline.shared.provider.storage;

import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

public interface StorageProviderPort {
   String upload(MultipartFile file, String prefix);
   String getPresignedUrl(String key, Duration ttl);
   void delete(String key);
}
