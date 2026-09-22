package me.andregarcia0412.platinado.shared.provider.cache;

import java.time.Duration;
import java.util.Optional;

public interface ICacheProvider {
    <T> Optional<T> get(String key, Class<T> type);
    <T> void set(String key, T value, Duration ttl);
    <T> boolean setIfAbsent(String key, T value, Duration ttl);
    void delete(String key);
}