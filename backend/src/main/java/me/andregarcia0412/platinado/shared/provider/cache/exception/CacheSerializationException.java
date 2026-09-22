package me.andregarcia0412.platinado.shared.provider.cache.exception;

public class CacheSerializationException extends RuntimeException {
    private final String key;

    public CacheSerializationException(String key, Throwable cause) {
        super("Failed to serialize value to cache's key: " + key, cause);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}