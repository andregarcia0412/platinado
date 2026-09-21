package me.andregarcia0412.pipeline.shared.provider.cache;

import java.time.Duration;
import java.util.Optional;

public interface CacheProviderPort {
    <T> Optional<T> get(String key, Class<T> type);
    <T> void set(String key, T value, Duration ttl);
    void delete(String key);
}